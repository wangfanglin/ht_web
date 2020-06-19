/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.httimeefficiency.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.httimeefficiency.entity.HtTimeEfficiency;
import com.jeesite.modules.httimeefficiency.dao.HtTimeEfficiencyDao;

/**
 * 维修时效表Service
 * @author hongmengzhong
 * @version 2020-04-07
 */
@Service
@Transactional(readOnly=true)
public class HtTimeEfficiencyService extends CrudService<HtTimeEfficiencyDao, HtTimeEfficiency> {
	
	/**
	 * 获取单条数据
	 * @param htTimeEfficiency
	 * @return
	 */
	@Override
	public HtTimeEfficiency get(HtTimeEfficiency htTimeEfficiency) {
		return super.get(htTimeEfficiency);
	}
	
	/**
	 * 查询分页数据
	 * @param htTimeEfficiency 查询条件
	 * @param htTimeEfficiency.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtTimeEfficiency> findPage(HtTimeEfficiency htTimeEfficiency) {
		return super.findPage(htTimeEfficiency);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htTimeEfficiency
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtTimeEfficiency htTimeEfficiency) {
		super.save(htTimeEfficiency);
	}
	
	/**
	 * 更新状态
	 * @param htTimeEfficiency
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtTimeEfficiency htTimeEfficiency) {
		super.updateStatus(htTimeEfficiency);
	}
	
	/**
	 * 删除数据
	 * @param htTimeEfficiency
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtTimeEfficiency htTimeEfficiency) {
		super.delete(htTimeEfficiency);
	}

	/**
	 * 更新状态
	 * @param htTimeEfficiency
	 */
	@Transactional(readOnly=false)
	public void updateFirst(HtTimeEfficiency htTimeEfficiency) {
		dao.updateFirst(htTimeEfficiency);
	}


	public Integer getTimelinessSum(String formId) {
		return dao.getTimelinessSum(formId);
	}

	//获取当前机构下超期单数
    public int getFormPastDueAmount(String officeId, String type) {
		return dao.getFormPastDueAmount(officeId,type);
	}
}