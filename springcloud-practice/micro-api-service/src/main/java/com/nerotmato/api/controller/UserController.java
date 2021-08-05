package com.nerotmato.api.controller;

import com.nerotmato.api.component.LoginUserInfoHolder;
import com.nerotmato.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nerotomato
 * @create 2021/8/3 下午5:27
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LoginUserInfoHolder loginUserInfoHolder;

    @GetMapping("/currentUser")
    public User currentUser() {
        return loginUserInfoHolder.getCurrentUser();
    }

}
