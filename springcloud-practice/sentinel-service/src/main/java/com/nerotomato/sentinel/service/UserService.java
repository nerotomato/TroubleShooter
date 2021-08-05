package com.nerotomato.sentinel.service;

import com.nerotomato.sentinel.common.CommonResult;
import com.nerotomato.sentinel.entity.User;
import com.nerotomato.sentinel.service.impl.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author nerotomato
 * @create 2021/7/26 下午11:21
 */
@FeignClient(value = "nacos-user-service", fallback = UserFallbackService.class)
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
