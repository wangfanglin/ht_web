/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.entity;

import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
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
 * 待申请工单Entity
 * @author hongmengzhong
 * @version 2020-04-01
 */
@Table(name="ht_wait_apply_form", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="htFormInfo.id", label="工单id"),
		@Column(name="contact_status", attrName="contactStatus", label="联系结果"),
		@Column(name="again_contact_date", attrName="againContactDate", label="再次联系时间"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
		@Column(name="whether_apply", attrName="whetherApply", label="0未申请 1以申请", isUpdate=false),
	}, joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity= HtFormInfo.class, alias="f",
				on="f.id = a.form_id",
				columns={@Column(includeEntity=HtFormInfo.class)})
		},orderBy="a.update_date DESC"
)
public class HtWaitApplyForm extends BpmEntity<HtWaitApplyForm> {
	
	private static final long serialVersionUID = 1L;
	private String contactStatus;		// 联系结果
	private Date againContactDate;		// 再次联系时间
	private String remark;		// 备注
	private String whetherApply;   //是否以申请
	private HtFormInfo htFormInfo;// 工单id
	//查询参数
	private String userName;
	private String phone;
	private String productId;
	private String isAssigned;
	private String typeClose;

	public String getWhetherApply() {
		return whetherApply;
	}

	public void setWhetherApply(String whetherApply) {
		this.whetherApply = whetherApply;
	}

	public String getTypeClose() {
		return typeClose;
	}

	public void setTypeClose(String typeClose) {
		this.typeClose = typeClose;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(String isAssigned) {
		this.isAssigned = isAssigned;
	}

	public HtFormInfo getHtFormInfo() {
		return htFormInfo;
	}

	public void setHtFormInfo(HtFormInfo htFormInfo) {
		this.htFormInfo = htFormInfo;
	}

	public HtWaitApplyForm() {
		this(null);
	}

	public HtWaitApplyForm(String id){
		super(id);
	}

	@Length(min=0, max=1, message="联系结果长度不能超过 1 个字符")
	public String getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAgainContactDate() {
		return againContactDate;
	}

	public void setAgainContactDate(Date againContactDate) {
		this.againContactDate = againContactDate;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}