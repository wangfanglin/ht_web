/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.history.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.history.entity.HtHistory;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;

/**
 * 操作历史总表DAO接口
 * @author tangchao
 * @version 2020-03-02
 */
@MyBatisDao
public interface HtHistoryDao extends CrudDao<HtHistory> {

    String findUserByOffer(@Param("intermediaryServiceId") String intermediaryServiceId);

    List<String> findVisible(@Param("userCode") String userCode);

    List<HtHistory> findListByType(@Param("workOrderId") String workOrderId, @Param("setList") HashSet<String> setList);

}