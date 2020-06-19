/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.product.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品表（权益）DAO接口
 * @author zhaohaifeng
 * @version 2020-02-17
 */
@MyBatisDao
public interface ProductInfoDao extends CrudDao<ProductInfo> {

    void insertProductPhoneModuleMiddle(@Param("productInfoId") String productInfoId, @Param("phoneModuleIds") List<String> phoneModuleIds);

    /**
     * 删除产品与机型的关联信息
     * @param productInfoId
     */
    void deleteProductPhoneModuleMiddle(@Param("productInfoId") String productInfoId);
}