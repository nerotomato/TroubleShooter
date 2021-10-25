package com.nerotomato.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * 单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。
 * 如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。
 * 此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
 * Created by nero on 2021/4/11.
 */
public class NewSingleThreadExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            final int num = i;
            executorService.execute(() -> {
                System.out.println("Start:" + num);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("End:" + num);
            });
        }
        executorService.shutdown();
        System.out.println("Main thread end");
    }
}
