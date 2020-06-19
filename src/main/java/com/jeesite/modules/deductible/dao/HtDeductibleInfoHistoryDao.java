/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.deductible.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.deductible.entity.HtDeductibleInfoHistory;

/**
 * 自付款信息历史表DAO接口
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@MyBatisDao
public interface HtDeductibleInfoHistoryDao extends CrudDao<HtDeductibleInfoHistory> {
	
}