/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimdisqualificationlog.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.settlementform.htclaimdisqualificationlog.entity.HtClaimDisqualificationLog;

/**
 * 核赔不合格日志表DAO接口
 * @author hongmengzhong
 * @version 2020-04-07
 */
@MyBatisDao
public interface HtClaimDisqualificationLogDao extends CrudDao<HtClaimDisqualificationLog> {
	
}