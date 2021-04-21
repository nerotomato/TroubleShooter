package com.nerotomato.spring;

import com.nerotomato.aop.ISchool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nero on 2021/4/18.
 */
public class Springdemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        ISchool school = applicationContext.getBean(ISchool.class);
        System.out.println(school);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+school.getClass());
        ISchool school1 = applicationContext.getBean(ISchool.class);
        System.out.println(school1);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+school1.getClass());

        Klass class1 = applicationContext.getBean(Klass.class);
        System.out.println(class1);
        System.out.println("Klass对象AOP代理后的实际类型："+class1.getClass());
        System.out.println("Klass对象AOP代理后的实际类型是否是Klass子类："+(class1 instanceof Klass));

        Student student1 = (Student) applicationContext.getBean("student1");
        Student student2 = (Student) applicationContext.getBean("student2");

        student1.print();
        student2.print();
        System.out.println("name:"+student1.getName());
        System.out.println("name:"+student2.getName());
        class1.beginClass();
        school.attendClass();
        school.getOutOfClass();
    }
}
