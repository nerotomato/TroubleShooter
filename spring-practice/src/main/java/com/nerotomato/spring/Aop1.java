package com.nerotomato.spring;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * xml配置文件方式配置aop
 * */
public class Aop1 {
    
        //前置通知
        public void startTransaction(){
            System.out.println("    ====>begin transaction... ");
        }
        
        //后置通知
        public void commitTransaction(){
            System.out.println("    ====>finish transaction... ");
        }
        
        //环绕通知
        public void around(ProceedingJoinPoint joinPoint) throws Throwable{
            System.out.println("    ====>around begin transaction");
            //调用process()方法才会真正的执行实际被代理的方法
            joinPoint.proceed();
            
            System.out.println("    ====>around " +
                    " transaction");
        }
        
}
