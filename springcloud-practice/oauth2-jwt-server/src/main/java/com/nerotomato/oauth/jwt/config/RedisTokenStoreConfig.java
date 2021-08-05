package com.nerotomato.oauth.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * redis存储token的配置类
 *
 * @author nerotomato
 * @create 2021/7/28 下午5:44
 */
@Configuration
public class RedisTokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

   /* @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }*/
}
