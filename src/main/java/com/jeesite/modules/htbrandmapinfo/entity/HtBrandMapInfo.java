/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbrandmapinfo.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 品牌映射表Entity
 * @author hongmengzhong
 * @version 2020-02-19
 */
@Table(name="ht_brand_map_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="distribution_id", attrName="distributionId", label="销售渠道"),
		@Column(name="original_brand", attrName="originalBrand", label="渠道商原品牌"),
		@Column(name="map_brand_id", attrName="mapBrandId", label="映射品牌"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
		},joinTable={
			@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= HtBrandInfo.class, alias="o",
					on="o.id = a.map_brand_id",
					columns={@Column(includeEntity=HtBrandInfo.class)}),
			@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Office.class, alias="f",
				on="f.office_code = a.distribution_id",
				columns={@Column(includeEntity=Office.class)}),
		},
		orderBy="a.update_date DESC"
)
public class HtBrandMapInfo extends DataEntity<HtBrandMapInfo> {
	
	private static final long serialVersionUID = 1L;
	private String distributionId;		// 销售渠道
	private String originalBrand;		// 渠道商原品牌
	private String mapBrandId;		// 映射品牌
	private String remark;		// remark
	private HtBrandInfo htBrandInfo;
	private Office office;

	@ExcelField(title="销售渠道",sort=10,
			fieldType = Office.class,
			attrName = "office.officeName",
			align = ExcelField.Align.LEFT,type = ExcelField.Type.EXPORT)
	//@Length(min=0, max=64, message="销售渠道长度不能超过 64 个字符")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	@ExcelField(title="映射品牌",sort=30,
			fieldType = HtBrandInfo.class,
			attrName = "htBrandInfo.name",
			align = ExcelField.Align.LEFT,type = ExcelField.Type.EXPORT)
	public HtBrandInfo getHtBrandInfo() {
		return htBrandInfo;
	}

	public void setHtBrandInfo(HtBrandInfo htBrandInfo) {
		this.htBrandInfo = htBrandInfo;
	}

	public HtBrandMapInfo() {
		this(null);
	}

	public HtBrandMapInfo(String id){
		super(id);
	}
	@ExcelField(title="销售渠道",sort=15,
			attrName = "distributionId",
			align = ExcelField.Align.LEFT,type = ExcelField.Type.IMPORT)
	@Length(min=0, max=64, message="销售渠道长度不能超过 64 个字符")
	public String getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(String distributionId) {
		this.distributionId = distributionId;
	}
	@ExcelField(title="原品牌",sort=20,
			align = ExcelField.Align.LEFT)
	@Length(min=0, max=255, message="渠道商原品牌长度不能超过 255 个字符")
	public String getOriginalBrand() {
		return originalBrand;
	}

	public void setOriginalBrand(String originalBrand) {
		this.originalBrand = originalBrand;
	}
	@ExcelField(title="映射品牌",sort=35,
			attrName = "mapBrandId",
			align = ExcelField.Align.LEFT,type = ExcelField.Type.IMPORT)
	@Length(min=0, max=64, message="映射品牌长度不能超过 64 个字符")
	public String getMapBrandId() {
		return mapBrandId;
	}

	public void setMapBrandId(String mapBrandId) {
		this.mapBrandId = mapBrandId;
	}
	@ExcelField(title="备注",sort=40,
			align = ExcelField.Align.LEFT)
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}