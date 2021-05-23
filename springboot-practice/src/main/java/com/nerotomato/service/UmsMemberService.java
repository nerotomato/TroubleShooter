package com.nerotomato.service;

import com.nerotomato.entity.PageQuery;
import com.nerotomato.entity.UmsMember;

import java.util.List;

/**
 * 会员用户Service
 * Created by nero on 2021/4/28.
 */
public interface UmsMemberService {
    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    public Object save(UmsMember user);

    /**
     * 删除用户
     *
     * @param user
     */
    public void delete(UmsMember user);

    /**
     * 查询全部用户
     *
     * @return
     */
    public List<UmsMember> findAll();

    /**
     * 查询分页数据
     *
     * @return
     */
    public Object findByPage(PageQuery pageQuery);
}
