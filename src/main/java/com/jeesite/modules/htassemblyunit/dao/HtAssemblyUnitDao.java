/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htassemblyunit.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 维修部件表DAO接口
 * @author hongmengzhong
 * @version 2020-02-18
 */
@MyBatisDao
public interface HtAssemblyUnitDao extends CrudDao<HtAssemblyUnit> {

    List<HtAssemblyUnit> findListByStart(@Param("isMainFlag") String isMainFlag);
    /**
     * 保存产品 部件中间表
     * @param productInfoId
     * @param assemblyIds
     */
    void insertProductAssemblyMiddle(@Param("productInfoId") String productInfoId, @Param("assemblyIds") List<String> assemblyIds);
    /**
     * 查出产品关联的部件ID
     * @param productInfoId
     * @return
     */
    List<String> findIdsByProductId(@Param("productInfoId") String productInfoId);
    /**
     * 删除产品与部件的关联信息
     * @param productInfoId
     */
    void deleteProductAssemblyMiddle(@Param("productInfoId") String productInfoId);
    /**
     * 查出与产品关联的部件
     * @return
     */
    List<HtAssemblyUnit> findAssemblyByProductId(@Param("productInfo") String productInfo);
}