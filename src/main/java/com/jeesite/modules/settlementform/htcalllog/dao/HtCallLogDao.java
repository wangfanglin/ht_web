/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htcalllog.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.settlementform.htcalllog.entity.HtCallLog;
import org.apache.ibatis.annotations.Param;

/**
 * 通话记录表DAO接口
 * @author hongmengzhong
 * @version 2020-03-12
 */
@MyBatisDao
public interface HtCallLogDao extends CrudDao<HtCallLog> {

    /**
     * 根据用户ID获取最新的记录
     * @param userCode      用户ID
     * @param type          类型 0：呼出 1：呼入
     * @return
     */
    HtCallLog getByUserId(@Param("userCode") String userCode,@Param("type")String type);

    int findListBuStatus(String type);

    /**
     * 根据工号查找第一个 用户的id
     * @param empId 工号
     * @return  用户id
     */
    String getUserIdByEmpNoFirst (String empId);
}