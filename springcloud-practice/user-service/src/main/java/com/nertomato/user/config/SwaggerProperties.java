package com.nertomato.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义swagger配置类SwaggerProperties
 * Created by nero on 2021/4/28.
 */
@Component
@ConfigurationProperties("swagger")
@Data
public class SwaggerProperties {

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable;

    /**
     * 项目应用名
     */
    private String applicationName;

    /**
     * 项目版本信息
     */
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    private String applicationDescription;


    /**
     * 接口调试地址
     */
    private String tryHost;

}
