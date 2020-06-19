/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * bh_policyEntity
 * @author tangchao
 * @version 2020-05-07
 */
@Table(name="bh_policy", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="policyinfoid", attrName="policyinfoid", label="保单表id"),
		@Column(name="clmno", attrName="clmno", label="渤海报案号"),
		@Column(name="categoryid", attrName="categoryid", label="类别ID"),
		@Column(name="categoryname", attrName="categoryname", label="类别名称"),
		@Column(name="deviceid", attrName="deviceid", label="型号id"),
		@Column(name="brandid", attrName="brandid", label="品牌id", comment="品牌id（ 产品id 没给）"),
		@Column(name="message", attrName="message", label="出险经过"),
		@Column(name="name", attrName="name", label="报案人姓名", queryType=QueryType.LIKE),
		@Column(name="plyno", attrName="plyno", label="保险单的单号"),
		@Column(name="regioncode", attrName="regioncode", label="区域代码"),
		@Column(name="repairdate", attrName="repairdate", label="上门时间"),
		@Column(name="coverageamt", attrName="coverageamt", label="总保额"),
		@Column(name="attributeid", attrName="attributeid", label="属性ID"),
		@Column(name="attributevalue", attrName="attributevalue", label="属性名称"),
		@Column(name="valueid", attrName="valueid", label="属性值ID"),
		@Column(name="value", attrName="value", label="属性值"),
		@Column(name="taskid", attrName="taskid", label="渤海流程号"),
		@Column(name="remainderamt_zj", attrName="remainderamtZj", label="折旧后剩余保额"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class BhPolicy extends DataEntity<BhPolicy> {
	
	private static final long serialVersionUID = 1L;
	private String policyinfoid;		// 保单表id
	private String clmno;		// 渤海报案号
	private Long categoryid;		// 类别ID
	private String categoryname;		// 类别名称
	private Long deviceid;		// 型号id
	private Long brandid;		// 品牌id（ 产品id 没给）
	private String message;		// 出险经过
	private String name;		// 报案人姓名
	private String plyno;		// 保险单的单号
	private String regioncode;		// 区域代码
	private Date repairdate;		// 上门时间
	private String coverageamt;		// 总保额
	private Long attributeid;		// 属性ID
	private String attributevalue;		// 属性名称
	private Long valueid;		// 属性值ID
	private String value;		// 属性值
	private String taskid;		// 渤海流程号
	private String remainderamtZj;		// 折旧后剩余保额
	private String delFlag;		// 删除标记
	
	public BhPolicy() {
		this(null);
	}

	public BhPolicy(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="保单表id长度不能超过 255 个字符")
	public String getPolicyinfoid() {
		return policyinfoid;
	}

	public void setPolicyinfoid(String policyinfoid) {
		this.policyinfoid = policyinfoid;
	}
	
	@Length(min=0, max=64, message="渤海报案号长度不能超过 64 个字符")
	public String getClmno() {
		return clmno;
	}

	public void setClmno(String clmno) {
		this.clmno = clmno;
	}
	
	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	
	@Length(min=0, max=64, message="类别名称长度不能超过 64 个字符")
	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	public Long getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Long deviceid) {
		this.deviceid = deviceid;
	}
	
	public Long getBrandid() {
		return brandid;
	}

	public void setBrandid(Long brandid) {
		this.brandid = brandid;
	}
	
	@Length(min=0, max=255, message="出险经过长度不能超过 255 个字符")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Length(min=0, max=64, message="报案人姓名长度不能超过 64 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="保险单的单号长度不能超过 64 个字符")
	public String getPlyno() {
		return plyno;
	}

	public void setPlyno(String plyno) {
		this.plyno = plyno;
	}
	
	@Length(min=0, max=64, message="区域代码长度不能超过 64 个字符")
	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepairdate() {
		return repairdate;
	}

	public void setRepairdate(Date repairdate) {
		this.repairdate = repairdate;
	}
	
	@Length(min=0, max=64, message="总保额长度不能超过 64 个字符")
	public String getCoverageamt() {
		return coverageamt;
	}

	public void setCoverageamt(String coverageamt) {
		this.coverageamt = coverageamt;
	}
	
	public Long getAttributeid() {
		return attributeid;
	}

	public void setAttributeid(Long attributeid) {
		this.attributeid = attributeid;
	}
	
	@Length(min=0, max=64, message="属性名称长度不能超过 64 个字符")
	public String getAttributevalue() {
		return attributevalue;
	}

	public void setAttributevalue(String attributevalue) {
		this.attributevalue = attributevalue;
	}
	
	public Long getValueid() {
		return valueid;
	}

	public void setValueid(Long valueid) {
		this.valueid = valueid;
	}
	
	@Length(min=0, max=64, message="属性值长度不能超过 64 个字符")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=0, max=64, message="渤海流程号长度不能超过 64 个字符")
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
	@Length(min=0, max=64, message="折旧后剩余保额长度不能超过 64 个字符")
	public String getRemainderamtZj() {
		return remainderamtZj;
	}

	public void setRemainderamtZj(String remainderamtZj) {
		this.remainderamtZj = remainderamtZj;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}