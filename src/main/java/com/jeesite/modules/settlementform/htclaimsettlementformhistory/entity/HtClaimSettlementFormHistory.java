/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementformhistory.entity;

import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.sys.entity.Area;
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
 * 在线理赔历史表Entity
 * @author hongmengzhong
 * @version 2020-03-07
 */
@Table(name="ht_claim_settlement_form_history", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单id"),
		@Column(name="work_order_id", attrName="workOrderId", label="工单ID"),
		@Column(name="operating_status", attrName="operatingStatus", label="操作状态"),
		@Column(name="task_rescription", attrName="taskRescription", label="环节定义描述"),
		@Column(name="task_assigenee", attrName="taskAssigenee", label="环节处理人"),
		@Column(name="activity_id", attrName="activityId", label="环节id"),
		@Column(name="form_type", attrName="formType", label="表单类型"),
		@Column(name="task_time", attrName="taskTime", label="环节时间"),
		@Column(name="contact_status", attrName="contactStatus", label="联系结果"),
		@Column(name="again_contact_date", attrName="againContactDate", label="再次联系时间"),
		@Column(name="user_name", attrName="userName", label="客户名字", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="手机号"),
		@Column(name="phone_model", attrName="phoneModel", label="手机型号"),
		@Column(name="settlement_data_id", attrName="settlementDataId", label="理赔资料中间表id"),
		@Column(name="phone_brand", attrName="phoneBrand", label="手机品牌"),
		@Column(name="vice_parts", attrName="viceParts", label="副部件"),
		@Column(name="master_unit", attrName="masterUnit", label="选择得主部件"),
		@Column(name="id_number", attrName="idNumber", label="身份证"),
		@Column(name="cause_damage", attrName="causeDamage", label="损坏原因"),
		@Column(name="maintain_id", attrName="maintainId", label="维修类型"),
		@Column(name="is_qualified", attrName="isQualified", label="理赔资料是否合格1是0否"),
		@Column(name="maintain_type", attrName="maintainType", label="维修类型1维修0换新"),
		@Column(name="serve_type", attrName="serveType", label="服务方式0", comment="服务方式0：寄修"),
		@Column(name="maintain_branch_id", attrName="maintainBranchId", label="维修网点ID"),
		@Column(name="sms", attrName="sms", label="生成短信"),
		@Column(name="is_express", attrName="isExpress", label="是否需要快递1是0否"),
		@Column(name="return_name", attrName="returnName", label="回寄姓名", queryType=QueryType.LIKE),
		@Column(name="return_phone", attrName="returnPhone", label="return_phone"),
		@Column(name="return_area_code", attrName="returnAreaCode", label="城市编码"),
		@Column(name="return_address", attrName="returnAddress", label="回寄全地址"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="disqualification_disqualification", attrName="disqualificationDisqualification", label="理赔资料不合格原因"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
		@Column(name="call_info_id", attrName="callInfoId", label="电话记录id"),
		@Column(name="damage_imgs", attrName="damageImgs", label="损坏图片"),
		@Column(name="identity_card_imgs", attrName="identityCardImgs", label="身份证图片"),
		@Column(name="purchase_imgs", attrName="purchaseImgs", label="购买图片"),
	}, joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Office.class, alias="of",
				on="of.office_code = a.maintain_branch_id",
				columns={@Column(includeEntity=Office.class)}),
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Area.class, alias="are",
				on="are.area_code = a.return_area_code",
				columns={@Column(includeEntity=Area.class)}),
},  orderBy="a.update_date DESC"
)
public class HtClaimSettlementFormHistory extends DataEntity<HtClaimSettlementFormHistory> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// 工单id
	private String workOrderId;		// 工单IDht_claim_settlement_form_history
	private String operatingStatus;		// 操作状态
	private String taskRescription;		// 环节定义描述
	private String taskAssigenee;		// 环节处理人
	private String activityId;		// 环节id
	private String formType;
	private Date taskTime;		// 环节时间
	private String contactStatus;		// 联系结果
	private Date againContactDate;		// 再次联系时间
	private String userName;		// 客户名字
	private String phone;		// 手机号
	private String phoneModel;		// 手机型号
	private String settlementDataId;		// 理赔资料中间表id
	private String phoneBrand;		// 手机品牌
	private String viceParts;		// 副部件
	private String masterUnit;		// 选择得主部件
	private String idNumber;		// 身份证
	private String causeDamage;		// 损坏原因
	private String maintainId;		// 维修类型
	private String isQualified;		// 理赔资料是否合格1是0否
	private String maintainType;		// 维修类型1维修0换新
	private String serveType;		// 服务方式0：寄修
	private String maintainBranchId;		// 维修网点ID
	private String sms;		// 生成短信
	private String isExpress;		// 是否需要快递1是0否
	private String returnName;		// 回寄姓名
	private String returnPhone;		// return_phone
	private String returnAreaCode;		// 城市编码
	private String returnAddress;		// 回寄全地址
	private String remark;		// remark
	private String disqualificationDisqualification;		// 理赔资料不合格原因
	private String claimDataStr;   //资料选项
	private String callInfoId;
	private String damageImgs;
	private String identityCardImgs;
	private String purchaseImgs;
	private Office office;
	private Area area;

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getDamageImgs() {
		return damageImgs;
	}

	public void setDamageImgs(String damageImgs) {
		this.damageImgs = damageImgs;
	}

	public String getIdentityCardImgs() {
		return identityCardImgs;
	}

	public void setIdentityCardImgs(String identityCardImgs) {
		this.identityCardImgs = identityCardImgs;
	}

	public String getPurchaseImgs() {
		return purchaseImgs;
	}

	public void setPurchaseImgs(String purchaseImgs) {
		this.purchaseImgs = purchaseImgs;
	}

	public String getCallInfoId() {
		return callInfoId;
	}

	public void setCallInfoId(String callInfoId) {
		this.callInfoId = callInfoId;
	}

	public String getClaimDataStr() {
		return claimDataStr;
	}

	public void setClaimDataStr(String claimDataStr) {
		this.claimDataStr = claimDataStr;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public HtClaimSettlementFormHistory() {
		this(null);
	}

	public HtClaimSettlementFormHistory(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=64, message="工单ID长度不能超过 64 个字符")
	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	
	@Length(min=0, max=64, message="操作状态长度不能超过 64 个字符")
	public String getOperatingStatus() {
		return operatingStatus;
	}

	public void setOperatingStatus(String operatingStatus) {
		this.operatingStatus = operatingStatus;
	}
	
	@Length(min=0, max=64, message="环节定义描述长度不能超过 64 个字符")
	public String getTaskRescription() {
		return taskRescription;
	}

	public void setTaskRescription(String taskRescription) {
		this.taskRescription = taskRescription;
	}
	
	@Length(min=0, max=64, message="环节处理人长度不能超过 64 个字符")
	public String getTaskAssigenee() {
		return taskAssigenee;
	}

	public void setTaskAssigenee(String taskAssigenee) {
		this.taskAssigenee = taskAssigenee;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}
	
	@Length(min=0, max=1, message="联系结果长度不能超过 1 个字符")
	public String getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAgainContactDate() {
		return againContactDate;
	}

	public void setAgainContactDate(Date againContactDate) {
		this.againContactDate = againContactDate;
	}
	
	@Length(min=0, max=255, message="客户名字长度不能超过 255 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="手机号长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=64, message="手机型号长度不能超过 64 个字符")
	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	
	@Length(min=0, max=64, message="理赔资料中间表id长度不能超过 64 个字符")
	public String getSettlementDataId() {
		return settlementDataId;
	}

	public void setSettlementDataId(String settlementDataId) {
		this.settlementDataId = settlementDataId;
	}
	
	@Length(min=0, max=255, message="手机品牌长度不能超过 255 个字符")
	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}
	
	@Length(min=0, max=64, message="副部件长度不能超过 64 个字符")
	public String getViceParts() {
		return viceParts;
	}

	public void setViceParts(String viceParts) {
		this.viceParts = viceParts;
	}
	
	@Length(min=0, max=64, message="选择得主部件长度不能超过 64 个字符")
	public String getMasterUnit() {
		return masterUnit;
	}

	public void setMasterUnit(String masterUnit) {
		this.masterUnit = masterUnit;
	}
	
	@Length(min=0, max=64, message="身份证长度不能超过 64 个字符")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@Length(min=0, max=255, message="损坏原因长度不能超过 255 个字符")
	public String getCauseDamage() {
		return causeDamage;
	}

	public void setCauseDamage(String causeDamage) {
		this.causeDamage = causeDamage;
	}
	
	@Length(min=0, max=64, message="维修类型长度不能超过 64 个字符")
	public String getMaintainId() {
		return maintainId;
	}

	public void setMaintainId(String maintainId) {
		this.maintainId = maintainId;
	}
	
	@Length(min=0, max=1, message="理赔资料是否合格1是0否长度不能超过 1 个字符")
	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	
	@Length(min=0, max=1, message="维修类型1维修0换新长度不能超过 1 个字符")
	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	
	@Length(min=0, max=1, message="服务方式0长度不能超过 1 个字符")
	public String getServeType() {
		return serveType;
	}

	public void setServeType(String serveType) {
		this.serveType = serveType;
	}
	
	@Length(min=0, max=64, message="维修网点ID长度不能超过 64 个字符")
	public String getMaintainBranchId() {
		return maintainBranchId;
	}

	public void setMaintainBranchId(String maintainBranchId) {
		this.maintainBranchId = maintainBranchId;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}
	
	@Length(min=0, max=1, message="是否需要快递1是0否长度不能超过 1 个字符")
	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	
	@Length(min=0, max=255, message="回寄姓名长度不能超过 255 个字符")
	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	
	@Length(min=0, max=11, message="return_phone长度不能超过 11 个字符")
	public String getReturnPhone() {
		return returnPhone;
	}

	public void setReturnPhone(String returnPhone) {
		this.returnPhone = returnPhone;
	}
	
	@Length(min=0, max=64, message="城市编码长度不能超过 64 个字符")
	public String getReturnAreaCode() {
		return returnAreaCode;
	}

	public void setReturnAreaCode(String returnAreaCode) {
		this.returnAreaCode = returnAreaCode;
	}
	
	@Length(min=0, max=255, message="回寄全地址长度不能超过 255 个字符")
	public String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}
	
	@Length(min=0, max=255, message="remark长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDisqualificationDisqualification() {
		return disqualificationDisqualification;
	}

	public void setDisqualificationDisqualification(String disqualificationDisqualification) {
		this.disqualificationDisqualification = disqualificationDisqualification;
	}
	
}