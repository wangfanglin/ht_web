/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.provider.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.provider.entity.HtInsuranceProviderInfo;
import com.jeesite.modules.provider.dao.HtInsuranceProviderInfoDao;

/**
 * 保险供应商Service
 * @author tangchao
 * @version 2020-02-21
 */
@Service
@Transactional(readOnly=true)
public class HtInsuranceProviderInfoService extends CrudService<HtInsuranceProviderInfoDao, HtInsuranceProviderInfo> {
	
	/**
	 * 获取单条数据
	 * @param htInsuranceProviderInfo
	 * @return
	 */
	@Override
	public HtInsuranceProviderInfo get(HtInsuranceProviderInfo htInsuranceProviderInfo) {
		return super.get(htInsuranceProviderInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htInsuranceProviderInfo 查询条件
	 * @param htInsuranceProviderInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtInsuranceProviderInfo> findPage(HtInsuranceProviderInfo htInsuranceProviderInfo) {
		return super.findPage(htInsuranceProviderInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htInsuranceProviderInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtInsuranceProviderInfo htInsuranceProviderInfo) {
		//因为数据原因
		this.preSave(htInsuranceProviderInfo);
		if (htInsuranceProviderInfo.getIsNewRecord()) {
			htInsuranceProviderInfo.setSerialnumber("C001-"+(sum()+1));
			this.insert(htInsuranceProviderInfo);
		} else {
			this.update(htInsuranceProviderInfo);
		}
	}
	
	/**
	 * 更新状态
	 * @param htInsuranceProviderInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtInsuranceProviderInfo htInsuranceProviderInfo) {
		super.updateStatus(htInsuranceProviderInfo);
	}
	
	/**
	 * 删除数据
	 * @param htInsuranceProviderInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtInsuranceProviderInfo htInsuranceProviderInfo) {
		super.delete(htInsuranceProviderInfo);
	}

	/**
	 * 获取目前保险供应商的总数
	 * @return
	 */
	public int sum(){
		return dao.sum();
	}

}