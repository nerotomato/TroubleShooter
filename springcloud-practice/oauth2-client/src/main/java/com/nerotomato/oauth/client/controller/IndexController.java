package com.nerotomato.oauth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nerotomato
 * @create 2021/8/1 上午12:25
 */
@RestController
public class IndexController {
    @Autowired
    private UserController userController;

    @GetMapping("/")
    public Object index(Authentication authentication) {
        return userController.getCurrentUser(authentication);
    }
}
