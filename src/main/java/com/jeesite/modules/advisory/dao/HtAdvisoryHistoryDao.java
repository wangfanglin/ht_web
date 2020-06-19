/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.advisory.entity.HtAdvisoryHistory;

/**
 * ht_advisory_historyDAO接口
 * @author zhaohaifeng
 * @version 2020-03-31
 */
@MyBatisDao
public interface HtAdvisoryHistoryDao extends CrudDao<HtAdvisoryHistory> {
	
}