/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.period.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;


/**
 * 月份表Entity
 * @author baixue
 * @version 2020-04-16
 */
@Table(name="ht_month_table", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="maintenance_point_name", attrName="maintenancePointName", label="maintenance_point_name", queryType=QueryType.LIKE),
		@Column(name="office_id", attrName="officeId", label="office_id"),
		@Column(name="january", attrName="january", label="1月"),
		@Column(name="february", attrName="february", label="2月"),
		@Column(name="march", attrName="march", label="3月"),
		@Column(name="april", attrName="april", label="4月"),
		@Column(name="may", attrName="may", label="5月"),
		@Column(name="june", attrName="june", label="6月"),
		@Column(name="july", attrName="july", label="7月"),
		@Column(name="august", attrName="august", label="8月"),
		@Column(name="september", attrName="september", label="9月"),
		@Column(name="october", attrName="october", label="10月"),
		@Column(name="november", attrName="november", label="11月"),
		@Column(name="december", attrName="december", label="12月"),
	}, orderBy="a.id DESC"
)
public class HtMonthTable extends DataEntity<HtMonthTable> {
	
	private static final long serialVersionUID = 1L;
	private String maintenancePointName;		// maintenance_point_name
	private String officeId;		// office_id
	private String january;		// 1月
	private String february;		// 2月
	private String march;		// 3月
	private String april;		// 4月
	private String may;		// 5月
	private String june;		// 6月
	private String july;		// 7月
	private String august;		// 8月
	private String september;		// 9月
	private String october;		// 10月
	private String november;		// 11月
	private String december;		// 12月

	private Date startTime;       //开始时间
	private Date endTime;        //结束时间
	private String type;      //页面类型

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public HtMonthTable() {
		this(null);
	}

	public HtMonthTable(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="maintenance_point_name长度不能超过 64 个字符")
	public String getMaintenancePointName() {
		return maintenancePointName;
	}

	public void setMaintenancePointName(String maintenancePointName) {
		this.maintenancePointName = maintenancePointName;
	}
	
	@NotBlank(message="office_id不能为空")
	@Length(min=0, max=64, message="office_id长度不能超过 64 个字符")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="1月长度不能超过 64 个字符")
	public String getJanuary() {
		return january;
	}

	public void setJanuary(String january) {
		this.january = january;
	}
	
	@Length(min=0, max=64, message="2月长度不能超过 64 个字符")
	public String getFebruary() {
		return february;
	}

	public void setFebruary(String february) {
		this.february = february;
	}
	
	@Length(min=0, max=64, message="3月长度不能超过 64 个字符")
	public String getMarch() {
		return march;
	}

	public void setMarch(String march) {
		this.march = march;
	}
	
	@Length(min=0, max=64, message="4月长度不能超过 64 个字符")
	public String getApril() {
		return april;
	}

	public void setApril(String april) {
		this.april = april;
	}
	
	@Length(min=0, max=64, message="5月长度不能超过 64 个字符")
	public String getMay() {
		return may;
	}

	public void setMay(String may) {
		this.may = may;
	}
	
	@Length(min=0, max=64, message="6月长度不能超过 64 个字符")
	public String getJune() {
		return june;
	}

	public void setJune(String june) {
		this.june = june;
	}
	
	@Length(min=0, max=64, message="7月长度不能超过 64 个字符")
	public String getJuly() {
		return july;
	}

	public void setJuly(String july) {
		this.july = july;
	}
	
	@Length(min=0, max=64, message="8月长度不能超过 64 个字符")
	public String getAugust() {
		return august;
	}

	public void setAugust(String august) {
		this.august = august;
	}
	
	@Length(min=0, max=64, message="9月长度不能超过 64 个字符")
	public String getSeptember() {
		return september;
	}

	public void setSeptember(String september) {
		this.september = september;
	}
	
	@Length(min=0, max=64, message="10月长度不能超过 64 个字符")
	public String getOctober() {
		return october;
	}

	public void setOctober(String october) {
		this.october = october;
	}
	
	@Length(min=0, max=64, message="11月长度不能超过 64 个字符")
	public String getNovember() {
		return november;
	}

	public void setNovember(String november) {
		this.november = november;
	}
	
	@Length(min=0, max=64, message="12月长度不能超过 64 个字符")
	public String getDecember() {
		return december;
	}

	public void setDecember(String december) {
		this.december = december;
	}
	
}