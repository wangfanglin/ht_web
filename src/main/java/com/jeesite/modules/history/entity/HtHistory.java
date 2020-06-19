/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.history.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 操作历史总表Entity
 * @author tangchao
 * @version 2020-03-02
 */
@Table(name="ht_history", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="work_order_id", attrName="workOrderId", label="工单id"),
		@Column(name="form_id", attrName="formId", label="表单id"),
		@Column(name="form_type", attrName="formType", label="操作类型"),
		@Column(name="user_visible", attrName="userVisible", label="对用户是否可见"),
		@Column(name="cms_visible", attrName="cmsVisible", label="后台管理系统是否可见"),
		@Column(name="activity_id", attrName="activityId", label="环节ID"),
		@Column(name="after_activity_id", attrName="afterActivityId", label="流转后环节ID"),
		@Column(name="operation_status", attrName="operationStatus", label="操作类型"),
		@Column(name="history_form_id", attrName="historyFormId", label="表单的历史ID"),
		@Column(name="dispose_user_id", attrName="disposeUserId", label="处理人ID"),
		@Column(name="uniqueness_id", attrName="uniquenessId", label="分支的标识"),
		@Column(name="activity_name", attrName="activityName", label="环节的名称"),
		@Column(name="is_back", attrName="isBack", label="是否退回"),
		@Column(name="exterior_id", attrName="exteriorId", label="外部ID"),
		@Column(name="after_activity_name", attrName="afterActivityName", label="流转后环节名称"),
		@Column(name="call_id", attrName="callId", label="录音id"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class HtHistory extends DataEntity<HtHistory> {
	
	private static final long serialVersionUID = 1L;
	private String workOrderId;		// 工单id
	private String formId;		// 表单id
	private String historyFormId; //表单的历史ID
	private String formType;		// 工单类型 0在线理赔1换新2维修
	private String userVisible;		// 对用户是否可见  1是0否使用系统字典sys_yes_no
	private String cmsVisible;		// 后台管理系统是否可见  1是0否使用系统字典sys_yes_no
	private String activityId;		// 环节ID
	private String afterActivityId;		// 流转后环节ID
	private String operationStatus;		// 操作类型
	private String activityName;		// 环节的名称
	private String disposeUserId;		// 处理人ID
	private String afterActivityName;		// 流转后环节名称
	private String isBack;		// 是否退回
	private String exteriorId;		// 外部ID
	private String uniquenessId;		// 分支操作的唯一标识（改派、重启、关闭）关联form_operation表的id

	private String callId;		// 录音id



	public String getExteriorId() {
		return exteriorId;
	}

	public void setExteriorId(String exteriorId) {
		this.exteriorId = exteriorId;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getUniquenessId() {
		return uniquenessId;
	}

	public void setUniquenessId(String uniquenessId) {
		this.uniquenessId = uniquenessId;
	}

	public String getHistoryFormId() {
		return historyFormId;
	}

	public void setHistoryFormId(String historyFormId) {
		this.historyFormId = historyFormId;
	}

	public HtHistory() {
		this(null);
	}

	public HtHistory(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	
	@Length(min=0, max=64, message="表单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=64, message="操作类型长度不能超过 64 个字符")
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	@Length(min=0, max=1, message="对用户是否可见长度不能超过 1 个字符")
	public String getUserVisible() {
		return userVisible;
	}

	public void setUserVisible(String userVisible) {
		this.userVisible = userVisible;
	}
	
	@Length(min=0, max=1, message="后台管理系统是否可见长度不能超过 1 个字符")
	public String getCmsVisible() {
		return cmsVisible;
	}

	public void setCmsVisible(String cmsVisible) {
		this.cmsVisible = cmsVisible;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getAfterActivityId() {
		return afterActivityId;
	}

	public void setAfterActivityId(String afterActivityId) {
		this.afterActivityId = afterActivityId;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

	public String getDisposeUserId() {
		return disposeUserId;
	}

	public void setDisposeUserId(String disposeUserId) {
		this.disposeUserId = disposeUserId;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}
}