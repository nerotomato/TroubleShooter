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
