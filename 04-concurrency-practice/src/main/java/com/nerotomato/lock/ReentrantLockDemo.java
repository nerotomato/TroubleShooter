package com.nerotomato.lock;

/**
 * 可重入锁：ReentrantLock 和 synchronized
 * 可重入就是说某个线程已经获得某个锁，可以再次获取锁而不会出现死锁。
 *
 * ReentrantLock 和 synchronized 不一样，需要手动释放锁
 * 所以使用 ReentrantLock的时候一定要手动释放锁，并且加锁次数和释放次数要一样
 * Created by nero on 2021/4/10.
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        final Count count = new Count();
        for (int i = 0; i < 20; i++) {
            new Thread() {
                @Override
                public void run() {
                    count.get();
                }
            }.start();
        }
        for (int i = 0; i < 20; i++) {
            new Thread(){
                @Override
                public void run() {
                    count.put();
                }
            }.start();
        }
    }
}
