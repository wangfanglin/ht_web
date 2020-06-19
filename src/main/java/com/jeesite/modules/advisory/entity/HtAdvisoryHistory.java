/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * ht_advisory_historyEntity
 * @author zhaohaifeng
 * @version 2020-03-31
 */
@Table(name="ht_advisory_history", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="task_by", attrName="taskBy", label="任务处理人"),
		@Column(name="activity_id", attrName="activityId", label="活动ID"),
		@Column(name="user_name", attrName="userName", label="user_name", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="phone"),
		@Column(name="id_type", attrName="idType", label="证件类型"),
		@Column(name="id_number", attrName="idNumber", label="id_number"),
		@Column(name="phone_brand", attrName="phoneBrand", label="手机品牌"),
		@Column(name="phone_type", attrName="phoneType", label="手机型号"),
		@Column(name="msg_type", attrName="msgType", label="信息类型"),
		@Column(name="office_id", attrName="officeId", label="人员/网点id"),
		@Column(name="department_id", attrName="departmentId", label="部门_id"),
		@Column(name="original_form_id", attrName="originalFormId", label="原始工单ID"),
		@Column(name="form_id", attrName="formId", label="工单ID"),
		@Column(name="contact_status", attrName="contactStatus", label="联系情况"),
		@Column(name="again_date", attrName="againDate", label="再次联系时间"),
		@Column(name="type", attrName="type", label="状态"),
		@Column(name="policy_id", attrName="policyId", label="保单ID"),
		@Column(name="from_phone", attrName="fromPhone", label="工单电话"),
		@Column(name="task_id", attrName="taskId", label="任务ID"),
		@Column(name="duty", attrName="duty", label="责任判定"),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="call_id", attrName="callId", label="remark"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="call_phone", attrName="callPhone", label="接入电话"),
	}, orderBy="a.update_date DESC"
)
public class HtAdvisoryHistory extends DataEntity<HtAdvisoryHistory> {
	
	private static final long serialVersionUID = 1L;
	private String taskBy;		// 任务处理人
	private String activityId;		// 活动ID
	private String taskId;		// 任务ID
	private String userName;		// user_name
	private String phone;		// phone
	private String idType;		// 证件类型
	private String idNumber;		// id_number
	private String phoneBrand;		// 手机品牌
	private String phoneType;		// 手机型号
	private String msgType;		// 信息类型
	private String officeId;		//
	private String departmentId;		//
	private String originalFormId;		// 原始工单ID
	private String formId;		// 工单ID
	private String fromPhone;		// 工单电话
	private String remark;		// remark
	private String policyId;		// remark
	private String type;		//0表示创建咨询工单1表示后续联系  展示历史判断
	private String callPhone;		// 接入电话
	private String contactStatus;  //联系情况
	private Date againDate;  //再次联系时间
	private String duty;  //责任判定
	private String callId;  //责任判定

	public HtAdvisoryHistory() {
		this(null);
	}

	public HtAdvisoryHistory(String id){
		super(id);
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=255, message="任务处理人长度不能超过 255 个字符")
	public String getTaskBy() {
		return taskBy;
	}

	public void setTaskBy(String taskBy) {
		this.taskBy = taskBy;
	}
	
	@Length(min=0, max=64, message="活动ID长度不能超过 64 个字符")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
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
	
	@Length(min=0, max=64, message="人员/网点id长度不能超过 64 个字符")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="部门_id长度不能超过 64 个字符")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Length(min=0, max=64, message="原始工单ID长度不能超过 64 个字符")
	public String getOriginalFormId() {
		return originalFormId;
	}

	public void setOriginalFormId(String originalFormId) {
		this.originalFormId = originalFormId;
	}
	
	@Length(min=0, max=64, message="工单ID长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
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
	
	@Length(min=0, max=11, message="接入电话长度不能超过 11 个字符")
	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	
}