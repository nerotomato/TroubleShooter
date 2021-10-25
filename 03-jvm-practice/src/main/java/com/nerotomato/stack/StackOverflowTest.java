package com.nerotomato.stack;

/**
 * 栈内存溢出测试
 * Created by nerotomato on 2021/10/14.
 */
public class StackOverflowTest {
    private static int count = 0;

    public static void main(String[] args) {
        try {
            redo();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("count:" + count);
        }
    }

    public static void redo() {
        count++;
        redo();
    }
}
