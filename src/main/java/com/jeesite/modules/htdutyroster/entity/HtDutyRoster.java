/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htdutyroster.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.User;
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
 * 排班表Entity
 * @author hongmengzhong
 * @version 2020-02-25
 */@Table(name="ht_duty_roster", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="organization_id", attrName="organizationId", label="机构"),
		@Column(name="staff_no", attrName="staffNo", label="员工工号"),
		@Column(name="staff_user_id", attrName="staffUserId", label="员工用户id"),
		@Column(name="staff_name", attrName="staffName", label="员工姓名", queryType=QueryType.LIKE),
		@Column(name="start_time", attrName="startTime", label="开始时间",queryType = QueryType.GT),
		@Column(name="end_time", attrName="endTime", label="结束时间",queryType = QueryType.LT),
		@Column(includeEntity=DataEntity.class),
	},
		joinTable={
				@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= User.class, alias="f",
						on="f.user_code = a.staff_user_id",
						columns={@Column(includeEntity=User.class)}),
				@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Employee.class, alias="g",
							on="g.emp_code = f.ref_code",
							columns={@Column(includeEntity=Employee.class)}),
				@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Office.class, alias="o",
						on="o.office_code = a.organization_id",
						columns={@Column(includeEntity=Office.class)}),
		},orderBy="a.start_time DESC"
)
public class HtDutyRoster extends DataEntity<HtDutyRoster> {

	private static final long serialVersionUID = 1L;
	private String organizationId;		// 机构
	private String staffNo;		// 工号
	private String staffUserId;   //员工用户id
	private String staffName;		// 员工姓名
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private Office office;
	private User user;
	private Employee employee;


	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStaffUserId() {
		return staffUserId;
	}

	public void setStaffUserId(String staffUserId) {
		this.staffUserId = staffUserId;
	}
	@ExcelField(title="机构",sort=10,
			fieldType = Office.class,
			attrName = "office.officeName",
			align = ExcelField.Align.LEFT)
	//@Length(min=0, max=255, message="销售渠道长度不能超过 255 个字符")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public HtDutyRoster() {
		this(null);
	}

	public HtDutyRoster(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="机构长度不能超过 64 个字符")
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	@Length(min=0, max=64, message="员工工号长度不能超过 64 个字符")
	@ExcelField(title="员工工号",sort=20,
			align = ExcelField.Align.LEFT)
	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	
	@Length(min=0, max=64, message="员工姓名长度不能超过 64 个字符")
	@ExcelField(title="员工姓名",sort=30,
			align = ExcelField.Align.LEFT)
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间",sort=40,
			attrName = "startTime",
			align = ExcelField.Align.LEFT)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间",sort=50,
			attrName = "endTime",
			align = ExcelField.Align.LEFT)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}