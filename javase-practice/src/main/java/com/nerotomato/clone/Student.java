package com.nerotomato.clone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @Created by nerotomato on 2021/8/17.
 */
@Getter
@Setter
@AllArgsConstructor
public class Student implements Cloneable {

    private int age;
    private String name;
    private Courses courses;

    /**
     * 浅拷贝
     */
    /*@Override
    protected Object clone() throws CloneNotSupportedException {
        // 浅拷贝 源对象的属性如果是引用类型，那拷贝后的目标对象的该属性于源对象的引用属性是同一个对象
        // 直接调用父类的clone()方法
        return super.clone();
    }*/

    /**
     * 深拷贝
     * 在 Student 的 clone() 方法中，需要拿到拷贝自己后产生的新的对象，然后对新的对象的引用类型再调用拷贝操作，实现对引用类型成员变量的深拷贝
     * 拷贝对象的引用类型属性也要实现Cloneable接口
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        student.courses = (Courses) courses.clone();
        return student;
    }

    @Override
    public String toString() {
        return "Student{" + this.hashCode() +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
