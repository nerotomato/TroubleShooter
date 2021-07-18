package com.nerotomato.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication_single {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication_single.class, args);
    }
}

