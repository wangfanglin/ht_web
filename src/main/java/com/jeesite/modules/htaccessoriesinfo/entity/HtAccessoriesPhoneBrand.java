/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htaccessoriesinfo.entity;

import com.jeesite.common.dao.CrudDao;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 配件与品牌中间表Entity
 * @author hongmengzhong
 * @version 2020-02-19
 */
@Table(name="ht_accessories_phone_brand", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="brand_id", attrName="brandId", label="品牌"),
		@Column(name="accessories_info_id", attrName="accessoriesInfoId", label="accessories_info_id"),
	}, orderBy="a.id DESC"
)
public class HtAccessoriesPhoneBrand extends DataEntity<HtAccessoriesPhoneBrand> {
	
	private static final long serialVersionUID = 1L;
	private String brandId;		// 品牌
	private String accessoriesInfoId;		// accessories_info_id
	
	public HtAccessoriesPhoneBrand() {
		this(null);
	}

	public HtAccessoriesPhoneBrand(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="品牌长度不能超过 64 个字符")
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	@Length(min=0, max=64, message="accessories_info_id长度不能超过 64 个字符")
	public String getAccessoriesInfoId() {
		return accessoriesInfoId;
	}

	public void setAccessoriesInfoId(String accessoriesInfoId) {
		this.accessoriesInfoId = accessoriesInfoId;
	}

}