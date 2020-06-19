/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.entity;

import javax.validation.constraints.NotBlank;

import com.jeesite.modules.bpm.entity.BpmEntity;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 工单操作表Entity
 * @author zhaohaifeng
 * @version 2020-03-02
 */
@Table(name="ht_form_operation", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单ID"),
		@Column(name="bd_id", attrName="bdId", label="表单ID"),
		@Column(name="recording", attrName="recording", label="占位用来存电话录音"),
		@Column(name="claim_type", attrName="claimType", label="理赔资料"),
		@Column(name="reason", attrName="reason", label="操作原因/备注"),
		@Column(name="close_type", attrName="closeType", label="关闭原因"),
		@Column(name="verifier", attrName="verifier", label="审核人"),
		@Column(name="url", attrName="url", label="图片路径"),
		@Column(name="reopen_type", attrName="reopenType", label="重启原因"),
		@Column(name="other_reason", attrName="otherReason", label="其他原因"),
		@Column(name="operation_type", attrName="operationType", label="操作类型"),
		@Column(name="audit_remark", attrName="auditRemark", label="审核备注"),
		@Column(name="audit_result", attrName="auditResult", label="审核结果"),
		@Column(name="type", attrName="type", label="申请、审核"),
		@Column(name="is_mail", attrName="isMail", label="申请关闭时自配资料是否需要邮寄 1是0否"),
		@Column(name="intermediary_service_id", attrName="intermediaryServiceId", label="维修网点ID"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
	}, orderBy="a.id DESC"
)
public class HtFormOperation extends BpmEntity<HtFormOperation> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// form_id  工单ID
	private String bdId;			//表单ID
	private String recording;		// 占位用来存电话录音
	private String claimType;		// 理赔资料
	private String reason;		// 操作原因/备注
	private String closeType;		// 关闭原因
	private String verifier;		// 审核人
	private String reopenType;		// 重启原因
	private String   urlName;		// 操作图片的名称
	private String url; //图片路径
	private String otherReason; //其他原因
	private String operationType; //操作类型 //
	private String auditRemark; //审核备注
	private String auditResult; //审核结果 1通过 2 驳回
	private String type; //申请、审核 1申请 2审核
	private String isMail; //申请关闭时自配资料是否需要邮寄 1是0否
	private String intermediaryServiceId;//维修网点Id
	private String officeName;//维修网点Id



	public String getIntermediaryServiceId() {
		return intermediaryServiceId;
	}

	public void setIntermediaryServiceId(String intermediaryServiceId) {
		this.intermediaryServiceId = intermediaryServiceId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getIsMail() {
		return isMail;
	}

	public void setIsMail(String isMail) {
		this.isMail = isMail;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
	}


	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HtFormOperation() {
		this(null);
	}

	public HtFormOperation(String id){
		super(id);
	}
	
	@NotBlank(message="form_id不能为空")
	@Length(min=0, max=64, message="form_id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=255, message="占位用来存电话录音长度不能超过 255 个字符")
	public String getRecording() {
		return recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
	}
	
	@Length(min=0, max=1, message="理赔资料长度不能超过 1 个字符")
	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=1, message="关闭原因长度不能超过 1 个字符")
	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}
	
	@Length(min=0, max=255, message="审核人长度不能超过 255 个字符")
	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	
	@Length(min=0, max=1, message="重启原因长度不能超过 1 个字符")
	public String getReopenType() {
		return reopenType;
	}

	public void setReopenType(String reopenType) {
		this.reopenType = reopenType;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
}