/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htmaintenancepoint.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 维修网点表Entity
 * @author hongmengzhong
 * @version 2020-02-18
 */
@Table(name="ht_maintain_brand_middle", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="maintenance_point_id", attrName="maintenancePointId.id", label="maintenance_point_id"),
		@Column(name="brand_id", attrName="brandId", label="brand_id"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
	}, orderBy="a.create_date ASC"
)
public class HtMaintainBrandMiddle extends DataEntity<HtMaintainBrandMiddle> {
	
	private static final long serialVersionUID = 1L;
	private HtMaintenancePoint maintenancePointId;		// maintenance_point_id 父类
	private String brandId;		// brand_id
	
	public HtMaintainBrandMiddle() {
		this(null);
	}


	public HtMaintainBrandMiddle(HtMaintenancePoint maintenancePointId){
		this.maintenancePointId = maintenancePointId;
	}
	
	@Length(min=0, max=64, message="maintenance_point_id长度不能超过 64 个字符")
	public HtMaintenancePoint getMaintenancePointId() {
		return maintenancePointId;
	}

	public void setMaintenancePointId(HtMaintenancePoint maintenancePointId) {
		this.maintenancePointId = maintenancePointId;
	}
	
	@Length(min=0, max=64, message="brand_id长度不能超过 64 个字符")
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
}