/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyFormHistory;
import com.jeesite.modules.htwaitapplyform.dao.HtWaitApplyFormHistoryDao;

/**
 * 待申请工单历史表Service
 * @author zhaohaifeng
 * @version 2020-04-09
 */
@Service
@Transactional(readOnly=true)
public class HtWaitApplyFormHistoryService extends CrudService<HtWaitApplyFormHistoryDao, HtWaitApplyFormHistory> {
	
	/**
	 * 获取单条数据
	 * @param htWaitApplyFormHistory
	 * @return
	 */
	@Override
	public HtWaitApplyFormHistory get(HtWaitApplyFormHistory htWaitApplyFormHistory) {
		return super.get(htWaitApplyFormHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htWaitApplyFormHistory 查询条件
	 * @param htWaitApplyFormHistory.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtWaitApplyFormHistory> findPage(HtWaitApplyFormHistory htWaitApplyFormHistory) {
		return super.findPage(htWaitApplyFormHistory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htWaitApplyFormHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtWaitApplyFormHistory htWaitApplyFormHistory) {
		super.save(htWaitApplyFormHistory);
	}
	
	/**
	 * 更新状态
	 * @param htWaitApplyFormHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtWaitApplyFormHistory htWaitApplyFormHistory) {
		super.updateStatus(htWaitApplyFormHistory);
	}
	
	/**
	 * 删除数据
	 * @param htWaitApplyFormHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtWaitApplyFormHistory htWaitApplyFormHistory) {
		super.delete(htWaitApplyFormHistory);
	}
	
}