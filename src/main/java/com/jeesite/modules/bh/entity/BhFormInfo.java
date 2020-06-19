/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bh.entity;

import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.policy.entity.BhPolicy;
import com.jeesite.modules.policy.entity.Policy;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * bh_form_infoEntity
 * @author lichao
 * @version 2020-04-20
 */
@Table(name="bh_form_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单号"),
		@Column(name="cln_no", attrName="clnNo", label="案件号", isQuery=false),
		@Column(name="order_id", attrName="orderId", label="订单号", isQuery=false),
		@Column(name="device_code", attrName="deviceCode", label="设备码", isQuery=false),
		@Column(name="device_type", attrName="deviceType", label="设备分类ID", isQuery=false),
		@Column(name="device_brand", attrName="deviceBrand", label="品牌ID", isQuery=false),
		@Column(name="device_model", attrName="deviceModel", label="型号ID", isQuery=false),
		@Column(name="device_attr", attrName="deviceAttr", label="属性值ID", isQuery=false),
	}, orderBy="a.id DESC"
)
public class BhFormInfo extends DataEntity<BhFormInfo> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// 工单号
	private String clnNo;		// 案件号
	private String orderId;		// 订单号
	private String deviceCode;		// 设备码
	private String deviceType;		// 设备分类ID
	private String deviceBrand;		// 品牌ID
	private String deviceModel;		// 型号ID
	private String deviceAttr;		// 属性值ID
	
	public BhFormInfo() {
		this(null);
	}

	public BhFormInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="工单号长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=255, message="案件号长度不能超过 255 个字符")
	public String getClnNo() {
		return clnNo;
	}

	public void setClnNo(String clnNo) {
		this.clnNo = clnNo;
	}
	
	@Length(min=0, max=255, message="订单号长度不能超过 255 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=255, message="设备码长度不能超过 255 个字符")
	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	@Length(min=0, max=255, message="设备分类ID长度不能超过 255 个字符")
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	@Length(min=0, max=255, message="品牌ID长度不能超过 255 个字符")
	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}
	
	@Length(min=0, max=255, message="型号ID长度不能超过 255 个字符")
	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	
	@Length(min=0, max=255, message="属性值ID长度不能超过 255 个字符")
	public String getDeviceAttr() {
		return deviceAttr;
	}

	public void setDeviceAttr(String deviceAttr) {
		this.deviceAttr = deviceAttr;
	}

	public static BhFormInfo convertByOld(Policy policy,BhPolicy bhPolicy, HtFormInfo formInfo){
		BhFormInfo bhFormInfo = new BhFormInfo();
		bhFormInfo.setFormId(formInfo.getId());
		bhFormInfo.setClnNo(bhPolicy.getClmno());
		bhFormInfo.setOrderId(policy.getId());
		bhFormInfo.setDeviceCode(policy.getStrimeinum());
		bhFormInfo.setDeviceType(Long.toString(bhPolicy.getCategoryid()));
		bhFormInfo.setDeviceBrand(Long.toString(bhPolicy.getBrandid()));
		bhFormInfo.setDeviceModel(Long.toString(bhPolicy.getDeviceid()));
		bhFormInfo.setDeviceAttr(Long.toString(bhPolicy.getValueid()));
		return bhFormInfo;
	}

}