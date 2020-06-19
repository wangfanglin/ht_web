/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.httimeefficiency.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 维修时效表Entity
 * @author hongmengzhong
 * @version 2020-04-07
 */
@Table(name="ht_time_efficiency", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单id"),
		@Column(name="start_date", attrName="startDate", label="开始时间"),
		@Column(name="end_date", attrName="endDate", label="结束时间"),
	}, orderBy="a.id DESC"
)
public class HtTimeEfficiency extends DataEntity<HtTimeEfficiency> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// 工单id
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	
	public HtTimeEfficiency() {
		this(null);
	}

	public HtTimeEfficiency(String id){
		super(id);
	}
	
	@NotBlank(message="工单id不能为空")
	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}