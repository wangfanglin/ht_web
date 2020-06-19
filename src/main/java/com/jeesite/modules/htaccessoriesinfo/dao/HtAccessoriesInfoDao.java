/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htaccessoriesinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesInfo;

import java.util.List;

/**
 * 配件表DAO接口
 * @author hongmengzhong
 * @version 2020-02-19
 */
@MyBatisDao
public interface HtAccessoriesInfoDao extends CrudDao<HtAccessoriesInfo> {
	List<HtAccessoriesInfo> getAccessoriesInfoList(HtAccessoriesInfo htAccessoriesInfo);

}