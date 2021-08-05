package com.nerotomato.nacos.loadbalance.controller;

import com.nerotomato.nacos.loadbalance.common.CommonResult;
import com.nerotomato.nacos.loadbalance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author nerotomato
 * @create 2021/7/17 下午4:10
 */
@RestController
@RequestMapping("/user")
public class UserRibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${serviceUrl.userService}")
    private String userServiceUrl;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult save(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/create", user, CommonResult.class);
    }

    @RequestMapping(value = "/getByUsername", method = RequestMethod.GET)
    public CommonResult get(@RequestParam(value = "username") String username) {
        return restTemplate.getForObject(userServiceUrl + "/user/getByUsername?username={1}", CommonResult.class, username);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/update", user, CommonResult.class);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResult delete(@RequestParam(value = "username") String username) {
        return restTemplate.postForObject(userServiceUrl + "/user/delete?username={1}", null
                , CommonResult.class, username);
    }
}
