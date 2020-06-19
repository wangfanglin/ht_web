/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.deductible.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.deductible.entity.HtDeductibleInfo;

/**
 * 自付款信息表DAO接口
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@MyBatisDao
public interface HtDeductibleInfoDao extends CrudDao<HtDeductibleInfo> {
	
}