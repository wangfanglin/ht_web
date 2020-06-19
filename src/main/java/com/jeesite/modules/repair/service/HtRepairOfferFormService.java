/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.repair.entity.HtRepairOfferForm;
import com.jeesite.modules.repair.dao.HtRepairOfferFormDao;

/**
 * 维修工单-待报价Service
 * @author lichao
 * @version 2020-02-26
 */
@Service
@Transactional(readOnly=true)
public class HtRepairOfferFormService extends CrudService<HtRepairOfferFormDao, HtRepairOfferForm> {
	
	/**
	 * 获取单条数据
	 * @param htRepairOfferForm
	 * @return
	 */
	@Override
	public HtRepairOfferForm get(HtRepairOfferForm htRepairOfferForm) {
		return super.get(htRepairOfferForm);
	}
	
	/**
	 * 查询分页数据
	 * @param htRepairOfferForm 查询条件
	 * @param htRepairOfferForm.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtRepairOfferForm> findPage(HtRepairOfferForm htRepairOfferForm) {
		return super.findPage(htRepairOfferForm);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htRepairOfferForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtRepairOfferForm htRepairOfferForm) {
		super.save(htRepairOfferForm);
	}
	
	/**
	 * 更新状态
	 * @param htRepairOfferForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtRepairOfferForm htRepairOfferForm) {
		super.updateStatus(htRepairOfferForm);
	}
	
	/**
	 * 删除数据
	 * @param htRepairOfferForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtRepairOfferForm htRepairOfferForm) {
		super.delete(htRepairOfferForm);
	}
	
}