package com.nerotomato.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关路由配置类
 *
 * @author nerotomato
 * @create 2021/7/22 下午3:49
 */
@Configuration
public class GatewayConfig {
    /**
     * 另一种方式
     *
     * 通过Java配置类配置路由以及各种过滤器，如断路器
     * 添加相关配置类，并配置一个RouteLocator对象
     * 通过配置类的方式添加路由
     */
    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(
                "path_route2", route -> route.path("/user/getByUsername")
                        .filters(f -> f.circuitBreaker(c -> c.setName("myCircuitBreaker").setFallbackUri("forward:/fallback")))
                        .uri("http://localhost:8201/"))
                .build();
    }*/
}
