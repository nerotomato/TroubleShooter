package com.nerotomato.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by nero on 2021/4/11.
 */
public class ExeceptionDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        try {
            Future<Double> future = executorService.submit(() -> {
                throw new RuntimeException("executorService.submit()");
            });

            double b = future.get();
            System.out.println(b);

        } catch (Exception ex) {
            System.out.println("catch submit");
            ex.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("Main Thread End!");
    }
}
