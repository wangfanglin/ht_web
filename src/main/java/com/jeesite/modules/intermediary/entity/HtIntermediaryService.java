/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.intermediary.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 中介服务商Entity
 * @author tangchao
 * @version 2020-02-20
 */
	@Table(name="ht_intermediary_service", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="agentid", attrName="agentid", label="中介服务商编码", isInsert=true, isUpdate=false),
		@Column(name="name", attrName="name", label="中介服务商名称", queryType=QueryType.LIKE),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注", isQuery=false),
		@Column(name="servicecharge", attrName="servicecharge", label="服务费", isInsert=false, isUpdate=false, isQuery=false),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtIntermediaryService extends DataEntity<HtIntermediaryService> {
	
	private static final long serialVersionUID = 1L;
	private String agentid;		// 中介服务商编码
	private String name;		// 中介服务商名称
	private String remark;		// 备注
	private String servicecharge;		// 服务费
	
	public HtIntermediaryService() {
		this(null);
	}

	public HtIntermediaryService(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="中介服务商编码长度不能超过 255 个字符")
	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	@NotBlank(message="中介服务商名称不能为空")
	@Length(min=0, max=255, message="中介服务商名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=64, message="服务费长度不能超过 64 个字符")
	public String getServicecharge() {
		return servicecharge;
	}

	public void setServicecharge(String servicecharge) {
		this.servicecharge = servicecharge;
	}
	
}