package com.nerotomato.separate.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nerotomato.separate.entity.PageRequest;
import com.nerotomato.separate.entity.PageResult;
import com.nerotomato.separate.entity.UmsMember;
import com.nerotomato.separate.mapper.UmsMemberMapper;
import com.nerotomato.separate.service.UmsMemberService;
import com.nerotomato.separate.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UmsMemberMapper umsMemberMapper;

    @Override
    public int save(UmsMember user) {
        return umsMemberMapper.insertMember(user);
    }

    @Override
    public int delete(UmsMember user) {
        umsMemberMapper.deleteMemberByUsername(user);
        return 0;
    }

    @Override
    public int update(UmsMember object) {
        return 0;
    }

    @Override
    public UmsMember find(UmsMember object) {
        return null;
    }

    @Override
    public List<UmsMember> findAll() {
        return umsMemberMapper.queryAllMembers();
    }

    @Override
    public PageResult findByPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest
     * @return
     */
    private PageInfo<UmsMember> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPage();
        int pageSize = pageRequest.getSize();
        PageHelper.startPage(pageNum, pageSize);
        List<UmsMember> umsMemberList = umsMemberMapper.queryMembersByPage();
        return new PageInfo<UmsMember>(umsMemberList);
    }
}
