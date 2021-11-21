package com.nerotomato.threadpool;

import java.util.concurrent.*;

/**
 * Created by nerotomato on 2021/11/2.
 */
public class Custom_ThreadPool {
    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        System.out.println("availableProcessors is:" + availableProcessors);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(availableProcessors, 2 * availableProcessors, 5000, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        for (int i = 0; i < 20; i++) {
            int num = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("this is task:" + num);
                }
            });
        }
        //executor.shutdown();
        //executor.shutdownNow();

    }
}
