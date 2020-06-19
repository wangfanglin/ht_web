/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.advisory.entity.HtAdvisoryInfo;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ht_advisory_infoDAO接口
 * @author zhaohaifeng
 * @version 2020-03-30
 */
@MyBatisDao
public interface HtAdvisoryInfoDao extends CrudDao<HtAdvisoryInfo> {
    /**
     * 查询工单列表
     * @param htFormInfo
     * @param pageSize
     * @param pageNo
     * @return
     */
    List<HtFormInfo> findFormLsit(@Param("htFormInfo") HtFormInfo htFormInfo, @Param("pageSize") Integer pageSize, @Param("pageNo") Integer pageNo);

    HtAdvisoryInfo findByFormId(@Param("id") String id);

    HtAdvisoryInfo findByTaskId(@Param("taskId") String taskId);

    Integer findCountInOffice(@Param("type") String type, @Param("officeId") String officeId);
}