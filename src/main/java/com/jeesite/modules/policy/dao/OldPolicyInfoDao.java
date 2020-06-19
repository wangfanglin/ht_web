/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.policy.entity.JxPolicyInfo;
import com.jeesite.modules.policy.entity.OldPolicyInfo;

import java.util.List;

/**
 * policy_infoDAO接口
 * @author tangchao
 * @version 2020-04-27
 */
@MyBatisDao
public interface OldPolicyInfoDao extends CrudDao<OldPolicyInfo> {

    List<OldPolicyInfo> findNewList(OldPolicyInfo oldPolicyInfo);
}