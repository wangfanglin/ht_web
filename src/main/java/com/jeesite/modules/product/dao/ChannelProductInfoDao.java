/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 渠道产品表DAO接口
 * @author zhaohaifeng
 * @version 2020-02-20
 */
@MyBatisDao
public interface ChannelProductInfoDao extends CrudDao<ChannelProductInfo> {

    /**
     * 查询出理赔资料清单
     * @return
     */
    List<Map<String,Object>> findClaimData();

    /**
     * 插入到渠道产品与理赔资料的中间表
     * @param channelProductId
     * @param list
     */
    void insertChannelClaimData(@Param("channelProductId") String channelProductId, @Param("list") List<String> list);

    /**
     * 删除与理赔信息中间表的信息
     * @param channelProductId
     */
    void delChannelClaimData(@Param("channelProductId") String channelProductId);
    /**
     * 根据渠道产品ID 查出关联的理赔信息
     * @param channelProductId
     * @return
     */
    List<Map<String,Object>> findClaimDataByChannelProductId(@Param("channelProductId") String channelProductId);

    Double findDeductible(@Param("policyId") String policyId);

}