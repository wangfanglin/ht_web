/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htmaintenancepoint.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 维修网点表DAO接口
 * @author hongmengzhong
 * @version 2020-02-22
 */
@MyBatisDao
public interface HtMaintenancePointDao extends CrudDao<HtMaintenancePoint> {

    void deleteByTableName(@Param("tableName") String tableName, @Param("pointId")String pointId);

    void saveTableInfo(Map<String, Object> maintainBrandMap);

    List<String> getStrListByTableName(@Param("tableName") String tableName,@Param("columnName") String columnName,@Param("pointId")String pointId);

    String getProvinceCityCode(@Param("areaCode") String areaCode);

    List<HtMaintenancePoint> findListRewrite(HtMaintenancePoint htMaintenancePoint);

    List<HtMaintenancePoint> getStatus(HtMaintenancePoint htMaintenancePoint);

    List<Map<String, String>> findPointConditionResult();

    HtMaintenancePoint findInfoByJG(String organizationId);

    List<HtMaintenancePoint> findPointOfficeList();
}