package com.nerotomato.thread;

/**
 * Created by nerotomato on 2021/11/1.
 */
public class TestThread {
    public static void main(String[] args) {
        int a = 2;
        int b = 3;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        //按位或(|)只要有1出现结果就是1，否则为0
        //System.out.println((2 | 3));
        System.out.println(1 << 29);
    }
}
