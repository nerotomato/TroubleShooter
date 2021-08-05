package com.nertomato.user.service;

import com.nertomato.user.entity.User;

/**
 * @author nerotomato
 * @create 2021/7/16 上午11:05
 */
public interface UserService {

    User create(User user);

    User getByUsername(String username);

    User update(User user);

    User delete(String username);
}
