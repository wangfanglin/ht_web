/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.repair.entity.HtRepairOfferPartHistory;
import com.jeesite.modules.repair.dao.HtRepairOfferPartHistoryDao;

/**
 * 维修工单-待报价-损害部位表Service
 * @author lichao
 * @version 2020-03-09
 */
@Service
@Transactional(readOnly=true)
public class HtRepairOfferPartHistoryService extends CrudService<HtRepairOfferPartHistoryDao, HtRepairOfferPartHistory> {
	
	/**
	 * 获取单条数据
	 * @param htRepairOfferPartHistory
	 * @return
	 */
	@Override
	public HtRepairOfferPartHistory get(HtRepairOfferPartHistory htRepairOfferPartHistory) {
		return super.get(htRepairOfferPartHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htRepairOfferPartHistory 查询条件
	 * @param htRepairOfferPartHistory.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtRepairOfferPartHistory> findPage(HtRepairOfferPartHistory htRepairOfferPartHistory) {
		return super.findPage(htRepairOfferPartHistory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htRepairOfferPartHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtRepairOfferPartHistory htRepairOfferPartHistory) {
		super.save(htRepairOfferPartHistory);
	}
	
	/**
	 * 更新状态
	 * @param htRepairOfferPartHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtRepairOfferPartHistory htRepairOfferPartHistory) {
		super.updateStatus(htRepairOfferPartHistory);
	}
	
	/**
	 * 删除数据
	 * @param htRepairOfferPartHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtRepairOfferPartHistory htRepairOfferPartHistory) {
		super.delete(htRepairOfferPartHistory);
	}
	
}