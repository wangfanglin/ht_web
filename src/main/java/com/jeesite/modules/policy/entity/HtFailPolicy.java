/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 映射失败保单Entity
 * @author zhaohaifeng
 * @version 2020-04-17
 */
@Table(name="ht_fail_policy", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="policy_id", attrName="policyId", label="policy_id"),
		@Column(name="status", attrName="status", label="状态 0未处理1已处理"),
		@Column(name="original_brand", attrName="originalBrand", label="原始品牌"),
		@Column(name="original_model", attrName="originalModel", label="原始型号"),
		@Column(name="original_channel", attrName="originalChannel", label="原始渠道"),
		@Column(name="original_channel_product", attrName="originalChannelProduct", label="原始渠道产品"),
		@Column(name="newest_brand", attrName="newestBrand", label="新品牌"),
		@Column(name="newest_model", attrName="newestModel", label="新型号"),
		@Column(name="newest_channel", attrName="newestChannel", label="新渠道"),
		@Column(name="newest_channel_product", attrName="newestChannelProduct", label="新渠道产品"),
		@Column(name="type", attrName="type", label="类型 0:捷信 1:非捷信"),
		@Column(name="remarks", attrName="remarks", label="备注"),
		@Column(name="create_date", attrName="createDate", label="创建时间"),
	},
		orderBy="a.id DESC"
)
public class HtFailPolicy extends DataEntity<HtFailPolicy> {
	
	private static final long serialVersionUID = 1L;
	private String policyId;		// policy_id
	private String originalBrand;  //原始品牌
	private String originalModel;  //原始型号
	private String originalChannel;  //原始渠道
	private String originalChannelProduct;  //原始渠道产品
	private String type;				//类型 0:捷信 1:非捷信
	private String newestBrand;  //新品牌
	private String newestModel;  //新型号
	private String newestChannel;  //新渠道
	private String newestChannelName;  //新渠道名称
	private String newestChannelProduct;  //新渠道产品
	private Date createDate;

	public HtFailPolicy() {
		this(null);
	}

	public HtFailPolicy(String id){
		super(id);
	}

	public HtFailPolicy(String policyId, String originalBrand, String originalModel, String originalChannel, String originalChannelProduct,String remarks,String type) {
		this.policyId = policyId;
		this.originalBrand = originalBrand;
		this.originalModel = originalModel;
		this.originalChannel = originalChannel;
		this.originalChannelProduct = originalChannelProduct;
		this.remarks = remarks;
		this.type = type;
		this.createDate = new Date();
	}

	public HtFailPolicy(Policy policy, PolicyInfo newPolicy, StringBuffer errorContent,String type) {
		this.policyId = policy.getId();
		this.originalBrand = policy.getStrphonebrand();
		this.originalModel = policy.getStrphonemodel();
		this.originalChannel = policy.getChannelSale().getSalechannelname();
		this.originalChannelProduct = policy.getChannelProtype().getProName();
		this.remarks = errorContent.toString();
		this.type = type;

		this.newestChannel = newPolicy.getChannelId();  //新渠道
		this.newestBrand = newPolicy.getStrPhoneBrand();  //新品牌
		this.newestModel = newPolicy.getStrPhoneModel();  //新型号
		this.newestChannelProduct = newPolicy.getChannelProductId();  //新渠道产品


		this.createDate = new Date();
	}

	public String getNewestChannelName() {
		return newestChannelName;
	}

	public void setNewestChannelName(String newestChannelName) {
		this.newestChannelName = newestChannelName;
	}

	public String getNewestBrand() {
		return newestBrand;
	}

	public void setNewestBrand(String newestBrand) {
		this.newestBrand = newestBrand;
	}

	public String getNewestModel() {
		return newestModel;
	}

	public void setNewestModel(String newestModel) {
		this.newestModel = newestModel;
	}

	public String getNewestChannel() {
		return newestChannel;
	}

	public void setNewestChannel(String newestChannel) {
		this.newestChannel = newestChannel;
	}

	public String getNewestChannelProduct() {
		return newestChannelProduct;
	}

	public void setNewestChannelProduct(String newestChannelProduct) {
		this.newestChannelProduct = newestChannelProduct;
	}

	public String getOriginalModel() {
		return originalModel;
	}

	public void setOriginalModel(String originalModel) {
		this.originalModel = originalModel;
	}

	public String getOriginalChannel() {
		return originalChannel;
	}

	public void setOriginalChannel(String originalChannel) {
		this.originalChannel = originalChannel;
	}

	public String getOriginalChannelProduct() {
		return originalChannelProduct;
	}

	public void setOriginalChannelProduct(String originalChannelProduct) {
		this.originalChannelProduct = originalChannelProduct;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}



	public String getOriginalBrand() {
		return originalBrand;
	}

	public void setOriginalBrand(String originalBrand) {
		this.originalBrand = originalBrand;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}