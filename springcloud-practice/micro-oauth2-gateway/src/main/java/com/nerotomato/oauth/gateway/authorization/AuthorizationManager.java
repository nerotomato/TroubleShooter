package com.nerotomato.oauth.gateway.authorization;

import cn.hutool.core.convert.Convert;
import com.nerotomato.oauth.gateway.constant.AuthConstant;
import com.nerotomato.oauth.gateway.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * <p>
 * 在WebFluxSecurity中自定义鉴权操作需要实现ReactiveAuthorizationManager接口
 *
 * @author nerotomato
 * @create 2021/8/2 下午5:39
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        authorities = authorities.stream().map(s -> s = AuthConstant.AUTHORITY_PREFIX + s).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                //java8 新特性 方法引用， 类名：：方法名
                //方法当做参数传到stream内部，使stream的每个元素都传入到该方法里面执行一下
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
