package com.nerotomato.singleton;

/**
 * 单例模式 - 懒加载测试
 */
public class LazySingletonDemoTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            LazySingletonDemo int1 = LazySingletonDemo.getInstance();
            System.out.println(int1);
        }).start();
        new Thread(() -> {
            LazySingletonDemo int2 = LazySingletonDemo.getInstance();
            System.out.println(int2);
        }).start();

    }
}
