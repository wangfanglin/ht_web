/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htuserapplyinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户在线申请表Entity
 * @author tangchao
 * @version 2020-04-20
 */
@Table(name="ht_user_apply_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="policy_id", attrName="policyId", label="保单id"),
		@Column(name="facility_brand_id", attrName="facilityBrandId", label="设备品牌"),
		@Column(name="form_id", attrName="formId", label="工单id"),
		@Column(name="facility_model_id", attrName="facilityModelId", label="设备型号"),
		@Column(name="facility_color", attrName="facilityColor", label="设备颜色"),
		@Column(name="area_id", attrName="areaId", label="地域"),
		@Column(name="malfunction_date", attrName="malfunctionDate", label="故障发生时间"),
		@Column(name="malfunction_id", attrName="malfunctionId", label="故障原因"),
		@Column(name="card_name", attrName="cardName", label="身份姓名", queryType=QueryType.LIKE),
		@Column(name="card_id", attrName="cardId", label="身份证号"),
		@Column(name="card_start_date", attrName="cardStartDate", label="身份证开始时间"),
		@Column(name="card_end_date", attrName="cardEndDate", label="身份证失效时间"),
		@Column(name="bad_part_img", attrName="badPartImg", label="损坏部位"),
		@Column(name="card_img", attrName="cardImg", label="身份证照片"),
		@Column(name="voucher_img", attrName="voucherImg", label="购买凭证"),
		@Column(name="is_main", attrName="isMain", label="是否为最新"),
		@Column(name="product_name", attrName="productName", label="产品名称", queryType=QueryType.LIKE),
		@Column(name="user_phone", attrName="userPhone", label="user_phone"),
		@Column(name="bad_side_wimg", attrName="badSideWimg", label="侧面照2"),
		@Column(name="bad_side_oimg", attrName="badSideOimg", label="侧面照1"),
		@Column(name="bad_reverse_img", attrName="badReverseImg", label="损坏反面"),
		@Column(name="bad_front_img", attrName="badFrontImg", label="损坏部位正面照"),
		@Column(name="card_reverse_img", attrName="cardReverseImg", label="身份证反面照"),
		@Column(name="card_front_img", attrName="cardFrontImg", label="身份证正面照"),
		@Column(name="card_hand_img", attrName="cardHandImg", label="身份证手持照片"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="clm_no", attrName="clmNo", label="clm_no", isQuery=false),
}, orderBy="a.update_date DESC"
)
public class HtUserApplyInfo extends DataEntity<HtUserApplyInfo> {

	private static final long serialVersionUID = 1L;
	private String policyId;		// 保单id
	private String facilityBrandId;		// 设备品牌
	private String formId;		// 工单id
	private String facilityModelId;		// 设备型号
	private String facilityColor;		// 设备颜色
	private String areaId;		// 地域
	private Date malfunctionDate;		// 故障发生时间
	private String malfunctionId;		// 故障原因
	private String cardName;		// 身份姓名
	private String cardId;		// 身份证号
	private Date cardStartDate;		// 身份证开始时间
	private Date cardEndDate;		// 身份证失效时间
	private String badPartImg;		// 损坏部位
	private String cardImg;		// 身份证照片
	private String voucherImg;		// 购买凭证
	private String isMain;		// 是否为最新
	private String productName;		// 产品名称
	private String insuranceFacilityName;		// 投保设备
	private String userPhone;		// user_phone
	private String badSideWimg;		// 侧面照2
	private String badSideOimg;		// 侧面照1
	private String badReverseImg;		// 损坏反面
	private String badFrontImg;		// 损坏部位正面照
	private String cardReverseImg;		// 身份证反面照
	private String cardFrontImg;		// 身份证正面照
	private String cardHandImg;		// 身份证手持照片
	private String userId;		// 身份证手持照片
	private String clmNo;		//案件号

	public HtUserApplyInfo() {
		this(null);
	}

	public HtUserApplyInfo(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min=0, max=64, message="保单id长度不能超过 64 个字符")
	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	@Length(min=0, max=64, message="设备品牌长度不能超过 64 个字符")
	public String getFacilityBrandId() {
		return facilityBrandId;
	}

	public void setFacilityBrandId(String facilityBrandId) {
		this.facilityBrandId = facilityBrandId;
	}

	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Length(min=0, max=64, message="设备型号长度不能超过 64 个字符")
	public String getFacilityModelId() {
		return facilityModelId;
	}

	public void setFacilityModelId(String facilityModelId) {
		this.facilityModelId = facilityModelId;
	}

	@Length(min=0, max=64, message="设备颜色长度不能超过 64 个字符")
	public String getFacilityColor() {
		return facilityColor;
	}

	public void setFacilityColor(String facilityColor) {
		this.facilityColor = facilityColor;
	}

	@Length(min=0, max=64, message="地域长度不能超过 64 个字符")
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMalfunctionDate() {
		return malfunctionDate;
	}

	public void setMalfunctionDate(Date malfunctionDate) {
		this.malfunctionDate = malfunctionDate;
	}

	@Length(min=0, max=64, message="故障原因长度不能超过 64 个字符")
	public String getMalfunctionId() {
		return malfunctionId;
	}

	public void setMalfunctionId(String malfunctionId) {
		this.malfunctionId = malfunctionId;
	}

	@Length(min=0, max=64, message="身份姓名长度不能超过 64 个字符")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@Length(min=0, max=18, message="身份证号长度不能超过 18 个字符")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCardStartDate() {
		return cardStartDate;
	}

	public void setCardStartDate(Date cardStartDate) {
		this.cardStartDate = cardStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCardEndDate() {
		return cardEndDate;
	}

	public void setCardEndDate(Date cardEndDate) {
		this.cardEndDate = cardEndDate;
	}

	@Length(min=0, max=255, message="损坏部位长度不能超过 255 个字符")
	public String getBadPartImg() {
		return badPartImg;
	}

	public void setBadPartImg(String badPartImg) {
		this.badPartImg = badPartImg;
	}

	@Length(min=0, max=255, message="身份证照片长度不能超过 255 个字符")
	public String getCardImg() {
		return cardImg;
	}

	public void setCardImg(String cardImg) {
		this.cardImg = cardImg;
	}

	@Length(min=0, max=255, message="购买凭证长度不能超过 255 个字符")
	public String getVoucherImg() {
		return voucherImg;
	}

	public void setVoucherImg(String voucherImg) {
		this.voucherImg = voucherImg;
	}

	@Length(min=0, max=1, message="是否为最新长度不能超过 1 个字符")
	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	@Length(min=0, max=255, message="产品名称长度不能超过 255 个字符")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Length(min=0, max=64, message="投保设备长度不能超过 64 个字符")
	public String getInsuranceFacilityName() {
		return insuranceFacilityName;
	}

	public void setInsuranceFacilityName(String insuranceFacilityName) {
		this.insuranceFacilityName = insuranceFacilityName;
	}

	@Length(min=0, max=64, message="user_phone长度不能超过 64 个字符")
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Length(min=0, max=255, message="侧面照2长度不能超过 255 个字符")
	public String getBadSideWimg() {
		return badSideWimg;
	}

	public void setBadSideWimg(String badSideWimg) {
		this.badSideWimg = badSideWimg;
	}

	@Length(min=0, max=255, message="侧面照1长度不能超过 255 个字符")
	public String getBadSideOimg() {
		return badSideOimg;
	}

	public void setBadSideOimg(String badSideOimg) {
		this.badSideOimg = badSideOimg;
	}

	@Length(min=0, max=255, message="损坏反面长度不能超过 255 个字符")
	public String getBadReverseImg() {
		return badReverseImg;
	}

	public void setBadReverseImg(String badReverseImg) {
		this.badReverseImg = badReverseImg;
	}

	@Length(min=0, max=255, message="损坏部位正面照长度不能超过 255 个字符")
	public String getBadFrontImg() {
		return badFrontImg;
	}

	public void setBadFrontImg(String badFrontImg) {
		this.badFrontImg = badFrontImg;
	}

	@Length(min=0, max=255, message="身份证反面照长度不能超过 255 个字符")
	public String getCardReverseImg() {
		return cardReverseImg;
	}

	public void setCardReverseImg(String cardReverseImg) {
		this.cardReverseImg = cardReverseImg;
	}

	@Length(min=0, max=255, message="身份证正面照长度不能超过 255 个字符")
	public String getCardFrontImg() {
		return cardFrontImg;
	}

	public void setCardFrontImg(String cardFrontImg) {
		this.cardFrontImg = cardFrontImg;
	}

	@Length(min=0, max=255, message="身份证手持照片长度不能超过 255 个字符")
	public String getCardHandImg() {
		return cardHandImg;
	}

	public void setCardHandImg(String cardHandImg) {
		this.cardHandImg = cardHandImg;
	}


	public String getClmNo() {
		return clmNo;
	}

	public void setClmNo(String clmNo) {
		this.clmNo = clmNo;
	}
}