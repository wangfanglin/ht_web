/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.user.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.user.entity.HtUserInfo;

/**
 * ht_user_infoDAO接口
 * @author lichao
 * @version 2020-04-20
 */
@MyBatisDao
public interface HtUserInfoDao extends CrudDao<HtUserInfo> {
	
}