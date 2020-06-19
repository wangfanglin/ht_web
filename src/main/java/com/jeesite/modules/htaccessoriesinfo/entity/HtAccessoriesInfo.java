/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htaccessoriesinfo.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 配件表Entity
 * @author hongmengzhong
 * @version 2020-02-19
 */
@Table(name="ht_accessories_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="配件名称", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="配件型号"),
		@Column(name="is_original", attrName="isOriginal", label="是否原厂"),
		@Column(name="category", attrName="category", label="所属类目"),
		@Column(name="assembly_id", attrName="assemblyId", label="所属部件"),
		@Column(name="price", attrName="price", label="价格"),
		@Column(name="is_start", attrName="isStart", label="是否启用"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	},
		joinTable={
				@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= HtAssemblyUnit.class, alias="b",
						on="b.id = a.assembly_id",
						columns={@Column(includeEntity=HtAssemblyUnit.class)}),
		},
		orderBy="a.update_date DESC"
)
public class HtAccessoriesInfo extends DataEntity<HtAccessoriesInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 配件名称
	private String type;		// 配件型号
	private String isOriginal;		// 是否原厂
	private String category;		// 所属类目
	private Long assemblyId;		// 所属部件
	private Double price;		// 价格
	private String isStart;		// 是否启用
	private String remark;		// 备注
	private String brandStr;
	private String phoneModelStr;
	private String phoneBrand;
	private String phoneModel;
	private HtAssemblyUnit htAssemblyUnit;

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getPhoneModelStr() {
		return phoneModelStr;
	}

	public void setPhoneModelStr(String phoneModelStr) {
		this.phoneModelStr = phoneModelStr;
	}

	public HtAssemblyUnit getHtAssemblyUnit() {
		return htAssemblyUnit;
	}

	public void setHtAssemblyUnit(HtAssemblyUnit htAssemblyUnit) {
		this.htAssemblyUnit = htAssemblyUnit;
	}

	public String getBrandStr() {
		return brandStr;
	}

	public void setBrandStr(String brandStr) {
		this.brandStr = brandStr;
	}

	public HtAccessoriesInfo() {
		this(null);
	}

	public HtAccessoriesInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="配件名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="配件型号长度不能超过 255 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=1, message="是否原厂长度不能超过 1 个字符")
	public String getIsOriginal() {
		return isOriginal;
	}

	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}
	
	@Length(min=0, max=1, message="所属类目长度不能超过 1 个字符")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public Long getAssemblyId() {
		return assemblyId;
	}

	public void setAssemblyId(Long assemblyId) {
		this.assemblyId = assemblyId;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Length(min=0, max=1, message="是否启用长度不能超过 1 个字符")
	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}