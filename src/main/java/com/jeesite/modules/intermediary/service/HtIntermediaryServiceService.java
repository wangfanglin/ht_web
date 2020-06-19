/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.intermediary.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.intermediary.entity.HtIntermediaryService;
import com.jeesite.modules.intermediary.dao.HtIntermediaryServiceDao;

/**
 * 中介服务商Service
 * @author tangchao
 * @version 2020-02-20
 */
@Service
@Transactional(readOnly=true)
public class HtIntermediaryServiceService extends CrudService<HtIntermediaryServiceDao, HtIntermediaryService> {
	
	/**
	 * 获取单条数据
	 * @param htIntermediaryService
	 * @return
	 */
	@Override
	public HtIntermediaryService get(HtIntermediaryService htIntermediaryService) {
		return super.get(htIntermediaryService);
	}
	
	/**
	 * 查询分页数据
	 * @param htIntermediaryService 查询条件
	 * @param htIntermediaryService.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtIntermediaryService> findPage(HtIntermediaryService htIntermediaryService) {
		return super.findPage(htIntermediaryService);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htIntermediaryService
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtIntermediaryService htIntermediaryService) {
		//因为数据原因
		this.preSave(htIntermediaryService);
		if (htIntermediaryService.getIsNewRecord()) {
			htIntermediaryService.setAgentid("IS001-"+(sum()+1));
			this.insert(htIntermediaryService);
		} else {
			this.update(htIntermediaryService);
		}
	}
	
	/**
	 * 更新状态
	 * @param htIntermediaryService
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtIntermediaryService htIntermediaryService) {
		super.updateStatus(htIntermediaryService);
	}
	
	/**
	 * 删除数据
	 * @param htIntermediaryService
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtIntermediaryService htIntermediaryService) {
		super.delete(htIntermediaryService);
	}

	/**
	 * 获取目前中介服务商的总数
	 * @return
	 */
	public int sum(){
		return dao.sum();
	}


	//	public void insertBy
	
}