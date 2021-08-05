package com.nerotomato.oauth.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * 启动类上添加@EnableOAuth2Sso注解来启用单点登录功能
 *
 * @author nerotomato
 * @create 2021/7/31 下午9:26
 */
@EnableOAuth2Sso
@SpringBootApplication
public class Oauth2ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2ClientApplication.class, args);
    }
}
