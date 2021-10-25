package com.nerotomato.aqs;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 字面意思是信号量的意思，它的作用是控制访问特定资源的线程数目，底层依
 * 赖AQS的状态State，是在生产当中比较常用的一个工具类
 * <p>
 * 资源访问，服务限流(Hystrix里限流就有基于信号量方式)
 * Created by nerotomato on 2021/10/21.
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        /**
         * 一次只有两个线程执行 acquire()，只有线程进行 release() 方法后
         * 才会有别的线程执行 acquire()。
         * */
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 5; i++) {
            new Thread(new SemaphoreTask(semaphore, "t-" + i
            )).start();
        }
    }

    static class SemaphoreTask extends Thread {
        Semaphore semaphore;

        public SemaphoreTask(Semaphore semaphore, String threadName) {
            this.semaphore = semaphore;
            this.setName(threadName);
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ":acquire() at time:" + System.currentTimeMillis());
                Thread.sleep(2000);
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + ":release() at time:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
