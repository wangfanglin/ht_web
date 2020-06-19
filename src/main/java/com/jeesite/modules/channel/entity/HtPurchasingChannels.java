/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 采购渠道表Entity
 * @author tangchao
 * @version 2020-02-22
 */
@Table(name="ht_purchasing_channels", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="code", attrName="code", label="采购渠道编码", isUpdate=false),
		@Column(name="strname", attrName="strname", label="渠道名称", queryType=QueryType.LIKE),
		@Column(name="intcommission", attrName="intcommission", label="反佣"),
		@Column(name="clearing_form", attrName="clearingForm", label="结算方式"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新人", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="strbusinessid", attrName="strbusinessid", label="1.0字段", isInsert=false, isUpdate=false, isQuery=false),
		@Column(name="straccount", attrName="straccount", label="1.0字段", isInsert=false, isUpdate=false, isQuery=false),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtPurchasingChannels extends DataEntity<HtPurchasingChannels> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 采购渠道编码
	private String strname;		// 渠道名称
	private BigDecimal intcommission;		// 反佣
	private String clearingForm;		// 结算方式
	private String remark;		// 备注
	private String strbusinessid;		// 1.0字段
	private String straccount;		// 1.0字段
	
	public HtPurchasingChannels() {
		this(null);
	}

	public HtPurchasingChannels(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="采购渠道编码长度不能超过 255 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotBlank(message="渠道名称不能为空")
	@Length(min=0, max=255, message="渠道名称长度不能超过 255 个字符")
	public String getStrname() {
		return strname;
	}

	public void setStrname(String strname) {
		this.strname = strname;
	}
	
	@NotNull(message="反佣不能为空")
	public BigDecimal getIntcommission() {
		return intcommission;
	}

	public void setIntcommission(BigDecimal intcommission) {
		this.intcommission = intcommission;
	}
	
	@NotBlank(message="结算方式不能为空")
	@Length(min=0, max=1, message="结算方式长度不能超过 1 个字符")
	public String getClearingForm() {
		return clearingForm;
	}

	public void setClearingForm(String clearingForm) {
		this.clearingForm = clearingForm;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public String getStrbusinessid() {
		return strbusinessid;
	}

	public void setStrbusinessid(String strbusinessid) {
		this.strbusinessid = strbusinessid;
	}
	

	public String getStraccount() {
		return straccount;
	}

	public void setStraccount(String straccount) {
		this.straccount = straccount;
	}
	
}