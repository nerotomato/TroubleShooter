package com.nerotomato.thread;

/**
 * Created by nero on 2021/4/6.
 */
public class ThreadA extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("这是线程A:" + Thread.currentThread().getName());
    }
}
