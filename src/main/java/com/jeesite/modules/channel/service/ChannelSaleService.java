/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.channel.entity.ChannelSale;
import com.jeesite.modules.channel.dao.ChannelSaleDao;

/**
 * 1.0销售渠道Service
 * @author tangchao
 * @version 2020-05-26
 */
@Service
@Transactional(readOnly=true)
public class ChannelSaleService extends CrudService<ChannelSaleDao, ChannelSale> {
	
	/**
	 * 获取单条数据
	 * @param channelSale
	 * @return
	 */
	@Override
	public ChannelSale get(ChannelSale channelSale) {
		return super.get(channelSale);
	}
	
	/**
	 * 查询分页数据
	 * @param channelSale 查询条件
	 * @param channelSale.page 分页对象
	 * @return
	 */
	@Override
	public Page<ChannelSale> findPage(ChannelSale channelSale) {
		return super.findPage(channelSale);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param channelSale
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ChannelSale channelSale) {
		super.save(channelSale);
	}
	
	/**
	 * 更新状态
	 * @param channelSale
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ChannelSale channelSale) {
		super.updateStatus(channelSale);
	}
	
	/**
	 * 删除数据
	 * @param channelSale
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ChannelSale channelSale) {
		super.delete(channelSale);
	}
	
}