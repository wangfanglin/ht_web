/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.deductible.entity;

import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
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
 * 自付款信息表Entity
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Table(name="ht_deductible_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="htFormInfo.id", label="工单号"),
		@Column(name="bd_id", attrName="bdId", label="bd_id"),
		@Column(name="user_name", attrName="userName", label="user_name", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="phone"),
		@Column(name="product_name", attrName="productName", label="product_name", queryType=QueryType.LIKE),
		@Column(name="product_id", attrName="productId", label="product_id"),
		@Column(name="deductibles_limit", attrName="deductiblesLimit", label="自付款额度"),
		@Column(name="maintain_typr", attrName="maintainTypr", label="维修类型"),
		@Column(name="offer_date", attrName="offerDate", label="确认报价时间"),
		@Column(name="pay_date", attrName="payDate", label="付款时间"),
		@Column(name="pay_status", attrName="payStatus", label="支付状态"),
		@Column(name="form_type", attrName="formType", label="工单类型"),
		@Column(name="pay_type", attrName="payType", label="支付方式"),
		@Column(name="affirm_status", attrName="affirmStatus", label="财务确认状态"),
		@Column(name="open_id", attrName="openId", label="open_id"),
		@Column(name="wx_nick", attrName="wxNick", label="微信昵称"),
		@Column(name="create_date", attrName="createDate", label="create_date"),
		@Column(name="create_by", attrName="createBy", label="create_by"),
		@Column(name="update_date", attrName="updateDate", label="update_date"),
		@Column(name="update_by", attrName="updateBy", label="update_by"),
		@Column(name="remark", attrName="remark", label="remark"),
	}, joinTable= {
		@JoinTable(type = Type.LEFT_JOIN, entity = HtFormInfo.class, alias = "f",
				on = "f.id = a.form_id",
				columns = {@Column(includeEntity = HtFormInfo.class)}),
} ,orderBy="a.update_date DESC"
)
public class HtDeductibleInfo extends BpmEntity<HtDeductibleInfo> {
	
	private static final long serialVersionUID = 1L;
	private HtFormInfo htFormInfo;		// 工单号
	private String bdId;		// bd_id
	private String userName;		// user_name
	private String phone;		// phone
	private String productName;		// product_name
	private String productId;		// product_id
	private Double deductiblesLimit;		// 自付款额度
	private String maintainTypr;		// 维修类型
	private Date offerDate;		// 确认报价时间
	private Date payDate;		// 付款时间
	private String payStatus;		// 支付状态
	private String formType;		// 工单类型
	private String affirmStatus;		// 财务确认状态
	private String openId;		// open_id
	private String wxNick;		// 微信昵称
	private String payType;		//支付方式
	private String remark;		// remark

	public HtFormInfo getHtFormInfo() {
		return htFormInfo;
	}

	public void setHtFormInfo(HtFormInfo htFormInfo) {
		this.htFormInfo = htFormInfo;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public HtDeductibleInfo() {
		this(null);
	}

	public HtDeductibleInfo(String id){
		super(id);
	}

	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	
	@Length(min=0, max=255, message="user_name长度不能超过 255 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="phone长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="product_name长度不能超过 255 个字符")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=255, message="product_id长度不能超过 255 个字符")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public Double getDeductiblesLimit() {
		return deductiblesLimit;
	}

	public void setDeductiblesLimit(Double deductiblesLimit) {
		this.deductiblesLimit = deductiblesLimit;
	}
	
	@Length(min=0, max=1, message="维修类型长度不能超过 1 个字符")
	public String getMaintainTypr() {
		return maintainTypr;
	}

	public void setMaintainTypr(String maintainTypr) {
		this.maintainTypr = maintainTypr;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@Length(min=0, max=1, message="支付状态长度不能超过 1 个字符")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Length(min=0, max=1, message="财务确认状态长度不能超过 1 个字符")
	public String getAffirmStatus() {
		return affirmStatus;
	}

	public void setAffirmStatus(String affirmStatus) {
		this.affirmStatus = affirmStatus;
	}
	
	@Length(min=0, max=64, message="open_id长度不能超过 64 个字符")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=255, message="微信昵称长度不能超过 255 个字符")
	public String getWxNick() {
		return wxNick;
	}

	public void setWxNick(String wxNick) {
		this.wxNick = wxNick;
	}
	
	@Length(min=0, max=255, message="remark长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}