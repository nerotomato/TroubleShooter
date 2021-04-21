package com.nerotomato.spring;

import lombok.Data;

import java.util.List;

/**
 * 班级
 * Created by nero on 2021/4/18.
 */
@Data
public class Klass {
    List<Student> students;

    public void beginClass() {
        System.out.println(this.getStudents());
    }
}
