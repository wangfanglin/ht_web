/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.repair.entity.HtRepairOfferForm;

/**
 * 维修工单-待报价DAO接口
 * @author lichao
 * @version 2020-02-26
 */
@MyBatisDao
public interface HtRepairOfferFormDao extends CrudDao<HtRepairOfferForm> {
	
}