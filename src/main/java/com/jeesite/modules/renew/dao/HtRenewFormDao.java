/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.renew.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.renew.entity.HtRenewEndForm;
import com.jeesite.modules.renew.entity.HtRenewForm;
import com.jeesite.modules.renew.entity.RenewListEntity;
import com.jeesite.modules.repair.entity.RepairListEntity;

import java.util.List;

/**
 * 换新工单DAO接口
 * @author lichao
 * @version 2020-03-25
 */
@MyBatisDao
public interface HtRenewFormDao extends CrudDao<HtRenewForm> {


    List<RenewListEntity> findRenewList(RenewListEntity renewListEntity);


    List<HtRenewEndForm> findEndList(HtRenewEndForm htRenewEndForm);

    List<HtRenewEndForm> findAllList(HtRenewEndForm htRenewEndForm);



}