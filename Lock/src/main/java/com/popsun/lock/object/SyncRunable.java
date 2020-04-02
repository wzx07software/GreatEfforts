package com.popsun.lock.object;

import com.popsun.lock.Log;

/**
 * @author 吴志祥
 * @create 2020-03-23 16:43
 */
public class SyncRunable implements Runnable {

    private boolean lock;

    /**
     * 这里的synchronized锁住的是类
     *
     * @throws InterruptedException
     */
    public synchronized static void lock() throws InterruptedException {
        Log.info("我开始全局锁定了！");
        Thread.sleep(5000);
        Log.info("我结束全局锁定了！");

    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public void run() {
        try {
            if (lock) {
                method1();
            } else {
                lock = true;
                method2();
            }
            lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里的方法上锁住的是这个类的实例对象
     *
     * @throws InterruptedException
     */
    public synchronized void method1() throws InterruptedException {
        Thread.sleep(5000);
        Log.info("method1");
        if (lock) {
            lock = false;
            method2();
        }
    }

    public synchronized void method2() throws InterruptedException {
        Thread.sleep(5000);
        Log.info("method2");
        if (lock) {
            lock = false;
            method1();
        }
    }

    /**
     * 这里的方法上锁住的是这个类的实例对象
     *
     */
    public void method3() {
        synchronized (this) {
            Log.info("我获得了锁");
        }
    }
}
