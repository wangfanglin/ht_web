/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htassemblyunit.service;

import java.util.List;

import com.jeesite.modules.product.entity.HtGroupProductChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.dao.HtAssemblyUnitDao;

/**
 * 维修部件表Service
 * @author hongmengzhong
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly=true)
public class HtAssemblyUnitService extends CrudService<HtAssemblyUnitDao, HtAssemblyUnit> {

	@Autowired
	private HtAssemblyUnitDao htAssemblyUnitDao;

	/**
	 * 获取单条数据
	 * @param htAssemblyUnit
	 * @return
	 */
	@Override
	public HtAssemblyUnit get(HtAssemblyUnit htAssemblyUnit) {
		return super.get(htAssemblyUnit);
	}
	
	/**
	 * 查询分页数据
	 * @param htAssemblyUnit 查询条件
	 * @param htAssemblyUnit.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtAssemblyUnit> findPage(HtAssemblyUnit htAssemblyUnit) {
		return super.findPage(htAssemblyUnit);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htAssemblyUnit
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtAssemblyUnit htAssemblyUnit) {
		super.save(htAssemblyUnit);
	}
	
	/**
	 * 更新状态
	 * @param htAssemblyUnit
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtAssemblyUnit htAssemblyUnit) {
		super.updateStatus(htAssemblyUnit);
	}
	
	/**
	 * 删除数据
	 * @param htAssemblyUnit
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtAssemblyUnit htAssemblyUnit) {
		super.delete(htAssemblyUnit);
	}

    public List<HtAssemblyUnit> findListByStart(String isMainFlag) {
		return htAssemblyUnitDao.findListByStart(isMainFlag);
    }

	/**
	 * 保存产品 部件中间表
	 * @param productInfoId
	 * @param assemblyIds
	 */
	public void insertProductAssemblyMiddle(String productInfoId, List<String> assemblyIds) {
		htAssemblyUnitDao.insertProductAssemblyMiddle(productInfoId,assemblyIds);
	}

	/**
	 * 查出产品关联的部件ID
	 * @param productInfoId
	 * @return
	 */
	public String findIdsByProductId(String productInfoId) {
		List<String> ids = htAssemblyUnitDao.findIdsByProductId(productInfoId);
		StringBuffer stringBuffer = new StringBuffer();
		for (String id : ids) {
			stringBuffer.append(",").append(id);
		}
		return stringBuffer.toString();
	}

	/**
	 * 删除产品与部件的关联信息
	 * @param productInfoId
	 */
	public void deleteProductAssemblyMiddle(String productInfoId) {
		htAssemblyUnitDao.deleteProductAssemblyMiddle(productInfoId);
	}
	/**
	 * 查出与产品关联的部件
	 * @return
	 */
	public String findAssemblyByProductId(String productInfo) {
		List<HtAssemblyUnit> assemblyUnits=htAssemblyUnitDao.findAssemblyByProductId(productInfo);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < assemblyUnits.size(); i++) {
			HtAssemblyUnit assemblyUnit = assemblyUnits.get(i);
			if (i==assemblyUnits.size()-1){
				stringBuilder.append(assemblyUnit.getName());
			}else{
				stringBuilder.append(assemblyUnit.getName()).append(",");
			}
		}
		return stringBuilder.toString();
	}
}