package com.nerotomato.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author nerotomato
 * @create 2021/7/26 下午3:29
 */
@EnableFeignClients //开启openfeign的功能；
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelServiceApplication.class, args);
    }
}
