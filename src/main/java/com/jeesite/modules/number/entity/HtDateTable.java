/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.number.entity;

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
 * 数量表Entity
 * @author baixue
 * @version 2020-04-20
 */
@Table(name="ht_date_table", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="maintenance_point_name", attrName="maintenancePointName", label="维修网点名称", queryType=QueryType.LIKE),
		@Column(name="office_name", attrName="officeName", label="渠道商名称", queryType=QueryType.LIKE),
		@Column(name="num_time", attrName="numTime", label="时间"),
		@Column(name="result_num", attrName="resultNum", label="结果数量"),
		@Column(name="month_num", attrName="dayNum", label="日数量"),
		@Column(name="day_num", attrName="monthNum", label="月数量"),
	}, orderBy="a.id DESC"
)
public class HtDateTable extends DataEntity<HtDateTable> {
	
	private static final long serialVersionUID = 1L;
	private String maintenancePointName;		// 维修网点名称
	private String officeName;  //渠道商名称
	private Date numTime;		// 时间
	private Long resultNum;		// 结果数量
	private Long dayNum;		// 日数量
	private Long monthNum;		// 月数量

	private Date startTime;  //起始时间

	public Long getDayNum() {
		return dayNum;
	}

	public void setDayNum(Long dayNum) {
		this.dayNum = dayNum;
	}

	public Long getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(Long monthNum) {
		this.monthNum = monthNum;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	private Date endTime;   //终止时间
	private String type;
	private Date overDate;  //超期时间

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	private  Date phoneTime; //通话量截止时间
	private  Long resultCallNum;  //呼出量
	private  Long resultGetNum;  //接入量

	public Date getPhoneTime() {
		return phoneTime;
	}

	public void setPhoneTime(Date phoneTime) {
		this.phoneTime = phoneTime;
	}




	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getResultCallNum() {
		return resultCallNum;
	}

	public void setResultCallNum(Long resultCallNum) {
		this.resultCallNum = resultCallNum;
	}

	public Long getResultGetNum() {
		return resultGetNum;
	}

	public void setResultGetNum(Long resultGetNum) {
		this.resultGetNum = resultGetNum;
	}

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

	public HtDateTable() {
		this(null);
	}

	public HtDateTable(String id){
		super(id);
	}
	
	@Length(min=0, max=100, message="维修网点名称长度不能超过 100 个字符")
	public String getMaintenancePointName() {
		return maintenancePointName;
	}

	public void setMaintenancePointName(String maintenancePointName) {
		this.maintenancePointName = maintenancePointName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getNumTime() {
		return numTime;
	}

	public void setNumTime(Date numTime) {
		this.numTime = numTime;
	}
	
	public Long getResultNum() {
		return resultNum;
	}

	public void setResultNum(Long resultNum) {
		this.resultNum = resultNum;
	}
	
}