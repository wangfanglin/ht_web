/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sforderinfo.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * sf_order_infoEntity
 * @author hongmengzhong
 * @version 2020-04-23
 */
@Table(name="sf_order_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="order_id", attrName="orderId", label="工单id"),
		@Column(name="sf_order_id", attrName="sfOrderId", label="顺丰单号"),
		@Column(name="mail_no", attrName="mailNo", label="顺丰运单号"),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
		@Column(name="j_contact", attrName="jcontact", label="寄件方联系人,如果需要生成电子面单,则为必填。"),
		@Column(name="j_mobile", attrName="jmobile", label="寄件方手机"),
		@Column(name="j_province", attrName="jprovince", label="寄件方省"),
		@Column(name="j_city", attrName="jcity", label="寄件方市"),
		@Column(name="j_area", attrName="jarea", label="寄件方区"),
		@Column(name="j_address", attrName="jaddress", label="寄件方详细地址,"),
		@Column(name="d_company", attrName="dcompany", label="到件方公司名称"),
		@Column(name="d_contact", attrName="dcontact", label="到件方联系人"),
		@Column(name="d_tel", attrName="dtel", label="到件方联系电话"),
		@Column(name="d_province", attrName="dprovince", label="到件方省"),
		@Column(name="d_city", attrName="dcity", label="到件方市"),
		@Column(name="d_area", attrName="darea", label="到件方区"),
		@Column(name="d_address", attrName="daddress", label="到件方详细地址,"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除标示"),
	}, orderBy="a.update_date DESC"
)
public class SfOrderInfo extends DataEntity<SfOrderInfo> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 工单id
	private String sfOrderId;		// 顺丰单号
	private String mailNo;		// 顺丰运单号
	private String jcontact;		// 寄件方联系人,如果需要生成电子面单,则为必填。
	private String jmobile;		// 寄件方手机
	private String jprovince;		// 寄件方省
	private String jcity;		// 寄件方市
	private String jarea;		// 寄件方区
	private String jaddress;		// 寄件方详细地址,
	private String dcompany;		// 到件方公司名称
	private String dcontact;		// 到件方联系人
	private String dtel;		// 到件方联系电话
	private String dprovince;		// 到件方省
	private String dcity;		// 到件方市
	private String darea;		// 到件方区
	private String daddress;		// 到件方详细地址,
	private String delFlag;		// 删除标示
	
	public SfOrderInfo() {
		this(null);
	}

	public SfOrderInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="顺丰单号长度不能超过 64 个字符")
	public String getSfOrderId() {
		return sfOrderId;
	}

	public void setSfOrderId(String sfOrderId) {
		this.sfOrderId = sfOrderId;
	}
	
	@Length(min=0, max=64, message="顺丰运单号长度不能超过 64 个字符")
	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	
	@Length(min=0, max=64, message="寄件方联系人,如果需要生成电子面单,则为必填。长度不能超过 64 个字符")
	public String getJcontact() {
		return jcontact;
	}

	public void setJcontact(String jcontact) {
		this.jcontact = jcontact;
	}
	
	@Length(min=0, max=64, message="寄件方手机长度不能超过 64 个字符")
	public String getJmobile() {
		return jmobile;
	}

	public void setJmobile(String jmobile) {
		this.jmobile = jmobile;
	}
	
	@Length(min=0, max=64, message="寄件方省长度不能超过 64 个字符")
	public String getJprovince() {
		return jprovince;
	}

	public void setJprovince(String jprovince) {
		this.jprovince = jprovince;
	}
	
	@Length(min=0, max=64, message="寄件方市长度不能超过 64 个字符")
	public String getJcity() {
		return jcity;
	}

	public void setJcity(String jcity) {
		this.jcity = jcity;
	}
	
	@Length(min=0, max=64, message="寄件方区长度不能超过 64 个字符")
	public String getJarea() {
		return jarea;
	}

	public void setJarea(String jarea) {
		this.jarea = jarea;
	}
	
	public String getJaddress() {
		return jaddress;
	}

	public void setJaddress(String jaddress) {
		this.jaddress = jaddress;
	}
	
	public String getDcompany() {
		return dcompany;
	}

	public void setDcompany(String dcompany) {
		this.dcompany = dcompany;
	}
	
	@Length(min=0, max=64, message="到件方联系人长度不能超过 64 个字符")
	public String getDcontact() {
		return dcontact;
	}

	public void setDcontact(String dcontact) {
		this.dcontact = dcontact;
	}
	
	@Length(min=0, max=64, message="到件方联系电话长度不能超过 64 个字符")
	public String getDtel() {
		return dtel;
	}

	public void setDtel(String dtel) {
		this.dtel = dtel;
	}
	
	@Length(min=0, max=64, message="到件方省长度不能超过 64 个字符")
	public String getDprovince() {
		return dprovince;
	}

	public void setDprovince(String dprovince) {
		this.dprovince = dprovince;
	}
	
	@Length(min=0, max=64, message="到件方市长度不能超过 64 个字符")
	public String getDcity() {
		return dcity;
	}

	public void setDcity(String dcity) {
		this.dcity = dcity;
	}
	
	@Length(min=0, max=64, message="到件方区长度不能超过 64 个字符")
	public String getDarea() {
		return darea;
	}

	public void setDarea(String darea) {
		this.darea = darea;
	}
	
	public String getDaddress() {
		return daddress;
	}

	public void setDaddress(String daddress) {
		this.daddress = daddress;
	}
	
	@Length(min=0, max=1, message="删除标示长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}