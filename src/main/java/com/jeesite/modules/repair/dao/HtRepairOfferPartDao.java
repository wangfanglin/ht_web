/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.repair.entity.HtRepairOfferPart;

/**
 * 维修工单-待报价-损害部位表DAO接口
 * @author lichao
 * @version 2020-02-27
 */
@MyBatisDao
public interface HtRepairOfferPartDao extends CrudDao<HtRepairOfferPart> {
	
}