/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.renew.entity.HtRenewEndForm;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import com.jeesite.modules.repair.entity.HtRepairEndForm;
import com.jeesite.modules.repair.entity.RepairListEntity;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 维修工单-待联系客户DAO接口
 * @author lichao
 * @version 2020-02-18
 */
@MyBatisDao
public interface HtRepairClientFormDao extends CrudDao<HtRepairClientForm> {


    List<RepairListEntity> findRepairList(RepairListEntity repairListEntity);


    //查询维修历史记录
    List<HtRepairClientForm> findHistory(HtFormInfo htFormInfo);


    List<Map<String, Object>> findClaimData(@Param("claimId") String claimId);

    //查询部件
    List<HtAssemblyUnit> findBuJian(@Param("bujianId")String bujianId);


    List<HtRepairEndForm> findEndList(HtRepairEndForm htRepairEndForm);

    List<HtRepairEndForm> findAllList(HtRepairEndForm htRepairEndForm);

}