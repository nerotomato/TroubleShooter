package com.nerotomato.sentinel.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Sentinel 支持对服务间调用进行保护，对故障应用进行熔断操作
 * 这里使用RestTemplate来调用nacos-user-service服务所提供的接口
 *
 * @author nerotomato
 * @create 2021/7/26 下午4:54
 */
@Configuration
public class RestTemplateConfig {
    /**
     * 需要使用@SentinelRestTemplate来包装RestTemplate实例
     * */
    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
