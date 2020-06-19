/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.receipt.entity.HtReceiptDataHistory;

/**
 * 收款人信息历史表DAO接口
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@MyBatisDao
public interface HtReceiptDataHistoryDao extends CrudDao<HtReceiptDataHistory> {
	
}