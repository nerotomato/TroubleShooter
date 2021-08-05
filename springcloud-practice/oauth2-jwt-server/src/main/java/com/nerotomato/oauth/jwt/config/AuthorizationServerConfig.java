package com.nerotomato.oauth.jwt.config;

import com.nerotomato.oauth.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 *
 * @author nerotomato
 * @create 2021/7/27 下午10:53
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    /**
     * 在认证服务器配置中指定令牌的存储策略为Redis
     */
    /*@Autowired
    @Qualifier("redisTokenStore")
    private TokenStore tokenStore;*/

    /**
     * 在认证服务器配置中指定令牌的存储策略为JWT
     */
    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 在认证服务器中配置JWT内容增强器
     */
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置JWT的内容增强器
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer); //配置JWT的内容增强器
        delegates.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .tokenStore(tokenStore) //配置令牌存储策略
                .accessTokenConverter(jwtAccessTokenConverter) //JWT方式存储时配置token转换器
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin") //配置client_id
                .secret(passwordEncoder.encode("admin123")) //配置client_secret
                .accessTokenValiditySeconds(1800) //配置访问token的有效期 30分钟
                .refreshTokenValiditySeconds(86400) //配置刷新token的有效期 24小时
                //.redirectUris("http://www.baidu.com") //配置redirect_uri，用于授权成功后跳转
                .redirectUris("http://localhost:9501/login") //单点登录时配置
                //.autoApprove(true) //自动授权配置
                .scopes("all") //配置申请的权限范围
                .authorizedGrantTypes("authorization_code", "password", "refresh_token"); //配置grant_type，表示授权类型 //添加授权模式refresh_token
    }

    /**
     * 添加获取秘钥时的身份认证
     */
    public void configure(AuthorizationServerSecurityConfigurer securityConfigurer) {
        // 获取密钥需要身份认证，使用单点登录时必须配置
        securityConfigurer.tokenKeyAccess("isAuthenticated");
    }
}
