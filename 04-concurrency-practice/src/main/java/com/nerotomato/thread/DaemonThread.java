package com.nerotomato.thread;

/**
 * Created by nero on 2021/4/6.
 */
public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("name:" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t = new Thread(task);
        //设置守护线程
        t.setDaemon(true);
        //设置线程名称
        t.setName("thread-1");
        t.start();
        //主线程sleep2秒，否则主线程执行完后，守护线程就不会再执行
        Thread.sleep(2000);
    }
}
