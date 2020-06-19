/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.repair.entity.HtRepairClientHistory;
import com.jeesite.modules.repair.dao.HtRepairClientHistoryDao;

/**
 * 维修工单操作历史Service
 * @author lihao
 * @version 2020-03-09
 */
@Service
@Transactional(readOnly=true)
public class HtRepairClientHistoryService extends CrudService<HtRepairClientHistoryDao, HtRepairClientHistory> {
	
	/**
	 * 获取单条数据
	 * @param htRepairClientHistory
	 * @return
	 */
	@Override
	public HtRepairClientHistory get(HtRepairClientHistory htRepairClientHistory) {
		return super.get(htRepairClientHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htRepairClientHistory 查询条件
	 * @param htRepairClientHistory.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtRepairClientHistory> findPage(HtRepairClientHistory htRepairClientHistory) {
		return super.findPage(htRepairClientHistory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htRepairClientHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtRepairClientHistory htRepairClientHistory) {
		super.save(htRepairClientHistory);
	}
	
	/**
	 * 更新状态
	 * @param htRepairClientHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtRepairClientHistory htRepairClientHistory) {
		super.updateStatus(htRepairClientHistory);
	}
	
	/**
	 * 删除数据
	 * @param htRepairClientHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtRepairClientHistory htRepairClientHistory) {
		super.delete(htRepairClientHistory);
	}
	
}