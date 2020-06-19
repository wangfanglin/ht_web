/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.entity;

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
 * 维修工单-待报价Entity
 * @author lichao
 * @version 2020-02-26
 */
@Table(name="ht_repair_offer_form", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单id"),
		@Column(name="damage_img", attrName="damageImg", label="损害部位相关照片"),
		@Column(name="repair_end_date", attrName="repairEndDate", label="维修完成时间"),
		@Column(name="other_price", attrName="otherPrice", label="其他收费"),
		@Column(name="other_remarks", attrName="otherRemarks", label="其他收费说明", queryType=QueryType.LIKE),
		@Column(name="man_hour_price", attrName="manHourPrice", label="工时费"),
		@Column(name="self_price", attrName="selfPrice", label="自付费"),
		@Column(name="sum_price", attrName="sumPrice", label="本次总额", isQuery=false),
		@Column(name="receipt_type", attrName="receiptType", label="收款方式", isQuery=false),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class HtRepairOfferForm extends DataEntity<HtRepairOfferForm> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// 工单id
	private String damageImg;		// 损害部位相关照片
	private Date repairEndDate;		// 维修完成时间
	private Double otherPrice;		// 其他收费
	private String otherRemarks;		// 其他收费说明
	private Double manHourPrice;		// 工时费
	private Double selfPrice;		// 自付费
	private Double sumPrice;		// 本次总额
	private String receiptType;		// 收款方式
	
	public HtRepairOfferForm() {
		this(null);
	}

	public HtRepairOfferForm(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	public String getDamageImg() {
		return damageImg;
	}

	public void setDamageImg(String damageImg) {
		this.damageImg = damageImg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepairEndDate() {
		return repairEndDate;
	}

	public void setRepairEndDate(Date repairEndDate) {
		this.repairEndDate = repairEndDate;
	}
	
	public Double getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(Double otherPrice) {
		this.otherPrice = otherPrice;
	}
	
	@Length(min=0, max=255, message="其他收费说明长度不能超过 255 个字符")
	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}
	
	public Double getManHourPrice() {
		return manHourPrice;
	}

	public void setManHourPrice(Double manHourPrice) {
		this.manHourPrice = manHourPrice;
	}
	
	public Double getSelfPrice() {
		return selfPrice;
	}

	public void setSelfPrice(Double selfPrice) {
		this.selfPrice = selfPrice;
	}
	
	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	
	@Length(min=0, max=10, message="收款方式长度不能超过 10 个字符")
	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
}