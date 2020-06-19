/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.renew.entity;

import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 换新工单Entity
 * @author lichao
 * @version 2020-03-25
 */
@Table(name="ht_renew_form", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="htFormInfo.id", label="工单ID"),
		@Column(name="contact_status", attrName="contactStatus", label="联系情况"),
		@Column(name="again_date", attrName="againDate", label="再次沟通时间"),
		@Column(name="claim_status", attrName="claimStatus", label="理赔资料状态"),
		@Column(name="claim_data", attrName="claimData", label="核实理赔资料"),
		@Column(name="phone_brand", attrName="phoneBrand", label="手机品牌"),
		@Column(name="is_qualified", attrName="isQualified", label="理赔是否齐全齐全1是，2否"),
		@Column(name="phone_type", attrName="phoneType", label="手机型号"),
		@Column(name="phone_color", attrName="phoneColor", label="手机颜色", isQuery=false),
		@Column(name="image", attrName="image", label="相关图片", isQuery=false),
		@Column(name="mail_date", attrName="mailDate", label="预期邮寄时间", isQuery=false),
		@Column(name="express_company", attrName="expressCompany", label="客户邮寄快递公司", isQuery=false),
		@Column(name="express_no", attrName="expressNo", label="客户快递单号", isQuery=false),
		@Column(name="is_model", attrName="isModel", label="是否同型号0否，1是", isQuery=false),
		@Column(name="change_model", attrName="changeModel", label="换机型号", isQuery=false),
		@Column(name="change_brand", attrName="changeBrand", label="换机品牌", isQuery=false),
		@Column(name="change_net", attrName="changeNet", label="换机制式", isQuery=false),
		@Column(name="change_color", attrName="changeColor", label="换机颜色", isQuery=false),
		@Column(name="change_memory", attrName="changeMemory", label="换机内存", isQuery=false),
		@Column(name="old_phone_price", attrName="oldPhonePrice", label="原购机价格", isQuery=false),
		@Column(name="purchase_channel", attrName="purchaseChannel", label="采购渠道", isQuery=false),
		@Column(name="channel_price", attrName="channelPrice", label="渠道考核价", isQuery=false),
		@Column(name="purchase_price", attrName="purchasePrice", label="采购价", isQuery=false),
		@Column(name="phone_price", attrName="phonePrice", label="手机报价", isQuery=false),
		@Column(name="market_price", attrName="marketPrice", label="平均市场价", isQuery=false),
		@Column(name="other_price", attrName="otherPrice", label="其他金额", isQuery=false),
		@Column(name="other_remarks", attrName="otherRemarks", label="其他金额说明", isQuery=false),
		@Column(name="self_price", attrName="selfPrice", label="自付额", isQuery=false),
		@Column(name="is_self_price", attrName="isSelfPrice", label="自付额是否代收", isQuery=false),
		@Column(name="express_price", attrName="expressPrice", label="快递到付金额", isQuery=false),
		@Column(name="receive_name", attrName="receiveName", label="收货人姓名", isQuery=false),
		@Column(name="receive_phone", attrName="receivePhone", label="收货人手机号", isQuery=false),
		@Column(name="receive_address", attrName="receiveAddress", label="收货人地址", isQuery=false),
		@Column(name="new_imei", attrName="newImei", label="新机imei", isQuery=false),
		@Column(name="renew_express_company", attrName="renewExpressCompany", label="换新完成快递公司", isQuery=false),
		@Column(name="renew_express_no", attrName="renewExpressNo", label="换新完成快递单号", isQuery=false),
		@Column(name="renew_express_date", attrName="renewExpressDate", label="换新完成快递日期", isQuery=false),
		@Column(name="renew_express_address", attrName="renewExpressAddress", label="换新完成收件地址", isQuery=false),
		@Column(name="renew_invoice", attrName="renewInvoice", label="电子发票", isQuery=false),
		@Column(name="renew_image", attrName="renewImage", label="留存照片", isQuery=false),
		@Column(name="old_month", attrName="oldMonth", label="折旧月数", isQuery=false),
		@Column(name="old_new_price", attrName="oldNewPrice", label="折旧后新机价格", isQuery=false),
		@Column(name="receive_address_detail", attrName="receiveAddressDetail", label="明细地址", isQuery=false),
		@Column(name="sms_model", attrName="smsModel", label="短信模板内容", isQuery=false),

		@Column(includeEntity=DataEntity.class),
	}, joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity=HtFormInfo.class, alias="f",
				on="f.id = a.form_id",
				columns={@Column(includeEntity=HtFormInfo.class)}),
		@JoinTable(type=Type.LEFT_JOIN, entity=PolicyInfo.class, alias="p",
				on="p.id = f.policy_id",
				columns={@Column(includeEntity=PolicyInfo.class)}),
		@JoinTable(type=Type.LEFT_JOIN, entity=HtClaimSettlementForm.class, alias="c",
				on="c.form_id = a.form_id",
				columns={@Column(includeEntity=HtClaimSettlementForm.class)}),
		@JoinTable(type=Type.LEFT_JOIN, entity=HtBrandInfo.class, alias="b",
				on="b.id = c.phone_brand",
				columns={@Column(includeEntity=HtBrandInfo.class)}),
		@JoinTable(type=Type.LEFT_JOIN, entity=HtPhoneModelInfo.class, alias="m",
				on="m.id = c.phone_model",
				columns={@Column(includeEntity=HtPhoneModelInfo.class)}),
}, orderBy="a.update_date DESC"
)
public class HtRenewForm extends BpmEntity<HtRenewForm> {
	
	private static final long serialVersionUID = 1L;
	private HtFormInfo htFormInfo;		// 工单号
	private HtClaimSettlementForm htClaimSettlementForm;//在线理赔实体
	private HtBrandInfo htBrandInfo;		// 品牌
	private HtPhoneModelInfo htPhoneModelInfo;		// 型号

	private String contactStatus;		// 联系情况
	private Date againDate;		// 再次沟通时间
	private String claimStatus;		// 理赔资料状态
	private String claimData;		// 核实理赔资料
	private String phoneBrand;		// 手机品牌
	private String phoneType;		// 手机型号
	private String isQualified;		// 理赔是否齐全齐全1是，2否
	private String phoneColor;		// 手机颜色
	private String image;		// 相关图片
	private Date mailDate;		// 预期邮寄时间
	private String expressCompany;		// 客户邮寄快递公司
	private String expressNo;		// 客户快递单号
	private String isModel;		// 是否同型号0否，1是
	private String changeModel;		// 换机型号
	private String changeBrand;		// 换机品牌
	private String changeNet;		// 换机制式
	private String changeColor;		// 换机颜色
	private String changeMemory;		// 换机内存
	private BigDecimal oldPhonePrice;		// 原购机价格
	private String purchaseChannel;		// 采购渠道
	private BigDecimal channelPrice;		// 渠道考核价
	private BigDecimal purchasePrice;		// 采购价
	private BigDecimal phonePrice;		// 手机报价
	private BigDecimal marketPrice;		// 平均市场价
	private BigDecimal otherPrice;		// 其他金额
	private String otherRemarks;		// 其他金额说明
	private BigDecimal selfPrice;		// 自付额
	private String isSelfPrice;		// 自付额是否代收
	private BigDecimal expressPrice;		// 快递到付金额
	private String receiveName;		// 收货人姓名
	private String receivePhone;		// 收货人手机号
	private String receiveAddress;		// 收货人地址
	private String newImei;		// 新机imei
	private String renewExpressCompany;		// 换新完成快递公司
	private String renewExpressNo;		// 换新完成快递单号
	private Date renewExpressDate;		// 换新完成快递日期
	private String renewExpressAddress;		// 换新完成收件地址
	private String renewInvoice;		// 电子发票
	private String renewImage;		// 留存照片

	private BigDecimal oldNewPrice;		// 折旧后新机价格
	private String oldMonth;		// 折旧月数
	private String receiveAddressDetail;		// 明细地址



	//临时属性
	private String repairFormType;//判断跳转表单，1待联系，2待报价，3
	private String lackClaimData;		// 缺少的理赔资料
	private String smsModel;		// 短信模板
	private PolicyInfo policyInfo;		// 保单
	private String isRun;		// 1关闭工单
	private String callId;		// 电话录音


	private Date startDate;
	private Date endDate;



	public HtRenewForm() {
		this(null);
	}

	public HtRenewForm(String id){
		super(id);
	}

	public HtFormInfo getHtFormInfo() {
		return htFormInfo;
	}

	public void setHtFormInfo(HtFormInfo htFormInfo) {
		this.htFormInfo = htFormInfo;
	}
	@NotBlank(message="联系情况不能为空")
	@Length(min=0, max=10, message="联系情况长度不能超过 10 个字符")
	public String getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAgainDate() {
		return againDate;
	}

	public void setAgainDate(Date againDate) {
		this.againDate = againDate;
	}
	
	@Length(min=0, max=10, message="理赔资料状态长度不能超过 10 个字符")
	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	
	@Length(min=0, max=64, message="核实理赔资料长度不能超过 64 个字符")
	public String getClaimData() {
		return claimData;
	}

	public void setClaimData(String claimData) {
		this.claimData = claimData;
	}

	public HtClaimSettlementForm getHtClaimSettlementForm() {
		return htClaimSettlementForm;
	}

	public void setHtClaimSettlementForm(HtClaimSettlementForm htClaimSettlementForm) {
		this.htClaimSettlementForm = htClaimSettlementForm;
	}

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	@Length(min=0, max=10, message="理赔是否齐全齐全1是，2否长度不能超过 10 个字符")
	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	

	
	@Length(min=0, max=64, message="手机颜色长度不能超过 64 个字符")
	public String getPhoneColor() {
		return phoneColor;
	}

	public void setPhoneColor(String phoneColor) {
		this.phoneColor = phoneColor;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMailDate() {
		return mailDate;
	}

	public void setMailDate(Date mailDate) {
		this.mailDate = mailDate;
	}
	
	@Length(min=0, max=64, message="客户邮寄快递公司长度不能超过 64 个字符")
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	
	@Length(min=0, max=64, message="客户快递单号长度不能超过 64 个字符")
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
	@Length(min=0, max=1, message="是否同型号0否，1是长度不能超过 1 个字符")
	public String getIsModel() {
		return isModel;
	}

	public void setIsModel(String isModel) {
		this.isModel = isModel;
	}
	
	@Length(min=0, max=64, message="换机型号长度不能超过 64 个字符")
	public String getChangeModel() {
		return changeModel;
	}

	public void setChangeModel(String changeModel) {
		this.changeModel = changeModel;
	}
	
	@Length(min=0, max=64, message="换机品牌长度不能超过 64 个字符")
	public String getChangeBrand() {
		return changeBrand;
	}

	public void setChangeBrand(String changeBrand) {
		this.changeBrand = changeBrand;
	}
	
	@Length(min=0, max=64, message="换机制式长度不能超过 64 个字符")
	public String getChangeNet() {
		return changeNet;
	}

	public void setChangeNet(String changeNet) {
		this.changeNet = changeNet;
	}
	
	@Length(min=0, max=64, message="换机颜色长度不能超过 64 个字符")
	public String getChangeColor() {
		return changeColor;
	}

	public void setChangeColor(String changeColor) {
		this.changeColor = changeColor;
	}
	
	@Length(min=0, max=64, message="换机内存长度不能超过 64 个字符")
	public String getChangeMemory() {
		return changeMemory;
	}

	public void setChangeMemory(String changeMemory) {
		this.changeMemory = changeMemory;
	}
	
	public BigDecimal getOldPhonePrice() {
		return oldPhonePrice;
	}

	public void setOldPhonePrice(BigDecimal oldPhonePrice) {
		this.oldPhonePrice = oldPhonePrice;
	}
	
	@Length(min=0, max=64, message="采购渠道长度不能超过 64 个字符")
	public String getPurchaseChannel() {
		return purchaseChannel;
	}

	public void setPurchaseChannel(String purchaseChannel) {
		this.purchaseChannel = purchaseChannel;
	}
	
	public BigDecimal getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(BigDecimal channelPrice) {
		this.channelPrice = channelPrice;
	}
	
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public BigDecimal getPhonePrice() {
		return phonePrice;
	}

	public void setPhonePrice(BigDecimal phonePrice) {
		this.phonePrice = phonePrice;
	}
	
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}
	
	@Length(min=0, max=255, message="其他金额说明长度不能超过 255 个字符")
	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}
	
	public BigDecimal getSelfPrice() {
		return selfPrice;
	}

	public void setSelfPrice(BigDecimal selfPrice) {
		this.selfPrice = selfPrice;
	}
	
	@Length(min=0, max=1, message="自付额是否代收长度不能超过 1 个字符")
	public String getIsSelfPrice() {
		return isSelfPrice;
	}

	public void setIsSelfPrice(String isSelfPrice) {
		this.isSelfPrice = isSelfPrice;
	}
	
	public BigDecimal getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(BigDecimal expressPrice) {
		this.expressPrice = expressPrice;
	}
	
	@Length(min=0, max=64, message="收货人姓名长度不能超过 64 个字符")
	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	
	@Length(min=0, max=64, message="收货人手机号长度不能超过 64 个字符")
	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	
	@Length(min=0, max=255, message="收货人地址长度不能超过 255 个字符")
	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	
	@Length(min=0, max=255, message="新机imei长度不能超过 255 个字符")
	public String getNewImei() {
		return newImei;
	}

	public void setNewImei(String newImei) {
		this.newImei = newImei;
	}
	
	@Length(min=0, max=64, message="换新完成快递公司长度不能超过 64 个字符")
	public String getRenewExpressCompany() {
		return renewExpressCompany;
	}

	public void setRenewExpressCompany(String renewExpressCompany) {
		this.renewExpressCompany = renewExpressCompany;
	}
	
	@Length(min=0, max=64, message="换新完成快递单号长度不能超过 64 个字符")
	public String getRenewExpressNo() {
		return renewExpressNo;
	}

	public void setRenewExpressNo(String renewExpressNo) {
		this.renewExpressNo = renewExpressNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRenewExpressDate() {
		return renewExpressDate;
	}

	public void setRenewExpressDate(Date renewExpressDate) {
		this.renewExpressDate = renewExpressDate;
	}
	
	@Length(min=0, max=255, message="换新完成收件地址长度不能超过 255 个字符")
	public String getRenewExpressAddress() {
		return renewExpressAddress;
	}

	public void setRenewExpressAddress(String renewExpressAddress) {
		this.renewExpressAddress = renewExpressAddress;
	}
	
	@Length(min=0, max=255, message="电子发票长度不能超过 255 个字符")
	public String getRenewInvoice() {
		return renewInvoice;
	}

	public void setRenewInvoice(String renewInvoice) {
		this.renewInvoice = renewInvoice;
	}
	
	public String getRenewImage() {
		return renewImage;
	}

	public void setRenewImage(String renewImage) {
		this.renewImage = renewImage;
	}

	public String getRepairFormType() {
		return repairFormType;
	}

	public void setRepairFormType(String repairFormType) {
		this.repairFormType = repairFormType;
	}

	public HtBrandInfo getHtBrandInfo() {
		return htBrandInfo;
	}

	public void setHtBrandInfo(HtBrandInfo htBrandInfo) {
		this.htBrandInfo = htBrandInfo;
	}

	public HtPhoneModelInfo getHtPhoneModelInfo() {
		return htPhoneModelInfo;
	}

	public void setHtPhoneModelInfo(HtPhoneModelInfo htPhoneModelInfo) {
		this.htPhoneModelInfo = htPhoneModelInfo;
	}

	public BigDecimal getOldNewPrice() {
		return oldNewPrice;
	}

	public void setOldNewPrice(BigDecimal oldNewPrice) {
		this.oldNewPrice = oldNewPrice;
	}

	public String getOldMonth() {
		return oldMonth;
	}

	public void setOldMonth(String oldMonth) {
		this.oldMonth = oldMonth;
	}

	public String getReceiveAddressDetail() {
		return receiveAddressDetail;
	}

	public void setReceiveAddressDetail(String receiveAddressDetail) {
		this.receiveAddressDetail = receiveAddressDetail;
	}

	public String getLackClaimData() {
		return lackClaimData;
	}

	public void setLackClaimData(String lackClaimData) {
		this.lackClaimData = lackClaimData;
	}

	public String getSmsModel() {
		return smsModel;
	}

	public void setSmsModel(String smsModel) {
		this.smsModel = smsModel;
	}

	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}

	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}

	public String getIsRun() {
		return isRun;
	}

	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


}