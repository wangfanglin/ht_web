/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htuserapplyinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户在线申请表DAO接口
 * @author tangchao
 * @version 2020-03-10
 */
@MyBatisDao
public interface HtUserApplyInfoDao extends CrudDao<HtUserApplyInfo> {
    /**
     * 根据工单ID 查新在线申请信息
     * @param formId
     */
    HtUserApplyInfo findByFormId(@Param("formId") String formId);

    Map<String,Object> finfArea(@Param("areaId") String areaId);
    /**
     * 查询所有图片
     * @param id
     * @return
     */
    Map<String,String> findPartImgs(@Param("id") String id);

    void insertApplyLog(@Param("applyId") String applyId);

    int check(@Param("applyId") String applyId);
    /**
     * 查询申请信息的userID
     * @param policyId
     * @return
     */
    String findUserByPolicy(@Param("policyId") String policyId);

    /**
     * 通过案件号获取对象
     * @param htUserApplyInfo
     * @return
     */
    HtUserApplyInfo getByClmNo(HtUserApplyInfo htUserApplyInfo);
}