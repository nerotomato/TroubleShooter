package com.nerotomato.thread;

import java.util.concurrent.Callable;

/**
 * Created by nero on 2021/4/6.
 */
public class ThreadC implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(500);
        System.out.println("这是线程C"+Thread.currentThread().getName());
        return "线程C";
    }
}
