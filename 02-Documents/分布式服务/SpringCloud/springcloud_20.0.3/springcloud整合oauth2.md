[TOC]

# 1.SpringCloud整合Oauth2

## 1.1.引入Oauth2依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloud-practice</artifactId>
        <groupId>com.nerotomato</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <!--创建oauth2-server模块作为认证服务器-->
    <artifactId>oauth2-server</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <!--Spring Cloud最新版20.0版本以后，Spring Cloud Security 已被删除，代码已移至各自的Spring Cloud项目。
            所以这里需要单独引入spring-cloud-starter-oauth2 和 spring-cloud-starter-security -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
            <version>2.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
            <version>2.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

## 1.2.添加认证服务器配置，使用@EnableAuthorizationServer注解开启

```java
package com.nerotomato.oauth.config;

import com.nerotomato.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

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
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin") //配置client_id
                .secret(passwordEncoder.encode("admin123")) //配置client_secret
                .accessTokenValiditySeconds(1800) //配置访问token的有效期 30分钟
                .refreshTokenValiditySeconds(86400) //配置刷新token的有效期 24小时
                .redirectUris("http://www.baidu.com") //配置redirect_uri，用于授权成功后跳转
                .scopes("all") //配置申请的权限范围
                .authorizedGrantTypes("authorization_code", "password"); //配置grant_type，表示授权类型
    }
}

```



## 1.3.添加资源服务器配置，使用@EnableResourceServer注解开启

```java
package com.nerotomato.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器配置
 *
 * @author nerotomato
 * @create 2021/7/27 下午11:22
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                .antMatchers("/user/**"); //配置需要保护的资源路径
    }
}

```

## 1.4.添加SpringSecurity配置,允许认证相关路径的访问及表单登录

```java
package com.nerotomato.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity配置,允许认证相关路径的访问及表单登录
 *
 * @author nerotomato
 * @create 2021/7/27 下午11:26
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/login/**", "/logout/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();
    }
}

```

## 1.5.实现UserDetailsService接口，加载用户信息

```java
package com.nerotomato.oauth.service;

import com.nerotomato.oauth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现UserDetailsService接口，加载用户信息
 *
 * @author nerotomato
 * @create 2021/7/27 下午5:52
 */
@Service
public class UserService implements UserDetailsService {

    private List<User> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Java中该注解的说明：@PostConstruct该注解被用来修饰一个非静态的void（）方法。
     * 被@PostConstruct修饰的方法，只会被服务器执行一次。
     * PostConstruct在构造函数之后执行，init（）方法之前执行。
     * <p>
     * 通常我们会是在Spring框架中使用到@PostConstruct注解 该注解的方法在整个Bean初始化中的执行顺序：
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("nero", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("dante", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("vergil", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> findUserList = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
    }
}

```

## 1.6.User实体类

```java
package com.nerotomato.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author nerotomato
 * @create 2021/7/17 下午3:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private long id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public User(String nero, String password, List<GrantedAuthority> admin) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```



# 2.授权码模式使用

- 启动oauth2-server服务；

- 在浏览器访问该地址进行登录授权：http://localhost:9401/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all&state=normal

- 输入账号密码进行登录操作

  ![](/home/nerotomato/下载/spingcloud_security_04.png)

浏览器会带着授权码跳转到我们指定的路径：

```html
https://www.baidu.com/?code=niyutN&state=normal
```

使用授权码发送post请求，请求该地址获取访问令牌：http://localhost:9401/oauth/token

使用Basic Auth认证通过client_id和client_secret构造一个Authorization头信息；

![](/home/nerotomato/桌面/QQ图片20210728161552.png)



在body中添加以下参数信息，通过POST请求获取访问令牌；

​	![](/home/nerotomato/桌面/QQ图片20210728161732.png)

