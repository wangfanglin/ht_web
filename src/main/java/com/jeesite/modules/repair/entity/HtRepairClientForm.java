/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.entity;

import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.history.form.FormData;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.sys.entity.Office;
import org.flowable.bpmn.model.Task;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 维修工单-待联系客户Entity
 * @author lichao
 * @version 2020-02-18
 */
@Table(name="ht_repair_client_form", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="htFormInfo.id", label="工单号"),
		@Column(name="contact_status", attrName="contactStatus", label="联系情况"),
		@Column(name="again_date", attrName="againDate", label="再次沟通时间"),
		@Column(name="claim_status", attrName="claimStatus", label="理赔资料状态"),
		@Column(name="claim_data", attrName="claimData", label="核实理赔资料"),
		@Column(name="phone_brand", attrName="phoneBrand", label="手机品牌"),
		@Column(name="phone_type", attrName="phoneType", label="手机型号"),
		@Column(name="phone_color", attrName="phoneColor", label="手机颜色"),
		@Column(name="is_qualified", attrName="isQualified", label="理赔是否合格"),
		@Column(name="is_end", attrName="isEnd", label="维修是否完成"),
		@Column(name="image", attrName="image", label="相关图片", isQuery=false),
		@Column(name="mail_date", attrName="mailDate", label="预期邮寄时间"),
		@Column(name="express_company", attrName="expressCompany", label="快递公司"),
		@Column(name="express_no", attrName="expressNo", label="快递单号"),
		@Column(name="damage_img", attrName="damageImg", label="损害部位相关照片", isQuery=false),
		@Column(name="repair_end_date", attrName="repairEndDate", label="维修完成时间"),
		@Column(name="other_price", attrName="otherPrice", label="其他收费", isQuery=false),
		@Column(name="other_remarks", attrName="otherRemarks", label="其他收费说明", isQuery=false),
		@Column(name="man_hour_price", attrName="manHourPrice", label="工时费", isQuery=false),
		@Column(name="self_price", attrName="selfPrice", label="自付费", isQuery=false),
		@Column(name="sum_price", attrName="sumPrice", label="本次总额", isQuery=false),
		@Column(name="receipt_type", attrName="receiptType", label="收款方式", isQuery=false),
		@Column(name="is_all", attrName="isAll", label="是否全损", isQuery=false),
		@Column(name="equipment_take", attrName="equipmentTake", label="取设备方式", isQuery=false),
		@Column(name="new_imei", attrName="newImei", label="新机IMEI", isQuery=false),
		@Column(name="repair_express_company", attrName="repairExpressCompany", label="维修完成邮寄公司", isQuery=false),
		@Column(name="repair_express_no", attrName="repairExpressNo", label="维修完成邮寄单号", isQuery=false),
		@Column(name="repair_express_date", attrName="repairExpressDate", label="维修完成快递日期", isQuery=false),
		@Column(name="repair_end_image", attrName="repairEndImage", label="留存照片", isQuery=false),
		@Column(name="upload_end_img", attrName="uploadEndImg", label="上传的留存照片", isQuery=false),
		@Column(name="upload_image", attrName="uploadImage", label="上传的维修工单照片", isQuery=false),

		@Column(name="repair_form_image", attrName="repairFormImage", label="维修工单照片", isQuery=false),
		@Column(name="repair_count", attrName="repairCount", label="修改维修时间次数", isQuery=false),
		@Column(name="new_image", attrName="newImage", label="新发现损坏部位照片", isQuery=false),
		@Column(name="is_yes", attrName="isYes", label="是否修改维修方案", isQuery=false),
		@Column(name="is_repair_back", attrName="isRepairBack", label="是否返修", isQuery=false),
		@Column(name="surplus_price", attrName="surplusPrice", label="剩余保额", isQuery=false),


		@Column(name="recording", attrName="recording", label="电话录音"),
		@Column(name="claim_type", attrName="claimType", label="理赔资料"),
		@Column(name="reason", attrName="reason", label="操作原因/备注"),
		@Column(name="close_type", attrName="closeType", label="关闭原因"),
		@Column(name="reopen_type", attrName="reopenType", label="重启原因"),
		@Column(name="other_reason", attrName="otherReason", label="其他原因"),
		@Column(name="operation_type", attrName="operationType", label="操作类型"),
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
		@JoinTable(type=Type.LEFT_JOIN, entity=Office.class, alias="o",
				on="o.office_code = f.office_id",
				columns={@Column(includeEntity=Office.class)}),
		}, orderBy="a.update_date DESC"
)
public class HtRepairClientForm extends BpmEntity<HtRepairClientForm> implements FormData {
	
	private static final long serialVersionUID = 1L;

	private HtFormInfo htFormInfo;		// 工单号
	private HtBrandInfo htBrandInfo;		// 品牌
	private HtPhoneModelInfo htPhoneModelInfo;		// 型号
	private HtClaimSettlementForm htClaimSettlementForm;//在线理赔实体
	private Office office;
	private String contactStatus;		// 联系情况
	private Date againDate;		// 再次沟通时间
	private String claimStatus;		// 理赔资料状态1未邮寄2已邮寄3已签收4未确认
	private String claimData;		// 核实理赔资料
	private String phoneBrand;		// 手机品牌
	private String phoneType;		// 手机型号
	private String phoneColor;		// 手机颜色
	private String isQualified;		// 理赔资料是否齐全1是2否
	private String image;		// 相关图片
	private Date mailDate;		// 预期邮寄时间
	private String expressCompany;		// 快递公司
	private String expressNo;		// 快递单号
	private String isYes;//是否修改维修方案，0否，1是
	private String isRepairBack;//是否返修



	//临时属性
	private String repairFormType;//判断跳转表单，2待报价，3待维修
	private PolicyInfo policyInfo;		// 保单
	private String isRun;		// 1关闭工单
	private String fuUnit;		// 副部件名称
	private String zhuUnit;		// 主部件名称

	private String callId;		// 电话录音


	private String lackClaimData;		// 缺少的理赔资料

	private List<PolicyDetail> weixiuList;//维修保
	private List<PolicyDetail> huanjiList;//换机保
	private List<PolicyDetail> yanbaoList;//延保
	private List<PolicyDetail> shujubaoList;//数据保



	//待报价属性
	private String damageImg;		// 损害部位相关照片
	private Date repairEndDate;		// 维修完成时间
	private BigDecimal otherPrice;		// 其他收费
	private String otherRemarks;		// 其他收费说明
	private BigDecimal manHourPrice;		// 工时费
	private BigDecimal selfPrice;		// 自付费
	private BigDecimal sumPrice;		// 本次总额
	private String receiptType;		// 收款方式
	private String maintainBranchName; //维修网点名称
	private BigDecimal surplusPrice; //剩余保额


	//待维修属性
	private String equipmentTake;		// 取设备方式
	private String newImei;		// 新机IMEI
	private String repairExpressCompany;		// 维修完成邮寄公司
	private String repairExpressNo;		// 维修完成邮寄单号
	private Date repairExpressDate;		// 维修完成快递日期
	private String repairEndImage;		// 留存照片
	private String uploadEndImg;		// 上传的留存照片
	private String uploadImage;		// 上传的维修工单照片


	private String repairFormImage;		// 维修工单照片
	private int repairCount;		// 修改维修时间次数

	private String newImage;		// 新发现损坏部位照片



	//报价明细
	private List<HtRepairOfferPart> offerPartList;
	private String isAll;			//是否全损 1全损2非全损
	private String isEnd;			//是否已完成 1未完成2已完成



	private String recording;		// 占位用来存电话录音
	private String claimType;		// 理赔资料
	private String reason;		// 操作原因/备注
	private String closeType;		// 关闭原因
	private String verifier;		// 审核人
	private String reopenType;		// 重启原因
	private String otherReason; //其他原因
	private String operationType; //操作类型

	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}

	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
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

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getRecording() {
		return recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public String getReopenType() {
		return reopenType;
	}

	public void setReopenType(String reopenType) {
		this.reopenType = reopenType;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getMaintainBranchName() {
		return maintainBranchName;
	}
	public void setMaintainBranchName(String maintainBranchName) {
		this.maintainBranchName = maintainBranchName;
	}


	public String getRepairFormType() {
		return repairFormType;
	}

	public void setRepairFormType(String repairFormType) {
		this.repairFormType = repairFormType;
	}

	public HtFormInfo getHtFormInfo() {
		return htFormInfo;
	}

	public void setHtFormInfo(HtFormInfo htFormInfo) {
		this.htFormInfo = htFormInfo;
	}

	public HtClaimSettlementForm getHtClaimSettlementForm() {
		return htClaimSettlementForm;
	}

	public void setHtClaimSettlementForm(HtClaimSettlementForm htClaimSettlementForm) {
		this.htClaimSettlementForm = htClaimSettlementForm;
	}

	public HtRepairClientForm() {
		this(null);
	}

	public HtRepairClientForm(String id){
		super(id);
	}

	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

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
	
	@Length(min=0, max=255, message="核实理赔资料长度不能超过 255 个字符")
	public String getClaimData() {
		return claimData;
	}

	public void setClaimData(String claimData) {
		this.claimData = claimData;
	}
	
	@Length(min=0, max=64, message="手机品牌长度不能超过 64 个字符")
	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}
	
	@Length(min=0, max=64, message="手机型号长度不能超过 64 个字符")
	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	@Length(min=0, max=64, message="手机颜色长度不能超过 64 个字符")
	public String getPhoneColor() {
		return phoneColor;
	}

	public void setPhoneColor(String phoneColor) {
		this.phoneColor = phoneColor;
	}
	
	@Length(min=0, max=10, message="理赔是否合格长度不能超过 10 个字符")
	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
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
	
	@Length(min=0, max=64, message="快递公司长度不能超过 64 个字符")
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	
	@Length(min=0, max=64, message="快递单号长度不能超过 64 个字符")
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getDamageImg() {
		return damageImg;
	}

	public void setDamageImg(String damageImg) {
		this.damageImg = damageImg;
	}

	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm"
	)
	public Date getRepairEndDate() {
		return repairEndDate;
	}

	public void setRepairEndDate(Date repairEndDate) {
		this.repairEndDate = repairEndDate;
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	public BigDecimal getManHourPrice() {
		return manHourPrice;
	}

	public void setManHourPrice(BigDecimal manHourPrice) {
		this.manHourPrice = manHourPrice;
	}

	public BigDecimal getSelfPrice() {
		return selfPrice;
	}

	public void setSelfPrice(BigDecimal selfPrice) {
		this.selfPrice = selfPrice;
	}

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public String getIsYes() {
		return isYes;
	}

	public void setIsYes(String isYes) {
		this.isYes = isYes;
	}

	public String getEquipmentTake() {
		return equipmentTake;
	}

	public void setEquipmentTake(String equipmentTake) {
		this.equipmentTake = equipmentTake;
	}

	public String getNewImei() {
		return newImei;
	}

	public void setNewImei(String newImei) {
		this.newImei = newImei;
	}

	public String getRepairExpressCompany() {
		return repairExpressCompany;
	}

	public void setRepairExpressCompany(String repairExpressCompany) {
		this.repairExpressCompany = repairExpressCompany;
	}

	public String getRepairExpressNo() {
		return repairExpressNo;
	}

	public void setRepairExpressNo(String repairExpressNo) {
		this.repairExpressNo = repairExpressNo;
	}

	public Date getRepairExpressDate() {
		return repairExpressDate;
	}

	public void setRepairExpressDate(Date repairExpressDate) {
		this.repairExpressDate = repairExpressDate;
	}

	public String getRepairEndImage() {
		return repairEndImage;
	}

	public void setRepairEndImage(String repairEndImage) {
		this.repairEndImage = repairEndImage;
	}

	public String getRepairFormImage() {
		return repairFormImage;
	}

	public void setRepairFormImage(String repairFormImage) {
		this.repairFormImage = repairFormImage;
	}

	public int getRepairCount() {
		return repairCount;
	}

	public void setRepairCount(int repairCount) {
		this.repairCount = repairCount;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public List<HtRepairOfferPart> getOfferPartList() {
		return offerPartList;
	}

	public void setOfferPartList(List<HtRepairOfferPart> offerPartList) {
		this.offerPartList = offerPartList;
	}

	public String getIsRun() {
		return isRun;
	}

	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}

	public String getNewImage() {
		return newImage;
	}

	public void setNewImage(String newImage) {
		this.newImage = newImage;
	}

	public String getIsRepairBack() {
		return isRepairBack;
	}

	public void setIsRepairBack(String isRepairBack) {
		this.isRepairBack = isRepairBack;
	}

	public String getUploadEndImg() {
		return uploadEndImg;
	}

	public void setUploadEndImg(String uploadEndImg) {
		this.uploadEndImg = uploadEndImg;
	}

	public String getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(String uploadImage) {
		this.uploadImage = uploadImage;
	}

	public String getFuUnit() {
		return fuUnit;
	}

	public void setFuUnit(String fuUnit) {
		this.fuUnit = fuUnit;
	}

	public String getZhuUnit() {
		return zhuUnit;
	}

	public void setZhuUnit(String zhuUnit) {
		this.zhuUnit = zhuUnit;
	}

	public BigDecimal getSurplusPrice() {
		return surplusPrice;
	}

	public void setSurplusPrice(BigDecimal surplusPrice) {
		this.surplusPrice = surplusPrice;
	}

	public String getLackClaimData() {
		return lackClaimData;
	}

	public void setLackClaimData(String lackClaimData) {
		this.lackClaimData = lackClaimData;
	}

	public List<PolicyDetail> getWeixiuList() {
		return weixiuList;
	}

	public void setWeixiuList(List<PolicyDetail> weixiuList) {
		this.weixiuList = weixiuList;
	}

	public List<PolicyDetail> getHuanjiList() {
		return huanjiList;
	}

	public void setHuanjiList(List<PolicyDetail> huanjiList) {
		this.huanjiList = huanjiList;
	}

	public List<PolicyDetail> getYanbaoList() {
		return yanbaoList;
	}

	public void setYanbaoList(List<PolicyDetail> yanbaoList) {
		this.yanbaoList = yanbaoList;
	}

	public List<PolicyDetail> getShujubaoList() {
		return shujubaoList;
	}

	public void setShujubaoList(List<PolicyDetail> shujubaoList) {
		this.shujubaoList = shujubaoList;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}
}