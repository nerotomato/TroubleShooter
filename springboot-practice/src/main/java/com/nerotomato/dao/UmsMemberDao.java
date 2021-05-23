package com.nerotomato.dao;

import com.nerotomato.entity.UmsMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UmsMemberDao接口类继承Jpa默认接口类
 * 进行数据持久化
 * Created by nero on 2021/4/28.
 */
public interface UmsMemberDao extends JpaRepository<UmsMember, Long> {
}
