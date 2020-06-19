/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.forminfo.entity.HtFormOperation;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

/**
 * 工单操作表DAO接口
 * @author zhaohaifeng
 * @version 2020-03-02
 */
@MyBatisDao
public interface HtFormOperationDao extends CrudDao<HtFormOperation> {


    HtFormOperation findByFormId(@Param("formId") String formId);

    ArrayList<Map<String,String>> findUser(@Param("repair_charge") String repair_charge);

    HtFormOperation findOpByFormId(@Param("formId") String formId);
}