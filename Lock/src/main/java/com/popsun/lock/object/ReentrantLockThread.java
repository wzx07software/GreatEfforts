package com.popsun.lock.object;

import com.popsun.lock.Log;

import java.util.concurrent.locks.Lock;

/**
 * @author 吴志祥
 * @create 2020-03-23 17:23
 */
public class ReentrantLockThread extends Thread {

    //重入锁
    private Lock reentrantLock;

    public Lock getReentrantLock() {
        return reentrantLock;
    }

    public void setReentrantLock(Lock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        method1();
    }

    public void method1() {
        try {
            Log.info(Thread.currentThread().getName() + " is getting lock1。。。。。。。。。。。。。。。。。。");
            reentrantLock.lock();
            Log.info(Thread.currentThread().getName() + " runed method1");
            Thread.sleep(2000);
            method2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void method2() {
        try {
            Log.info(Thread.currentThread().getName() + " is getting lock2。。。。。。。。。。。。。。。。。。");
            reentrantLock.lock();
            Log.info(Thread.currentThread().getName() + " runed method2");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}
