package com.nerotomato.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在java中可以通过锁和循环CAS的方式来实现原子操作
 * JVM中的CAS操作正是利用了处理器提供的CMPXCHG指令实现的。自
 * 旋CAS实现的基本思路就是循环进行CAS操作直到成功为止
 *
 * Created by nerotomato on 2021/10/25.
 */
public class Test_AtomicInteger {
    public static volatile int total = 0;
    private static Object object = new Object();
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                /*synchronized (object) {
                    for (int j = 0; j < 1000; j++) {
                        total++;
                    }
                }*/
                for (int j = 0; j < 1000; j++) {
                    /**
                     * 底层依赖Unsafe魔术类的compareAndSwapInt方法实现CAS原子操作
                     * compareAndSwapInt(var1, var2, var5, var5 + var4)
                     * var1当前原子对象
                     * var2当前原子对象内部的属性的在内存中的偏移量，即内存地址
                     * var5当前原子对象内部属性的值
                     * var4需要添加加的数值
                     * */
                    atomicInteger.getAndIncrement();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(atomicInteger.get());
    }

}
