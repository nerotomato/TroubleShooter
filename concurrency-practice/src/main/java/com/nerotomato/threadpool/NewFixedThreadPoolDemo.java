package com.nerotomato.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到
 * 最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
 * Created by nero on 2021/4/11.
 */
public class NewFixedThreadPoolDemo {
    public static void main(String[] args) {
        /**
         * 创建固定线程池的经验
         * 不是越大越好，太小肯定也不好：
         * 假设核心数为 N
         * 1、如果是 CPU 密集型应用，则线程池大小设置为 N 或 N+1
         * 2、如果是 IO 密集型应用，则线程池大小设置为 2N 或 2N+2
         * */
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 100; i++) {
            int num = i;
            executorService.execute(() -> {
                try {
                    System.out.println("Start:" + num);
                    Thread.sleep(1000);
                    System.out.println("End:" + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        System.out.println("Main thread end");

    }
}
