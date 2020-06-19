/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expressage.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.expressage.entity.HtExpressage;

/**
 * 快递渠道DAO接口
 * @author tangchao
 * @version 2020-02-22
 */
@MyBatisDao
public interface HtExpressageDao extends CrudDao<HtExpressage> {
	
}