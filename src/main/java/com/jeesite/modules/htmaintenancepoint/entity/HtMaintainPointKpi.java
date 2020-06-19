/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htmaintenancepoint.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 维修网点kpi指数Entity
 * @author hongmengzhong
 * @version 2020-04-07
 */
@Table(name="ht_maintain_point_kpi", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="point_id", attrName="pointId", label="维修网点id"),
		@Column(name="average_price", attrName="averagePrice", label="平均单价"),
		@Column(name="time_efficiency", attrName="timeEfficiency", label="维修时效"),
		@Column(name="repair_rate", attrName="repairRate", label="返修率"),
		@Column(name="complain_efficiency", attrName="complainEfficiency", label="投诉率"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
	}, orderBy="a.update_date DESC"
)
public class HtMaintainPointKpi extends DataEntity<HtMaintainPointKpi> {
	
	private static final long serialVersionUID = 1L;
	private String pointId;		// 维修网点id
	private double averagePrice;		// 平均单价
	private double timeEfficiency;		// 维修时效
	private double repairRate;		// 返修率
	private double complainEfficiency;		// 投诉率
	
	public HtMaintainPointKpi() {
		this(null);
	}

	public HtMaintainPointKpi(String id){
		super(id);
	}
	
	@NotBlank(message="维修网点id不能为空")
	@Length(min=0, max=64, message="维修网点id长度不能超过 64 个字符")
	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
	@NotBlank(message="平均单价不能为空")
	@Length(min=0, max=64, message="平均单价长度不能超过 64 个字符")
	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}
	
	@NotBlank(message="维修时效不能为空")
	@Length(min=0, max=64, message="维修时效长度不能超过 64 个字符")
	public double getTimeEfficiency() {
		return timeEfficiency;
	}

	public void setTimeEfficiency(double timeEfficiency) {
		this.timeEfficiency = timeEfficiency;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="返修率不能为空")
	public double getRepairRate() {
		return repairRate;
	}

	public void setRepairRate(double repairRate) {
		this.repairRate = repairRate;
	}
	
	@NotBlank(message="投诉率不能为空")
	@Length(min=0, max=64, message="投诉率长度不能超过 64 个字符")
	public double getComplainEfficiency() {
		return complainEfficiency;
	}

	public void setComplainEfficiency(double complainEfficiency) {
		this.complainEfficiency = complainEfficiency;
	}
	
}