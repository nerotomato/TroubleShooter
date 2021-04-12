package com.nerotomato.thread;

/**
 * Created by nero on 2021/4/6.
 */
public class ThreadCount {
    public static void main(String[] args) {
        //System.out.println("system：" + Thread.currentThread().getThreadGroup().getParent());
        //Thread.currentThread().getThreadGroup().getParent().list();

        //System.out.println("main："+Thread.currentThread().getThreadGroup());
        Thread.currentThread().getThreadGroup().list();
    }
}
