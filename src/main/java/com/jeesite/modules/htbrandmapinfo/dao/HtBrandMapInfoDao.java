/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbrandmapinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htbrandmapinfo.entity.HtBrandMapInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 品牌映射表DAO接口
 * @author hongmengzhong
 * @version 2020-02-19
 */
@MyBatisDao
public interface HtBrandMapInfoDao extends CrudDao<HtBrandMapInfo> {

    HtBrandMapInfo findInfoBrandId(@Param("brandId") String brandId);

    String getMapBrandId(@Param("name") String name);
    String getDistributionId(@Param("officeName") String officeName);
    String getMap(HtBrandMapInfo htBrandMapInfo);
    void saveMap(HtBrandMapInfo htBrandMapInfo);

    /**
     *  根据销售渠道ID和渠道商原品牌获取对象
     * @return
     */
    HtBrandMapInfo getByDistributionIdAndOriginalBrand(HtBrandMapInfo htBrandMapInfo);
}