/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.number.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.number.entity.HtDateTable;
import com.jeesite.modules.period.entity.HtMonthTable;

import java.util.List;
import java.util.Map;

/**
 * 数量表DAO接口
 * @author baixue
 * @version 2020-04-20
 */
@MyBatisDao
public interface HtDateTableDao extends CrudDao<HtDateTable> {
    /**
     * 报案量图
     * @return 与渠道相对应的日报案量和月报案量
     */
    List<HtDateTable> pushData();
    /**
     * 超期件数表
     * @param htDateTable 数量表信息
     * @return 存在超期情况的网点以及对应超期件数
     */
    List<HtDateTable> overTime(HtDateTable htDateTable);

    /**
     * 超期件数图
     * @return 当月超期数量
     */
    List<Map<String, Object>> getOverOffice();
    /**
     * 拒单量表
     * @param htDateTable 数量表信息
     * @return  拒单量
     */
    List<HtDateTable> refuseDate(HtDateTable htDateTable);

    /**
     * 拒单量图
     * @return  拒单量
     */
    List<Integer> refuseNumber();


    /**
     * 通话量
     * @param htDateTable 数量表信息
     * @return 通话量
     */
    List<HtDateTable> phoneNum(HtDateTable htDateTable);

}