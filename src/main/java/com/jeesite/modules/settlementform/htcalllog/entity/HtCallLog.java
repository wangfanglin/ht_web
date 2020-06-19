/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htcalllog.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 通话记录表Entity
 * @author hongmengzhong
 * @version 2020-03-12
 */
@Table(name="ht_call_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="user_phone", attrName="userPhone", label="用户手机"),
		@Column(name="data", attrName="data", label="数据"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
		@Column(name="type", attrName="type", label="类型 呼入：0 呼出1 呼入", isUpdate=false),
	}, orderBy="a.id DESC"
)
public class HtCallLog extends DataEntity<HtCallLog> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userPhone;		// 用户手机
	private String data;		// 数据
	private String type;		// 类型 呼入：0 呼出1 呼入
	
	public HtCallLog() {
		this(null);
	}

	public HtCallLog(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="用户手机长度不能超过 64 个字符")
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@Length(min=0, max=255, message="数据长度不能超过 255 个字符")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}