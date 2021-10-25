package com.nerotomato.collection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nero on 2021/4/15.
 */
public class CopyOnWriteArrayListDemo2 {
    private static final CopyOnWriteArrayList<String> cowlist = new CopyOnWriteArrayList();
    // 这个例子再次证明，
    // 多个步骤的操作，不能保证原子性
    // list.size() 获取到的数，再继续用list时，可能已经变了

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        for (int i = 0; i < 10000; i++) {
            cowlist.add("cowboy" + i);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (cowlist.size() > 0) { // todo:下一个get操作执行时，size可能已经是0了
                        String content = cowlist.get(cowlist.size() - 1);
                    } else {
                        break;
                    }
                }
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(cowlist.size() <= 0){
                        break;
                    }
                    cowlist.remove(0);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t2").start();
    }
}
