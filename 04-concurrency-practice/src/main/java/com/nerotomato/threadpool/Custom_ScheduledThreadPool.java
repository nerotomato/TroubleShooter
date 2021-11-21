package com.nerotomato.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by nerotomato on 2021/11/8.
 */
public class Custom_ScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        System.out.println("===============================>");
        /*ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + " :延迟10秒执行");
            return 10;
        }, 10000, TimeUnit.MILLISECONDS);

        ScheduledFuture<Integer> scheduledFuture1 = scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + " :延迟3秒执行");
            return 3;
        }, 3000, TimeUnit.MILLISECONDS);

        ScheduledFuture<?> scheduledFuture2 = scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + " :延迟4秒执行");
            return 4;
        }, 4000, TimeUnit.MILLISECONDS);

        ScheduledFuture<?> scheduledFuture3 = scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + " :延迟2秒执行");
            return 2;
        }, 2000, TimeUnit.MILLISECONDS);

        System.out.println(scheduledFuture.get());
        System.out.println(scheduledFuture1.get());
        System.out.println(scheduledFuture2.get());
        System.out.println(scheduledFuture3.get());*/

        //System.out.println("This is te end.");

        /*scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);*/

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },0,1000,TimeUnit.MILLISECONDS);

        //scheduledThreadPoolExecutor.shutdown();
    }

}
