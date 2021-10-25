package com.nerotomato.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 大小无限的线程池，此线程池支持定时以及周期性执行任务的需求。
 * Created by nero on 2021/4/11.
 */
public class NewScheduledThreadPoolDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(16);
        for (int i = 0; i < 100; i++) {
            int num = i;
            Runnable runnable = () -> {
                try {
                    System.out.println("start:" + num);
                    Thread.sleep(1000L);
                    System.out.println("end:" + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            // 5s后执行
            executorService.schedule(runnable,5, TimeUnit.SECONDS);
        }
        executorService.shutdown();
        System.out.println("Main thread end!");
    }
}
