package com.nertomato.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author nerotomato
 * @create 2021/7/17 下午5:43
 */
@EnableEurekaClient
@SpringBootApplication
public class UserServiceApplication_8202 {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication_8202.class,args);
    }
}
