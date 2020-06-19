/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.entity;

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
 * 待申请工单历史表Entity
 * @author zhaohaifeng
 * @version 2020-04-09
 */
@Table(name="ht_wait_apply_form_history", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单id"),
		@Column(name="operating_status", attrName="operatingStatus", label="操作状态"),
		@Column(name="task_rescription", attrName="taskRescription", label="环节定义描述"),
		@Column(name="task_assigenee", attrName="taskAssigenee", label="环节处理人"),
		@Column(name="activity_id", attrName="activityId", label="环节id"),
		@Column(name="form_type", attrName="formType", label="表单类型"),
		@Column(name="task_time", attrName="taskTime", label="环节时间"),
		@Column(name="contact_status", attrName="contactStatus", label="联系结果"),
		@Column(name="again_contact_date", attrName="againContactDate", label="再次联系时间"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="whether_apply", attrName="whetherApply", label="是否以申请"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtWaitApplyFormHistory extends DataEntity<HtWaitApplyFormHistory> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// 工单id
	private String operatingStatus;		// 操作状态
	private String taskRescription;		// 环节定义描述
	private String taskAssigenee;		// 环节处理人
	private String activityId;		// 环节id
	private String formType;		// 表单类型
	private Date taskTime;		// 环节时间
	private String contactStatus;		// 联系结果
	private Date againContactDate;		// 再次联系时间
	private String remark;		// remark
	private String whetherApply;   //是否以申请


	public String getWhetherApply() {
		return whetherApply;
	}

	public void setWhetherApply(String whetherApply) {
		this.whetherApply = whetherApply;
	}

	public HtWaitApplyFormHistory() {
		this(null);
	}

	public HtWaitApplyFormHistory(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
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
	
	@Length(min=0, max=64, message="环节id长度不能超过 64 个字符")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=64, message="表单类型长度不能超过 64 个字符")
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
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
	
	@Length(min=0, max=255, message="remark长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}