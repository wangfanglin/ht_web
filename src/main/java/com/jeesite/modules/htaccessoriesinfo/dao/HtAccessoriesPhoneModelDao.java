/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htaccessoriesinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesPhoneModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配件与型号中间表DAO接口
 * @author hongmengzhong
 * @version 2020-02-19
 */
@MyBatisDao
public interface HtAccessoriesPhoneModelDao extends CrudDao<HtAccessoriesPhoneModel> {

    void deleteBuAccessId(@Param("accessoriesInfoId") String accessoriesInfoId);

    List<String> getModelStr(String accessoriesInfoId);
}