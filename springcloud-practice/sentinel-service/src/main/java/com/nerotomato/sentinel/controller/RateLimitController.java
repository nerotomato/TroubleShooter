package com.nerotomato.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nerotomato.sentinel.common.CommonResult;
import com.nerotomato.sentinel.handler.CustomBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sentinel Starter 默认为所有的 HTTP 服务提供了限流埋点
 * 我们也可以通过使用@SentinelResource来自定义一些限流行为,用于定义资源，并提供可选的异常处理和 fallback 配置项。
 * 注意：注解方式埋点不支持 private 方法。
 *
 * @author nerotomato
 * @create 2021/7/26 下午2:46
 */
@RestController
@RequestMapping("/rateLimit")
public class RateLimitController {

    /**
     * 由于Sentinel采用的懒加载规则，需要我们先访问下接口，Sentinel控制台中才会有对应服务信息
     * */

    /**
     * 按资源名称限流，需要指定限流处理逻辑
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "resource_limit", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult("按资源名称限流", 200);
    }

    /**
     * 按URL限流，有默认的限流处理逻辑
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "url_limit", blockHandler = "handleException")
    public CommonResult byUrl() {
        return new CommonResult("按url限流", 200);
    }
    /**
     * 按自定义通用的限流处理类逻辑限流
     */
    @GetMapping("/byCustomResource")
    @SentinelResource(value = "custom_resource_limit", blockHandler = "handleException",blockHandlerClass = CustomBlockHandler.class)
    public CommonResult byCustomResource() {
        return new CommonResult("根据自定义通用的限流处理类逻辑按照资源名称限流", 200);
    }
    /**
     * 按自定义通用的限流处理类逻辑限流
     */
    @GetMapping("/byCustomUrl")
    @SentinelResource(value = "custom_url_limit", blockHandler = "handleException",blockHandlerClass = CustomBlockHandler.class)
    public CommonResult byCustomUrl() {
        return new CommonResult("根据自定义通用的限流处理类逻辑按照url限流", 200);
    }

    public CommonResult handleException(BlockException blockException) {
        return new CommonResult(blockException.getClass().getCanonicalName(), 200);
    }

}
