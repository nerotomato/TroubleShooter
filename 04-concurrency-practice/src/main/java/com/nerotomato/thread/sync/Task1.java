package com.nerotomato.thread.sync;

/**
 * Created by nero on 2021/4/10.
 */
public class Task1 implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " :synchronized loop" + i);
            }
        }
    }

    public static void main(String[] args) {
        Task1 task1 = new Task1();

        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task1, "t2");
        t1.start();
        t2.start();
    }
}
