/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.log.entity;

import com.jeesite.modules.bohai.utils.GsonUtils;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 第三方接口调用日志Entity
 * @author tangchao
 * @version 2020-04-02
 */
@Table(name="third_interface_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="url", attrName="url", label="接口URL", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="类型", queryType=QueryType.LIKE),
		@Column(name="method", attrName="method", label="方法名", queryType=QueryType.LIKE),
		@Column(name="parameter", attrName="parameter", label="入参", queryType=QueryType.LIKE),
		@Column(name="result", attrName="result", label="出参", queryType=QueryType.LIKE),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
	}, orderBy="a.id DESC"
)
public class ThirdInterfaceLog extends DataEntity<ThirdInterfaceLog> {
	
	private static final long serialVersionUID = 1L;
	private String url;		// 接口URL
	private String type;		// 类型
	private String method;		// 方法名
	private String parameter;		// 入参
	private String result;		// 出参
	
	public ThirdInterfaceLog() {
		this(null);
	}

	public ThirdInterfaceLog(String id){
		super(id);
	}

	/**
	 *
	 * @param url			接口URL
	 * @param type			类型
	 * @param method		方法名
	 * @param parameter		入参
	 * @param result		出参
	 */
	public ThirdInterfaceLog(String url, ThirdInterfaceType type, String method, Object parameter, Object result) {
		this.url = url;
		this.type = type.getCode();
		this.method = method;
		this.parameter = GsonUtils.toJson(parameter);
		this.result = GsonUtils.toJson(result);
	}


	/**
	 *
	 * @param url			接口URL
	 * @param type			类型
	 * @param method		方法名
	 * @param parameter		入参
	 * @param result		出参
	 */
	public ThirdInterfaceLog(String url, ThirdInterfaceType type, String method, String parameter, String result) {
		this.url = url;
		this.type = type.getCode();
		this.method = method;
		this.parameter = GsonUtils.toJson(parameter);
		this.result = result;
	}


	@Length(min=0, max=255, message="接口URL长度不能超过 255 个字符")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=1, message="类型长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="方法名长度不能超过 64 个字符")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}