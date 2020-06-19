/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.channel.entity.HtPurchasingChannels;
import com.jeesite.modules.channel.dao.HtPurchasingChannelsDao;

/**
 * 采购渠道表Service
 * @author tangchao
 * @version 2020-02-22
 */
@Service
@Transactional(readOnly=true)
public class HtPurchasingChannelsService extends CrudService<HtPurchasingChannelsDao, HtPurchasingChannels> {
	
	/**
	 * 获取单条数据
	 * @param htPurchasingChannels
	 * @return
	 */
	@Override
	public HtPurchasingChannels get(HtPurchasingChannels htPurchasingChannels) {
		return super.get(htPurchasingChannels);
	}
	
	/**
	 * 查询分页数据
	 * @param htPurchasingChannels 查询条件
	 * @param htPurchasingChannels.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtPurchasingChannels> findPage(HtPurchasingChannels htPurchasingChannels) {
		return super.findPage(htPurchasingChannels);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htPurchasingChannels
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtPurchasingChannels htPurchasingChannels) {
		this.preSave(htPurchasingChannels);
		if (htPurchasingChannels.getIsNewRecord()) {
			htPurchasingChannels.setCode("HD-"+(sum()+1));
			this.insert(htPurchasingChannels);
		} else {
			this.update(htPurchasingChannels);
		}
	}



	/**
	 * 更新状态
	 * @param htPurchasingChannels
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtPurchasingChannels htPurchasingChannels) {
		super.updateStatus(htPurchasingChannels);
	}
	
	/**
	 * 删除数据
	 * @param htPurchasingChannels
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtPurchasingChannels htPurchasingChannels) {
		super.delete(htPurchasingChannels);
	}

	/**
	 * 获取目前采购渠道的总数
	 * @return
	 */
	private int sum() {
		return dao.sum();
	}
}