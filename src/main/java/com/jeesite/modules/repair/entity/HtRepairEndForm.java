/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.forminfo.entity.HtFormOperation;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.renew.entity.HtRenewForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtProcessInstanceBase;
import com.jeesite.modules.sys.entity.Office;

import java.util.Date;

/**
 * 维修工单Entity
 * @author lichao
 * @version 2020-03-25
 */
@Table
public class HtRepairEndForm extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private HtRepairClientForm htRepairClientForm ;		// 维修
	private HtClaimSettlementForm htClaimSettlementForm;//在线理赔实体
	private PolicyInfo policyInfo; //保单
	private Office office; //机构

	private HtFormOperation htFormOperation; //重启


	private String id;
	private String formStatus;
	private String manageStatus;
	private String formType;
	private Date updateDate;

	private String isRun;


	private Date startDate;
	private Date endDate;



	public HtRepairClientForm getHtRepairClientForm() {
		return htRepairClientForm;
	}

	public void setHtRepairClientForm(HtRepairClientForm htRepairClientForm) {
		this.htRepairClientForm = htRepairClientForm;
	}

	public HtClaimSettlementForm getHtClaimSettlementForm() {
		return htClaimSettlementForm;
	}

	public void setHtClaimSettlementForm(HtClaimSettlementForm htClaimSettlementForm) {
		this.htClaimSettlementForm = htClaimSettlementForm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}

	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getManageStatus() {
		return manageStatus;
	}

	public void setManageStatus(String manageStatus) {
		this.manageStatus = manageStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public HtFormOperation getHtFormOperation() {
		return htFormOperation;
	}

	public void setHtFormOperation(HtFormOperation htFormOperation) {
		this.htFormOperation = htFormOperation;
	}

	public String getIsRun() {
		return isRun;
	}

	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}