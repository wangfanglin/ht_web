/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.brandinfo.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 设备品牌表Entity
 * @author hongmengzhong
 * @version 2020-02-17
 */
@Table(name="ht_brand_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="设备品牌", queryType=QueryType.LIKE),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="修改时间", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtBrandInfo extends DataEntity<HtBrandInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 设备品牌
	private String remark;		// 备注
	
	public HtBrandInfo() {
		this(null);
	}

	public HtBrandInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="设备品牌长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}