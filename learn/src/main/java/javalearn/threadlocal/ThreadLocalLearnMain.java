package javalearn.threadlocal;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/2/7
 */
public class ThreadLocalLearnMain {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 使用继承ThreadLocal，子线程可以读取父线程的thread local值，但是无法覆盖父线程的thread local
         * 但是如果ThreadLoca中的值是对象应用，则会存在"逃逸"风险。
         */
        ThreadLocal<String> scalarValue = new InheritableThreadLocal<>();
        ThreadLocal<ValueInThreadLocal> value = new InheritableThreadLocal();
        value.set(new ValueInThreadLocal("wudale"));
        scalarValue.set("12345");

        new Thread(() -> {
            ValueInThreadLocal tlvalue = value.get();
            if (tlvalue != null) {
                System.out.println("value in child thread is:" + tlvalue.value);
                tlvalue.value = "wuxiaole";
                value.set(new ValueInThreadLocal("wuxiaoquan"));
            } else {
                System.out.println("value in child thread is null");
            }

            String strInParentTL = scalarValue.get();
            System.out.println("scalar string in parentThreadLocal is :" + strInParentTL);
            scalarValue.set("67890");
            strInParentTL = scalarValue.get();
            System.out.println("scalar string changed in child thread:" + strInParentTL);
        }).start();
        Thread.sleep(1000L);
        System.out.println("value in parent thread is:" + value.get().value);
        System.out.println("scalar string in parent thread is :" + scalarValue.get());
    }

    private static class ValueInThreadLocal {
        String value;

        public ValueInThreadLocal(String value) {
            this.value = value;
        }

    }
}
