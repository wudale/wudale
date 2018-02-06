package rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/1/16
 */
public class HelloWorld {

    public static void main(String[] args) throws InterruptedException {
        long esp = System.currentTimeMillis();
        Flowable.fromCallable(() -> {
            Thread.sleep(1000L);
            return "Done";
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println);

        Flowable.range(1, 10)
            .flatMap(v ->
                    Flowable.just(v)
                            .subscribeOn(Schedulers.computation())
                            .map(HelloWorld::mutiple)
            )
            .blockingSubscribe(System.out::println);

        System.out.println("end of main");
    }

    private static Integer mutiple(int w) throws InterruptedException {
        int v = w * w;
        Thread.sleep(2L);
        return v;
    }
}
