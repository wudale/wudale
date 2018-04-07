package rxjava;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.single.SingleJust;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/1/16
 */
public class HelloRxJava {

    static final Logger logger = LoggerFactory.getLogger(HelloRxJava.class);

    public static void main(String[] args) throws InterruptedException {
        Observable<String> just = Observable.just("wudale", "wuxiaole");
        logger.info("Class of Observable.just() : {}", just.getClass().getName());

        h("range observe on computation");
        Flowable.range(1, 10)
            .observeOn(Schedulers.computation())
            .map(v -> { logger.info("receive {} in thread: {}", v, Thread.currentThread().getName()); return v * v ; })
            .observeOn(Schedulers.single())
            .map(v -> { logger.info("receive on single Schedule thread:{}", Thread.currentThread().getName()); return v;})
            .blockingSubscribe(d -> logger.info("consuming: {}, Thread: {}", d, Thread.currentThread().getName()));

        h("range observe parallel");
        Flowable.range(1, 10)
            .parallel()
            .runOn(Schedulers.computation())
            .map(v -> { logger.info("parallel receive {} in thread: {}", v, Thread.currentThread().getName()); return v * v ; })
            .sequential() //这行代码放在 上面的map之前的效果 又是不一样的。
            .blockingSubscribe(d -> logger.info("consuming: {}, Thread: {}", d, Thread.currentThread().getName()));

        h("range flatmap on computation");
        Flowable.range(1, 10)
            .flatMap(v ->
                Flowable.just(v)
                    .subscribeOn(Schedulers.computation())
                    .map(w -> w * w)
            )
           .blockingSubscribe(d -> logger.info("consuming: {}, Thread: {}", d, Thread.currentThread().getName()));

        h("Non-dependent");
        Flowable.range(1, 10)
            .flatMapSingle(v -> Single.just(20))
            .blockingSubscribe(d -> logger.info("consuming: {}, Thread: {}", d, Thread.currentThread().getName()));

        h("Completable instead of Non-dependent");
        Flowable.range(1, 10)
            .ignoreElements()
            .andThen(Single.just(20))
            .subscribe(d -> logger.info("consuming: {}, Thread: {}", d, Thread.currentThread().getName()));

        h("ConcatWith SingleSource");
        Flowable.range(1, 10)
            .subscribeOn(Schedulers.computation())
            .concatWith(Single.defer(() -> {
                logger.info("concatWith SingleSource in thread: {}", Thread.currentThread().getName());
                return Single.just(20);
            }))
            .observeOn(Schedulers.computation())
            .map(v -> { logger.info("receive {} in thread: {}", v, Thread.currentThread().getName()); return v * v ; })
            .blockingSubscribe(d -> logger.info("consuming: {}, Thread: {}", d, Thread.currentThread().getName()));

        h("Observable.Create");
        Observable<Object> observableCreated = Observable.create(emitter -> {
            int count = 10;
            for (int i = 0; i < count; i++) {
                logger.info("Created observable subscribe on thread :{}", Thread.currentThread().getName());
                emitter.onNext(i);
                sleep(100);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
        Disposable subscribe = observableCreated.observeOn(Schedulers.computation())
            .subscribe(t -> logger.info("received count: {}, on thread:{}", t, Thread.currentThread().getName()));
        logger.info(subscribe.getClass().getName());

        h("Observable.from");
        Observable.just()


        h("other test");
        long esp = System.currentTimeMillis();
        Flowable.fromCallable(() -> {
            sleep(1000L);
            return "Done";
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println);

        Flowable.range(1, 10)
            .flatMap(v ->
                    Flowable.just(v)
                            .subscribeOn(Schedulers.computation())
                            .map(HelloRxJava::mutiple)
            )
            .blockingSubscribe(System.out::println);

        System.out.println("end of main");
        sleep(2000L);
    }

    private static void h(String start) {
        logger.info("=================={} start here =========================", start);
    }

    private static Integer mutiple(int w) throws InterruptedException {
        int v = w * w;
        sleep(2L);
        return v;
    }
}
