/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.channel.entity.HtPurchasingChannels;

/**
 * 采购渠道表DAO接口
 * @author tangchao
 * @version 2020-02-22
 */
@MyBatisDao
public interface HtPurchasingChannelsDao extends CrudDao<HtPurchasingChannels> {
    /**
     * 获取目前采购渠道的总数
     * @return
     */
    int sum();
}