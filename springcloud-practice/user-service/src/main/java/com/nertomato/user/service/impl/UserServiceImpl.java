package com.nertomato.user.service.impl;

import com.nertomato.user.entity.User;
import com.nertomato.user.service.UserService;
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
    public Object save(User user) {
        return userMap.put(user.getUsername(), user);
    }

    @Override
    public Object get(String username) {
        return userMap.get(username);
    }

    @Override
    public Object update(User user) {
        return userMap.put(user.getUsername(), user);
    }

    @Override
    public Object delete(String username) {
        return userMap.remove(username);
    }

}
