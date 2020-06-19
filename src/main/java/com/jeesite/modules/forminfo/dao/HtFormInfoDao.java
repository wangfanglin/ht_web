/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.Map;

/**
 * 工单主表DAO接口
 * @author lichao
 * @version 2020-02-27
 */
@MyBatisDao
public interface HtFormInfoDao extends CrudDao<HtFormInfo> {

    int findFormAmount(@Param("policyId") String policyId);

    List<HtFormInfo> findListByPolicyId(@Param("policyId") String policyId);

    Map<String, String> findMainPointCondition(@Param("formId")String formId, @Param("childId") String childId);

    Integer findFormCount(String officeId);

    Integer findOrderSum(String officeId);


    /**
     * 根据保单查询工单
     * @param policyId
     */

    Double getPointSumPrice(String organizationId);

    List<HtFormInfo> findTotalFromInOffice(String organizationId);

    List<Map<String, String>> findFormOffice();

    int findDateSales(@Param("officeId") String officeId, @Param("dates") String dates);

    List<HtFormInfo> findPageByPolicyId(@Param("formInfo") HtFormInfo formInfo);
    /**
     * 根据保单查询工单
     * @param policyId
     */
    String getBypolicyId(@Param("policyId") String policyId);

    /**
     * 查询需要挂起的工单
     * @return
     */
    List<String> findPutUpForm();
    /**
     * 查询挂起工单
     * @param htFormInfo
     * @return
     */
    List<HtFormInfo> findputUpPage(@Param("id") String id, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("manageStatus") String manageStatus, @Param("formStatus") String formStatus);
    /**
     * 根据保单查询1.0工单
     * @param formInfo
     * @return
     */
    List<HtFormInfo> findOldFormByPolicyId(@Param("formInfo") HtFormInfo formInfo);
}