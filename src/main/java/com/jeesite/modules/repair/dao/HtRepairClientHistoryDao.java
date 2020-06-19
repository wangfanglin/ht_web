/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.repair.entity.HtRepairClientHistory;

/**
 * 维修工单操作历史DAO接口
 * @author lihao
 * @version 2020-03-09
 */
@MyBatisDao
public interface HtRepairClientHistoryDao extends CrudDao<HtRepairClientHistory> {
	
}