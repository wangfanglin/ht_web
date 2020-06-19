/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sforderinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sforderinfo.entity.SfOrderInfo;
import com.jeesite.modules.sforderinfo.dao.SfOrderInfoDao;

/**
 * sf_order_infoService
 * @author hongmengzhong
 * @version 2020-04-23
 */
@Service
@Transactional(readOnly=true)
public class SfOrderInfoService extends CrudService<SfOrderInfoDao, SfOrderInfo> {
	
	/**
	 * 获取单条数据
	 * @param sfOrderInfo
	 * @return
	 */
	@Override
	public SfOrderInfo get(SfOrderInfo sfOrderInfo) {
		return super.get(sfOrderInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param sfOrderInfo 查询条件
	 * @param sfOrderInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<SfOrderInfo> findPage(SfOrderInfo sfOrderInfo) {
		return super.findPage(sfOrderInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sfOrderInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SfOrderInfo sfOrderInfo) {
		super.save(sfOrderInfo);
	}
	
	/**
	 * 更新状态
	 * @param sfOrderInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SfOrderInfo sfOrderInfo) {
		super.updateStatus(sfOrderInfo);
	}
	
	/**
	 * 删除数据
	 * @param sfOrderInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SfOrderInfo sfOrderInfo) {
		super.delete(sfOrderInfo);
	}
	
}