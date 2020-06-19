/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.product.entity.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.dao.ChannelProductInfoDao;

/**
 * 渠道产品表Service
 * @author zhaohaifeng
 * @version 2020-02-20
 */
@Service
@Transactional(readOnly=true)
public class ChannelProductInfoService extends CrudService<ChannelProductInfoDao, ChannelProductInfo> {
	@Autowired
	private ChannelProductInfoDao channelProductDao;

	/**
	 * 获取单条数据
	 * @param channelProductInfo
	 * @return
	 */
	@Override
	public ChannelProductInfo get(ChannelProductInfo channelProductInfo) {
		return super.get(channelProductInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param channelProductInfo 查询条件
	 * @return
	 */
	@Override
	public Page<ChannelProductInfo> findPage(ChannelProductInfo channelProductInfo) {
		return super.findPage(channelProductInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param channelProductInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ChannelProductInfo channelProductInfo) {
		//保存渠道信息的时候需要把理赔清单信息也处理了 并且做中间表关联
		String id = channelProductInfo.getId();
		String channelProductId = IdGen.uuid();
		if (id==null||"".equals(id)){
			channelProductInfo.setId(channelProductId);
			channelProductInfo.setIsNewRecord(true);
		}

		String isStart = channelProductInfo.getIsStart();
		if ("1".equals(isStart)){channelProductInfo.setStatus("0");}
		if ("0".equals(isStart)){channelProductInfo.setStatus("2");}

		this.updateUnit(channelProductInfo,"1");
		super.save(channelProductInfo);
		String claimData = channelProductInfo.getClaimData();
		String[] split = claimData.split(",");
		List<String> list = Arrays.asList(split);
		//先删除与理赔资料的中间信息
		channelProductDao.delChannelClaimData(channelProductId);
		channelProductDao.insertChannelClaimData(channelProductId,list);
	}
	
	/**
	 * 更新状态
	 * @param channelProductInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ChannelProductInfo channelProductInfo) {
		super.updateStatus(channelProductInfo);
	}
	
	/**
	 * 删除数据
	 * @param channelProductInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ChannelProductInfo channelProductInfo) {
		super.delete(channelProductInfo);
	}

	public List<Map<String, Object>> findClaimData() {
		return channelProductDao.findClaimData();
	}

	/**
	 * 单位处理
	 * @param channelProduct
	 * @param flag
	 * @return
	 */
	public ChannelProductInfo updateUnit(ChannelProductInfo channelProduct, String flag) {
		//1 是元转分  0 是分转元
		if ("0".equals(flag)){
			if (channelProduct.getServeCostPrice() != null) {
				channelProduct.setServeCostPrice(channelProduct.getServeCostPrice() / 100);
			}
			if (channelProduct.getPremium() != null) {
				channelProduct.setPremium(channelProduct.getPremium() / 100);
			}
			if (channelProduct.getSuggestedRetailPrice() != null) {
				channelProduct.setSuggestedRetailPrice(channelProduct.getSuggestedRetailPrice() / 100);
			}
		}else if ("1".equals(flag)){
			if (channelProduct.getServeCostPrice() != null) {
				channelProduct.setServeCostPrice(channelProduct.getServeCostPrice() *100);
			}
			if (channelProduct.getPremium() != null) {
				channelProduct.setPremium(channelProduct.getPremium() * 100);
			}
			if (channelProduct.getSuggestedRetailPrice() != null) {
				channelProduct.setSuggestedRetailPrice(channelProduct.getSuggestedRetailPrice() * 100);
			}
		}
		return channelProduct;
	}

	/**
	 * 根据渠道产品ID 查出关联的理赔信息
	 * @param channelProductId
	 * @return
	 */
	public List<Map<String, Object>> findClaimDataByChannelProductId(String channelProductId) {
		return channelProductDao.findClaimDataByChannelProductId(channelProductId);
	}


	/**
	 * 根据保单ID 查自付款
	 * @return
	 */
	public Double findDeductible(String policyId) {
		return channelProductDao.findDeductible(policyId);
	}



	public List<ProductInfo> findProductLsit(String channelProductId) {
	List<ProductInfo> productInfos = new ArrayList<>();
		return productInfos;
	}
}