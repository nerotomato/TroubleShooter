package com.nerotomato.thread.sync;

/**
 * Created by nero on 2021/4/10.
 */
public class MyThread2 {
    class Inner {
        private void m1() {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : Inner.m1()=" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }

        private void m2() {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : Inner.m2()=" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    private void m1(Inner inner) {
        synchronized (inner) {//使用对象锁
            inner.m1();
        }
    }

    private void m2(Inner inner) {
        synchronized (inner) {
            inner.m2();
        }
    }

    public static void main(String[] args) {
        MyThread2 myThread2 = new MyThread2();
        final Inner inner = myThread2.new Inner();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myThread2.m1(inner);
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myThread2.m2(inner);
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
