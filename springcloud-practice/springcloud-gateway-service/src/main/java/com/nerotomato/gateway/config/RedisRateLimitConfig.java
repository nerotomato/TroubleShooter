package com.nerotomato.gateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 限流策略配置类
 *
 * @author nerotomato
 * @create 2021/7/23 下午4:43
 */
@Configuration
public class RedisRateLimitConfig {

    /**
     * redis限流过滤器配置
     * */
    @Primary
    @Bean(value = "myRedisRateLimiter")
    public RateLimiter myRedisRateLimiter(){
        return new RedisRateLimiter(1, 2, 1);
    }
    /**
     * 根据请求参数中的username进行限流
     */
    /*@Primary
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("username"));
    }*/

    /**
     * 根据访问IP进行限流
     * */
    @Bean
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
