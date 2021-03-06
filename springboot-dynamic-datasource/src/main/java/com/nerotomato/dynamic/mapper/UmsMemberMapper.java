package com.nerotomato.dynamic.mapper;

import com.nerotomato.dynamic.entity.UmsMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by nero on 2021/4/30.
 */
@Mapper
public interface UmsMemberMapper {
    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    public int insertMember(UmsMember user);

    /**
     * 删除用户
     *
     * @param user
     */
    public int deleteMemberbyUsername(UmsMember user);

    /**
     * 查询全部用户
     *
     * @return
     */
    public List<UmsMember> queryAllMembers();

    /**
     * 查询分页数据
     *
     * @return
     */
    public List<UmsMember> queryMembersByPage();


    int updateMemberbyUsername(UmsMember user);
}
