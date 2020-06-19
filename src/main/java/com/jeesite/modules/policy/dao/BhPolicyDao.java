/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.policy.entity.BhPolicy;

/**
 * bh_policyDAO接口
 * @author tangchao
 * @version 2020-05-07
 */
@MyBatisDao
public interface BhPolicyDao extends CrudDao<BhPolicy> {

    BhPolicy getByPolicyinfoid(BhPolicy bhPolicy);
}