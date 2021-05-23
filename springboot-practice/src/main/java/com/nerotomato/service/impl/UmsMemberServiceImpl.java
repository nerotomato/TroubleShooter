package com.nerotomato.service.impl;

import com.nerotomato.dao.UmsMemberDao;
import com.nerotomato.entity.PageQuery;
import com.nerotomato.entity.UmsMember;
import com.nerotomato.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员用户Service实现类
 * Created by nero on 2021/4/28.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    //注入dao数据库持久化操作类
    @Autowired
    private UmsMemberDao umsMemberDao;

    @Override
    public Object save(UmsMember user) {
        return umsMemberDao.save(user);
    }

    @Override
    public void delete(UmsMember user) {
        umsMemberDao.delete(user);
    }

    @Override
    public List<UmsMember> findAll() {
        return umsMemberDao.findAll();
    }

    @Override
    public Object findByPage(PageQuery pageQuery) {
        return umsMemberDao.findAll(PageRequest.of(pageQuery.getPage(), pageQuery.getSize()));
    }
}
