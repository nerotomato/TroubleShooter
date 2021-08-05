package com.nerotomato.nacos.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author nerotomato
 * @create 2021/7/25 下午7:13
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosConfigServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigServiceApplication.class, args);
    }
}
