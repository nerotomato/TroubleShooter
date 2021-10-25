package com.nerotomato.thread.sync;

/**
 * Created by nero on 2021/4/10.
 */
public class MyThread {
    public void m1() {
        synchronized (this) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void m2() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        Thread t1 = new Thread(() -> {
            myThread.m1();
        }, "t1");

        Thread t2 = new Thread(() -> {
            myThread.m2();
        }, "t2");
        t1.start();
        t2.start();
    }
}
