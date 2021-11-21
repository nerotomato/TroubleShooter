package com.nerotomato.cache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nerotomato.cache.entity.UmsMember;
import com.nerotomato.cache.mapper.UmsMemberMapper;
import com.nerotomato.cache.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author nero
 * @since 2021-06-18
 */
@Service
@Slf4j
//添加类级别缓存
@CacheConfig(cacheNames = "umsMembers")
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Override
    // 开启spring cache  key - EL表达式
    //@Cacheable(key = "#umsMember.id")
    @Cacheable
    public Object queryUserByName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        log.info("======== query user of:" + username);
        return umsMemberMapper.selectOne(queryWrapper);
    }

    @Override
    // 开启spring cache
    @Cacheable
    public Object queryAll() {
        log.info("======== query all user");
        return umsMemberMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object saveUser(UmsMember umsMember) {
        return umsMemberMapper.insert(umsMember);
    }

}
