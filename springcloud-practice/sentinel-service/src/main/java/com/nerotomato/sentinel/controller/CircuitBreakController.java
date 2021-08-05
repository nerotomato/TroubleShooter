package com.nerotomato.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.nerotomato.sentinel.common.CommonResult;
import com.nerotomato.sentinel.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author nerotomato
 * @create 2021/7/26 下午5:11
 */
@Slf4j
@RestController
@RequestMapping("/breaker")
public class CircuitBreakController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @GetMapping(value = "/getByUsername")
    @SentinelResource(value = "fallback", fallback = "handleFallback")
    public CommonResult getByUsername(@RequestParam(value = "username") String username) {
        return restTemplate.getForObject(userServiceUrl + "/user/getByUsername?username={1}", CommonResult.class, username);
    }

    @GetMapping(value = "/getByUsername2")
    @SentinelResource(value = "fallbackException", fallback = "handleFallbackException"
            , exceptionsToIgnore = {NullPointerException.class})
    public CommonResult getByUsernameFoException(@RequestParam(value = "username") String username) {
        if ("null".equalsIgnoreCase(username)) {
            throw new NullPointerException();
        } else if ("any".equalsIgnoreCase(username)) {
            throw new IndexOutOfBoundsException();
        }
        return restTemplate.getForObject(userServiceUrl + "/user/getByUsername?username={1}", CommonResult.class, username);
    }

    /**
     * fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。
     * fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。
     * fallback 函数签名和位置要求：
     * 返回值类型必须与原函数返回值类型一致；
     * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     * fallback 函数默认需要和原方法在同一个类中。
     * 若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象,注意对应的函数必需为 static 函数，否则无法解析。
     */
    public CommonResult handleFallback(String username) {
        User defaultUser = new User(-1L, "defaultUser", "123456");
        return new CommonResult<>(defaultUser, "服务降级返回", 200);
    }

    public CommonResult handleFallbackException(String username, Throwable e) {
        log.error("handleFallbackException username:{},throwable class:{}", username, e.getClass());
        User defaultUser = new User(-2L, "defaultUser2", "123456");
        return new CommonResult<>(defaultUser, "服务降级返回", 200);
    }
}
