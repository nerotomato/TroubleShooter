package com.nerotomato.ribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author nerotomato
 * @create 2021/7/17 下午4:06
 */
@Configuration
public class RibbonConfig {

    /**
     * 使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
     * */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
