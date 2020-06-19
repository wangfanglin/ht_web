/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementformhistory.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.entity.HtClaimSettlementFormHistory;

/**
 * 在线理赔历史表DAO接口
 * @author hongmengzhong
 * @version 2020-03-07
 */
@MyBatisDao
public interface HtClaimSettlementFormHistoryDao extends CrudDao<HtClaimSettlementFormHistory> {
	
}