package com.nerotomato.nacos.user.service.impl;

import com.nerotomato.nacos.user.entity.User;
import com.nerotomato.nacos.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nerotomato
 * @create 2021/7/16 上午11:06
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Map<String, User> userMap = new ConcurrentHashMap();

    @Override
    public User create(User user) {
        return userMap.put(user.getUsername(), user);
    }

    @Override
    public User getByUsername(String username) {
        return userMap.get(username);
    }

    @Override
    public User update(User user) {
        return userMap.put(user.getUsername(), user);
    }

    @Override
    public User delete(String username) {
        return userMap.remove(username);
    }

}
