/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 1.0销售渠道商与产品类型关联表Entity
 * @author tangchao
 * @version 2020-05-26
 */
@Table(name="channel_pro_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="sale_channel_id", attrName="saleChannelId", label="销售渠道"),
		@Column(name="agent_id", attrName="agentId", label="中介渠道商id"),
		@Column(name="product_type_id", attrName="productTypeId", label="产品id"),
		@Column(name="cost_type", attrName="costType", label="产品成本类别"),
		@Column(name="cost", attrName="cost", label="成本价格"),
		@Column(name="price", attrName="price", label="零售价格"),
		@Column(name="picture", attrName="picture", label="产品图片"),
		@Column(name="product_desc", attrName="productDesc", label="产品的描述信息"),
		@Column(name="store_reward_precent", attrName="storeRewardPrecent", label="店员返佣比例"),
		@Column(name="manager_reward_precent", attrName="managerRewardPrecent", label="店长返佣比例"),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="产品介绍", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="remarks", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="del_flag"),
		@Column(name="product_code", attrName="productCode", label="渠道下商品编码"),
		@Column(name="max_price", attrName="maxPrice", label="手机价格上限"),
		@Column(name="min_price", attrName="minPrice", label="手机价格下限"),
	}, orderBy="a.update_date DESC"
)
public class ChannelProInfo extends DataEntity<ChannelProInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long saleChannelId;		// 销售渠道
	private String agentId;		// 中介渠道商id
	private String productTypeId;		// 产品id
	private String costType;		// 产品成本类别
	private Integer cost;		// 成本价格
	private String price;		// 零售价格
	private String picture;		// 产品图片
	private String productDesc;		// 产品的描述信息
	private Double storeRewardPrecent;		// 店员返佣比例
	private Double managerRewardPrecent;		// 店长返佣比例
	private String delFlag;		// del_flag
	private String productCode;		// 渠道下商品编码
	private String maxPrice;		// 手机价格上限
	private String minPrice;		// 手机价格下限
	
	public ChannelProInfo() {
		this(null);
	}

	public ChannelProInfo(String id){
		super(id);
	}
	
	@NotNull(message="销售渠道不能为空")
	public Long getSaleChannelId() {
		return saleChannelId;
	}

	public void setSaleChannelId(Long saleChannelId) {
		this.saleChannelId = saleChannelId;
	}
	
	@NotBlank(message="中介渠道商id不能为空")
	@Length(min=0, max=255, message="中介渠道商id长度不能超过 255 个字符")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	@NotBlank(message="产品id不能为空")
	@Length(min=0, max=255, message="产品id长度不能超过 255 个字符")
	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	
	@NotBlank(message="产品成本类别不能为空")
	@Length(min=0, max=255, message="产品成本类别长度不能超过 255 个字符")
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}
	
	@NotNull(message="成本价格不能为空")
	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	@Length(min=0, max=10, message="零售价格长度不能超过 10 个字符")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=255, message="产品图片长度不能超过 255 个字符")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	
	public Double getStoreRewardPrecent() {
		return storeRewardPrecent;
	}

	public void setStoreRewardPrecent(Double storeRewardPrecent) {
		this.storeRewardPrecent = storeRewardPrecent;
	}
	
	public Double getManagerRewardPrecent() {
		return managerRewardPrecent;
	}

	public void setManagerRewardPrecent(Double managerRewardPrecent) {
		this.managerRewardPrecent = managerRewardPrecent;
	}
	
	@NotBlank(message="del_flag不能为空")
	@Length(min=0, max=255, message="del_flag长度不能超过 255 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@Length(min=0, max=255, message="渠道下商品编码长度不能超过 255 个字符")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=128, message="手机价格上限长度不能超过 128 个字符")
	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	@Length(min=0, max=128, message="手机价格下限长度不能超过 128 个字符")
	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	
}