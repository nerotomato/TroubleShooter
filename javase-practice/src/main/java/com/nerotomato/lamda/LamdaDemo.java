package com.nerotomato.lamda;

import com.google.common.cache.Weigher;

import java.util.Arrays;

/**
 * lamda表达式
 * Created by nero on 2021/4/22.
 */
public class LamdaDemo {
    public static void main(String[] args) {
        LamdaDemo demo = new LamdaDemo();
        MathOperation<Integer> op = new MathOperation<Integer>() {
            @Override
            public Integer operation(int a, int b) {
                return 1;
            }
        };
        MathOperation<Integer> op1 = (a, b) -> 1;

        MathOperation<Integer> op2 = new MathOperation<Integer>() {

            @Override
            public Integer operation(int a, int b) {
                return a + b;
            }
        };
        // 类型声明
        MathOperation<Integer> addition = (int a, int b) -> a + b;
        MathOperation subtraction = (int a, int b) -> a - b;
        MathOperation multiplication = (int a, int b) -> {
            int c = 1000;
            return a * b * c;
        };

        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + demo.operate(10, 5, addition));
        System.out.println("10 - 5 = " + demo.operate(10, 5, subtraction));
        System.out.println("10 x 5 x 1000 = " + demo.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + demo.operate(10, 5, division));

        System.out.println("10 ^ 5 = " + demo.operate(10, 5, (a, b) -> Math.pow(a, b)));

        // 不用括号
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) -> {
            System.out.println(message);
        };

        GreetingService greetService3 = System.out::println;
        Arrays.asList(1, 2, 3).forEach(a -> System.out.println(a + 3));
        Arrays.asList(1, 2, 3).forEach(LamdaDemo::println);

        greetService1.sayMessage("nero");
        greetService2.sayMessage("dante");

    }

    private static void println(int x) {
        System.out.println(x + 3);
    }

    interface MathOperation<T> {
        // 返回类型+函数名+参数类型的列表
        T operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private <T> T operate(int a, int b, MathOperation<T> mathOperation) {
        return mathOperation.operation(a, b);
    }
}

