package com.popsun.thread.multi;

import java.util.concurrent.Semaphore;

/**
 * @author 吴志祥
 * @create 2020-04-09 14:05
 */
public class SemaphoreTest {

    public static void main(String args[]) throws InterruptedException {
        Semaphore semaphore = new Semaphore(4, true);
        semaphore.acquire();//如果没有可用的型号量，会一直等待，除非被interrupted
        System.out.println("Acquired semaphore");
        boolean result = semaphore.tryAcquire();//不管能否获取都是立即返回，通过结果判定是否取到信号量了
        System.out.println("Try acquired semaphore " + result);
        semaphore.acquireUninterruptibly();//如果没有可用的型号量，会一直等待，无法中断
        System.out.println("Acquired semaphore uninterruptibly");
        semaphore.release(5);//这样子会增加许可证的数量
        System.out.println("Released semaphore " + semaphore.availablePermits());

        Semaphore semaphoreT = new Semaphore(1);
        Thread thread = new Thread(() -> (new Service()).testMethod(semaphoreT));
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        //Thread.sleep(1000);
        System.out.println("Remained permits " + semaphoreT.availablePermits() + " " + semaphoreT.getQueueLength());
    }


    private static class Service {

        public void testMethod(Semaphore semaphore) {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " begin 1 timer=" + System.currentTimeMillis());
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " begin 2 timer=" + System.currentTimeMillis());
                semaphore.acquireUninterruptibly();
                System.out.println(Thread.currentThread().getName() + " begin 3 timer=" + System.currentTimeMillis());
                for (int i = 0; i < Integer.MAX_VALUE / 50; i++) {
                    new String();
                    Math.random();
                }
                System.out.println(Thread.currentThread().getName() + " end timer=" + System.currentTimeMillis());
                semaphore.release();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " in catch..." + semaphore.availablePermits());
                e.printStackTrace();
            } finally {
                //finally 如果在线程被销毁的情况下也是不会被执行的
                semaphore.release();
                System.out.println("Finally released!");
            }
        }
    }

}
