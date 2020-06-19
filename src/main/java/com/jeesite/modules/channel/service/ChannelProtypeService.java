/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.channel.entity.ChannelProtype;
import com.jeesite.modules.channel.dao.ChannelProtypeDao;

/**
 * 1.0产品类型表Service
 * @author tangchao
 * @version 2020-05-26
 */
@Service
@Transactional(readOnly=true)
public class ChannelProtypeService extends CrudService<ChannelProtypeDao, ChannelProtype> {
	
	/**
	 * 获取单条数据
	 * @param channelProtype
	 * @return
	 */
	@Override
	public ChannelProtype get(ChannelProtype channelProtype) {
		return super.get(channelProtype);
	}
	
	/**
	 * 查询分页数据
	 * @param channelProtype 查询条件
	 * @param channelProtype.page 分页对象
	 * @return
	 */
	@Override
	public Page<ChannelProtype> findPage(ChannelProtype channelProtype) {
		return super.findPage(channelProtype);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param channelProtype
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ChannelProtype channelProtype) {
		super.save(channelProtype);
	}
	
	/**
	 * 更新状态
	 * @param channelProtype
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ChannelProtype channelProtype) {
		super.updateStatus(channelProtype);
	}
	
	/**
	 * 删除数据
	 * @param channelProtype
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ChannelProtype channelProtype) {
		super.delete(channelProtype);
	}
	
}