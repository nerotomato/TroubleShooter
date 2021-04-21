package com.nerotomato.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

/**
 * Created by nero on 2021/4/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable, BeanNameAware, ApplicationContextAware {

    private int id;
    private String name;

    private String beanName;
    private ApplicationContext applicationContext;

    public void init() {
        System.out.println("hello student...........");
    }

//    public Student create(){
//        return new Student(101,"KK101");
//    }

    public void print() {
        System.out.println(this.beanName);
        System.out.println("======>> context.getBeanDefinitionNames() ===>> "
                + String.join(",", applicationContext.getBeanDefinitionNames()));
    }
}
