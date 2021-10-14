package com.nerotomato.bytecode;

/**
 * Created by nerotomato on 2021/10/6.
 */
public class Math {
    public static void main(String[] args) {
        Math math = new Math();
        int result = math.compute();
        System.out.println(result);
    }

    public static int compute(){
        int a=1;
        int b=2;
        return (a+b)*8;
    }
}
