package com.nerotomato.thread;

/**
 * Created by nero on 2021/4/6.
 */
public class RunnerMain {
    public static void main(String[] args) {
        Runner1 runner1 = new Runner1();
        Thread thread1 = new Thread(runner1);

        Runner2 runner2 = new Runner2();
        Thread thread2 = new Thread(runner2);

        thread1.start();
        thread2.start();

        thread2.interrupt();  // i = true

        System.out.println(Thread.activeCount());

        //Thread.currentThread().getThreadGroup().list();
        System.out.println(Thread.currentThread().getThreadGroup().getParent()
                .activeGroupCount());
        //Thread.currentThread().getThreadGroup().getParent().list();
    }
}
