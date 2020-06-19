package com.jeesite.modules.policy.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.policy.entity.HtFailPolicy;

import javax.annotation.Resource;

@MyBatisDao
public interface ConvertDao {

    int insertJxConvert(String id);

    int insertOldConvert(String id);
}
