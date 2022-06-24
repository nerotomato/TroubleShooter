package com.nerotomato.bit;

public class BitOperationDemo {
    public static void main(String[] args) {
        System.out.println(2&2);
        System.out.println(1&4);
        System.out.println(1|2|4|8);
        System.out.println(15&8);
        System.out.println(4&1);
        System.out.println(4&4);
        System.out.println(4&8);
        System.out.println(15&2);

        // 0001    1
        // 0010    2
        // 0011    3
        // 0100    4
        // 0101    5
        // 0110    6
        // 0111    7
        // 1000    8
        // 1111    15
    }
}
