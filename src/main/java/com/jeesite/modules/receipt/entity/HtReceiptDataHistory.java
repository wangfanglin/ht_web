/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 收款人信息历史表Entity
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Table(name="ht_receipt_data_history", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="form_id"),
		@Column(name="bd_id", attrName="bdId", label="bd_id"),
		@Column(name="bank_name", attrName="bankName", label="银行名称", queryType=QueryType.LIKE),
		@Column(name="bank_number", attrName="bankNumber", label="银行账号"),
		@Column(name="bh_report_no", attrName="bhReportNo", label="渤海报案号"),
		@Column(name="payee_name", attrName="payeeName", label="收款人姓名", queryType=QueryType.LIKE),
		@Column(name="payee_id_number", attrName="payeeIdNumber", label="收款人身份证号"),
		@Column(name="audit_status", attrName="auditStatus", label="审核状态"),
		@Column(name="pass_back", attrName="passBack", label="回传状态"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="province_code", attrName="provinceCode", label="省"),
		@Column(name="city_code", attrName="cityCode", label="市"),
		@Column(name="district_code", attrName="districtCode", label="区"),
		@Column(name="bank_type", attrName="bankType", label="银行类别"),
		@Column(name="band_img", attrName="bandImg", label="银行卡照片"),
		@Column(name="keep_img", attrName="keepImg", label="留存照片"),
		@Column(name="insurance_number", attrName="insuranceNumber", label="保险单号"),
	}, orderBy="a.update_date DESC"
)
public class HtReceiptDataHistory extends DataEntity<HtReceiptDataHistory> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// form_id
	private String bdId;		// bd_id
	private String bankName;		// 银行名称
	private String bankNumber;		// 银行账号
	private String bhReportNo;		// 渤海报案号
	private String payeeName;		// 收款人姓名
	private String payeeIdNumber;		// 收款人身份证号
	private String auditStatus;		// 审核状态
	private String passBack;		// 回传状态
	private String remark;		// remark
	private String provinceCode;		// 省
	private String cityCode;		// 市
	private String districtCode;		// 区
	private String bankType;		// 银行类别
	private String bandImg;		// 银行卡照片
	private String keepImg;		// 留存照片
	private String insuranceNumber;		// 保险单号
	
	public HtReceiptDataHistory() {
		this(null);
	}

	public HtReceiptDataHistory(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="form_id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=64, message="bd_id长度不能超过 64 个字符")
	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	
	@Length(min=0, max=255, message="银行名称长度不能超过 255 个字符")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=255, message="银行账号长度不能超过 255 个字符")
	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	public String getBhReportNo() {
		return bhReportNo;
	}

	public void setBhReportNo(String bhReportNo) {
		this.bhReportNo = bhReportNo;
	}
	
	@Length(min=0, max=255, message="收款人姓名长度不能超过 255 个字符")
	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	
	@Length(min=0, max=255, message="收款人身份证号长度不能超过 255 个字符")
	public String getPayeeIdNumber() {
		return payeeIdNumber;
	}

	public void setPayeeIdNumber(String payeeIdNumber) {
		this.payeeIdNumber = payeeIdNumber;
	}
	
	@Length(min=0, max=1, message="审核状态长度不能超过 1 个字符")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Length(min=0, max=1, message="回传状态长度不能超过 1 个字符")
	public String getPassBack() {
		return passBack;
	}

	public void setPassBack(String passBack) {
		this.passBack = passBack;
	}
	
	@Length(min=0, max=255, message="remark长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=255, message="省长度不能超过 255 个字符")
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	@Length(min=0, max=255, message="市长度不能超过 255 个字符")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Length(min=0, max=255, message="区长度不能超过 255 个字符")
	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	@Length(min=0, max=255, message="银行类别长度不能超过 255 个字符")
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	
	@Length(min=0, max=500, message="银行卡照片长度不能超过 500 个字符")
	public String getBandImg() {
		return bandImg;
	}

	public void setBandImg(String bandImg) {
		this.bandImg = bandImg;
	}
	
	@Length(min=0, max=500, message="留存照片长度不能超过 500 个字符")
	public String getKeepImg() {
		return keepImg;
	}

	public void setKeepImg(String keepImg) {
		this.keepImg = keepImg;
	}
	
	@Length(min=0, max=64, message="保险单号长度不能超过 64 个字符")
	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
	
}