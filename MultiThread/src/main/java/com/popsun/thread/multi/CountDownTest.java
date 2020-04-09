package com.popsun.thread.multi;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 吴志祥
 * @create 2020-04-09 11:46
 */
public class CountDownTest {
    public static CountDownLatch countDownLatch = new CountDownLatch(0);

    public static void main(String args[]) throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final ThreadGroup group = new ThreadGroup("CountDownThreadGroup");
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private final String namePrefix = "CountDownThreadWorker-";
            public Thread newThread(Runnable r) {
                Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
                if (t.isDaemon())
                    t.setDaemon(false);
                if (t.getPriority() != Thread.NORM_PRIORITY)
                    t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        };
        AbstractExecutorService threadPoolExecutorE = (AbstractExecutorService) Executors.newSingleThreadExecutor(threadFactory);
        threadPoolExecutorE.submit(new DeamonCountDown());
        System.out.println("Await");
        countDownLatch.await();
        System.out.println("All data used!");
        threadPoolExecutorE.shutdown();
        System.out.println("End!");
    }


    private static class DeamonCountDown implements Runnable {
        public void run() {
            System.out.println(Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName());
            while (countDownLatch.getCount() > 0) {
                System.out.println("Deamon thread is running,remaining count is " + countDownLatch.getCount());
                System.out.println("Count down...");
                countDownLatch.countDown();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Deamon thread run end!");
        }
    }
}
