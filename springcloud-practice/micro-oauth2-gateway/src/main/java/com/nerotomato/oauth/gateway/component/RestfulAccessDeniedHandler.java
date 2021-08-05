package com.nerotomato.oauth.gateway.component;

import cn.hutool.json.JSONUtil;
import com.nerotomato.oauth.gateway.common.CommonResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 自定义返回结果：没有权限访问时的处理逻辑
 *
 * @author nerotomato
 * @create 2021/8/3 上午10:02
 */
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {
    /**
     * Mono
     * <p>
     * just() 创建对象
     * empty() 创建一个不包含任何元素，只发布结束消息的序列
     * error() 抛出异常，使用示例：
     */
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException deniedException) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String jsonStr = JSONUtil.toJsonStr(CommonResult.forbidden(deniedException.getMessage()));
        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
