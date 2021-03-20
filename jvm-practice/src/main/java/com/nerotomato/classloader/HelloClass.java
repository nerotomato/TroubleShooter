package com.nerotomato.classloader;

/**
 * Created by nero on 2021/3/18.
 */
public class HelloClass {
    static {
        System.out.println("HelloClass has been initialized!");
    }

    public static void main(String[] args) {
        System.out.println("The main method is running!");
    }
}
