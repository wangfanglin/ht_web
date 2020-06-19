/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyFormHistory;

/**
 * 待申请工单历史表DAO接口
 * @author zhaohaifeng
 * @version 2020-04-09
 */
@MyBatisDao
public interface HtWaitApplyFormHistoryDao extends CrudDao<HtWaitApplyFormHistory> {
	
}