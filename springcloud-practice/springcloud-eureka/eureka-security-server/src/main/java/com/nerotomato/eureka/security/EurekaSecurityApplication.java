package com.nerotomato.eureka.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author nerotomato
 * @create 2021/7/15 下午5:41
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaSecurityApplication.class, args);
    }
}
