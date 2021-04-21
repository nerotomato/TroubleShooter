package com.nerotomato.spring;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 通过注解方式实现aop
 * */

@Aspect //切面
public class Aop2 {
    //切点
    @Pointcut(value="execution(* com.nerotomato.aop.*.*(..))")
    public void point(){
        
    }
    //前置通知
    @Before(value="point()")
    public void before(){
        System.out.println("========>begin klass dong...");
    }

    //后置通知
    @AfterReturning(value = "point()")
    public void after(){
        System.out.println("========>after klass dong...");
    }
    //环绕通知
    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("========>around begin klass dong");
        joinPoint.proceed();
        System.out.println("========>around after klass dong");
        
    }
    
}
