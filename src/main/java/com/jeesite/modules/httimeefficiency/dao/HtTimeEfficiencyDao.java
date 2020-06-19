/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.httimeefficiency.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.httimeefficiency.entity.HtTimeEfficiency;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

/**
 * 维修时效表DAO接口
 * @author hongmengzhong
 * @version 2020-04-07
 */
@MyBatisDao
public interface HtTimeEfficiencyDao extends CrudDao<HtTimeEfficiency> {

    void updateFirst(HtTimeEfficiency htTimeEfficiency);

    Integer getTimelinessSum(String formId);

    Integer getFormPastDueAmount(@Param("officeId") String officeId,@Param("type") String type);
}