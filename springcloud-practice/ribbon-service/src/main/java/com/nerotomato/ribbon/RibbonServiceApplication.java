package com.nerotomato.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author nerotomato
 * @create 2021/7/17 下午5:41
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RibbonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RibbonServiceApplication.class, args);
    }
}
