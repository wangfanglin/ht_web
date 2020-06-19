/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.entity;

import com.jeesite.modules.history.form.FormData;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * test_formEntity
 * @author tangchao
 * @version 2020-03-01
 */
@Table(name="test_form", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="mobile", attrName="mobile", label="mobile"),
		@Column(name="age", attrName="age", label="age"),
		@Column(name="mail", attrName="mail", label="mail"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
	}, orderBy="a.id DESC"
)
public class TestForm extends DataEntity<TestForm> implements FormData {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String mobile;		// mobile
	private Long age;		// age
	private String mail;		// mail
	
	public TestForm() {
		this(null);
	}

	public TestForm(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="name长度不能超过 64 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="mobile长度不能超过 11 个字符")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	@Length(min=0, max=64, message="mail长度不能超过 64 个字符")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "TestForm{" +
				"name='" + name + '\'' +
				", mobile='" + mobile + '\'' +
				", age=" + age +
				", mail='" + mail + '\'' +
				", remarks='" + remarks + '\'' +
				", createByName='" + createByName + '\'' +
				", updateBy='" + updateBy + '\'' +
				", status='" + status + '\'' +
				", updateDate=" + updateDate +
				", lastUpdateDateTime=" + lastUpdateDateTime +
				", createBy='" + createBy + '\'' +
				", updateByName='" + updateByName + '\'' +
				", createDate=" + createDate +
				", corpName='" + corpName + '\'' +
				", currentUser=" + currentUser +
				", sqlMap=" + sqlMap +
				", idAttrName='" + idAttrName + '\'' +
				", id='" + id + '\'' +
				", corpCode='" + corpCode + '\'' +
				", idColumnName='" + idColumnName + '\'' +
				", isNewRecord=" + isNewRecord +
				", page=" + page +
				'}';
	}
}