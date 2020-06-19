/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.renew.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.renew.entity.HtRenewHistory;

/**
 * 换新工单历史DAO接口
 * @author lichao
 * @version 2020-03-25
 */
@MyBatisDao
public interface HtRenewHistoryDao extends CrudDao<HtRenewHistory> {
	
}