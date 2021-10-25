package com.nerotomato.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nero on 2021/4/10.
 */
public class Count {
    //可重入 非公平锁
    final ReentrantLock lock = new ReentrantLock();
    //可重入 公平锁
    //final ReentrantLock lock = new ReentrantLock(true);
    public void get() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " get begin");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + " get end");
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " put begin");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + " put end");
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
