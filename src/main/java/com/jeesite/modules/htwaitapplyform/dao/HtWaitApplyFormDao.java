/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 待申请工单DAO接口
 * @author hongmengzhong
 * @version 2020-04-01
 */
@MyBatisDao
public interface HtWaitApplyFormDao extends CrudDao<HtWaitApplyForm> {

    List<ProcessListingEntity> selectProcessInstanceByQueryCriteria(ProcessListingEntity processListingEntity);

    String getBypolicyId(@Param("policyId") String policyId);

    HtWaitApplyForm findByFormId(@Param("formId") String formId);
}