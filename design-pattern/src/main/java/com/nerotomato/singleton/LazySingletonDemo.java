package com.nerotomato.singleton;

/**
 * 单例模式 - 懒加载 double-check
 */
public class LazySingletonDemo {

    private static LazySingletonDemo instance;

    public LazySingletonDemo() {

    }

    public static LazySingletonDemo getInstance(){
        if (instance == null){
            synchronized (LazySingletonDemo.class){
                if(instance == null){
                    instance = new LazySingletonDemo();
                }
            }
        }
        return instance;
    }
}
