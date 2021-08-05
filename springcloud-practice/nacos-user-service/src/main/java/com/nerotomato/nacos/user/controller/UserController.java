package com.nerotomato.nacos.user.controller;

import com.nerotomato.nacos.user.common.CommonResult;
import com.nerotomato.nacos.user.entity.User;
import com.nerotomato.nacos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author nerotomato
 * @create 2021/7/16 上午11:05
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody User user) {
        log.info("create user: " + user.toString());
        userService.create(user);
        return new CommonResult(user, "create user: " + user.getUsername() + " successfully!", 200);
    }

    @RequestMapping(value = "/getByUsername", method = RequestMethod.GET)
    public CommonResult getByUsername(@RequestParam(value = "username") String username) {
        User user = userService.getByUsername(username);
        if (user != null) {
            log.info("User: " + user.toString());
            return new CommonResult(user, "user is : " + username, 200);
        } else {
            log.info("User is null!");
            return new CommonResult(user, "user is not found.", 200);
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestBody User user) {
        log.info("update user: {}", user.toString());
        User updateUser = userService.update(user);
        if (null != updateUser) {
            return new CommonResult(updateUser, "Updating :" + user.getUsername() + "successfully!", 200);
        } else {
            return new CommonResult("Failed updating the user: " + user.getUsername(), 500);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResult deleteByUsername(@RequestParam(value = "username") String username) {
        log.info("delete user: " + username);
        User deleteUser = userService.delete(username);
        if (null != deleteUser) {
            return new CommonResult(deleteUser, "delete user: " + username + " successfully!", 200);
        } else {
            return new CommonResult("Failed to delete user: " + username + " !", 500);
        }
    }

}
