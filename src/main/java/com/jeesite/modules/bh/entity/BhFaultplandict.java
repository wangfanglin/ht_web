/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bh.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 渤海方案库Entity
 * @author lichao
 * @version 2020-04-21
 */
@Table(name="bh_faultplandict", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="categoryid", attrName="categoryid", label="类别ID"),
		@Column(name="categoryname", attrName="categoryname", label="类别名称"),
		@Column(name="brandid", attrName="brandid", label="品牌id"),
		@Column(name="brandname", attrName="brandname", label="品牌名称"),
		@Column(name="modelid", attrName="modelid", label="型号id"),
		@Column(name="modelname", attrName="modelname", label="型号名称"),
		@Column(name="topid", attrName="topid", label="故障大类ID"),
		@Column(name="topname", attrName="topname", label="故障大类名称"),
		@Column(name="malfunctionid", attrName="malfunctionid", label="故障ID"),
		@Column(name="malfunctionname", attrName="malfunctionname", label="故障名称", isQuery=false),
		@Column(name="solutionid", attrName="solutionid", label="方案ID", isQuery=false),
		@Column(name="solution", attrName="solution", label="方案名称", isQuery=false),
		@Column(name="price", attrName="price", label="价格：单位", comment="价格：单位（元）", isQuery=false),
		@Column(name="attributeid", attrName="attributeid", label="属性ID", isQuery=false),
		@Column(name="name", attrName="name", label="属性名称", isQuery=false),
		@Column(name="attributevalueid", attrName="attributevalueid", label="属性值ID", isQuery=false),
		@Column(name="attributevalue", attrName="attributevalue", label="属性值名称", isQuery=false),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标识", isQuery=false),
	}, orderBy="a.update_date DESC"
)
public class BhFaultplandict extends DataEntity<BhFaultplandict> {
	
	private static final long serialVersionUID = 1L;
	private String categoryid;		// 类别ID
	private String categoryname;		// 类别名称
	private String brandid;		// 品牌id
	private String brandname;		// 品牌名称
	private String modelid;		// 型号id
	private String modelname;		// 型号名称
	private String topid;		// 故障大类ID
	private String topname;		// 故障大类名称
	private String malfunctionid;		// 故障ID
	private String malfunctionname;		// 故障名称
	private String solutionid;		// 方案ID
	private String solution;		// 方案名称
	private String price;		// 价格：单位（元）
	private String attributeid;		// 属性ID
	private String name;		// 属性名称
	private String attributevalueid;		// 属性值ID
	private String attributevalue;		// 属性值名称
	private String delFlag;		// 删除标识
	
	public BhFaultplandict() {
		this(null);
	}

	public BhFaultplandict(String id){
		super(id);
	}
	
	@NotBlank(message="类别ID不能为空")
	@Length(min=0, max=64, message="类别ID长度不能超过 64 个字符")
	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
	@NotBlank(message="类别名称不能为空")
	@Length(min=0, max=255, message="类别名称长度不能超过 255 个字符")
	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	@NotBlank(message="品牌id不能为空")
	@Length(min=0, max=64, message="品牌id长度不能超过 64 个字符")
	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	
	@NotBlank(message="品牌名称不能为空")
	@Length(min=0, max=255, message="品牌名称长度不能超过 255 个字符")
	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
	@NotBlank(message="型号id不能为空")
	@Length(min=0, max=64, message="型号id长度不能超过 64 个字符")
	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}
	
	@NotBlank(message="型号名称不能为空")
	@Length(min=0, max=255, message="型号名称长度不能超过 255 个字符")
	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	
	@NotBlank(message="故障大类ID不能为空")
	@Length(min=0, max=64, message="故障大类ID长度不能超过 64 个字符")
	public String getTopid() {
		return topid;
	}

	public void setTopid(String topid) {
		this.topid = topid;
	}
	
	@NotBlank(message="故障大类名称不能为空")
	@Length(min=0, max=255, message="故障大类名称长度不能超过 255 个字符")
	public String getTopname() {
		return topname;
	}

	public void setTopname(String topname) {
		this.topname = topname;
	}
	
	@NotBlank(message="故障ID不能为空")
	@Length(min=0, max=255, message="故障ID长度不能超过 255 个字符")
	public String getMalfunctionid() {
		return malfunctionid;
	}

	public void setMalfunctionid(String malfunctionid) {
		this.malfunctionid = malfunctionid;
	}
	
	@NotBlank(message="故障名称不能为空")
	@Length(min=0, max=255, message="故障名称长度不能超过 255 个字符")
	public String getMalfunctionname() {
		return malfunctionname;
	}

	public void setMalfunctionname(String malfunctionname) {
		this.malfunctionname = malfunctionname;
	}
	
	@NotBlank(message="方案ID不能为空")
	@Length(min=0, max=64, message="方案ID长度不能超过 64 个字符")
	public String getSolutionid() {
		return solutionid;
	}

	public void setSolutionid(String solutionid) {
		this.solutionid = solutionid;
	}
	
	@NotBlank(message="方案名称不能为空")
	@Length(min=0, max=255, message="方案名称长度不能超过 255 个字符")
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	@NotBlank(message="价格：单位不能为空")
	@Length(min=0, max=10, message="价格：单位长度不能超过 10 个字符")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=64, message="属性ID长度不能超过 64 个字符")
	public String getAttributeid() {
		return attributeid;
	}

	public void setAttributeid(String attributeid) {
		this.attributeid = attributeid;
	}
	
	@Length(min=0, max=255, message="属性名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="属性值ID长度不能超过 64 个字符")
	public String getAttributevalueid() {
		return attributevalueid;
	}

	public void setAttributevalueid(String attributevalueid) {
		this.attributevalueid = attributevalueid;
	}
	
	@Length(min=0, max=255, message="属性值名称长度不能超过 255 个字符")
	public String getAttributevalue() {
		return attributevalue;
	}

	public void setAttributevalue(String attributevalue) {
		this.attributevalue = attributevalue;
	}
	
	@Length(min=0, max=255, message="删除标识长度不能超过 255 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}