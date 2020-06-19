/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.entity.PolicyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * 保单DAO接口
 * @author zhaohaifeng
 * @version 2020-02-24
 */
@MyBatisDao
public interface PolicyInfoDao extends CrudDao<PolicyInfo> {
    /**
     * 保存保单详情
     * @param policyDetails
     */
  //  void insertDetail(@Param("policyDetails") ArrayList<PolicyDetail> policyDetails);

    /**
     * 修改保单限额
     * @param price
     * @param policyId
     */
    void updateSurplusCoverage(@Param("price") double price,@Param("policyId") String policyId);
    /**
     * 加入渤海的保单日志
     * @param uniqueMark
     */
    void insertBhLog(@Param("uniqueMark") String uniqueMark);
    /**
     * 查询渤海的保单日志数量
     * @param uniqueMark
     */
    int check(@Param("uniqueMark") String uniqueMark);

    /**
     * 扣减保单保额
     * @param surplusCoverage
     * @param policyId
     * @param formId
     */
    void insertCoverageLog(@Param("coverage") Double surplusCoverage, @Param("policyId") String policyId, @Param("formId") String formId);

    /**
     * 修改保单的手机品牌 手机型号
     * @param id
     * @param strPhoneBrand
     * @param strPhoneModel
     */
    void updatePhone(@Param("id") String id, @Param("strPhoneBrand") String strPhoneBrand, @Param("strPhoneModel") String strPhoneModel);

    /**
     * 查出保单的最小维修次数
     * @param policyId
     * @return
     */
    String findCountLast(@Param("policyId") String policyId);

    void updatePolicyFlag(@Param("policyId") String policyId);

    String findCountWx(@Param("policyId") String policyId);

    String findCountHx(@Param("policyId") String policyId);
}