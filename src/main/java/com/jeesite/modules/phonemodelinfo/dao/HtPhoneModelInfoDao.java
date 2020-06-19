/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.phonemodelinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机型库表DAO接口
 * @author hongmengzhong
 * @version 2020-02-17
 */
@MyBatisDao
public interface HtPhoneModelInfoDao extends CrudDao<HtPhoneModelInfo> {
    /**
     * 查出产品关联的机型ID
     * @param productInfoId
     * @return
     */
    List<String> findIdsByProductId(@Param("productInfoId") String productInfoId);

}