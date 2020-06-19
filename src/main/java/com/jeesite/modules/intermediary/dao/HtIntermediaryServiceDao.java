/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.intermediary.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.intermediary.entity.HtIntermediaryService;

/**
 * 中介服务商DAO接口
 * @author tangchao
 * @version 2020-02-20
 */
@MyBatisDao
public interface HtIntermediaryServiceDao extends CrudDao<HtIntermediaryService> {
    /**
     * 获取目前中介服务商的总数
     * @return
     */
	int sum();
}