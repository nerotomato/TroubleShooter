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
