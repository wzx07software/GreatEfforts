package com.popsun.thread.multi;

/**
 * @author 吴志祥
 * @create 2020-04-10 13:16
 */
public class ThreadLocalTest {
    /**
     * 这里的测试案例有问题,我想这么ThreadLocal内部使用弱引用，但是ThreadLocal都是被强引用，我发在gc的时候回收掉
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {

        System.out.println("Before system gc!");

        ThreadLocal<AppleInner> threadLocal = new ThreadLocal<>();
        threadLocal.set(new AppleInner("RedApple"));
        System.out.println(threadLocal.get());

        AppleInner greenApple = new AppleInner("GreenApple");
        ThreadLocal<AppleInner> threadLocal1 = new ThreadLocal<>();
        threadLocal1.set(greenApple);
        System.out.println(threadLocal1.get());

        System.gc();
        Thread.sleep(5000);

        System.out.println("After system gc!");
        System.out.println(threadLocal.get());
        System.out.println(threadLocal1.get());


    }

    static class AppleInner {
        String name;

        public AppleInner(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "This apple is " + name + "!";
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Apple " + name + " has been cleaned!");
        }
    }
}
