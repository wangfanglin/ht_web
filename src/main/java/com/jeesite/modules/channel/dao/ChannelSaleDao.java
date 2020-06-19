/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.channel.entity.ChannelSale;

/**
 * 1.0销售渠道DAO接口
 * @author tangchao
 * @version 2020-05-26
 */
@MyBatisDao
public interface ChannelSaleDao extends CrudDao<ChannelSale> {
	
}