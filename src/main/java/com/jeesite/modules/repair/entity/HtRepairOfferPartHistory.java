/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.math.BigDecimal;

/**
 * 维修工单-待报价-损害部位表Entity
 * @author lichao
 * @version 2020-03-09
 */
@Table(name="ht_repair_offer_part_history", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="工单id"),
		@Column(name="client_history_id", attrName="clientHistoryId", label="历史表id"),
		@Column(name="damage_id", attrName="damageId", label="损害部位id"),
		@Column(name="damage_name", attrName="damageName", label="损害部位名称", queryType=QueryType.LIKE),
		@Column(name="bh_project_id", attrName="bhProjectId", label="渤海方案id"),
		@Column(name="hd_project_id", attrName="hdProjectId", label="和德方案id"),
		@Column(name="bh_project_name", attrName="bhProjectName", label="渤海方案名称"),
		@Column(name="hd_project_name", attrName="hdProjectName", label="和德方案名称"),
		@Column(name="damage_price", attrName="damagePrice", label="配件报价"),
		@Column(name="is_salvage", attrName="isSalvage", label="是否有残值 0 否  1是"),
		@Column(name="salvage_type", attrName="salvageType", label="残值类型"),
		@Column(name="salvage_price", attrName="salvagePrice", label="残值价格"),
		@Column(includeEntity=DataEntity.class),
}, orderBy="a.update_date DESC"
)
public class HtRepairOfferPartHistory extends DataEntity<HtRepairOfferPartHistory> {

	private static final long serialVersionUID = 1L;
	private String formId;		// 工单id
	private String clientHistoryId;		// 历史表id
	private String damageId;		// 损害部位id
	private String damageName;		// 损害部位名称
	private String bhProjectId;		// 渤海方案id
	private String hdProjectId;		// 和德方案id
	private String hdProjectName;		// 和德方案名称
	private String bhProjectName;		// 渤海方案名称
	private BigDecimal damagePrice;		// 配件报价
	private String isSalvage;		// 是否有残值 0 否  1是
	private String salvageType;		// 残值类型
	private BigDecimal salvagePrice;		// 残值价格

	public HtRepairOfferPartHistory() {
		this(null);
	}

	public HtRepairOfferPartHistory(String id){
		super(id);
	}

	@Length(min=0, max=64, message="工单id长度不能超过 64 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Length(min=0, max=64, message="损害部位id长度不能超过 64 个字符")
	public String getDamageId() {
		return damageId;
	}

	public void setDamageId(String damageId) {
		this.damageId = damageId;
	}

	@Length(min=0, max=64, message="损害部位名称长度不能超过 64 个字符")
	public String getDamageName() {
		return damageName;
	}

	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}

	@Length(min=0, max=64, message="渤海方案id长度不能超过 64 个字符")
	public String getBhProjectId() {
		return bhProjectId;
	}

	public void setBhProjectId(String bhProjectId) {
		this.bhProjectId = bhProjectId;
	}

	@Length(min=0, max=64, message="和德方案id长度不能超过 64 个字符")
	public String getHdProjectId() {
		return hdProjectId;
	}

	public void setHdProjectId(String hdProjectId) {
		this.hdProjectId = hdProjectId;
	}

	public BigDecimal getDamagePrice() {
		return damagePrice;
	}

	public void setDamagePrice(BigDecimal damagePrice) {
		this.damagePrice = damagePrice;
	}

	@Length(min=0, max=1, message="是否有残值 0 否  1是长度不能超过 1 个字符")
	public String getIsSalvage() {
		return isSalvage;
	}

	public void setIsSalvage(String isSalvage) {
		this.isSalvage = isSalvage;
	}

	@Length(min=0, max=10, message="残值类型长度不能超过 10 个字符")
	public String getSalvageType() {
		return salvageType;
	}

	public void setSalvageType(String salvageType) {
		this.salvageType = salvageType;
	}

	public BigDecimal getSalvagePrice() {
		return salvagePrice;
	}

	public void setSalvagePrice(BigDecimal salvagePrice) {
		this.salvagePrice = salvagePrice;
	}

	public String getClientHistoryId() {
		return clientHistoryId;
	}

	public void setClientHistoryId(String clientHistoryId) {
		this.clientHistoryId = clientHistoryId;
	}

	public String getHdProjectName() {
		return hdProjectName;
	}

	public void setHdProjectName(String hdProjectName) {
		this.hdProjectName = hdProjectName;
	}

	public String getBhProjectName() {
		return bhProjectName;
	}

	public void setBhProjectName(String bhProjectName) {
		this.bhProjectName = bhProjectName;
	}
}