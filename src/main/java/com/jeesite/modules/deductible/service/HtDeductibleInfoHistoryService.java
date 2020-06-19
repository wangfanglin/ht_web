/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.deductible.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.deductible.entity.HtDeductibleInfoHistory;
import com.jeesite.modules.deductible.dao.HtDeductibleInfoHistoryDao;

/**
 * 自付款信息历史表Service
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Service
@Transactional(readOnly=true)
public class HtDeductibleInfoHistoryService extends CrudService<HtDeductibleInfoHistoryDao, HtDeductibleInfoHistory> {
	
	/**
	 * 获取单条数据
	 * @param htDeductibleInfoHistory
	 * @return
	 */
	@Override
	public HtDeductibleInfoHistory get(HtDeductibleInfoHistory htDeductibleInfoHistory) {
		return super.get(htDeductibleInfoHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htDeductibleInfoHistory 查询条件
	 * @return
	 */
	@Override
	public Page<HtDeductibleInfoHistory> findPage(HtDeductibleInfoHistory htDeductibleInfoHistory) {
		return super.findPage(htDeductibleInfoHistory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htDeductibleInfoHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtDeductibleInfoHistory htDeductibleInfoHistory) {
		dao.insert(htDeductibleInfoHistory);
	}
	
	/**
	 * 更新状态
	 * @param htDeductibleInfoHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtDeductibleInfoHistory htDeductibleInfoHistory) {
		super.updateStatus(htDeductibleInfoHistory);
	}
	
	/**
	 * 删除数据
	 * @param htDeductibleInfoHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtDeductibleInfoHistory htDeductibleInfoHistory) {
		super.delete(htDeductibleInfoHistory);
	}
	
}