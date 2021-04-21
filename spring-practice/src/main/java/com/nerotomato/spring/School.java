package com.nerotomato.spring;

import com.nerotomato.aop.ISchool;

/**
 * 学校
 * Created by nero on 2021/4/17.
 */
public class School implements ISchool {
    @Override
    public void attendClass() {
        System.out.println("I'm going to school!");
    }

    @Override
    public void getOutOfClass() {
        System.out.println("I'm out of school!");
    }
}
