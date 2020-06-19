/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.policy.entity.JxPolicyInfo;

import java.util.List;

/**
 * jx_policy_infoDAO接口
 * @author tangchao
 * @version 2020-04-27
 */
@MyBatisDao
public interface JxPolicyInfoDao extends CrudDao<JxPolicyInfo> {

    /**
     *
     * @param jxPolicyInfo
     * @return
     */
    List<JxPolicyInfo> findNewList(JxPolicyInfo jxPolicyInfo);
}