/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementform.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import com.jeesite.modules.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 在线理赔表DAO接口
 * @author hongmengzhong
 * @version 2020-02-28
 */
@MyBatisDao
public interface HtClaimSettlementFormDao extends CrudDao<HtClaimSettlementForm> {

    void saveMiddleInfo(@Param("dataid") String dataid, @Param("settlementData") String settlementData);

    List<String> getSettlementDataList(@Param("settlementDataId") String settlementDataId);

    Map<String, String> getUnitStrInfo(@Param("formId") String formId);

    List<Map<String,Object>> getAccessoriesInfoList(@Param("assemblyId")String assemblyId,@Param("phoneModelId")String phoneModelId);


    List<ProcessListingEntity> selectProcessInstanceByQueryCriteria(ProcessListingEntity processListingEntity);

    HtClaimSettlementForm getByFormId(@Param("formId") String formId);
}