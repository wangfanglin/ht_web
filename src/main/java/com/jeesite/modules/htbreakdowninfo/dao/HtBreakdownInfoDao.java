/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbreakdowninfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htbreakdowninfo.entity.HtBreakdownInfo;
import com.jeesite.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 维修故障表DAO接口
 * @author hongmengzhong
 * @version 2020-02-20
 */
@MyBatisDao
public interface HtBreakdownInfoDao extends CrudDao<HtBreakdownInfo> {

    void saveBreakDownMiddle(List<Map<String, Object>> middleInfo);
    List<HtBreakdownInfo> findAssemblyConcat(HtBreakdownInfo htBreakdownInfo);
    List<Map<String,Object>> getBreakDownAssemblyList(@Param("breakdownId")String breakdownId);

    void delBreakDownMiddle(@Param("breakDownInfoId") String breakDownInfoId);

    List<Map<String, Object>> getAreaByLevel(String level);
    List<Area> getAreaLinkage(List<String> strList);
    List<Map<String, Object>> getOfficeList();
    List<Map<String, Object>> getBreakDownInfo(@Param("counts") int count);
}