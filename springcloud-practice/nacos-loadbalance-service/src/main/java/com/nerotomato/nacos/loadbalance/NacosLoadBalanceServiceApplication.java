package com.nerotomato.nacos.loadbalance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author nerotomato
 * @create 2021/7/17 下午5:41
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosLoadBalanceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosLoadBalanceServiceApplication.class, args);
    }
}
