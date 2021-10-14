

[TOC]

# 1.SpringCloud整合openfeign实现负载均衡及服务降级

**新建open-feign-service服务**

**pom.xml引入相关依赖**

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
    <!--<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.1</version>
        <relativePath/> &lt;!&ndash; lookup parent from repository &ndash;&gt;
    </parent>-->
    <modelVersion>4.0.0</modelVersion>

    <artifactId>open-feign-service</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <!--<spring-cloud.version>2020.0.3</spring-cloud.version>-->
    </properties>
    <!--
    Spring Cloud 2020.0.0版本以后对Spring Cloud NetFlix 组件进行剔除，仅保留了Eureka组件，其核心组件 Hystrix、Ribbon、Zuul、Archaius 等均进入维护状态
    旧版本的spring-cloud-netflix-dependencies管理着Netflix所有组件，包括Hystrix、Ribbon、Zuul、Eureka等。而自2020.0版本起，它有且只管理Eureka（包括Server和Client）
    其中Feign虽然最初属Netflix公司，但从9.x版本开始就移交给OpenFeign组织管理了，因此不再划入Netflix管辖范畴。
    Spring Cloud 2020.0.0版本彻底删除掉了Netflix除Eureka外的所有组件
    -->
    <!--<dependencyManagement>
        <dependencies>
            &lt;!&ndash;spring-cloud相关依赖&ndash;&gt;
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>-->

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!--引入resilience4j实现服务降级断路器功能-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-circuitbreaker-resilience4j</artifactId>
        </dependency>

        <!--SpringCloud20.0版本默认去除了hystrix等组件，这里也可自行引入hystrix具体的版本，实现服务降级断路器功能-->
        <!--<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
            <version>2.2.1.RELEASE</version>
        </dependency>-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-commons</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
</project>
```



**application.yml文件注册open-feign-service服务，并开启服务降级功能**

**使用20.0版本springcloud自带的circuitbreaker并整合resilience4j来实现断路器功能**

```yaml
server:
  port: 8701
spring:
  application:
    name: open-feign-service
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://nerotomato:123456@localhost:8084/eureka/
feign:
  circuitbreaker:
    enabled: true ##在Feign中开启circuitbreaker

logging:
  level:
    com.nerotomto.openfeign.service.UserService: debug
# ===== 自定义swagger配置 ===== #
swagger:
  enable: true
  application-name: OPEN-FEIGN-SERVICE
  application-version: 1.0
  application-description: springfox swagger 3.0整合
  try-host: http://localhost:${server.port}
```

**添加UserFeignController**

```JAVA
package com.nerotomto.openfeign.controller;

import com.nerotomto.openfeign.common.CommonResult;
import com.nerotomto.openfeign.entity.User;
import com.nerotomto.openfeign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author nerotomato
 * @create 2021/7/20 下午6:03
 */
@RestController
@RequestMapping("/user")
public class UserFeignController {

    @Autowired
    UserService userService;

    @GetMapping("/getByUsername")
    public CommonResult getUserByName(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestParam String username) {
        return userService.deleteByUsername(username);
    }

}

```

**添加UserService接口，并使用@FeignClient注解绑定注册中心的user-service服务**

**添加fallback = UserFallbackService.class参数指定服务降级的具体实现类**

```JAVA
package com.nerotomto.openfeign.service;

import com.nerotomto.openfeign.common.CommonResult;
import com.nerotomto.openfeign.entity.User;
import com.nerotomto.openfeign.service.fallback.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author nerotomato
 * @FeignClient 注解绑定user-service服务
 * @create 2021/7/20 下午5:41
 */
@FeignClient(value = "user-service", fallback = UserFallbackService.class)
public interface UserService {
    @PostMapping("/user/create")
    CommonResult create(@RequestBody User user);

    @GetMapping("/user/getByUsername")
    CommonResult getByUsername(@RequestParam String username);

    @PostMapping("/user/update")
    CommonResult update(@RequestBody User user);

    @PostMapping("/user/delete")
    CommonResult deleteByUsername(@RequestParam String username);
}

```

**添加服务降级实现类UserFallbackService**

```java
package com.nerotomto.openfeign.service.fallback;

import com.nerotomto.openfeign.common.CommonResult;
import com.nerotomto.openfeign.entity.User;
import com.nerotomto.openfeign.service.UserService;
import org.springframework.stereotype.Component;

/**
 * 添加服务降级实现类UserFallbackService
 * 实现了UserService接口，并且对接口中的每个实现方法进行了服务降级逻辑的实现
 * @author nerotomato
 * @create 2021/7/20 下午11:05
 */
@Component
public class UserFallbackService implements UserService {
    @Override
    public CommonResult create(User user) {
        User defaultUser = new User(-1L, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult getByUsername(String username) {
        User defaultUser = new User(-1L, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }

    @Override
    public CommonResult update(User user) {
        return new CommonResult("调用失败，服务被降级",500);
    }

    @Override
    public CommonResult deleteByUsername(String username) {
        return new CommonResult("调用失败，服务被降级",500);
    }
}

```

