/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.service;

import java.util.List;

import com.jeesite.modules.product.entity.ProductInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.dao.HtGroupProductChildDao;

/**
 * 组合产品子表Service
 * @author zhaofaifeng
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly=true)
public class HtGroupProductChildService extends CrudService<HtGroupProductChildDao, HtGroupProductChild> {
	
	/**
	 * 获取单条数据
	 * @param htGroupProductChild
	 * @return
	 */
	@Override
	public HtGroupProductChild get(HtGroupProductChild htGroupProductChild) {
		return super.get(htGroupProductChild);
	}
	
	/**
	 * 查询分页数据
	 * @param htGroupProductChild 查询条件
	 * @param htGroupProductChild.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtGroupProductChild> findPage(HtGroupProductChild htGroupProductChild) {
		return super.findPage(htGroupProductChild);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htGroupProductChild
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtGroupProductChild htGroupProductChild) {
		super.save(htGroupProductChild);
	}
	
	/**
	 * 更新状态
	 * @param htGroupProductChild
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtGroupProductChild htGroupProductChild) {
		super.updateStatus(htGroupProductChild);
	}
	
	/**
	 * 删除数据
	 * @param htGroupProductChild
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtGroupProductChild htGroupProductChild) {
		super.delete(htGroupProductChild);
	}

	/**
	 * 查出子表中的终止规则
	 * @param productInfoId
	 * @return
	 */
	public HtGroupProductChild getByProductId(String productInfoId) {
		return dao.getByProductId(productInfoId);
	}

	/**
	 * 根据组合产品ID查询子产品信息
	 * @param groupProductId
	 * @return
	 */
	public List<HtGroupProductChild> findChildByGroupId(String groupProductId){
		return dao.findChildByGroupId(groupProductId);
	}
	/**
	 * 根据组合产品查询固定类型的产品（权益）
	 * @param productType
	 * @param groupProductId
	 * @return
	 */
	public List<ProductInfo> listProduct(String productType, String groupProductId) {
		return dao.listProduct(productType, groupProductId);
	}

	/**
	 * 查询关联的机型
	 * @param groupProductInfoId
	 * @return
	 */
	public String findPhoneMod(String groupProductInfoId) {
		return dao.findPhoneMod(groupProductInfoId);
	}
}