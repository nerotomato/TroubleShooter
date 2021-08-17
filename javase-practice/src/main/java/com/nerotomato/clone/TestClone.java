package com.nerotomato.clone;

import java.util.Date;
import java.util.HashMap;

/**
 * @Created by nerotomato on 2021/8/17.
 */
public class TestClone {
    public static void main(String[] args) throws CloneNotSupportedException {
        Courses chinese = new Courses("Chinese");
        Student studentA = new Student(26, "nero", chinese);
        // 浅拷贝 源对象的属性如果是引用类型，那拷贝后的目标对象的该属性与源对象的引用属性是同一个对象
        Student studentB = (Student) studentA.clone();
        studentB.setName("dante");
        studentB.setAge(220);
        Courses courses = studentB.getCourses();
        // 浅拷贝后，修改对象的引用属性，会影响到源对象的引用属性，因为该引用属性就是同一个对象
        courses.setName("English");
        System.out.println(studentA);
        System.out.println(studentB);

    }
}
