/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expressage.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 快递渠道Entity
 * @author tangchao
 * @version 2020-02-22
 */
@Table(name="ht_expressage", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="exprid", attrName="exprid", label="快递渠道编码"),
		@Column(name="exprname", attrName="exprname", label="快递渠道名称", queryType=QueryType.LIKE),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新人", isQuery=false),
		@Column(name="exprpay", attrName="exprpay", label="快递结算方式"),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class HtExpressage extends DataEntity<HtExpressage> {
	
	private static final long serialVersionUID = 1L;
	private String exprid;		// 快递渠道编码
	private String exprname;		// 快递渠道名称
	private String exprpay;		// 快递结算方式
	private String remark;		// 备注
	
	public HtExpressage() {
		this(null);
	}

	public HtExpressage(String id){
		super(id);
	}
	
	@NotBlank(message="快递渠道编码不能为空")
	@Length(min=0, max=255, message="快递渠道编码长度不能超过 255 个字符")
	public String getExprid() {
		return exprid;
	}

	public void setExprid(String exprid) {
		this.exprid = exprid;
	}
	
	@NotBlank(message="快递渠道名称不能为空")
	@Length(min=0, max=255, message="快递渠道名称长度不能超过 255 个字符")
	public String getExprname() {
		return exprname;
	}

	public void setExprname(String exprname) {
		this.exprname = exprname;
	}
	
	@NotBlank(message="快递结算方式不能为空")
	@Length(min=0, max=64, message="快递结算方式长度不能超过 64 个字符")
	public String getExprpay() {
		return exprpay;
	}

	public void setExprpay(String exprpay) {
		this.exprpay = exprpay;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}