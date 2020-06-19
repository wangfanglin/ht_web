/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementform.entity;

import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.Office;
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
 * 在线理赔表Entity
 * @author hongmengzhong
 * @version 2020-02-28
 */
@Table(name="ht_claim_settlement_form", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="htFormInfo.id", label="工单号"),
		@Column(name="work_order_id", attrName="workOrderId", label="工单ID", isQuery=false),
		@Column(name="contact_status", attrName="contactStatus", label="联系结果", isQuery=false),
		@Column(name="again_contact_date", attrName="againContactDate", label="再次联系时间", isQuery=false),
		@Column(name="user_name", attrName="userName", label="客户名字", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="手机号"),
		@Column(name="phone_model", attrName="phoneModel", label="手机型号", isQuery=false),
		@Column(name="phone_brand", attrName="phoneBrand", label="手机品牌", isQuery=false),
		@Column(name="id_number", attrName="idNumber", label="身份证", isQuery=false),
		@Column(name="cause_damage", attrName="causeDamage", label="损坏原因", isQuery=false),
		@Column(name="maintain_id", attrName="maintainId", label="维修类型", isQuery=false),
		@Column(name="master_unit", attrName="masterUnit", label="主部件选择", isQuery=false),
		@Column(name="vice_parts", attrName="viceParts", label="副部件选择", isQuery=false),
		@Column(name="is_qualified", attrName="isQualified", label="理赔资料是否合格", isQuery=false),
		@Column(name="settlement_data_id", attrName="settlementDataId", label="资料中间表id", isQuery=false),
		@Column(name="maintain_type", attrName="maintainType", label="维修类型", isQuery=false),
		@Column(name="serve_type", attrName="serveType", label="服务方式", isQuery=false),
		@Column(name="return_area_code", attrName="returnAreaCode", label="联系电话", isQuery=false),
		@Column(name="maintain_branch_id", attrName="maintainBranchId", label="维修网点", isQuery=false),
		@Column(name="sms", attrName="sms", label="生成短信模板", isQuery=false),
		@Column(name="is_express", attrName="isExpress", label="是否需要给客户叫快递", isQuery=false),
		@Column(name="return_name", attrName="returnName", label="回寄姓名", isQuery=false),
		@Column(name="return_phone", attrName="returnPhone", label="联系电话", isQuery=false),
		@Column(name="return_address", attrName="returnAddress", label="具体地址"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="disqualification_disqualification", attrName="disqualificationDisqualification", label="理赔资料不合格原因"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
		@Column(name="call_info_id", attrName="callInfoId", label="电话记录id"),
		@Column(name="damage_imgs", attrName="damageImgs", label="损坏图片"),
		@Column(name="identity_card_imgs", attrName="identityCardImgs", label="身份证图片"),
		@Column(name="purchase_imgs", attrName="purchaseImgs", label="购买图片"),
		@Column(name="product_child_id", attrName="productChildId", label="子产品id"),
	}, joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity=HtFormInfo.class, alias="f",
				on="f.id = a.form_id",
				columns={@Column(includeEntity=HtFormInfo.class)}),
		@JoinTable(type=Type.LEFT_JOIN, entity= HtUserApplyInfo.class, alias="p",
				on="p.form_id = f.id",
				columns={@Column(includeEntity=HtUserApplyInfo.class)}),
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= PolicyInfo.class, alias="po",
				on="po.id = f.policy_id",
				columns={@Column(includeEntity=PolicyInfo.class)}),
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Office.class, alias="of",
				on="of.office_code = a.maintain_branch_id",
				columns={@Column(includeEntity=Office.class)}),
	},  orderBy="a.update_date DESC"
)
public class HtClaimSettlementForm extends BpmEntity<HtClaimSettlementForm> {

	private static final long serialVersionUID = 1L;
	private HtFormInfo htFormInfo;		// 工单号
	private String workOrderId;		// 工单ID
	private String formId;
	private String contactStatus;		// 联系结果
	private Date againContactDate;		// 再次联系时间
	private String userName;		// 客户名字
	private String phone;		// 手机号
	private String phoneModel;		// 手机型号
	private String phoneBrand;		// 手机品牌
	private String idNumber;		// 身份证
	private String causeDamage;		// 损坏原因
	private String maintainId;		// 维修类型
	private String isQualified;		// 理赔资料是否合格
	private String maintainType;		// 维修类型
	private String serveType;		// 服务方式
	private String maintainBranchId;		// 维修网点
	private String sms;		// 生成短信模板
	private String isExpress;		// 是否需要给客户叫快递
	private String returnName;		// 回寄姓名
	private String returnAreaCode;
	private String returnPhone;		// 联系电话
	private String returnAddress;		// 具体地址
	private String remark;		// 备注
	private String disqualificationDisqualification;		// 理赔资料不合格原因
	private String claimDataStr;   //资料选项
	private String claimDataNameStr;
	private String settlementDataId;
	private String masterUnit;   //主部件
	private String viceParts;    //副部件
	private HtUserApplyInfo htUserApplyInfo;
	private String callInfoId;
	private String damageImgs;
	private String identityCardImgs;
	private String purchaseImgs;
	private String productId;
	private String isAssigned;
	private String typeClose;
	private PolicyInfo policyInfo;		// 保单
	private Office office;
	private Area area;
	private String productChildId;

	public String getClaimDataNameStr() {
		return claimDataNameStr;
	}

	public void setClaimDataNameStr(String claimDataNameStr) {
		this.claimDataNameStr = claimDataNameStr;
	}

	public String getProductChildId() {
		return productChildId;
	}

	public void setProductChildId(String productChildId) {
		this.productChildId = productChildId;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}

	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}

	public String getTypeClose() {
		return typeClose;
	}

	public void setTypeClose(String typeClose) {
		this.typeClose = typeClose;
	}

	public String getIsAssigned() {
		return isAssigned;
	}
	public void setIsAssigned(String isAssigned) {
		this.isAssigned = isAssigned;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDamageImgs() {
		return damageImgs;
	}

	public void setDamageImgs(String damageImgs) {
		this.damageImgs = damageImgs;
	}

	public String getIdentityCardImgs() {
		return identityCardImgs;
	}

	public void setIdentityCardImgs(String identityCardImgs) {
		this.identityCardImgs = identityCardImgs;
	}

	public String getPurchaseImgs() {
		return purchaseImgs;
	}

	public void setPurchaseImgs(String purchaseImgs) {
		this.purchaseImgs = purchaseImgs;
	}

	public String getCallInfoId() {
		return callInfoId;
	}

	public void setCallInfoId(String callInfoId) {
		this.callInfoId = callInfoId;
	}

	public HtUserApplyInfo getHtUserApplyInfo() {
		return htUserApplyInfo;
	}

	public void setHtUserApplyInfo(HtUserApplyInfo htUserApplyInfo) {
		this.htUserApplyInfo = htUserApplyInfo;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getMasterUnit() {
		return masterUnit;
	}

	public void setMasterUnit(String masterUnit) {
		this.masterUnit = masterUnit;
	}

	public String getViceParts() {
		return viceParts;
	}

	public void setViceParts(String viceParts) {
		this.viceParts = viceParts;
	}

	public String getSettlementDataId() {
		return settlementDataId;
	}

	public void setSettlementDataId(String settlementDataId) {
		this.settlementDataId = settlementDataId;
	}

	public String getReturnAreaCode() {
		return returnAreaCode;
	}

	public void setReturnAreaCode(String returnAreaCode) {
		this.returnAreaCode = returnAreaCode;
	}

	public HtFormInfo getHtFormInfo() {
		return htFormInfo;
	}

	public void setHtFormInfo(HtFormInfo htFormInfo) {
		this.htFormInfo = htFormInfo;
	}


	public String getClaimDataStr() {
		return claimDataStr;
	}

	public void setClaimDataStr(String claimDataStr) {
		this.claimDataStr = claimDataStr;
	}

	public HtClaimSettlementForm() {
		this(null);
	}


	public HtClaimSettlementForm(String id){
		super(id);
	}


	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	@Length(min=0, max=64, message="工单ID长度不能超过 64 个字符")
	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
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
	
	@Length(min=0, max=255, message="客户名字长度不能超过 255 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="手机号长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="手机品牌长度不能超过 255 个字符")
	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@Length(min=0, max=255, message="损坏原因长度不能超过 255 个字符")
	public String getCauseDamage() {
		return causeDamage;
	}

	public void setCauseDamage(String causeDamage) {
		this.causeDamage = causeDamage;
	}
	
	public String getMaintainId() {
		return maintainId;
	}

	public void setMaintainId(String maintainId) {
		this.maintainId = maintainId;
	}
	
	@Length(min=0, max=1, message="理赔资料是否合格长度不能超过 1 个字符")
	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	
	@Length(min=0, max=1, message="维修类型长度不能超过 1 个字符")
	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	
	@Length(min=0, max=1, message="服务方式长度不能超过 1 个字符")
	public String getServeType() {
		return serveType;
	}

	public void setServeType(String serveType) {
		this.serveType = serveType;
	}
	
	public String getMaintainBranchId() {
		return maintainBranchId;
	}

	public void setMaintainBranchId(String maintainBranchId) {
		this.maintainBranchId = maintainBranchId;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}
	
	@Length(min=0, max=1, message="是否需要给客户叫快递长度不能超过 1 个字符")
	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	
	@Length(min=0, max=255, message="回寄姓名长度不能超过 255 个字符")
	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	
	public String getReturnPhone() {
		return returnPhone;
	}

	public void setReturnPhone(String returnPhone) {
		this.returnPhone = returnPhone;
	}
	
	@Length(min=0, max=255, message="具体地址长度不能超过 255 个字符")
	public String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}
	
	@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDisqualificationDisqualification() {
		return disqualificationDisqualification;
	}

	public void setDisqualificationDisqualification(String disqualificationDisqualification) {
		this.disqualificationDisqualification = disqualificationDisqualification;
	}
	
}