/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.receipt.entity.HtReceiptDataHistory;
import com.jeesite.modules.receipt.dao.HtReceiptDataHistoryDao;

/**
 * 收款人信息历史表Service
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Service
@Transactional(readOnly=true)
public class HtReceiptDataHistoryService extends CrudService<HtReceiptDataHistoryDao, HtReceiptDataHistory> {
	
	/**
	 * 获取单条数据
	 * @param htReceiptDataHistory
	 * @return
	 */
	@Override
	public HtReceiptDataHistory get(HtReceiptDataHistory htReceiptDataHistory) {
		return super.get(htReceiptDataHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htReceiptDataHistory 查询条件
	 * @param htReceiptDataHistory.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtReceiptDataHistory> findPage(HtReceiptDataHistory htReceiptDataHistory) {
		return super.findPage(htReceiptDataHistory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htReceiptDataHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtReceiptDataHistory htReceiptDataHistory) {
		super.save(htReceiptDataHistory);
	}
	
	/**
	 * 更新状态
	 * @param htReceiptDataHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtReceiptDataHistory htReceiptDataHistory) {
		super.updateStatus(htReceiptDataHistory);
	}
	
	/**
	 * 删除数据
	 * @param htReceiptDataHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtReceiptDataHistory htReceiptDataHistory) {
		super.delete(htReceiptDataHistory);
	}
	
}