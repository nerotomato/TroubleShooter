package com.nerotomato.sentinel.controller;

import com.nerotomato.sentinel.common.CommonResult;
import com.nerotomato.sentinel.entity.User;
import com.nerotomato.sentinel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author nerotomato
 * @create 2021/7/20 下午6:03
 */
@RestController
@RequestMapping("/user")
public class UserFeignController {

    @Autowired
    UserService userService;

    @GetMapping("/getByUsername")
    public CommonResult getUserByName(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/delete")
    public CommonResult delete(@RequestParam String username) {
        return userService.deleteByUsername(username);
    }

}
