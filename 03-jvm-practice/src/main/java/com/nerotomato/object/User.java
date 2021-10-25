package com.nerotomato.object;

/**
 * Created by nerotomato on 2021/10/15.
 */
class User {
    int age;
    String name;

    public User() {
    }

    public User(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
