package com.nerotomato.oauth.service;

import cn.hutool.core.collection.CollUtil;
import com.nerotomato.oauth.constant.MessageConstant;
import com.nerotomato.oauth.entity.SecurityUser;
import com.nerotomato.oauth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理业务类
 *
 * @author nerotomato
 * @create 2021/8/1 下午4:35
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    private List<User> userList;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        String encodePasswd = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User(1l, "nero", encodePasswd, 1, CollUtil.toList("ADMIN")));
        userList.add(new User(2l, "dante", encodePasswd, 1, CollUtil.toList("STAFF")));
        userList.add(new User(3l, "vergil", encodePasswd, 1, CollUtil.toList("STAFF")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> findUserList = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(findUserList)) {
            throw new UsernameNotFoundException("password invalid!");
        }
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
