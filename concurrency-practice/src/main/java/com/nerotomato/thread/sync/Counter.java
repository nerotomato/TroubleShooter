package com.nerotomato.thread.sync;

/**
 * Created by nero on 2021/4/10.
 */
public class Counter {
    public final static int A = 10;

    public static int B = 10;

    private volatile int sum = 0;

    public void increment() {
        sum = sum + 1;
    }

    public int getSum() {
        return sum;
    }

    public static void main(String[] args) {
        int loop = 100000;
        //测试单线程
        Counter counter = new Counter();
        for (int i = 0; i < loop; i++) {
            counter.increment();
        }
        System.out.println(Thread.currentThread().getName() + " : " + counter.getSum());
        //测试多线程
        Counter counter2 = new Counter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < loop / 2; i++) {
                counter2.increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < loop / 2; i++) {
                counter2.increment();
            }
            counter2.increment();
        }, "t2");
        t1.start();
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        while (Thread.activeCount()>2){//当前线程的线程组中的数量>2
//            Thread.yield();
//        }
        System.out.println("multiple threads:" + counter2.getSum());
    }
}
