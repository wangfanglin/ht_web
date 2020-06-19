/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 组合产品子表DAO接口
 * @author zhaofaifeng
 * @version 2020-02-18
 */
@MyBatisDao
public interface HtGroupProductChildDao extends CrudDao<HtGroupProductChild> {
    /**
     * 查出子表中的终止规则信息
     * @param productInfoId
     * @return
     */
    HtGroupProductChild getByProductId(@Param("productInfoId") String productInfoId);

    /**
     * 删除子表与产品的关联信息
     * @param productInfoId
     */
    void deleteByProductId(@Param("productInfoId") String productInfoId);

    /**
     * 删除组合产品
     * @param productInfoId
     */
    void delGroupProduct(@Param("productInfoId") String productInfoId);

    List<Map<String,String>> findMiddel(@Param("groupProductId") String groupProductId);

    void delMiddel(@Param("middelid") String middelid);
    /**
     * 根据组合产品ID查询子产品信息
     * @param groupProductId
     * @return
     */
    List<HtGroupProductChild> findChildByGroupId(@Param("groupProductId") String groupProductId);

    void deleteById(@Param("childId") String childId);

    List<ProductInfo> listProduct(@Param("productType") String productType, @Param("groupProductId") String groupProductId);
    /**
     * 查询关联的机型
     * @param groupProductInfoId
     * @return
     */
    String findPhoneMod(@Param("groupProductInfoId") String groupProductInfoId);
}