package com.nerotomato.oauth.gateway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关白名单配置
 *
 * @author nerotomato
 * @create 2021/8/3 上午12:03
 */
@Data
@EqualsAndHashCode(callSuper = false) //false表示不继承父类的属性，会影响equals比较方法
@Component
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
