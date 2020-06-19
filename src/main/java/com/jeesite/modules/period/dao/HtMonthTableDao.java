/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.period.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.period.entity.HtMonthTable;
import java.util.List;


/**
 * 月份表DAO接口
 * @author baixue
 * @version 2020-04-16
 */
@MyBatisDao
public interface HtMonthTableDao extends CrudDao<HtMonthTable> {
    /**
     * 网点平均维修金额表
     * @param htMonthTable 月份表信息
     * @return 平均维修金额
     */
    List<HtMonthTable> pricesList(HtMonthTable htMonthTable);

    /**
     * 网点平均维修金额图
     * @return 平均维修金额
     */
    List<HtMonthTable> getPricesList();

    /**
     * 网点平均维修周期图
     * @return  平均维修周期
     */
    List<HtMonthTable> getPeriodList();

}



