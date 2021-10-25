package com.nerotomato.lock;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试公平锁非公平锁
 * Created by nero on 2021/4/11.
 */
public class TestFair {
    public static volatile int race;
    //public static ReentrantLock lock = new ReentrantLock(true);
    //非公平锁比公平锁快100倍
    public static ReentrantLock lock = new ReentrantLock(false);
    private static final int THREADS_COUNT = 20;

    public static void increase() {
        lock.lock();
        race++; //变量自增操作
        lock.unlock();
    }

    public static void main(String[] args) {
        int count = Thread.activeCount();
        long now = System.currentTimeMillis();
        System.out.println(count);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        //定义20个线程
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }//等待所以累加线程都结束
        while (Thread.activeCount() > count) {
            Thread.yield();
        }
        System.out.println(lock.getClass().getName() + " ts = " + (System.currentTimeMillis() - now));
        System.out.println("race:" + race);
    }
}
