package com.nertomato.user.service;

import com.nertomato.user.entity.User;

/**
 * @author nerotomato
 * @create 2021/7/16 上午11:05
 */
public interface UserService {

    Object save(User user);

    Object get(String username);

    Object update(User user);

    Object delete(String username);
}
