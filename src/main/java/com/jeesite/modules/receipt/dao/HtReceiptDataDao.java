/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.receipt.entity.HtReceiptData;
import org.apache.ibatis.annotations.Param;

/**
 * 收款人信息表DAO接口
 * @author zhaohaifeng
 * @version 2020-03-23
 */
@MyBatisDao
public interface HtReceiptDataDao extends CrudDao<HtReceiptData> {

    HtReceiptData findByFormId(@Param("formId") String formId);
}