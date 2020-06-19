/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.entity;

import javax.validation.constraints.NotBlank;

import com.jeesite.common.mybatis.annotation.JoinTable;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 渠道产品表Entity
 * @author zhaohaifeng
 * @version 2020-02-20
 */
@Table(name="ht_channel_product_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="code", attrName="code", label="code"),
		@Column(name="distribution_id", attrName="distributionId", label="渠道商"),
		@Column(name="distribution_name", attrName="distributionName", label="渠道商名称"),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="data_transfer_type", attrName="dataTransferType", label="数据传输方式1API 2EXCEL3APP"),
		@Column(name="serve_cost_price", attrName="serveCostPrice", label="服务成本价"),
		@Column(name="rate", attrName="rate", label="费率"),
		@Column(name="premium", attrName="premium", label="保费"),
		@Column(name="suggested_retail_price", attrName="suggestedRetailPrice", label="建议零售价"),
		@Column(name="intermediary_service_id", attrName="intermediaryServiceId", label="中介供应商"),
		@Column(name="is_start", attrName="isStart", label="是否立即启用1是0否"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="reamrk", attrName="reamrk", label="reamrk"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
		@Column(name="group_product_id", attrName="groupProductId", label="组合产品ID"),
	},
		joinTable={
				@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=HtGroupProductInfo.class, alias="o",
						on="o.id = a.group_product_id",
						columns={@Column(includeEntity=HtGroupProductInfo.class)})
		},
		orderBy="a.update_date DESC"
)
public class ChannelProductInfo extends DataEntity<ChannelProductInfo> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	private String distributionId;		// 渠道商
	private String distributionName;		// 渠道商名称
	private String name;		// name
	private String dataTransferType;		// 数据传输方式1API 2EXCEL3APP
	private Double serveCostPrice;		// 服务成本价
	private Double rate;		// 费率
	private Double premium;		// 保费
	private Double suggestedRetailPrice;		// 建议零售价
	private String intermediaryServiceId;		// 中介供应商
	private String isStart;		// 是否立即启用1是0否
	private String reamrk;		// reamrk
	private String groupProductId;		// 组合产品ID
	private String claimData;		//理赔资料清单
	private String groupProductName; //组合产品名称
	private String maintainProductName; //维修产品名称
	private String changeProductName; //换机产品名称
	private String extendProductName; //延保产品名称
	private String dataProductName; //数据保产品名称

	public ChannelProductInfo() {
		this(null);
	}

	public ChannelProductInfo(String id){
		super(id);
	}

	public String getClaimData() {
		return claimData;
	}

	public void setClaimData(String claimData) {
		this.claimData = claimData;
	}

	public String getGroupProductName() {
		return groupProductName;
	}

	public void setGroupProductName(String groupProductName) {
		this.groupProductName = groupProductName;
	}

	public String getMaintainProductName() {
		return maintainProductName;
	}

	public void setMaintainProductName(String maintainProductName) {
		this.maintainProductName = maintainProductName;
	}

	public String getChangeProductName() {
		return changeProductName;
	}

	public void setChangeProductName(String changeProductName) {
		this.changeProductName = changeProductName;
	}

	public String getExtendProductName() {
		return extendProductName;
	}

	public String getDistributionName() {
		return distributionName;
	}

	public void setDistributionName(String distributionName) {
		this.distributionName = distributionName;
	}

	public void setExtendProductName(String extendProductName) {
		this.extendProductName = extendProductName;
	}

	public String getDataProductName() {
		return dataProductName;
	}

	public void setDataProductName(String dataProductName) {
		this.dataProductName = dataProductName;
	}

	@NotBlank(message="code不能为空")
	@Length(min=0, max=255, message="code长度不能超过 255 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotNull(message="渠道商不能为空")
	public String getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(String distributionId) {
		this.distributionId = distributionId;
	}



	
	@NotBlank(message="name不能为空")
	@Length(min=0, max=255, message="name长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message="数据传输方式不能为空")
	@Length(min=0, max=1, message="数据传输方式长度不能超过 1 个字符")
	public String getDataTransferType() {
		return dataTransferType;
	}

	public void setDataTransferType(String dataTransferType) {
		this.dataTransferType = dataTransferType;
	}
	
	public Double getServeCostPrice() {
		return serveCostPrice;
	}

	public void setServeCostPrice(Double serveCostPrice) {
		this.serveCostPrice = serveCostPrice;
	}
	
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}
	
	@NotNull(message="建议零售价不能为空")
	public Double getSuggestedRetailPrice() {
		return suggestedRetailPrice;
	}

	public void setSuggestedRetailPrice(Double suggestedRetailPrice) {
		this.suggestedRetailPrice = suggestedRetailPrice;
	}

	@NotNull(message="中介供应商不能为空")
	public String getIntermediaryServiceId() {
		return intermediaryServiceId;
	}

	public void setIntermediaryServiceId(String intermediaryServiceId) {
		this.intermediaryServiceId = intermediaryServiceId;
	}



	
	@NotBlank(message="是否立即启用1是0否不能为空")
	@Length(min=0, max=1, message="是否立即启用长度不能超过 1 个字符")
	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	
	public String getReamrk() {
		return reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}
	
	@NotBlank(message="组合产品ID不能为空")
	public String getGroupProductId() {
		return groupProductId;
	}

	public void setGroupProductId(String groupProductId) {
		this.groupProductId = groupProductId;
	}
	
}