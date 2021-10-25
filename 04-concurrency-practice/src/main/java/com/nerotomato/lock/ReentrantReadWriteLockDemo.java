package com.nerotomato.lock;

/**
 * 可重入读写锁
 * 可重入就是说某个线程已经获得某个锁，可以再次获取锁而不会出现死锁。
 * Created by nero on 2021/4/11.
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        final Count2 count2 = new Count2();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> count2.get(),"t1").start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(() -> count2.put(),"t2").start();
        }

    }
}
