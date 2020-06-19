/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimdisqualificationlog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.settlementform.htclaimdisqualificationlog.entity.HtClaimDisqualificationLog;
import com.jeesite.modules.settlementform.htclaimdisqualificationlog.dao.HtClaimDisqualificationLogDao;

/**
 * 核赔不合格日志表Service
 * @author hongmengzhong
 * @version 2020-04-07
 */
@Service
@Transactional(readOnly=true)
public class HtClaimDisqualificationLogService extends CrudService<HtClaimDisqualificationLogDao, HtClaimDisqualificationLog> {
	
	/**
	 * 获取单条数据
	 * @param htClaimDisqualificationLog
	 * @return
	 */
	@Override
	public HtClaimDisqualificationLog get(HtClaimDisqualificationLog htClaimDisqualificationLog) {
		return super.get(htClaimDisqualificationLog);
	}
	
	/**
	 * 查询分页数据
	 * @param htClaimDisqualificationLog 查询条件
	 * @param htClaimDisqualificationLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtClaimDisqualificationLog> findPage(HtClaimDisqualificationLog htClaimDisqualificationLog) {
		return super.findPage(htClaimDisqualificationLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htClaimDisqualificationLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtClaimDisqualificationLog htClaimDisqualificationLog) {
		super.save(htClaimDisqualificationLog);
	}
	
	/**
	 * 更新状态
	 * @param htClaimDisqualificationLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtClaimDisqualificationLog htClaimDisqualificationLog) {
		super.updateStatus(htClaimDisqualificationLog);
	}
	
	/**
	 * 删除数据
	 * @param htClaimDisqualificationLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtClaimDisqualificationLog htClaimDisqualificationLog) {
		super.delete(htClaimDisqualificationLog);
	}
	
}