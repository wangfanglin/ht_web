/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.provider.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.provider.entity.HtInsuranceProviderInfo;

/**
 * 保险供应商DAO接口
 * @author tangchao
 * @version 2020-02-21
 */
@MyBatisDao
public interface HtInsuranceProviderInfoDao extends CrudDao<HtInsuranceProviderInfo> {
    /**
     * 获取目前保险供应商的总数
     * @return
     */
    int sum();
}