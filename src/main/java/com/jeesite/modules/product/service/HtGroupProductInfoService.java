/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.product.dao.HtGroupProductChildDao;
import com.jeesite.modules.product.dao.ProductInfoDao;
import com.jeesite.modules.product.entity.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.product.dao.HtGroupProductInfoDao;

import javax.validation.constraints.NotBlank;

/**
 * 组合产品表Service
 * @author zhaoheifeng
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly=true)
public class HtGroupProductInfoService extends CrudService<HtGroupProductInfoDao, HtGroupProductInfo> {

	@Autowired
	private HtGroupProductChildDao groupProductChildDao;
	@Autowired
	private ProductInfoDao productInfoDao;

	/**
	 * 获取单条数据
	 * @param htGroupProductInfo
	 * @return
	 */
	@Override
	public HtGroupProductInfo get(HtGroupProductInfo htGroupProductInfo) {
		return super.get(htGroupProductInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htGroupProductInfo 查询条件
	 * @return
	 */
	@Override
	public Page<HtGroupProductInfo> findPage(HtGroupProductInfo htGroupProductInfo) {
		return super.findPage(htGroupProductInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htGroupProductInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtGroupProductInfo htGroupProductInfo) {
		HtGroupProductInfo gGroupProductInfo = this.get(htGroupProductInfo);
		if (gGroupProductInfo==null){htGroupProductInfo.setIsNewRecord(true);}
		if ("0".equals(htGroupProductInfo.getIsStart())){
			htGroupProductInfo.setStatus("2");
		}
		//判断是否单一设置生效 0
		String isSingleValidity = htGroupProductInfo.getIsSingleValidity();
		//1是0否
		String groupProductId = htGroupProductInfo.getId();
		String huanJiProductIds = htGroupProductInfo.getHuanJiProductIds();
		String weiXiuProductIds = htGroupProductInfo.getWeiXiuProductIds();
		String shuJuBaoProductIds = htGroupProductInfo.getShuJuBaoProductIds();
		String yanBaoProductIds = htGroupProductInfo.getYanBaoProductIds();

		HtGroupProductChild weiXiuOverResult = htGroupProductInfo.getWeiXiuOverResult();
		HtGroupProductChild huanJiOverResult = htGroupProductInfo.getHuanJiOverResult();
		HtGroupProductChild shuJuBaoOverResult = htGroupProductInfo.getShuJuBaoOverResult();
		HtGroupProductChild yanBaoOverResult = htGroupProductInfo.getYanBaoOverResult();

		//查出中间表信息
		List<Map<String,String>> middelids = groupProductChildDao.findMiddel(groupProductId);
		//删除中间表信息
		if (middelids!=null&&middelids.size()>0){
			for (Map<String, String> middelid : middelids) {
				//删除中间表的数据
				groupProductChildDao.delMiddel(String.valueOf(middelid.get("id")));
				//删除组合产品的子表信息 需要用子表的主键ID 删除
				//groupProductChildDao.deleteById(middelid.get("group_product_child_id"));
			}
		}

		if ("1".equals(isSingleValidity)){//是否单独设置   取出子对象 循环产品IDS
			if (!"".equals(huanJiProductIds)&&null!=huanJiProductIds){
				List<String> list = Arrays.asList(huanJiProductIds.split(","));
				this.saveChild(huanJiOverResult,list,groupProductId);
			}

			if (!"".equals(weiXiuProductIds)&&null!=weiXiuProductIds){
				List<String> list = Arrays.asList(weiXiuProductIds.split(","));
				this.saveChild(weiXiuOverResult,list,groupProductId);
			}
			if (!"".equals(shuJuBaoProductIds)&&null!=shuJuBaoProductIds){
				List<String> list = Arrays.asList(shuJuBaoProductIds.split(","));
				this.saveChild(shuJuBaoOverResult,list,groupProductId);
			}
			if (!"".equals(yanBaoProductIds)&&null!=yanBaoProductIds){
				List<String> list = Arrays.asList(yanBaoProductIds.split(","));
				this.saveChild(yanBaoOverResult,list,groupProductId);
			}
			//这个是有效期不一致的标识
			htGroupProductInfo.setIsAccordance("0");
		}
		if ("0".equals(isSingleValidity)){
			//不单独设置 统一的有效期
			//有效期在主对象中

			//这个是有效期一致的标识
			htGroupProductInfo.setIsAccordance("1");

			String isImmediately = htGroupProductInfo.getIsImmediately();
			Long takeDay = htGroupProductInfo.getTakeDay();
			Long validity = htGroupProductInfo.getValidity();

			HtGroupProductChild child = new HtGroupProductChild();
			child.setTakeDay(takeDay);
			child.setIsImmediately(isImmediately);
			child.setValidity(validity);

			if (!"".equals(huanJiProductIds)&&null!=huanJiProductIds){

				List<String> list = Arrays.asList(huanJiProductIds.split(","));
				this.saveChild(child,list,groupProductId);
			}
			if (!"".equals(weiXiuProductIds)&&null!=weiXiuProductIds){
				List<String> list = Arrays.asList(weiXiuProductIds.split(","));
				this.saveChild(child,list,groupProductId);
			}
			if (!"".equals(shuJuBaoProductIds)&&null!=shuJuBaoProductIds){
				List<String> list = Arrays.asList(shuJuBaoProductIds.split(","));
				this.saveChild(child,list,groupProductId);
			}
			if (!"".equals(yanBaoProductIds)&&null!=yanBaoProductIds){
				List<String> list = Arrays.asList(yanBaoProductIds.split(","));
				this.saveChild(child,list,groupProductId);
			}
		}

		/*销售上下限 和 限额 转为分*/

		if (htGroupProductInfo.getMaxPrice()!=null&&htGroupProductInfo.getMaxPrice()!=0){
			htGroupProductInfo.setMaxPrice(htGroupProductInfo.getMaxPrice() * 100); }
		if (htGroupProductInfo.getMinPrice()!=null && htGroupProductInfo.getMinPrice()!=0){
			htGroupProductInfo.setMinPrice(htGroupProductInfo.getMinPrice() * 100); }
		if (htGroupProductInfo.getCoverage()!=null){
			htGroupProductInfo.setCoverage(htGroupProductInfo.getCoverage().multiply(new BigDecimal(100))); }
		super.save(htGroupProductInfo);
		// 产品跟机型是有一张中间表的
		   String[] phoneModelId = htGroupProductInfo.getPhoneModelId().split(",");
		  List<String> phoneModleIds = Arrays.asList(phoneModelId);
		  productInfoDao.deleteProductPhoneModuleMiddle(htGroupProductInfo.getId());
		  productInfoDao.insertProductPhoneModuleMiddle(htGroupProductInfo.getId(), phoneModleIds);
	}

	/**
	 * 保存子产品 和中间表的方法
	 */
	@Transactional(readOnly=false)
	public void saveChild(HtGroupProductChild groupProductChild,List<String> productIds,String groupProductId) {
		/**
		 * groupProductChild   装有规则的 子产品  (没有ID)
		 * productIds          所选中的  权益产品
		 * groupProductId      组合产品的ID
		 */

		for (String productId : productIds) {

			String productChildId = IdGen.randomLong()+"";
			groupProductChild.setId(productChildId);
			groupProductChild.setProductInfo(new ProductInfo(productId));
			groupProductChildDao.insert(groupProductChild);
			dao.insertMiddle(groupProductId, productChildId);
		}
	}
	/**
	 * 更新状态
	 * @param htGroupProductInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtGroupProductInfo htGroupProductInfo) {
		super.updateStatus(htGroupProductInfo);
	}
	
	/**
	 * 删除数据
	 * @param htGroupProductInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtGroupProductInfo htGroupProductInfo) {
		super.delete(htGroupProductInfo);
	}

	/**
	 * 查询产品（权益）
	 * @param productId
	 * @return
	 */
	public ProductInfo findProductInfo(String productId) {
		return dao.findProductInfo(productId);
	}


	public List<Map<String, String>> findProductName(String id) {
		return dao.findProductName(id);
	}
}