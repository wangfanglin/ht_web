/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 收款人信息表Entity
 * @author zhaohaifeng
 * @version 2020-03-23
 */
@Table(name="ht_receipt_data", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="htFormInfo.id", label="工单号"),
		@Column(name="bd_id", attrName="bdId", label="bd_id"),
		@Column(name="insurance_number", attrName="insuranceNumber", label="保险单号"),
		@Column(name="bank_name", attrName="bankName", label="银行名称"),
		@Column(name="bank_number", attrName="bankNumber", label="银行账号"),
		@Column(name="bh_report_no", attrName="bhReportNo", label="渤海报案号"),
		@Column(name="payee_name", attrName="payeeName", label="收款人姓名",queryType=QueryType.LIKE),
		@Column(name="payee_id_number", attrName="payeeIdNumber", label="收款人身份证号"),
		@Column(name="audit_status", attrName="auditStatus", label="审核状态"),
		@Column(name="pass_back", attrName="passBack", label="回传状态"),
		@Column(name="create_date", attrName="createDate", label="create_date"),
		@Column(name="create_by", attrName="createBy", label="create_by" ),
		@Column(name="update_date", attrName="updateDate", label="update_date" ),
		@Column(name="update_by", attrName="updateBy", label="update_by" ),
		@Column(name="remark", attrName="remark", label="remark" ),
		@Column(name="province_code", attrName="provinceCode", label="省" ),
		@Column(name="city_code", attrName="cityCode", label="市" ),
		@Column(name="district_code", attrName="districtCode", label="区" ),
		@Column(name="bank_type", attrName="bankType", label="银行类别" ),
		@Column(name="band_img", attrName="bandImg", label="银行卡照片"),
		@Column(name="keep_img", attrName="keepImg", label="留存照片"),
		@Column(name="status", attrName="status", label="留存照片"),
		@Column(name="apply_date", attrName="applyDate", label="申请时间"),
		@Column(name="examine_date", attrName="examineDate", label="审核时间"),
	}, joinTable= {
		@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = HtFormInfo.class, alias = "f",
				on = "f.id = a.form_id",
				columns = {@Column(includeEntity = HtFormInfo.class)}),
} , orderBy="a.update_date DESC"
)
public class HtReceiptData extends BpmEntity<HtReceiptData> {
	
	private static final long serialVersionUID = 1L;
	private HtFormInfo htFormInfo;		// 工单号
	private String bdId;		// bd_id
	private String insuranceNumber;		// 保险单单号
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
	private String status;		// 当前状态
	private Date applyDate;		// 申请时间
	private Date examineDate;		// 审核时间
	//时间查询条件
	private Date beginDate;
	private Date endDate;

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	public HtFormInfo getHtFormInfo() {
		return htFormInfo;
	}

	public void setHtFormInfo(HtFormInfo htFormInfo) {
		this.htFormInfo = htFormInfo;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public HtReceiptData() {
		this(null);
	}

	public HtReceiptData(String id){
		super(id);
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
	
}