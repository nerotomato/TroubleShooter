package com.nerotomato.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 * 栅栏屏障，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程
 * 到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行。
 * CyclicBarrier默认的构造方法是CyclicBarrier（int parties），其参数表示屏障拦截的线
 * 程数量，每个线程调用await方法告CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
 * <p>
 * Created by nerotomato on 2021/10/25.
 */
public class CycliBarrierTest {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(11,() -> {
            System.out.println("============> ");
        });
        for (int i = 0; i < 10; i++) {
            new Thread(new CycliBarrierTask(cyclicBarrier, i)).start();
        }
        cyclicBarrier.await();
        System.out.println("All 10 threads come to the barrier.");


        for (int i = 10; i < 20; i++) {
            new Thread(new CycliBarrierTask(cyclicBarrier, i)).start();
        }
        cyclicBarrier.await();
        System.out.println("ALl 20 threads come to the barrier.");
    }

    static class CycliBarrierTask implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private int index;

        public CycliBarrierTask(CyclicBarrier cyclicBarrier, int index) {
            this.cyclicBarrier = cyclicBarrier;
            this.index = index;
        }

        @Override
        public void run() {
            try {
                System.out.println("index:" + index);
                index--;
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
