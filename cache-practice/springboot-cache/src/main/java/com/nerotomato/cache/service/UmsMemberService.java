package com.nerotomato.cache.service;

import com.nerotomato.cache.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheConfig;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author nero
 * @since 2021-06-18
 */
public interface UmsMemberService extends IService<UmsMember> {

    Object queryUserByName(String username);

    Object queryAll();

    Object saveUser(UmsMember umsMember);
}

