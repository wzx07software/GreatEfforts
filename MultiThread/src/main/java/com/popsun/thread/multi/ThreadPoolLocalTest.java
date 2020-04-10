package com.popsun.thread.multi;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 吴志祥
 * @create 2020-04-10 17:32
 */
public class ThreadPoolLocalTest {
    public static void main(String args[]) {
        InheritableThreadLocal<AtomicInteger> threadLocal = new InheritableThreadLocal();
        threadLocal.set(new AtomicInteger(0));
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4, true),//new LinkedBlockingQueue<>(),//如果不设置大小，则为int最大值
                new ThreadFactory() {
                    private final ThreadGroup group = new ThreadGroup("Group-");
                    private final AtomicInteger threadNumber = new AtomicInteger(1);
                    private final String namePrefix = "WorkerThread-";

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
                        if (t.isDaemon())
                            t.setDaemon(false);
                        if (t.getPriority() != Thread.NORM_PRIORITY)
                            t.setPriority(Thread.NORM_PRIORITY);
                        return t;
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(false);//如果设置为true，核心线程在闲置超过一定的时间后也会自动释放
        //这里注意一下，线程池在核心线程耗尽后，不会立即扩展线程，而是先将任务放到阻塞队列中，只有阻塞队列满了，才会扩展新线程
        for (int i = 0; i < 10; i++) {
            Integer counter = Integer.valueOf(i);
            try {
                threadPoolExecutor.submit(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " content is " + counter.intValue());
                    threadLocal.get().incrementAndGet();
                });
            } catch (RejectedExecutionException e) {
                System.out.println("The thread pool is using out and cann't add thread " + i + " to run.");
            }
        }
        threadLocal.get().decrementAndGet();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadLocal.get());
        threadPoolExecutor.shutdown();
    }
}
