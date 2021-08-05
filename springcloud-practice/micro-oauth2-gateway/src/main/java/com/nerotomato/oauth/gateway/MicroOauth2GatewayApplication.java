package com.nerotomato.oauth.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author nerotomato
 * @create 2021/8/3 下午5:53
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MicroOauth2GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroOauth2GatewayApplication.class, args);
    }
}
