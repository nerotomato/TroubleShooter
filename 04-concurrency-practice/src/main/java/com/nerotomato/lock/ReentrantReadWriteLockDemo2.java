package com.nerotomato.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock 管理一组锁，一个读锁，一个写锁。
 * 读锁可以在没有写锁的时候被多个线程同时持有，写锁是独占的。
 * 所有读写锁的实现必须确保写操作对读操作的内存影响。每次只能有一个写线程，但是同
 * 时可以有多个线程并发地读数据。ReadWriteLock 适用于读多写少的并发情况。
 * <p>
 * Created by nero on 2021/4/11.
 */
public class ReentrantReadWriteLockDemo2 {
    private final Map<String, Object> map = new HashMap();

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public Object readWrite(String key) {
        Object value = null;
        System.out.println("1.首先开启读锁去缓存中取数据");
        rwLock.readLock().lock();
        value = map.get(key);

        try {
            if (value == null) {
                System.out.println("2.数据不存在，则释放读锁，开启写锁");
                //释放读锁
                rwLock.readLock().unlock();
                //数据不存在，开启写锁
                rwLock.writeLock().lock();
                value = "apple";
            }
        } finally {
            //释放写锁
            System.out.println("3.释放写锁");
            rwLock.writeLock().unlock();
        }
        return value;
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockDemo2 demo2 = new ReentrantReadWriteLockDemo2();
        Object result = demo2.readWrite("computer");
        System.out.println(result);
    }

}
