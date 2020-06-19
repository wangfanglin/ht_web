/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.entity;

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
 * 1.0销售渠道Entity
 * @author tangchao
 * @version 2020-05-26
 */
@Table(name="channel_sale", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="salechannelid", attrName="salechannelid", label="业务id"),
		@Column(name="salechannelname", attrName="salechannelname", label="销售渠道名称"),
		@Column(name="remarks", attrName="remarks", label="remarks", queryType=QueryType.LIKE),
		@Column(name="createuserid", attrName="createuserid", label="createuserid"),
		@Column(name="createdate", attrName="createdate", label="createdate"),
		@Column(name="updateuserid", attrName="updateuserid", label="updateuserid"),
		@Column(name="position", attrName="position", label="职务"),
		@Column(name="stake_holder", attrName="stakeHolder", label="项目干系人"),
		@Column(name="telephone", attrName="telephone", label="电话"),
		@Column(name="linkman", attrName="linkman", label="联系人"),
		@Column(name="account", attrName="account", label="账号"),
		@Column(name="bank", attrName="bank", label="开户行"),
		@Column(name="usersaleid", attrName="usersaleid", label="人员渠道id"),
		@Column(name="updatedate", attrName="updatedate", label="updatedate"),
		@Column(name="delflag", attrName="delflag", label="删除标记", comment="删除标记（0：正常；1：删除；2：审核）"),
	}, orderBy="a.id DESC"
)
public class ChannelSale extends DataEntity<ChannelSale> {
	
	private static final long serialVersionUID = 1L;
	private String salechannelid;		// 业务id
	private String salechannelname;		// 销售渠道名称
	private String createuserid;		// createuserid
	private Date createdate;		// createdate
	private String updateuserid;		// updateuserid
	private String position;		// 职务
	private String stakeHolder;		// 项目干系人
	private String telephone;		// 电话
	private String linkman;		// 联系人
	private String account;		// 账号
	private String bank;		// 开户行
	private String usersaleid;		// 人员渠道id
	private Date updatedate;		// updatedate
	private Long delflag;		// 删除标记（0：正常；1：删除；2：审核）
	
	public ChannelSale() {
		this(null);
	}

	public ChannelSale(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="业务id长度不能超过 255 个字符")
	public String getSalechannelid() {
		return salechannelid;
	}

	public void setSalechannelid(String salechannelid) {
		this.salechannelid = salechannelid;
	}
	
	@Length(min=0, max=255, message="销售渠道名称长度不能超过 255 个字符")
	public String getSalechannelname() {
		return salechannelname;
	}

	public void setSalechannelname(String salechannelname) {
		this.salechannelname = salechannelname;
	}
	
	@Length(min=0, max=255, message="createuserid长度不能超过 255 个字符")
	public String getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Length(min=0, max=255, message="updateuserid长度不能超过 255 个字符")
	public String getUpdateuserid() {
		return updateuserid;
	}

	public void setUpdateuserid(String updateuserid) {
		this.updateuserid = updateuserid;
	}
	
	@Length(min=0, max=255, message="职务长度不能超过 255 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=255, message="项目干系人长度不能超过 255 个字符")
	public String getStakeHolder() {
		return stakeHolder;
	}

	public void setStakeHolder(String stakeHolder) {
		this.stakeHolder = stakeHolder;
	}
	
	@Length(min=0, max=255, message="电话长度不能超过 255 个字符")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=255, message="联系人长度不能超过 255 个字符")
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@Length(min=0, max=255, message="账号长度不能超过 255 个字符")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Length(min=0, max=255, message="开户行长度不能超过 255 个字符")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@Length(min=0, max=255, message="人员渠道id长度不能超过 255 个字符")
	public String getUsersaleid() {
		return usersaleid;
	}

	public void setUsersaleid(String usersaleid) {
		this.usersaleid = usersaleid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
	public Long getDelflag() {
		return delflag;
	}

	public void setDelflag(Long delflag) {
		this.delflag = delflag;
	}
	
}