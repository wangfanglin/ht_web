/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.test.entity.TestForm;

/**
 * test_formDAO接口
 * @author tangchao
 * @version 2020-03-01
 */
@MyBatisDao
public interface TestFormDao extends CrudDao<TestForm> {
	
}