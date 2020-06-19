/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.entity;

import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.entity.ProductInfo;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 保单详情表Entity
 * @author zhaohaifeng
 * @version 2020-04-13
 */
@Table(name="ht_policy_detail", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="policy_id", attrName="policyInfo.id", label="保单ID"),
		@Column(name="child_id", attrName="htGroupProductChild.id", label="剩余的保额"),
		@Column(name="equity_status", attrName="equityStatus", label="子产品的权益状态"),
		@Column(name="equity_start_time", attrName="equityStartTime", label="子产品的开始时间"),
		@Column(name="equity_end_time", attrName="equityEndTime", label="子产品的结束时间"),
		@Column(includeEntity=DataEntity.class),
	}, joinTable={
		@JoinTable(type=Type.JOIN, entity=PolicyInfo.class, alias="p",
				on="p.id = a.policy_id",
				columns={@Column(includeEntity=PolicyInfo.class)}),
		@JoinTable(type=Type.JOIN, entity=HtGroupProductChild.class, alias="g",
				on="g.id = a.child_id",
				columns={@Column(includeEntity=HtGroupProductChild.class)}),
		@JoinTable(type=JoinTable.Type.JOIN, entity= ProductInfo.class, alias="po",
					on="po.id = g.product_id",
				columns={@Column(includeEntity=ProductInfo.class)}),
}, orderBy="a.update_date DESC"
)
public class PolicyDetail extends DataEntity<PolicyDetail> {
	
	private static final long serialVersionUID = 1L;
	private PolicyInfo policyInfo;		// 保单ID
	private HtGroupProductChild htGroupProductChild;		// 组合产品子产品
	private ProductInfo productInfo;
	private String equityStatus;//子产品的权益状态
	private Date equityStartTime;//子产品的开始时间
	private Date equityEndTime;//子产品的结束时间

	private HtGroupProductInfo htGroupProductInfo;//组合产品


	//临时属性
	private String unitName ;//部件名称
	private String isEnd ;//是否终止1是0否


	public HtGroupProductInfo getHtGroupProductInfo() {
		return htGroupProductInfo;
	}

	public void setHtGroupProductInfo(HtGroupProductInfo htGroupProductInfo) {
		this.htGroupProductInfo = htGroupProductInfo;
	}

	public String getEquityStatus() {
		return equityStatus;
	}

	public void setEquityStatus(String equityStatus) {
		this.equityStatus = equityStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEquityStartTime() {
		return equityStartTime;
	}

	public void setEquityStartTime(Date equityStartTime) {
		this.equityStartTime = equityStartTime;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEquityEndTime() {
		return equityEndTime;
	}

	public void setEquityEndTime(Date equityEndTime) {
		this.equityEndTime = equityEndTime;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public PolicyDetail() {
		this(null);
	}

	public PolicyDetail(String id){
		super(id);
	}

	public HtGroupProductChild getHtGroupProductChild() {
		return htGroupProductChild;
	}

	public void setHtGroupProductChild(HtGroupProductChild htGroupProductChild) {
		this.htGroupProductChild = htGroupProductChild;
	}

	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}

	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
}