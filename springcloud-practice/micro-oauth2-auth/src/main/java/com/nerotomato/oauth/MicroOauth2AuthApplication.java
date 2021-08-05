package com.nerotomato.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author nerotomato
 * @create 2021/8/3 下午6:04
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MicroOauth2AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroOauth2AuthApplication.class, args);
    }
}
