/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.policy.entity.HtFailPolicy;

/**
 * 映射失败保单DAO接口
 * @author zhaohaifeng
 * @version 2020-04-17
 */
@MyBatisDao
public interface HtFailPolicyDao extends CrudDao<HtFailPolicy> {
	
}