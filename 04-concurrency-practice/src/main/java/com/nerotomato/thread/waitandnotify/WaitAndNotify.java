package com.nerotomato.thread.waitandnotify;

/**
 * wait notify
 * Created by nero on 2021/4/9.
 */
public class WaitAndNotify {
    public static void main(String[] args) {
        Object lockObj = new Object();

        MethodClass methodClass = new MethodClass(lockObj);

        Thread t1 = new Thread(() -> {
            try {
                methodClass.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                methodClass.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
            try {
                methodClass.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
    }
}

class MethodClass {
    //定义产生最大量
    private final int MAX_COUNT = 20;
    private Object lockObj;
    int productCount = 0;

    public MethodClass(Object obj) {
        this.lockObj=obj;
    }

    public synchronized void produce() throws InterruptedException {

        //synchronized (this){
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":::run:::" + productCount);
                Thread.sleep(100);
                if (productCount >= MAX_COUNT) {
                    System.out.println("货舱已满，不必再生产！");
                    wait();
                    //lockObj.wait();
                } else {
                    productCount++;
                }
                notifyAll();
                //lockObj.notify();
            }
        //}
    }

    public synchronized void consume() throws InterruptedException {
        //synchronized (this){
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":::run:::" + productCount);
                Thread.sleep(100);
                if (productCount <= 0) {
                    System.out.println("货舱已无货，无法消费！");
                    wait();
                    //lockObj.wait();
                } else {
                    productCount--;
                }
                notifyAll();
                //lockObj.notify();
            }
        //}
    }
}
