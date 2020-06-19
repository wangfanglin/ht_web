/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 组合产品表DAO接口
 * @author zhaoheifeng
 * @version 2020-02-18
 */
@MyBatisDao
public interface HtGroupProductInfoDao extends CrudDao<HtGroupProductInfo> {
    /**
     * 存入组合产品和组合子产品的中间表
     * @param groupProductId
     * @param productChildId
     */
    void insertMiddle(@Param("groupProductId") String groupProductId, @Param("productChildId") String productChildId);
    /**
     * 查询产品（权益）
     * @param productId
     * @return
     */
    ProductInfo findProductInfo(@Param("productId") String productId);

    List<Map<String,String>> findProductName(@Param("id") String id);
}