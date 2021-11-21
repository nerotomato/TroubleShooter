package com.nerotomato.cache.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nerotomato.cache.entity.UmsMember;
import com.nerotomato.cache.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author nero
 * @since 2021-06-18
 */
@RestController
@RequestMapping("/ums-member")
public class UmsMemberController {

    @Autowired
    UmsMemberService umsMemberService;

    @RequestMapping(value = "/findByUsername", method = RequestMethod.POST)
    public Object queryUserByName(@RequestParam(value = "username") String username) {
        return umsMemberService.queryUserByName(username);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Object queryAll() {
        return umsMemberService.queryAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@RequestBody UmsMember umsMember) {
        return umsMemberService.saveUser(umsMember);
    }

}

