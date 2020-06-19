/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.renew.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.renew.entity.HtRenewHistory;
import com.jeesite.modules.renew.dao.HtRenewHistoryDao;

/**
 * 换新工单历史Service
 * @author lichao
 * @version 2020-03-25
 */
@Service
@Transactional(readOnly=true)
public class HtRenewHistoryService extends CrudService<HtRenewHistoryDao, HtRenewHistory> {
	
	/**
	 * 获取单条数据
	 * @param htRenewHistory
	 * @return
	 */
	@Override
	public HtRenewHistory get(HtRenewHistory htRenewHistory) {
		return super.get(htRenewHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htRenewHistory 查询条件
	 * @param htRenewHistory.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtRenewHistory> findPage(HtRenewHistory htRenewHistory) {
		return super.findPage(htRenewHistory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htRenewHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtRenewHistory htRenewHistory) {
		super.save(htRenewHistory);
	}
	
	/**
	 * 更新状态
	 * @param htRenewHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtRenewHistory htRenewHistory) {
		super.updateStatus(htRenewHistory);
	}
	
	/**
	 * 删除数据
	 * @param htRenewHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtRenewHistory htRenewHistory) {
		super.delete(htRenewHistory);
	}
	
}