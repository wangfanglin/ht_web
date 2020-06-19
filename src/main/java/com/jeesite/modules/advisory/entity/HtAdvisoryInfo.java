/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.entity;

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
 * ht_advisory_infoEntity
 * @author zhaohaifeng
 * @version 2020-03-30
 */
@Table(name="ht_advisory_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="user_name", attrName="userName", label="user_name", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="phone"),
		@Column(name="id_type", attrName="idType", label="证件类型"),
		@Column(name="id_number", attrName="idNumber", label="id_number"),
		@Column(name="phone_brand", attrName="phoneBrand", label="手机品牌"),
		@Column(name="phone_type", attrName="phoneType", label="手机型号"),
		@Column(name="msg_type", attrName="msgType", label="信息类型"),
		@Column(name="office_id", attrName="officeId", label="人员/网点id"),
		@Column(name="department_id", attrName="departmentId", label="部门_id"),
		@Column(name="form_id", attrName="htFormInfo.id", label="工单ID"),
		@Column(name="original_form_id", attrName="originalFormId", label="原始工单ID"),
		@Column(name="from_phone", attrName="fromPhone", label="工单电话"),
		@Column(name="contact_status", attrName="contactStatus", label="联系情况"),
		@Column(name="again_date", attrName="againDate", label="再次联系时间"),
		@Column(name="call_phone", attrName="callPhone", label="接入电话"),
		@Column(name="policy_id", attrName="policyId", label="保单ID"),
		@Column(name="duty", attrName="duty", label="责任判定"),
		@Column(name="task_id", attrName="taskId", label="任务ID"),
		@Column(name="form_type", attrName="formType", label="工单类型"),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="call_id", attrName="callId", label="remark"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=HtFormInfo.class, alias="f",
				on="f.id = a.form_id",
				columns={@Column(includeEntity=HtFormInfo.class)}) }
				,orderBy="a.update_date DESC"
)
public class HtAdvisoryInfo extends BpmEntity<HtAdvisoryInfo> {
	
	private static final long serialVersionUID = 1L;
	private HtFormInfo htFormInfo;		// 工单ID
	private String userName;		// user_name
	private String taskId;		// 任务ID
	private String phone;		// phone
	private String idType;		// 证件类型
	private String idNumber;		// id_number
	private String phoneBrand;		// 手机品牌
	private String phoneType;		// 手机型号
	private String msgType;		// 信息类型
	private String officeId;		// 部门_id
	private String departmentId;		// 人员/网点id
	private String fromPhone;		// 工单电话
	private String callPhone;		//接入电话
	private String originalFormId;  //原始工单ID
	private String contactStatus;  //联系情况
	private Date againDate;  //再次联系时间
	private String policyId;  //保单ID
	private String duty;  //责任判定
	private String formType;  // 工单的类型   固定式  咨询工单
	private String remark;		// remark
	private String callId;		// 电话录音ID
	private String officeName;		// 电话录音ID

	public HtAdvisoryInfo() {
		this(null);
	}

	public HtAdvisoryInfo(String id){
		super(id);
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	public Date getAgainDate() {
		return againDate;
	}

	public void setAgainDate(Date againDate) {
		this.againDate = againDate;
	}

	public String getOriginalFormId() {
		return originalFormId;
	}

	public void setOriginalFormId(String originalFormId) {
		this.originalFormId = originalFormId;
	}

	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
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
	
	@Length(min=0, max=1, message="证件类型长度不能超过 1 个字符")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=18, message="id_number长度不能超过 18 个字符")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@Length(min=0, max=255, message="手机品牌长度不能超过 255 个字符")
	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}
	
	@Length(min=0, max=255, message="手机型号长度不能超过 255 个字符")
	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	@Length(min=0, max=1, message="信息类型长度不能超过 1 个字符")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=64, message="部门_id长度不能超过 64 个字符")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="人员/网点id长度不能超过 64 个字符")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public HtFormInfo getHtFormInfo() {
		return htFormInfo;
	}

	public void setHtFormInfo(HtFormInfo htFormInfo) {
		this.htFormInfo = htFormInfo;
	}

	@Length(min=0, max=11, message="工单电话长度不能超过 11 个字符")
	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}
	
	@Length(min=0, max=255, message="remark长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}