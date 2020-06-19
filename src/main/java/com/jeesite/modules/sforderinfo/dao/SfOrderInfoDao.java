/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sforderinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sforderinfo.entity.SfOrderInfo;

/**
 * sf_order_infoDAO接口
 * @author hongmengzhong
 * @version 2020-04-23
 */
@MyBatisDao
public interface SfOrderInfoDao extends CrudDao<SfOrderInfo> {
	
}