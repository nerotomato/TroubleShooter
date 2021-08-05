package com.nerotomato.oauth.gateway.common;

/**
 * 封装API的错误码
 *
 * @author nerotomato
 * @create 2021/8/3 下午1:57
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
