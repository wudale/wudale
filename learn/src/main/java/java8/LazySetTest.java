package java8;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/19
 */
public class LazySetTest {


    public static void main(String[] args) {


        AtomicInteger a1 = new AtomicInteger();

        long nano = System.nanoTime();
        for (int i = 0; i < 100000000; i++) {
            a1.set(i);
        }
        System.out.println(System.nanoTime() - nano);

        nano = System.nanoTime();
        AtomicInteger a2 = new AtomicInteger();
        for (int i = 0; i < 100000000; i++) {
            a2.lazySet(i);
        }
        System.out.println(System.nanoTime() - nano);
    }

}
