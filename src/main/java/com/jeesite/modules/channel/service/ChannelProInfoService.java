/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.channel.entity.ChannelProInfo;
import com.jeesite.modules.channel.dao.ChannelProInfoDao;

/**
 * 1.0销售渠道商与产品类型关联表Service
 * @author tangchao
 * @version 2020-05-26
 */
@Service
@Transactional(readOnly=true)
public class ChannelProInfoService extends CrudService<ChannelProInfoDao, ChannelProInfo> {
	
	/**
	 * 获取单条数据
	 * @param channelProInfo
	 * @return
	 */
	@Override
	public ChannelProInfo get(ChannelProInfo channelProInfo) {
		return super.get(channelProInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param channelProInfo 查询条件
	 * @param channelProInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<ChannelProInfo> findPage(ChannelProInfo channelProInfo) {
		return super.findPage(channelProInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param channelProInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ChannelProInfo channelProInfo) {
		super.save(channelProInfo);
	}
	
	/**
	 * 更新状态
	 * @param channelProInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ChannelProInfo channelProInfo) {
		super.updateStatus(channelProInfo);
	}
	
	/**
	 * 删除数据
	 * @param channelProInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ChannelProInfo channelProInfo) {
		super.delete(channelProInfo);
	}
	
}