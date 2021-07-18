package com.nertomato.user.controller;

import com.nertomato.user.entity.User;
import com.nertomato.user.service.UserService;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@RequestBody User user) {
        log.info("create user: "+user.toString());
        return userService.save(user);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(@RequestParam(value = "username") String username) {
        Object user = userService.get(username);
        if(user!=null){
            log.info("User: "+user.toString());
        }
        return user;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody User user) {
        log.info("update user: {}",user.toString());
        return userService.update(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object delete(@RequestParam(value = "username") String username) {
        log.info("delete user: "+username);
        return userService.delete(username);
    }

}
