package com.nerotomato.atomic;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试CAS并发操作的ABA问题
 * Created by nerotomato on 2021/10/25.
 */
@Slf4j
public class Test_AtomicABA {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int i = atomicInteger.get();
            log.info(Thread.currentThread().getName() + "====> 修改前数值为：" + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean casResult = atomicInteger.compareAndSet(i, 2);
            if (casResult) {
                log.info(Thread.currentThread().getName() + "====> 修改后数值为：" + atomicInteger.get());
            } else {
                log.info(Thread.currentThread().getName() + " CAS修改失败！");
            }

        }, "t1");

        Thread t2 = new Thread(() -> {
            atomicInteger.incrementAndGet();
            log.info(Thread.currentThread().getName() + "------ increase 后数值为：" + atomicInteger.get());
            atomicInteger.decrementAndGet();
            log.info(Thread.currentThread().getName() + "------ decrease 后数值为：" + atomicInteger.get());
        }, "t2");

        t1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();

    }
}
