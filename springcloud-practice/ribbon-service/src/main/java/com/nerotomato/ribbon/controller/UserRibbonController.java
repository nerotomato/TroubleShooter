package com.nerotomato.ribbon.controller;

import com.nerotomato.ribbon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author nerotomato
 * @create 2021/7/17 下午4:10
 */
@RestController
@RequestMapping("/user")
public class UserRibbonController {
    /*@Autowired
    private RestTemplate restTemplate;*/
    @Autowired
    private RestOperations restTemplate;

    @Value("${serviceUrl.userService}")
    private String userServiceUrl;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/save", user, Object.class);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(@RequestParam(value = "username") String username) {
        return restTemplate.getForObject(userServiceUrl + "/user/get?username={1}", Object.class, username);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/update", user, Object.class);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object delete(@RequestParam(value = "username") String username) {
        return restTemplate.postForObject(userServiceUrl + "/user/delete?username={1}", null
                , Object.class, username);
    }
}
