package com.nerotomato.sentinel.service.impl;

import com.nerotomato.sentinel.common.CommonResult;
import com.nerotomato.sentinel.entity.User;
import com.nerotomato.sentinel.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @author nerotomato
 * @create 2021/7/26 下午11:21
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
