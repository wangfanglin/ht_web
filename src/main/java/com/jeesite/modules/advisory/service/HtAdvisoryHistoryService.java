/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.advisory.entity.HtAdvisoryHistory;
import com.jeesite.modules.advisory.dao.HtAdvisoryHistoryDao;

/**
 * ht_advisory_historyService
 * @author zhaohaifeng
 * @version 2020-03-31
 */
@Service
@Transactional(readOnly=true)
public class HtAdvisoryHistoryService extends CrudService<HtAdvisoryHistoryDao, HtAdvisoryHistory> {
	
	/**
	 * 获取单条数据
	 * @param htAdvisoryHistory
	 * @return
	 */
	@Override
	public HtAdvisoryHistory get(HtAdvisoryHistory htAdvisoryHistory) {
		return super.get(htAdvisoryHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htAdvisoryHistory 查询条件
	 * @param htAdvisoryHistory.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtAdvisoryHistory> findPage(HtAdvisoryHistory htAdvisoryHistory) {
		return super.findPage(htAdvisoryHistory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htAdvisoryHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtAdvisoryHistory htAdvisoryHistory) {
		super.save(htAdvisoryHistory);
	}
	
	/**
	 * 更新状态
	 * @param htAdvisoryHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtAdvisoryHistory htAdvisoryHistory) {
		super.updateStatus(htAdvisoryHistory);
	}
	
	/**
	 * 删除数据
	 * @param htAdvisoryHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtAdvisoryHistory htAdvisoryHistory) {
		super.delete(htAdvisoryHistory);
	}
	
}