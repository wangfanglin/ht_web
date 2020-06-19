/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.entity;

import com.jeesite.modules.channel.entity.ChannelProInfo;
import com.jeesite.modules.channel.entity.ChannelProtype;
import com.jeesite.modules.channel.entity.ChannelSale;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * policy_infoEntity
 * @author tangchao
 * @version 2020-04-27
 */
@Table(name="policy_info", alias="a", columns={
		@Column(name="id", attrName="id", label="保单id", isPK=true),
		@Column(name="uniquemark", attrName="uniquemark", label="唯一标识"),
		@Column(name="strcardnumber", attrName="strcardnumber", label="卡号"),
		@Column(name="intbatchnum", attrName="intbatchnum", label="批次号"),
		@Column(name="strname", attrName="strname", label="客户姓名"),
		@Column(name="strcontactnum", attrName="strcontactnum", label="联系方式"),
		@Column(name="strwechat", attrName="strwechat", label="微信"),
		@Column(name="strtype", attrName="strtype", label="证件类型"),
		@Column(name="strcardid", attrName="strcardid", label="证件号码"),
		@Column(name="strprovince", attrName="strprovince", label="省份"),
		@Column(name="strcity", attrName="strcity", label="城市"),
		@Column(name="datebirthday", attrName="datebirthday", label="生日"),
		@Column(name="strsex", attrName="strsex", label="性别"),
		@Column(name="strfiliale", attrName="strfiliale", label="分公司"),
		@Column(name="strstore", attrName="strstore", label="门店"),
		@Column(name="strcolor", attrName="strcolor", label="颜色"),
		@Column(name="strsalesman", attrName="strsalesman", label="销售员"),
		@Column(name="datebuycard", attrName="datebuycard", label="购卡时间"),
		@Column(name="datecosttime", attrName="datecosttime", label="购机时间"),
		@Column(name="strimeinum", attrName="strimeinum", label="手机IMEI号"),
		@Column(name="strsys", attrName="strsys", label="制式"),
		@Column(name="strphonebrand", attrName="strphonebrand", label="手机品牌"),
		@Column(name="intinternal", attrName="intinternal", label="内存"),
		@Column(name="strbuypattern", attrName="strbuypattern", label="手机购买方式"),
		@Column(name="intloanamount", attrName="intloanamount", label="贷款额度"),
		@Column(name="strchannelname", attrName="strchannelname", label="渠道名称"),
		@Column(name="intsellprice", attrName="intsellprice", label="手机价格"),
		@Column(name="intstatus", attrName="intstatus", label="保费收取状态"),
		@Column(name="strcardtype", attrName="strcardtype", label="产品名称"),
		@Column(name="intproductprice", attrName="intproductprice", label="产品售价"),
		@Column(name="strphonemodel", attrName="strphonemodel", label="手机型号"),
		@Column(name="dateeffectivedate", attrName="dateeffectivedate", label="保单生效时间"),
		@Column(name="dateenddate", attrName="dateenddate", label="保单终止时间"),
		@Column(name="channel_pro_info_id", attrName="channelProInfoId", label="渠道产品关联ID"),
		@Column(name="policysubmissiondate", attrName="policysubmissiondate", label="保单提交时间"),
		@Column(name="createby", attrName="createby", label="createby"),
		@Column(name="createdate", attrName="createdate", label="createdate"),
		@Column(name="updateby", attrName="updateby", label="updateby"),
		@Column(name="updatedate", attrName="updatedate", label="updatedate"),
		@Column(name="remarks", attrName="remarks", label="remarks", queryType=QueryType.LIKE),
		@Column(name="delflag", attrName="delflag", label="delflag"),
		@Column(name="invite", attrName="invite", label="小程序创建保单填写的 邀请人工号"),
		@Column(name="inviteflag", attrName="inviteflag", label="是否需要短信发送邀请码，1发送,2客户要求不发送"),
		@Column(name="fromtype", attrName="fromtype", label="保单来源,1为客户端"),
		@Column(name="bhstauts", attrName="bhstauts", label="bhstauts"),
		@Column(name="callguke", attrName="callguke", label="是否联系成功顾客"),
		@Column(name="openid", attrName="openid", label="微信openId"),
		@Column(name="external_identifier", attrName="externalIdentifier", label="设备号"),
		@Column(name="bh_flag", attrName="bhFlag", label="渤海取消类型"),
		@Column(name="insurance_periods", attrName="insurancePeriods", label="甜橙延保期数"),
	},joinTable= {
		@JoinTable(type = Type.JOIN, entity = ChannelSale.class, alias = "b",
				on = "b.id = a.strchannelname",
				columns = {@Column(includeEntity = ChannelSale.class)}),
		@JoinTable(type = Type.JOIN, entity = ChannelProInfo.class, alias = "c",
				on = "c.id = a.channel_pro_info_id",
				columns = {@Column(includeEntity = ChannelProInfo.class)}),
		@JoinTable(type = Type.JOIN, entity = ChannelProtype.class, alias = "d",
				on = "d.id = c.product_type_id",
				columns = {@Column(includeEntity = ChannelProtype.class)})
}, orderBy="a.id DESC"
)
public class OldPolicyInfo extends DataEntity<OldPolicyInfo> implements Policy{
	
	private static final long serialVersionUID = 1L;
	private String uniquemark;		// 唯一标识
	private String strcardnumber;		// 卡号
	private Long intbatchnum;		// 批次号
	private String strname;		// 客户姓名
	private String strcontactnum;		// 联系方式
	private String strwechat;		// 微信
	private String strtype;		// 证件类型
	private String strcardid;		// 证件号码
	private String strprovince;		// 省份
	private String strcity;		// 城市
	private Date datebirthday;		// 生日
	private String strsex;		// 性别
	private String strfiliale;		// 分公司
	private String strstore;		// 门店
	private String strcolor;		// 颜色
	private String strsalesman;		// 销售员
	private Date datebuycard;		// 购卡时间
	private Date datecosttime;		// 购机时间
	private String strimeinum;		// 手机IMEI号
	private String strsys;		// 制式
	private String strphonebrand;		// 手机品牌
	private Long intinternal;		// 内存
	private String strbuypattern;		// 手机购买方式
	private BigDecimal intloanamount;		// 贷款额度
	private String strchannelname;		// 渠道名称
	private BigDecimal intsellprice;		// 手机价格
	private Long intstatus;		// 保费收取状态
	private String strcardtype;		// 产品名称
	private BigDecimal intproductprice;		// 产品售价
	private String strphonemodel;		// 手机型号
	private Date dateeffectivedate;		// 保单生效时间
	private Date dateenddate;		// 保单终止时间
	private String channelProInfoId;		// 渠道产品关联ID
	private Date policysubmissiondate;		// 保单提交时间
	private String createby;		// createby
	private Date createdate;		// createdate
	private String updateby;		// updateby
	private Date updatedate;		// updatedate
	private String delflag;		// delflag
	private String invite;		// 小程序创建保单填写的 邀请人工号
	private String inviteflag;		// 是否需要短信发送邀请码，1发送,2客户要求不发送
	private String fromtype;		// 保单来源,1为客户端
	private Integer bhstauts;		// bhstauts
	private String callguke;		// 是否联系成功顾客
	private String openid;		// 微信openId
	private String externalIdentifier;		// 设备号
	private String bhFlag;		// 渤海取消类型
	private String insurancePeriods;		// 甜橙延保期数
	private String newModel;					//新机型
	private String newBrand;					//新品牌
	private String newChannelProInfoId;			//新产品
	private String newChannelId;				//新渠道

	private ChannelSale channelSale;
	private ChannelProInfo channelProInfo;
	private ChannelProtype channelProtype;


	
	public OldPolicyInfo() {
		this(null);
	}

	public OldPolicyInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="唯一标识长度不能超过 255 个字符")
	public String getUniquemark() {
		return uniquemark;
	}

	public void setUniquemark(String uniquemark) {
		this.uniquemark = uniquemark;
	}
	
	@Length(min=0, max=255, message="卡号长度不能超过 255 个字符")
	public String getStrcardnumber() {
		return strcardnumber;
	}

	public void setStrcardnumber(String strcardnumber) {
		this.strcardnumber = strcardnumber;
	}
	
	public Long getIntbatchnum() {
		return intbatchnum;
	}

	public void setIntbatchnum(Long intbatchnum) {
		this.intbatchnum = intbatchnum;
	}
	
	@Length(min=0, max=64, message="客户姓名长度不能超过 64 个字符")
	public String getStrname() {
		return strname;
	}

	public void setStrname(String strname) {
		this.strname = strname;
	}
	
	@Length(min=0, max=50, message="联系方式长度不能超过 50 个字符")
	public String getStrcontactnum() {
		return strcontactnum;
	}

	public void setStrcontactnum(String strcontactnum) {
		this.strcontactnum = strcontactnum;
	}
	
	@Length(min=0, max=50, message="微信长度不能超过 50 个字符")
	public String getStrwechat() {
		return strwechat;
	}

	public void setStrwechat(String strwechat) {
		this.strwechat = strwechat;
	}
	
	@Length(min=0, max=255, message="证件类型长度不能超过 255 个字符")
	public String getStrtype() {
		return strtype;
	}

	public void setStrtype(String strtype) {
		this.strtype = strtype;
	}
	
	@Length(min=0, max=255, message="证件号码长度不能超过 255 个字符")
	public String getStrcardid() {
		return strcardid;
	}

	public void setStrcardid(String strcardid) {
		this.strcardid = strcardid;
	}
	
	@Length(min=0, max=32, message="省份长度不能超过 32 个字符")
	public String getStrprovince() {
		return strprovince;
	}

	public void setStrprovince(String strprovince) {
		this.strprovince = strprovince;
	}
	
	@Length(min=0, max=255, message="城市长度不能超过 255 个字符")
	public String getStrcity() {
		return strcity;
	}

	public void setStrcity(String strcity) {
		this.strcity = strcity;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDatebirthday() {
		return datebirthday;
	}

	public void setDatebirthday(Date datebirthday) {
		this.datebirthday = datebirthday;
	}
	
	@Length(min=0, max=10, message="性别长度不能超过 10 个字符")
	public String getStrsex() {
		return strsex;
	}

	public void setStrsex(String strsex) {
		this.strsex = strsex;
	}
	
	@Length(min=0, max=50, message="分公司长度不能超过 50 个字符")
	public String getStrfiliale() {
		return strfiliale;
	}

	public void setStrfiliale(String strfiliale) {
		this.strfiliale = strfiliale;
	}
	
	@Length(min=0, max=50, message="门店长度不能超过 50 个字符")
	public String getStrstore() {
		return strstore;
	}

	public void setStrstore(String strstore) {
		this.strstore = strstore;
	}
	
	@Length(min=0, max=10, message="颜色长度不能超过 10 个字符")
	public String getStrcolor() {
		return strcolor;
	}

	public void setStrcolor(String strcolor) {
		this.strcolor = strcolor;
	}
	
	@Length(min=0, max=64, message="销售员长度不能超过 64 个字符")
	public String getStrsalesman() {
		return strsalesman;
	}

	public void setStrsalesman(String strsalesman) {
		this.strsalesman = strsalesman;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDatebuycard() {
		return datebuycard;
	}

	public void setDatebuycard(Date datebuycard) {
		this.datebuycard = datebuycard;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDatecosttime() {
		return datecosttime;
	}

	public void setDatecosttime(Date datecosttime) {
		this.datecosttime = datecosttime;
	}
	
	@Length(min=0, max=255, message="手机IMEI号长度不能超过 255 个字符")
	public String getStrimeinum() {
		return strimeinum;
	}

	public void setStrimeinum(String strimeinum) {
		this.strimeinum = strimeinum;
	}
	
	@Length(min=0, max=20, message="制式长度不能超过 20 个字符")
	public String getStrsys() {
		return strsys;
	}

	public void setStrsys(String strsys) {
		this.strsys = strsys;
	}
	
	@Length(min=0, max=20, message="手机品牌长度不能超过 20 个字符")
	public String getStrphonebrand() {
		return strphonebrand;
	}

	public void setStrphonebrand(String strphonebrand) {
		this.strphonebrand = strphonebrand;
	}
	
	public Long getIntinternal() {
		return intinternal;
	}

	public void setIntinternal(Long intinternal) {
		this.intinternal = intinternal;
	}
	
	@Length(min=0, max=20, message="手机购买方式长度不能超过 20 个字符")
	public String getStrbuypattern() {
		return strbuypattern;
	}

	public void setStrbuypattern(String strbuypattern) {
		this.strbuypattern = strbuypattern;
	}
	

	
	public String getStrchannelname() {
		return strchannelname;
	}

	public void setStrchannelname(String strchannelname) {
		this.strchannelname = strchannelname;
	}

	public BigDecimal getIntsellprice() {
		return intsellprice;
	}

	public void setIntsellprice(BigDecimal intsellprice) {
		this.intsellprice = intsellprice;
	}

	@NotNull(message="保费收取状态不能为空")
	public Long getIntstatus() {
		return intstatus;
	}

	public void setIntstatus(Long intstatus) {
		this.intstatus = intstatus;
	}
	
	@Length(min=0, max=20, message="产品名称长度不能超过 20 个字符")
	public String getStrcardtype() {
		return strcardtype;
	}

	public void setStrcardtype(String strcardtype) {
		this.strcardtype = strcardtype;
	}

	public BigDecimal getIntproductprice() {
		return intproductprice;
	}

	public void setIntproductprice(BigDecimal intproductprice) {
		this.intproductprice = intproductprice;
	}

	public BigDecimal getIntloanamount() {
		return intloanamount;
	}

	public void setIntloanamount(BigDecimal intloanamount) {
		this.intloanamount = intloanamount;
	}

	@Length(min=0, max=64, message="手机型号长度不能超过 64 个字符")
	public String getStrphonemodel() {
		return strphonemodel;
	}

	public void setStrphonemodel(String strphonemodel) {
		this.strphonemodel = strphonemodel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateeffectivedate() {
		return dateeffectivedate;
	}

	public void setDateeffectivedate(Date dateeffectivedate) {
		this.dateeffectivedate = dateeffectivedate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateenddate() {
		return dateenddate;
	}

	public void setDateenddate(Date dateenddate) {
		this.dateenddate = dateenddate;
	}
	
	@Length(min=0, max=32, message="渠道产品关联ID长度不能超过 32 个字符")
	public String getChannelProInfoId() {
		return channelProInfoId;
	}

	public void setChannelProInfoId(String channelProInfoId) {
		this.channelProInfoId = channelProInfoId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPolicysubmissiondate() {
		return policysubmissiondate;
	}

	public void setPolicysubmissiondate(Date policysubmissiondate) {
		this.policysubmissiondate = policysubmissiondate;
	}
	
	@Length(min=0, max=64, message="createby长度不能超过 64 个字符")
	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Length(min=0, max=64, message="updateby长度不能超过 64 个字符")
	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
	@NotBlank(message="delflag不能为空")
	@Length(min=0, max=1, message="delflag长度不能超过 1 个字符")
	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	
	@Length(min=0, max=255, message="小程序创建保单填写的 邀请人工号长度不能超过 255 个字符")
	public String getInvite() {
		return invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}
	
	@Length(min=0, max=1, message="是否需要短信发送邀请码，1发送,2客户要求不发送长度不能超过 1 个字符")
	public String getInviteflag() {
		return inviteflag;
	}

	public void setInviteflag(String inviteflag) {
		this.inviteflag = inviteflag;
	}
	
	@Length(min=0, max=1, message="保单来源,1为客户端长度不能超过 1 个字符")
	public String getFromtype() {
		return fromtype;
	}

	public void setFromtype(String fromtype) {
		this.fromtype = fromtype;
	}
	
	public Integer getBhstauts() {
		return bhstauts;
	}

	public void setBhstauts(Integer bhstauts) {
		this.bhstauts = bhstauts;
	}
	
	@Length(min=0, max=2, message="是否联系成功顾客长度不能超过 2 个字符")
	public String getCallguke() {
		return callguke;
	}

	public void setCallguke(String callguke) {
		this.callguke = callguke;
	}
	
	@Length(min=0, max=64, message="微信openId长度不能超过 64 个字符")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=255, message="设备号长度不能超过 255 个字符")
	public String getExternalIdentifier() {
		return externalIdentifier;
	}

	public void setExternalIdentifier(String externalIdentifier) {
		this.externalIdentifier = externalIdentifier;
	}
	
	@Length(min=0, max=255, message="渤海取消类型长度不能超过 255 个字符")
	public String getBhFlag() {
		return bhFlag;
	}

	public void setBhFlag(String bhFlag) {
		this.bhFlag = bhFlag;
	}
	
	@Length(min=0, max=4, message="甜橙延保期数长度不能超过 4 个字符")
	public String getInsurancePeriods() {
		return insurancePeriods;
	}

	public void setInsurancePeriods(String insurancePeriods) {
		this.insurancePeriods = insurancePeriods;
	}


	@Override
	public String getNewModel() {
		return newModel;
	}

	@Override
	public void setNewModel(String newModel) {
		this.newModel = newModel;
	}

	@Override
	public String getNewBrand() {
		return newBrand;
	}

	@Override
	public void setNewBrand(String newBrand) {
		this.newBrand = newBrand;
	}

	@Override
	public String getNewChannelProInfoId() {
		return newChannelProInfoId;
	}

	@Override
	public void setNewChannelProInfoId(String newChannelProInfoId) {
		this.newChannelProInfoId = newChannelProInfoId;
	}

	@Override
	public String getNewChannelId() {
		return newChannelId;
	}

	@Override
	public void setNewChannelId(String newChannelId) {
		this.newChannelId = newChannelId;
	}

	@Override
	public ChannelSale getChannelSale() {
		return channelSale;
	}

	public void setChannelSale(ChannelSale channelSale) {
		this.channelSale = channelSale;
	}

	public ChannelProInfo getChannelProInfo() {
		return channelProInfo;
	}

	public void setChannelProInfo(ChannelProInfo channelProInfo) {
		this.channelProInfo = channelProInfo;
	}

	@Override
	public ChannelProtype getChannelProtype() {
		return channelProtype;
	}

	public void setChannelProtype(ChannelProtype channelProtype) {
		this.channelProtype = channelProtype;
	}

	@Override
	public PolicyInfo convertNew() {
		PolicyInfo newPolicy = new PolicyInfo();
		newPolicy.setIsOld("1");
		newPolicy.setOtherPolicyId(id);
		newPolicy.setUniqueMark(uniquemark);// 唯一标识
		newPolicy.setStrCardNumber(strcardnumber);// 卡号
		newPolicy.setIntBatchNum(intbatchnum);// 批次号
		newPolicy.setStrName(strname);// 客户姓名
		newPolicy.setStrContactNum(strcontactnum);// 联系方式
		newPolicy.setStrWechat(strwechat);// 微信
		newPolicy.setStrType("1");// 证件类型
		newPolicy.setStrCardId(strcardid);// 证件号码
		newPolicy.setStrProvince(strprovince);// 省份
		newPolicy.setStrCity(strcity);// 城市
		newPolicy.setDateBirthday(datebirthday);// 生日
		newPolicy.setStrSex(strsex);// 性别
		newPolicy.setStrFiliale(strfiliale);// 分公司
		newPolicy.setStrStore(strstore);// 门店
		newPolicy.setStrColor(strcolor); // 颜色
		newPolicy.setStrSalesman(strsalesman); // 销售员
		newPolicy.setDateBuyCard(datebuycard); // 购卡时间
		newPolicy.setDateCostTime(datecosttime); // 购机时间
		newPolicy.setStrImeiNum(strimeinum); // 手机IMEI号
		newPolicy.setStrSys(strsys); // 制式

		newPolicy.setIntInternal(intinternal); // 内存
		newPolicy.setStrBuyPattern(strbuypattern); // 手机购买方式
		newPolicy.setIntLoanAmount(intloanamount); // 贷款额度
		newPolicy.setStrChannelName(strchannelname); // 渠道名称
		newPolicy.setIntSellPrice(intsellprice); // 手机价格
		newPolicy.setIntStatus(intstatus); // 保费收取状态
		newPolicy.setStrCardType(strcardtype); // 产品名称
		newPolicy.setIntProductPrice(intproductprice); // 产品售价

		newPolicy.setDateEffectiveDate(dateeffectivedate); // 保单生效时间
		newPolicy.setDateEndDate(dateenddate); // 保单终止时间
		newPolicy.setChannelProductId(channelProInfoId); // 渠道产品关联ID
		newPolicy.setPolicySubmissionDate(policysubmissiondate); // 保单提交时间
		newPolicy.setCreateBy(createby); // createby
		newPolicy.setCreateDate(createdate); // createdate
		newPolicy.setUpdateBy(updateby); // updateby
		newPolicy.setUpdateDate(updatedate); // updatedate
		newPolicy.setDelflag(delflag); // delflag
		newPolicy.setInvite(invite); // 小程序创建保单填写的 邀请人工号
		newPolicy.setInviteflag(inviteflag);// 是否需要短信发送邀请码，1发送,2客户要求不发送
		newPolicy.setFromtype("0"); // 保单来源,1为客户端
		newPolicy.setBhstauts(bhstauts); // bhstauts
		newPolicy.setCallguke(callguke); // 是否联系成功顾客
		newPolicy.setOpenid(openid); // 微信openId
		newPolicy.setExternalIdentifier(externalIdentifier); // 设备号
		newPolicy.setBhFlag(bhFlag); // 渤海取消类型
		newPolicy.setInsurancePeriods(insurancePeriods); // 甜橙延保期数
		return newPolicy;
	}
}