package com.nerotomato.object;

import java.io.IOException;

public class EscapeAnalysis {

    public static Object globalObject;
    private static Object instance;

    public static void createGlobalObject() {
        globalObject = new Object();
    }

    public void createInstance() {
        instance = new Object();
    }

    public static int fn(int i) {
        User user = new User(i);
        return user.getAge();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        int loop = 1000000;
        int sum = 0;

        //
        //createGlobalObject();
        //new EscapeAnalysis().createInstance();

        for (int i = 0; i < loop; i++) {
            sum += fn(i);
        }
        System.out.println(sum);
        Thread.sleep(2000);
        for (int i = 0; i < loop; i++) {
            sum += fn(i);
        }
        System.out.println(sum);
        System.in.read();
    }
}

class User {
    int age;
    String name;

    public User(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}