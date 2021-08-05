package com.nerotmato.api.component;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.nerotmato.api.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息
 *
 * @author nerotomato
 * @create 2021/8/3 下午5:28
 */
@Component
public class LoginUserInfoHolder {
    
    public User getCurrentUser() {
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userInfo = request.getHeader("user");
        JSONObject jsonObject = new JSONObject(userInfo);
        User user = new User();
        //get the user_name from jsonObject
        //获取user_name字段
        user.setUsername(jsonObject.getStr("user_name"));
        user.setId(Convert.toLong(jsonObject.get("id")));
        user.setRoles(Convert.toList(String.class, jsonObject.get("authorities")));
        return user;
    }
}
