package com.nerotomto.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @EnableFeignClients 注解启动feign客户端功能
 * @EnableDiscoveryClient 注解开启服务注册功能
 * Spring Cloud OpenFeign 是声明式的服务调用工具，它整合了Ribbon和Hystrix，拥有负载均衡和服务容错功能
 * @author nerotomato
 * @create 2021/7/20 下午2:15
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OpenFeignServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignServiceApplication.class, args);
    }
}
