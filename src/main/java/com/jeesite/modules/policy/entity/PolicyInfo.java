/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 保单Entity
 * @author zhaohaifeng
 * @version 2020-02-24
 */
@Table(name="ht_policy_info", alias="a", columns={
		@Column(name="id", attrName="id", label="保单id", isPK=true),
		@Column(name="unique_mark", attrName="uniqueMark", label="唯一标识"),
		@Column(name="str_name", attrName="strName", label="客户姓名", queryType=QueryType.LIKE),
		@Column(name="str_contact_num", attrName="strContactNum", label="联系方式"),
		@Column(name="str_type", attrName="strType", label="证件类型 1身份证"),
		@Column(name="str_wechat", attrName="strWechat", label="微信"),
		@Column(name="str_card_id", attrName="strCardId", label="证件号码"),
		@Column(name="date_birthday", attrName="dateBirthday", label="生日"),
		@Column(name="str_sex", attrName="strSex", label="性别1男0女"),
		@Column(name="str_filiale", attrName="strFiliale", label="分公司"),
		@Column(name="str_channel_name", attrName="strChannelName", label="渠道名称", queryType=QueryType.LIKE),
		@Column(name="channel_product_name", attrName="channelProductName", label="渠道产品名称", queryType=QueryType.LIKE),
		@Column(name="int_product_price", attrName="intProductPrice", label="产品售价"),
		@Column(name="int_sell_price", attrName="intSellPrice", label="手机价格"),
		@Column(name="date_cost_time", attrName="dateCostTime", label="购机时间"),
		@Column(name="str_province", attrName="strProvince", label="省份"),
		@Column(name="str_card_number", attrName="strCardNumber", label="卡号"),
		@Column(name="date_buy_card", attrName="dateBuyCard", label="购卡时间"),
		@Column(name="date_effective_date", attrName="dateEffectiveDate", label="保单生效时间",queryType=QueryType.GT),
		@Column(name="date_end_date", attrName="dateEndDate", label="保单终止时间",queryType=QueryType.LT),
		@Column(name="str_buy_pattern", attrName="strBuyPattern", label="手机购买方式"),
		@Column(name="str_sys", attrName="strSys", label="制式"),
		@Column(name="str_phone_brand", attrName="strPhoneBrand", label="手机品牌"),
		@Column(name="str_color", attrName="strColor", label="颜色"),
		@Column(name="str_phone_model", attrName="strPhoneModel", label="手机型号"),
		@Column(name="str_city", attrName="strCity", label="城市"),
		@Column(name="int_internal", attrName="intInternal", label="内存"),
		@Column(name="str_store", attrName="strStore", label="门店"),
		@Column(name="str_imei_num", attrName="strImeiNum", label="手机IMEI号"),
		@Column(name="str_salesman", attrName="strSalesman", label="销售员"),
		@Column(name="policy_status", attrName="policyStatus", label="保单状态"),
		@Column(name="policy_flag", attrName="policyFlag", label="保单流转标识"),
		@Column(name="int_loan_amount", attrName="intLoanAmount", label="贷款额度"),
		@Column(name="int_batch_num", attrName="intBatchNum", label="批次号"),
		@Column(name="int_status", attrName="intStatus", label="保费收取状态"),
		@Column(name="str_card_type", attrName="strCardType", label="产品名称"),
		@Column(name="channel_id", attrName="channelId", label="渠道ID"),
		@Column(name="user_id", attrName="userId", label="用户ID "),
		@Column(name="channel_product_id", attrName="channelProductId", label="渠道产品关联ID"),
		@Column(name="policy_submission_date", attrName="policySubmissionDate", label="保单提交时间"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="delflag", attrName="delflag", label="0不删除 1删除"),
		@Column(name="surplus_coverage", attrName="surplusCoverage", label="保单的剩余限额"),
		@Column(name="invite", attrName="invite", label="小程序创建保单填写的 邀请人工号"),
		@Column(name="inviteflag", attrName="inviteflag", label="是否需要短信发送邀请码，1发送,2客户要求不发送"),
		@Column(name="fromtype", attrName="fromtype", label="保单来源,1为客户端"),
		@Column(name="bhstauts", attrName="bhstauts", label="bhstauts"),
		@Column(name="callguke", attrName="callguke", label="是否联系成功顾客"),
		@Column(name="openid", attrName="openid", label="微信openId"),
		@Column(name="external_identifier", attrName="externalIdentifier", label="设备号"),
		@Column(name="bh_flag", attrName="bhFlag", label="渤海取消类型"),
		@Column(name="insurance_periods", attrName="insurancePeriods", label="甜橙延保期数"),
		@Column(name="is_create_form", attrName="isCreateForm", label="甜橙延保期数"),
		@Column(name="is_old", attrName="isOld", label="是否是老数据订单"),
		@Column(name="is_error", attrName="isError", label="是否存在异常"),
		@Column(name="jx_policy_id", attrName="jxPolicyId", label="捷信的保单id"),
		@Column(name="other_policy_id", attrName="otherPolicyId", label="非捷信的老保单id"),
	}, orderBy="a.update_date DESC"
)
public class PolicyInfo extends DataEntity<PolicyInfo> {
	
	private static final long serialVersionUID = 1L;
	//private Double coverage; //保额
	private String uniqueMark;		// 唯一标识
	private String strName;		// 客户姓名
	private String strContactNum;		// 联系方式
	private String strType;		// 证件类型 1身份证
	private String strWechat;		// 微信
	private String strCardId;		// 证件号码
	private Date dateBirthday;		// 生日
	private String strSex;		// 性别1男2女
	private String strFiliale;		// 分公司
	private String strChannelName;		// 渠道名称
	private String channelProductName;		// 渠道产品名称
	private BigDecimal intProductPrice;		// 产品售价
	private BigDecimal intSellPrice;		// 手机价格
	private Date dateCostTime;		// 购机时间
	private String strProvince;		// 省份
	private String strCardNumber;		// 卡号
	private Date dateBuyCard;		// 购卡时间
	private Date dateEffectiveDate;		// 保单生效时间
	private String strBuyPattern;		// 手机购买方式
	private Date dateEndDate;		// 保单终止时间
	private String strSys;		// 制式
	private String strPhoneBrand;		// 手机品牌
	private String strColor;		// 颜色
	private String strPhoneModel;		// 手机型号
	private String strCity;		// 城市
	private Long intInternal;		// 内存
	private String strStore;		// 门店
	private String strImeiNum;		// 手机IMEI号
	private String strSalesman;		// 销售员
	private BigDecimal intLoanAmount;		// 贷款额度

	private String strCardType;		// 产品名称
	private String channelId;      //渠道ID
	private String channelProductId;		// 渠道产品关联ID
	private Date policySubmissionDate;		// 保单提交时间
	private String delflag;		// 0不删除 1删除
	private BigDecimal surplusCoverage;  //保单剩余限额  剩余保额
	private String policyStatus;  //保单状态
	private String userId;  //用户ID
	private String policyFlag;  //保单流转标识


	private Long intBatchNum;		// 批次号
	private Long intStatus;		// 保费收取状态
	private String callguke;		// 是否联系成功顾客
	private String isApply; //是否申请
	private String guarantee_status;
	private String bhReturnStatus; //渤海回传状态  bh_return_status



	private String invite;		// 小程序创建保单填写的 邀请人工号
	private String inviteflag;		// 是否需要短信发送邀请码，1发送,2客户要求不发送
	private String fromtype;		// 保单来源,0 默认 1 渤海 2捷信
	private Integer bhstauts;		// bhstauts
	private String openid;		// 微信openId
	private String externalIdentifier;		// 设备号
	private String bhFlag;		// 渤海取消类型
	private String insurancePeriods;		// 甜橙延保期数

	private int formAmount; //工单数量
	private int polAmountByPhone; //此电话保单数量
	private String isCreateForm; //是否创建工单 主要用来标识渤海保单

	private String isOld;		//是否是老数据订单
	private String isError;		//是否存在异常
	private String jxPolicyId;	//捷信的保单id
	private String otherPolicyId;	//非捷信的老保单id

	public String getPolicyFlag() {
		return policyFlag;
	}

	public void setPolicyFlag(String policyFlag) {
		this.policyFlag = policyFlag;
	}

	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public int getPolAmountByPhone() {
		return polAmountByPhone;
	}

	public void setPolAmountByPhone(int polAmountByPhone) {
		this.polAmountByPhone = polAmountByPhone;
	}

	public String getIsCreateForm() {
		return isCreateForm;
	}

	public void setIsCreateForm(String isCreateForm) {
		this.isCreateForm = isCreateForm;
	}

	public int getFormAmount() {
		return formAmount;
	}

	public void setFormAmount(int formAmount) {
		this.formAmount = formAmount;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //	public Double getCoverage() {
//		return coverage;
//	}
//
//	public void setCoverage(Double coverage) {
//		this.coverage = coverage;
//	}

	public PolicyInfo() {
		this(null);
	}

	public PolicyInfo(String id){
		super(id);
	}
	
	public String getUniqueMark() {
		return uniqueMark;
	}

	public void setUniqueMark(String uniqueMark) {
		this.uniqueMark = uniqueMark;
	}

	public BigDecimal getSurplusCoverage() {
		return surplusCoverage;
	}

	public void setSurplusCoverage(BigDecimal surplusCoverage) {
		this.surplusCoverage = surplusCoverage;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@NotBlank(message="客户姓名不能为空")
	@Length(min=0, max=64, message="客户姓名长度不能超过 64 个字符")
	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}
	
	@NotBlank(message="联系方式不能为空")
	@Length(min=0, max=50, message="联系方式长度不能超过 50 个字符")
	public String getStrContactNum() {
		return strContactNum;
	}

	public void setStrContactNum(String strContactNum) {
		this.strContactNum = strContactNum;
	}
	
	@Length(min=0, max=255, message="证件类型 1身份证长度不能超过 255 个字符")
	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}
	
	@Length(min=0, max=50, message="微信长度不能超过 50 个字符")
	public String getStrWechat() {
		return strWechat;
	}

	public void setStrWechat(String strWechat) {
		this.strWechat = strWechat;
	}
	
	@Length(min=0, max=255, message="证件号码长度不能超过 255 个字符")
	public String getStrCardId() {
		return strCardId;
	}

	public void setStrCardId(String strCardId) {
		this.strCardId = strCardId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateBirthday() {
		return dateBirthday;
	}

	public void setDateBirthday(Date dateBirthday) {
		this.dateBirthday = dateBirthday;
	}
	
	@Length(min=0, max=10, message="性别1男0女长度不能超过 10 个字符")
	public String getStrSex() {
		return strSex;
	}

	public void setStrSex(String strSex) {
		this.strSex = strSex;
	}
	
	@Length(min=0, max=50, message="分公司长度不能超过 50 个字符")
	public String getStrFiliale() {
		return strFiliale;
	}

	public void setStrFiliale(String strFiliale) {
		this.strFiliale = strFiliale;
	}
	

	public String getStrChannelName() {
		return strChannelName;
	}

	public void setStrChannelName(String strChannelName) {
		this.strChannelName = strChannelName;
	}


	public String getChannelProductName() {
		return channelProductName;
	}
	public void setChannelProductName(String channelProductName) {
		this.channelProductName = channelProductName;
	}

	public BigDecimal getIntProductPrice() {
		return intProductPrice;
	}

	public void setIntProductPrice(BigDecimal intProductPrice) {
		this.intProductPrice = intProductPrice;
	}
	@NotNull(message="手机价格不能为空")
	public BigDecimal getIntSellPrice() {
		return intSellPrice;
	}

	public void setIntSellPrice(BigDecimal intSellPrice) {
		this.intSellPrice = intSellPrice;
	}



	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="购机时间不能为空")
	public Date getDateCostTime() {
		return dateCostTime;
	}

	public void setDateCostTime(Date dateCostTime) {
		this.dateCostTime = dateCostTime;
	}
	
	@Length(min=0, max=32, message="省份长度不能超过 32 个字符")
	public String getStrProvince() {
		return strProvince;
	}

	public void setStrProvince(String strProvince) {
		this.strProvince = strProvince;
	}
	
	@Length(min=0, max=255, message="卡号长度不能超过 255 个字符")
	public String getStrCardNumber() {
		return strCardNumber;
	}

	public void setStrCardNumber(String strCardNumber) {
		this.strCardNumber = strCardNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDateBuyCard() {
		return dateBuyCard;
	}

	public void setDateBuyCard(Date dateBuyCard) {
		this.dateBuyCard = dateBuyCard;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="保单生效时间不能为空")
	public Date getDateEffectiveDate() {
		return dateEffectiveDate;
	}

	public void setDateEffectiveDate(Date dateEffectiveDate) {
		this.dateEffectiveDate = dateEffectiveDate;
	}
	
	@Length(min=0, max=20, message="手机购买方式长度不能超过 20 个字符")
	public String getStrBuyPattern() {
		return strBuyPattern;
	}

	public void setStrBuyPattern(String strBuyPattern) {
		this.strBuyPattern = strBuyPattern;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="保单终止时间不能为空")
	public Date getDateEndDate() {
		return dateEndDate;
	}

	public void setDateEndDate(Date dateEndDate) {
		this.dateEndDate = dateEndDate;
	}
	
	@Length(min=0, max=20, message="制式长度不能超过 20 个字符")
	public String getStrSys() {
		return strSys;
	}

	public void setStrSys(String strSys) {
		this.strSys = strSys;
	}
	
	@NotBlank(message="手机品牌不能为空")
	@Length(min=0, max=20, message="手机品牌长度不能超过 20 个字符")
	public String getStrPhoneBrand() {
		return strPhoneBrand;
	}

	public void setStrPhoneBrand(String strPhoneBrand) {
		this.strPhoneBrand = strPhoneBrand;
	}
	
	@Length(min=0, max=10, message="颜色长度不能超过 10 个字符")
	public String getStrColor() {
		return strColor;
	}

	public void setStrColor(String strColor) {
		this.strColor = strColor;
	}
	
	@Length(min=0, max=64, message="手机型号长度不能超过 64 个字符")
	public String getStrPhoneModel() {
		return strPhoneModel;
	}

	public void setStrPhoneModel(String strPhoneModel) {
		this.strPhoneModel = strPhoneModel;
	}
	
	@NotBlank(message="城市不能为空")
	@Length(min=0, max=255, message="城市长度不能超过 255 个字符")
	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}
	
	public Long getIntInternal() {
		return intInternal;
	}

	public void setIntInternal(Long intInternal) {
		this.intInternal = intInternal;
	}
	
	@Length(min=0, max=50, message="门店长度不能超过 50 个字符")
	public String getStrStore() {
		return strStore;
	}

	public void setStrStore(String strStore) {
		this.strStore = strStore;
	}
	
	@NotBlank(message="手机IMEI号不能为空")
	@Length(min=0, max=255, message="手机IMEI号长度不能超过 255 个字符")
	public String getStrImeiNum() {
		return strImeiNum;
	}

	public void setStrImeiNum(String strImeiNum) {
		this.strImeiNum = strImeiNum;
	}
	
	@Length(min=0, max=64, message="销售员长度不能超过 64 个字符")
	public String getStrSalesman() {
		return strSalesman;
	}

	public void setStrSalesman(String strSalesman) {
		this.strSalesman = strSalesman;
	}

	public BigDecimal getIntLoanAmount() {
		return intLoanAmount;
	}

	public void setIntLoanAmount(BigDecimal intLoanAmount) {
		this.intLoanAmount = intLoanAmount;
	}

	public Long getIntBatchNum() {
		return intBatchNum;
	}

	public void setIntBatchNum(Long intBatchNum) {
		this.intBatchNum = intBatchNum;
	}
	
	public Long getIntStatus() {
		return intStatus;
	}

	public void setIntStatus(Long intStatus) {
		this.intStatus = intStatus;
	}
	
	public String getStrCardType() {
		return strCardType;
	}

	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
	}

	@NotBlank(message="渠道产品名称不能为空")
	@Length(min=0, max=255, message="渠道产品名称长度不能超过 255 个字符")
	public String getChannelProductId() {
		return channelProductId;
	}

	public void setChannelProductId(String channelProductId) {
		this.channelProductId = channelProductId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPolicySubmissionDate() {
		return policySubmissionDate;
	}

	public void setPolicySubmissionDate(Date policySubmissionDate) {
		this.policySubmissionDate = policySubmissionDate;
	}
	
	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	
	public String getInvite() {
		return invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}
	
	public String getInviteflag() {
		return inviteflag;
	}

	public void setInviteflag(String inviteflag) {
		this.inviteflag = inviteflag;
	}
	
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
	
	public String getCallguke() {
		return callguke;
	}

	public void setCallguke(String callguke) {
		this.callguke = callguke;
	}
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getExternalIdentifier() {
		return externalIdentifier;
	}

	public void setExternalIdentifier(String externalIdentifier) {
		this.externalIdentifier = externalIdentifier;
	}
	
	public String getBhFlag() {
		return bhFlag;
	}

	public void setBhFlag(String bhFlag) {
		this.bhFlag = bhFlag;
	}
	
	public String getInsurancePeriods() {
		return insurancePeriods;
	}

	public void setInsurancePeriods(String insurancePeriods) {
		this.insurancePeriods = insurancePeriods;
	}

	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getJxPolicyId() {
		return jxPolicyId;
	}

	public void setJxPolicyId(String jxPolicyId) {
		this.jxPolicyId = jxPolicyId;
	}

	public String getOtherPolicyId() {
		return otherPolicyId;
	}

	public void setOtherPolicyId(String otherPolicyId) {
		this.otherPolicyId = otherPolicyId;
	}

	public String getIsOld() {
		return isOld;
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}
}