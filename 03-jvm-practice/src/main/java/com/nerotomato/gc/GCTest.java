package com.nerotomato.gc;

/**
 * Created by nerotomato on 2021/10/15.
 */
public class GCTest {
    /**
     * JVM参数：-Xms256m -Xmx256m -XX:+PrintGCDetails
     *
     * JVM参数 -XX:PretenureSizeThreshold 可以设置大
     * 对象的大小，如果对象超过设置大小会直接进入老年代，不会进入年轻代，这个参数只在 Serial 和ParNew两个收集器下
     * 有效。
     * */
    public static void main(String[] args) {
        byte[] allocation1, allocation2 , allocation3, allocation4, allocation5, allocation6;
        allocation1 = new byte[60000 * 1024];

        allocation2 = new byte[8000*1024];

        allocation3 = new byte[1000*1024];
        allocation4 = new byte[1000*1024];
        allocation5 = new byte[1000*1024];
        allocation6 = new byte[1000*1024];
    }
}
