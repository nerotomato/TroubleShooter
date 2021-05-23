package com.nerotomato.dynamic.service;

import com.nerotomato.dynamic.entity.PageRequest;
import com.nerotomato.dynamic.entity.PageResult;
import com.nerotomato.dynamic.entity.UmsMember;

/**
 * 会员用户Service
 * Created by nero on 2021/4/28.
 */
public interface UmsMemberService extends BaseService<UmsMember> {

    /**
     * 查询分页数据
     *
     * @return
     */
    public PageResult findByPage(PageRequest pageRequest);
}
