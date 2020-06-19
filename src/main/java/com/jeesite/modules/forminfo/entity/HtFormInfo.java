/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 工单主表Entity
 * @author lichao
 * @version 2020-02-27
 */
@Table(name="ht_form_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="policy_id", attrName="policyInfo.id", label="保单id"),
		@Column(name="manage_status", attrName="manageStatus", label="处理状态"),
		@Column(name="form_status", attrName="formStatus", label="工单状态"),
		@Column(name="form_type", attrName="formType", label="工单类型，1换新，2维修，3投诉"),
		@Column(name="office_id", attrName="office.id", label="维修网点"),
		@Column(name="is_run", attrName="isRun", label="是否关闭"),
		@Column(name="product_child_id", attrName="productChildId", label="子产品id"),
		@Column(name="bh_id", attrName="bhId", label="渤海案件号"),
		@Column(name="bh_flag", attrName="bhFlag", label="是否渤海工单"),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="put_up", attrName="putUp", label="是否挂起"),

		@Column(includeEntity=DataEntity.class),
		@Column(name="is_automatic", attrName="isAutomatic", label="是否自动分配1是2否"),
	}, joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= PolicyInfo.class, alias="p",
				on="p.id = a.policy_id",
				columns={@Column(includeEntity=PolicyInfo.class)}),
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= HtUserApplyInfo.class, alias="ua",
				on="ua.form_id = a.id",
				columns={@Column(includeEntity=HtUserApplyInfo.class)}),
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Office.class, alias="o",
				on="o.office_code = a.office_id",
				columns={@Column(includeEntity=Office.class)}),
	}, orderBy="a.update_date DESC"
)
public class HtFormInfo extends BpmEntity<HtFormInfo> {
	
	private static final long serialVersionUID = 1L;
	private PolicyInfo policyInfo;		// 保单id
	private String manageStatus;		// 处理状f态
	private String formStatus;		// 工单状态
	private String formType;		// 工单类型，1换新，2维修，3投诉 0在线理赔
	private String isAutomatic;		// 是否自动分配1是2否
	private Office office;		// 是否自动分配1是2否
	private String isRun;		// 工单是否关闭0关闭1反之 默认1
	private String productChildId;
	private String bhId;		//渤海案件号
	private String bhFlag;		//0普通工单1渤海工单
	private String putUp;		//是否挂起  0正常 1挂起状态

	private String userId;   //用户id


	private HtUserApplyInfo htUserApplyInfo;

	public HtUserApplyInfo getHtUserApplyInfo() {
		return htUserApplyInfo;
	}

	public void setHtUserApplyInfo(HtUserApplyInfo htUserApplyInfo) {
		this.htUserApplyInfo = htUserApplyInfo;
	}

	public String getPutUp() {
		return putUp;
	}

	public void setPutUp(String putUp) {
		this.putUp = putUp;
	}

	public String getProductChildId() {
		return productChildId;
	}

	public void setProductChildId(String productChildId) {
		this.productChildId = productChildId;
	}

	public HtFormInfo() {
		this(null);
	}

	public HtFormInfo(String id){
		super(id);
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

	@Length(min=0, max=10, message="处理状态长度不能超过 10 个字符")
	public String getManageStatus() {
		return manageStatus;
	}

	public void setManageStatus(String manageStatus) {
		this.manageStatus = manageStatus;
	}
	
	@Length(min=0, max=10, message="工单状态长度不能超过 10 个字符")
	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	@Length(min=0, max=10, message="工单类型，0在线核赔，1换新，2维修，3投诉长度不能超过 10 个字符")
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	@Length(min=0, max=1, message="是否自动分配1是2否长度不能超过 1 个字符")
	public String getIsAutomatic() {
		return isAutomatic;
	}

	public void setIsAutomatic(String isAutomatic) {
		this.isAutomatic = isAutomatic;
	}

	public String getIsRun() {
		return isRun;
	}

	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}

	public String getBhId() {
		return bhId;
	}

	public void setBhId(String bhId) {
		this.bhId = bhId;
	}

	public String getBhFlag() {
		return bhFlag;
	}

	public void setBhFlag(String bhFlag) {
		this.bhFlag = bhFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}