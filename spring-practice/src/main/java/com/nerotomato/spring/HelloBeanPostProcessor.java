package com.nerotomato.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by nero on 2021/4/18.
 */
@Component
public class HelloBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(" ====> postProcessBeforeInitialization " + beanName + ":" + bean);
        if (bean instanceof Student) {
            Student student = (Student) bean;
            student.setName(student.getName() + System.currentTimeMillis());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(" ====> postProcessAfterInitialization " + beanName + ":" + bean);
        return bean;
    }

}
