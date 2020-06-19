/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.user.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * ht_user_infoEntity
 * @author lichao
 * @version 2020-04-20
 */
@Table(name="ht_user_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="open_id", attrName="openId", label="微信唯一标识"),
		@Column(name="user_name", attrName="userName", label="用户姓名", isQuery=false),
		@Column(name="user_phone", attrName="userPhone", label="用户手机号", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="创建时间", isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标示", isQuery=false),
	}, orderBy="a.update_date DESC"
)
public class HtUserInfo extends DataEntity<HtUserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 微信唯一标识
	private String userName;		// 用户姓名
	private String userPhone;		// 用户手机号
	private String delFlag;		// 删除标示
	
	public HtUserInfo() {
		this(null);
	}

	public HtUserInfo(String id){
		super(id);
	}
	
	@NotBlank(message="微信唯一标识不能为空")
	@Length(min=0, max=64, message="微信唯一标识长度不能超过 64 个字符")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=64, message="用户姓名长度不能超过 64 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=64, message="用户手机号长度不能超过 64 个字符")
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@Length(min=0, max=1, message="删除标示长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}