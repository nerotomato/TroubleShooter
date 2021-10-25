package com.nerotomato.bytecode;

/**
 * Created by nero on 2021/3/16.
 */
public class Hello {

    public static int numArr[] = {1, 2, 6, 7, 8, 10};

    public static void main(String[] args) {
        ByteCodeObject byteCodeObject = new ByteCodeObject();
        byteCodeObject.add(2);
        byteCodeObject.add(5);
        double avg = byteCodeObject.getAvg();
        double mult = byteCodeObject.getMult();

        System.out.println(avg);
        System.out.println(mult);

        for (int num : numArr) {
            if (num % 2 == 0) {
                System.out.println(num);
            }
        }
    }
}
