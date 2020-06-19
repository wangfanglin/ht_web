/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.policy.entity.PolicyDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 保单详情表DAO接口
 * @author zhaohaifeng
 * @version 2020-04-13
 */
@MyBatisDao
public interface PolicyDetailDao extends CrudDao<PolicyDetail> {
    /**
     * 终止子权益
     * @param policyId
     * @param child
     */
    void updateEquity(@Param("policyId") String policyId, @Param("child") String child);

    /**
     * 查询1.0的工单
     * @param commonFromId
     * @return
     */
    Map<String,Object> findOrder(@Param("commonFromId") String commonFromId);
}