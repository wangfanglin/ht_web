/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.repair.entity.HtRepairOfferPartHistory;

/**
 * 维修工单-待报价-损害部位表DAO接口
 * @author lichao
 * @version 2020-03-09
 */
@MyBatisDao
public interface HtRepairOfferPartHistoryDao extends CrudDao<HtRepairOfferPartHistory> {
	
}