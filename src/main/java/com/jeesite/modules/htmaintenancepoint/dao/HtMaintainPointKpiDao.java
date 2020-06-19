/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htmaintenancepoint.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintainPointKpi;

/**
 * 维修网点kpi指数DAO接口
 * @author hongmengzhong
 * @version 2020-04-07
 */
@MyBatisDao
public interface HtMaintainPointKpiDao extends CrudDao<HtMaintainPointKpi> {
    void truncateInfo();

    HtMaintainPointKpi getTableInfo(String pointId);
}