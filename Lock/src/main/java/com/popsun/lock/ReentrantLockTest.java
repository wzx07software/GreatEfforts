package com.popsun.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 吴志祥
 * @create 2020-04-09 15:30
 */
public class ReentrantLockTest {
    public static void main(String args[]) {
        AtomicInteger i = new AtomicInteger();
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        Thread thread = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " try get lock!");
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock!");
                Thread.sleep(2000);
                while (i.get() == 0) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " await!");
                        condition.await();//会释放锁，被signal或signalAll唤醒的时候重新获取锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i.getAndDecrement();
                System.out.println("After decrement " + i.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println(Thread.currentThread().getName() + " released lock!");

            }
        });
        thread.start();

        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " try get lock!");
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock!");
                Thread.sleep(2000);
                if (i.get() == 0) {
                    condition.signalAll();
                }
                i.getAndIncrement();
                System.out.println("After increment " + i.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println(Thread.currentThread().getName() + " released lock!");
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }
}
