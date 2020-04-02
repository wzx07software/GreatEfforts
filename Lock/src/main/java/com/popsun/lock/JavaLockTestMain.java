package com.popsun.lock;

import com.popsun.lock.object.ReentrantLockThread;
import com.popsun.lock.object.SyncRunable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 吴志祥
 * @create 2020-03-23 16:42
 */
public class JavaLockTestMain {
    public static void main(String args[]) {
//        SyncRunable syncRunable = new SyncRunable();
//        syncRunable.setLock(true);
//        SyncRunable syncRunable1 = new SyncRunable();
//        syncRunable1.setLock(false);
//        new Thread(syncRunable).start();
//        new Thread(syncRunable1).start();
//        syncRunable.method3();
//        syncRunable1.method3();
        Log.info("主进程1运行结束");


        Lock lock = new ReentrantLock();
        ReentrantLockThread reentrantLockThread = new ReentrantLockThread();
        reentrantLockThread.setReentrantLock(lock);
        ReentrantLockThread reentrantLockThread1 = new ReentrantLockThread();
        reentrantLockThread1.setReentrantLock(lock);
        reentrantLockThread.start();
        reentrantLockThread1.start();
        Log.info("主进程2运行结束");
    }
}
