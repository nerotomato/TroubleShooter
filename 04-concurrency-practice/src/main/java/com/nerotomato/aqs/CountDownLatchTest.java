package com.nerotomato.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。每当
 * 一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的
 * 线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。
 * <p>
 * Created by nerotomato on 2021/10/21.
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new SeeDoctorTask(countDownLatch)).start();
        new Thread(new QueueTask(countDownLatch)).start();
        //等待线程池中的2个任务执行完毕，否则一直
        countDownLatch.await();
        System.out.println("over,go home!");
    }

    static class SeeDoctorTask implements Runnable {
        private CountDownLatch countDownLatch;

        public SeeDoctorTask(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println("Start seeing doctor...");
                Thread.sleep(3000);
                System.out.println("Finish seeing doctor.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        }
    }

    static class QueueTask implements Runnable{
        private CountDownLatch countDownLatch;

        public QueueTask(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println("Waiting to buy the medicine.");
                Thread.sleep(5000);
                System.out.println("Bought the medicine.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (countDownLatch!=null){
                    countDownLatch.countDown();
                }
            }
        }
    }
}
