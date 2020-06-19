/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.brandinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;

import java.util.List;

/**
 * 设备品牌表DAO接口
 * @author hongmengzhong
 * @version 2020-02-17
 */
@MyBatisDao
public interface HtBrandInfoDao extends CrudDao<HtBrandInfo> {

    List<HtBrandInfo> getBrandList();

}