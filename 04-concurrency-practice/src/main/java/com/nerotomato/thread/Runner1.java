package com.nerotomato.thread;

/**
 * Created by nero on 2021/4/6.
 */
public class Runner1 implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            //try {
                //Thread.sleep(1000);
                System.out.println("进入Runner1运行状态——————————" + i);
            //} catch (InterruptedException e) {
                //e.printStackTrace();
            //}
        }
    }
}
