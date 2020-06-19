/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.provider.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 保险供应商Entity
 * @author tangchao
 * @version 2020-02-21
 */
@Table(name="ht_insurance_provider_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="serialnumber", attrName="serialnumber", label="编码", isInsert=true, isUpdate=false),
		@Column(name="suppliername", attrName="suppliername", label="保险供应商名称", queryType=QueryType.LIKE),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注", isQuery=false),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtInsuranceProviderInfo extends DataEntity<HtInsuranceProviderInfo> {
	
	private static final long serialVersionUID = 1L;
	private String serialnumber;		// 编码
	private String suppliername;		// 保险供应商名称
	private String remark;		// 备注
	
	public HtInsuranceProviderInfo() {
		this(null);
	}

	public HtInsuranceProviderInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="编码长度不能超过 255 个字符")
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	
	@NotBlank(message="保险供应商名称不能为空")
	@Length(min=0, max=255, message="保险供应商名称长度不能超过 255 个字符")
	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}