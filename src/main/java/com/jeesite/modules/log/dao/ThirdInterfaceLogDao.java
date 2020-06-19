/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.log.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.log.entity.ThirdInterfaceLog;

/**
 * 第三方接口调用日志DAO接口
 * @author tangchao
 * @version 2020-04-02
 */
@MyBatisDao
public interface ThirdInterfaceLogDao extends CrudDao<ThirdInterfaceLog> {
	
}