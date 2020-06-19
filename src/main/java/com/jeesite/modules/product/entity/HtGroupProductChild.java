/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.entity;

import javax.validation.constraints.NotBlank;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.policy.entity.PolicyInfo;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 组合产品子表Entity
 * @author zhaofaifeng
 * @version 2020-02-18
 */
@Table(name="ht_group_product_child", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="product_id", attrName="productInfo.id", label="product_id"),
		@Column(name="is_immediately", attrName="isImmediately", label="是否购买立即生效1是0否"),
		@Column(name="take_day", attrName="takeDay", label="购买。。。天后生效"),
		@Column(name="validity", attrName="validity", label="有效期"),
		@Column(name="effect_time", attrName="effectTime", label="生效事件"),
		@Column(name="termination_rules_sketch", attrName="terminationRulesSketch", label="符合规则简述"),

		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="reamrk", attrName="reamrk", label="reamrk"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.JOIN, entity=ProductInfo.class, alias="p",
				on="p.id = a.product_id",
				columns={@Column(includeEntity=ProductInfo.class)}),
}, orderBy="a.update_date DESC"
)
public class HtGroupProductChild extends DataEntity<HtGroupProductChild> {
	
	private static final long serialVersionUID = 1L;
	private ProductInfo productInfo;		// product_id
	private String productType;		// product_type
	private String isImmediately;		// 是否购买立即生效1是0否
	private Long   takeDay;		// 购买。。。天后生效
	private Date   effectTime;  //生效时间
	private Long   validity;		// 有效期

	private String reamrk;		// reamrk


	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}



	public HtGroupProductChild() {
		this(null);
	}

	public HtGroupProductChild(String id){
		super(id);
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	@NotBlank(message="是否购买立即生效1是0否不能为空")
	@Length(min=0, max=1, message="是否购买立即生效1是0否长度不能超过 1 个字符")
	public String getIsImmediately() {
		return isImmediately;
	}

	public void setIsImmediately(String isImmediately) {
		this.isImmediately = isImmediately;
	}
	
	public Long getTakeDay() {
		return takeDay;
	}

	public void setTakeDay(Long takeDay) {
		this.takeDay = takeDay;
	}
	
	public Long getValidity() {
		return validity;
	}

	public void setValidity(Long validity) {
		this.validity = validity;
	}

	public String getReamrk() {
		return reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}
	
}