/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.repair.entity.HtRepairOfferPart;
import com.jeesite.modules.repair.dao.HtRepairOfferPartDao;

/**
 * 维修工单-待报价-损害部位表Service
 * @author lichao
 * @version 2020-02-27
 */
@Service
@Transactional(readOnly=true)
public class HtRepairOfferPartService extends CrudService<HtRepairOfferPartDao, HtRepairOfferPart> {
	
	/**
	 * 获取单条数据
	 * @param htRepairOfferPart
	 * @return
	 */
	@Override
	public HtRepairOfferPart get(HtRepairOfferPart htRepairOfferPart) {
		return super.get(htRepairOfferPart);
	}
	
	/**
	 * 查询分页数据
	 * @param htRepairOfferPart 查询条件
	 * @param htRepairOfferPart.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtRepairOfferPart> findPage(HtRepairOfferPart htRepairOfferPart) {
		return super.findPage(htRepairOfferPart);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htRepairOfferPart
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtRepairOfferPart htRepairOfferPart) {
		super.save(htRepairOfferPart);
	}
	
	/**
	 * 更新状态
	 * @param htRepairOfferPart
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtRepairOfferPart htRepairOfferPart) {
		super.updateStatus(htRepairOfferPart);
	}
	
	/**
	 * 删除数据
	 * @param htRepairOfferPart
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtRepairOfferPart htRepairOfferPart) {
		super.delete(htRepairOfferPart);
	}
	
}