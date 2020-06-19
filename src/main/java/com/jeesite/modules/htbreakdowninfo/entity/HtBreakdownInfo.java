/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbreakdowninfo.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 维修故障表Entity
 * @author hongmengzhong
 * @version 2020-02-20
 */
@Table(name="ht_breakdown_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="break_type", attrName="breakType", label="故障类型"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtBreakdownInfo extends DataEntity<HtBreakdownInfo> {
	
	private static final long serialVersionUID = 1L;
	private String breakType;		// 故障类型
	private String remark;		// 备注
	private String unitZhuStr; //
	private String unitFuStr; //
	private String assemblyConcat;  //配件拼接展示字段

	public String getAssemblyConcat() {
		return assemblyConcat;
	}

	public void setAssemblyConcat(String assemblyConcat) {
		this.assemblyConcat = assemblyConcat;
	}

	public String getUnitZhuStr() {
		return unitZhuStr;
	}

	public void setUnitZhuStr(String unitZhuStr) {
		this.unitZhuStr = unitZhuStr;
	}

	public String getUnitFuStr() {
		return unitFuStr;
	}

	public void setUnitFuStr(String unitFuStr) {
		this.unitFuStr = unitFuStr;
	}

	public HtBreakdownInfo() {
		this(null);
	}

	public HtBreakdownInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="故障类型长度不能超过 255 个字符")
	public String getBreakType() {
		return breakType;
	}

	public void setBreakType(String breakType) {
		this.breakType = breakType;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}