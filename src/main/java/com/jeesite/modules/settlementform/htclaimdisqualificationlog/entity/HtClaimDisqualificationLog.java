/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimdisqualificationlog.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 核赔不合格日志表Entity
 * @author hongmengzhong
 * @version 2020-04-07
 */
@Table(name="ht_claim_disqualification_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单id"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtClaimDisqualificationLog extends DataEntity<HtClaimDisqualificationLog> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// 工单id
	private String remark;		// 备注
	
	public HtClaimDisqualificationLog() {
		this(null);
	}

	public HtClaimDisqualificationLog(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}