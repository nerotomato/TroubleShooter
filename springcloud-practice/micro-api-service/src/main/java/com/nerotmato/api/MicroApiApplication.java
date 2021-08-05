package com.nerotmato.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author nerotomato
 * @create 2021/8/3 下午5:58
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MicroApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroApiApplication.class, args);
    }
}
