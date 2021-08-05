package com.nerotomato.nacos.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author nerotomato
 * @create 2021/7/17 下午5:43
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosUserServiceApplication.class,args);
    }
}
