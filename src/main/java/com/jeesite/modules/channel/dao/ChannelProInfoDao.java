/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.channel.entity.ChannelProInfo;

/**
 * 1.0销售渠道商与产品类型关联表DAO接口
 * @author tangchao
 * @version 2020-05-26
 */
@MyBatisDao
public interface ChannelProInfoDao extends CrudDao<ChannelProInfo> {
	
}