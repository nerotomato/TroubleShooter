
package com.nerotomato.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncCount {

    private AtomicInteger num = new AtomicInteger(0);
    //private int num = 0;
    //非公平锁比公平锁快的多
    private Lock lock = new ReentrantLock(false);
    //公平锁慢，测试过程发现线程还没执行完，主线程就跑完了
    //private Lock lock = new ReentrantLock(true);
    public int add() {
        try {
            lock.lock();
            return num.incrementAndGet();
            //return num++;
        } finally {
            lock.unlock();
        }
    }

    /*public int getNum() {
        return num;
    }*/
    public AtomicInteger getNum() {
        return num;
    }
}
