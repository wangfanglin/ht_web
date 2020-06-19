/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htdutyroster.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.htdutyroster.entity.HtDutyRoster;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 排班表DAO接口
 * @author hongmengzhong
 * @version 2020-02-25
 */
@MyBatisDao
public interface HtDutyRosterDao extends CrudDao<HtDutyRoster> {

    Map<String,String> getOfficeInfoByNo(@Param("organizationNo") String organizationNo);

    String getStaffWhether(HtDutyRoster htDutyRoster);

    String getDutyRosterStaffUserId(@Param("officeId") String officeId);


    String getDutyRosterStaffUserIdByRole(@Param("roleId") String roleId);

    void saveRosterLog(@Param("userId") String userId);

    int findWhetherBeOnDuty(@Param("userCode")String userCode);
}