package com.nerotomato.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nero on 2021/4/15.
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        demo1();
    }

    public static void demo1() {
        Map<String, AtomicInteger> cchMap = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable task = () -> {
            AtomicInteger oldValue = null;
            for (int i = 0; i < 5; i++) {
                oldValue = cchMap.get("a");
                if (oldValue == null) {
                    AtomicInteger zeroValue = new AtomicInteger(0);
                    oldValue = cchMap.putIfAbsent("a", zeroValue);
                    if (null == oldValue) {
                        oldValue = zeroValue;
                    }
                }
                oldValue.incrementAndGet();
            }
            countDownLatch.countDown();
        };
        new Thread(task).start();
        new Thread(task).start();

        try {
            countDownLatch.await();
            System.out.println(cchMap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
