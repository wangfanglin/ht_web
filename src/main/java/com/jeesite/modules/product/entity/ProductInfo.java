/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
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
 * 产品表（权益）Entity
 * @author zhaohaifeng
 * @version 2020-02-17
 */
@Table(name="ht_product_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="pro_name", attrName="proName", label="产品名称", queryType=QueryType.LIKE),
		@Column(name="pro_code", attrName="proCode", label="产品编码"),
		@Column(name="category", attrName="category", label="服务标的/类目"),
		@Column(name="system_type", attrName="systemType", label="系统类型"),
		@Column(name="product_type", attrName="productType", label="产品类别"),
		@Column(name="price_flag", attrName="priceFlag", label="保额是否为主商品金额1是0否"),
		@Column(name="maintain_standard", attrName="maintainStandard", label="维修品质1原厂0非原厂"),
		@Column(name="equity_intro", attrName="equityIntro", label="权益简介"),
		@Column(name="equity_url", attrName="equityUrl", label="权益地址"),
		@Column(name="into_market_date", attrName="intoMarketDate", label="上市时间"),
		@Column(name="exit_market_date", attrName="exitMarketDate", label="退市时间"),
//		@Column(name="min_price", attrName="minPrice", label="销售价格下限"),
//		@Column(name="max_price", attrName="maxPrice", label="销售价格上限"),
		//@Column(name="phone_model_id", attrName="phoneModelId", label="机型"),
		@Column(name="maintenance_frequency", attrName="maintenanceFrequency", label="维修次数"),
		@Column(name="is_restrict", attrName="isRestrict", label="维修限额是否有限制1是0否"),
		@Column(name="restrict_price", attrName="restrictPrice", label="限制金额"),
		@Column(name="insurance_restrict_price", attrName="insuranceRestrictPrice", label="保险公司维修限额"),
		@Column(name="change_frequency", attrName="changeFrequency", label="换机次数"),
		@Column(name="change_condition", attrName="changeCondition", label="换机条件"),
		@Column(name="change_payment", attrName="changePayment", label="换机自付额"),
		@Column(name="basis_depreciation", attrName="basisDepreciation", label="基础折旧率"),
		@Column(name="monthly_depreciation", attrName="monthlyDepreciation", label="每月折旧率"),
		@Column(name="serve_cost_price", attrName="serveCostPrice", label="服务成本"),
		@Column(name="rate", attrName="rate", label="费率"),
		@Column(name="assembly_id", attrName="assemblyId", label="维修部件"),
		@Column(name="premium", attrName="premium", label="保费"),
		@Column(name="suggested_retail_price", attrName="suggestedRetailPrice", label="建议零售价"),
		@Column(name="insurance_provider_id", attrName="insuranceProviderId", label="保险供应商"),
		@Column(name="intermediary_service_id", attrName="intermediaryServiceId", label="中介服务商"),
		@Column(name="excess_rate", attrName="excessRate", label="1.0字段"),
		@Column(name="fix_excess_rate", attrName="fixExcessRate", label="1.0字段"),
		@Column(name="insure_limit", attrName="insureLimit", label="1.0字段"),
		@Column(name="gua_limt", attrName="guaLimt", label="1.0字段"),
		@Column(name="is_start", attrName="isStart", label="是否立即启用1是0否"),
		@Column(name="coverage", attrName="coverage", label="商品金额"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class ProductInfo extends DataEntity<ProductInfo> {
	
	private static final long serialVersionUID = 1L;
	private String proName;		// 产品名称
	private String proCode;		// 产品编码
	private String category;		// 服务标的/类目
	private String systemType;		// 系统类型
	private String productType;		// 产品类别

	private String maintainStandard;		// 维修品质1原厂0非原厂
	private String equityIntro;		// 权益简介
	private String equityUrl;		// 权益地址
	private Date intoMarketDate;		// 上市时间
	private Date exitMarketDate;		// 退市时间
	private Long maintenanceFrequency;		// 维修次数
	private String isRestrict;		// 维修限额是否有限制1是0否
	private Double restrictPrice;		// 限制金额
	private Double insuranceRestrictPrice;		// 保险公司维修限额
	private Long changeFrequency;		// 换机次数
	private String changeCondition;		// 换机条件
	private Double changePayment;		// 换机自付额
	private Double basisDepreciation;		// 基础折旧率
	private Double monthlyDepreciation;		// 每月折旧率
	private Double serveCostPrice;		// 服务成本
	private Long rate;		// 费率
	private Double premium;		// 保费
	private Double suggestedRetailPrice;		// 建议零售价
	private String insuranceProviderId;		// 保险供应商
	private String intermediaryServiceId;		// 中介服务商
	private String isStart;		// 是否立即启用1是0否
	private String remark;		// remark
	private String assemblyId;  //维修部件ID


	private Double coverage;  //商品金额
	private String priceFlag;		// 保额是否为主商品金额1是0否
	private Double minPrice;		// 销售价格下限
	private Double maxPrice;		// 销售价格上限
	private String phoneModelId;		// 机型
	private String excessRate;		// 1.0字段
	private String fixExcessRate;		// 1.0字段
	private String insureLimit;		// 1.0字段
	private String guaLimt;		// 1.0字段
	private String isImmediately;		// 是否购买立即生效1是0否
	private Long   takeDay;		// 购买。。。天后生效
	private Date   effectTime;  //生效时间
	private Long   validity;		// 有效期
	private String terminationChangeFrequency;		// 终止规则：换机次数
	private String terminationRemainCoverage;		// 终止规则：剩余保费
	private String terminationServeValidity;		// 终止规则：服务有效期
	private String terminationMaintenanceFrequency;		// 终止规则：维修次数
	private String terminationMaintenanceAmount;		// 终止规则：维修金限制
	private String terminationRecoverFrequency;


	public ProductInfo() {
		this(null);
	}

	public ProductInfo(String id){
		super(id);
	}


	public String getAssemblyId() {
		return assemblyId;
	}

	public void setAssemblyId(String assemblyId) {
		this.assemblyId = assemblyId;
	}

	public Double getCoverage() {
		return coverage;
	}

	public void setCoverage(Double coverage) {
		this.coverage = coverage;
	}

    @NotBlank(message="产品名称不能为空")
	@Length(min=0, max=255, message="产品名称长度不能超过 255 个字符")
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@NotBlank(message="产品编码不能为空")
	@Length(min=0, max=255, message="产品编码长度不能超过 255 个字符")
	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	
	@NotBlank(message="服务标的/类目不能为空")
	@Length(min=0, max=1, message="服务标的/类目长度不能超过 1 个字符")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@NotBlank(message="系统类型不能为空")
	@Length(min=0, max=1, message="系统类型长度不能超过 1 个字符")
	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	
	@NotBlank(message="产品类别不能为空")
	@Length(min=0, max=1, message="产品类别长度不能超过 1 个字符")
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	

	@Length(min=0, max=1, message="保额是否为主商品金额1是0否长度不能超过 1 个字符")
	public String getPriceFlag() {
		return priceFlag;
	}

	public void setPriceFlag(String priceFlag) {
		this.priceFlag = priceFlag;
	}
	
	@NotBlank(message="维修品质不能为空")
	@Length(min=0, max=1, message="维修品质1原厂0非原厂长度不能超过 1 个字符")
	public String getMaintainStandard() {
		return maintainStandard;
	}

	public void setMaintainStandard(String maintainStandard) {
		this.maintainStandard = maintainStandard;
	}
	
	public String getEquityIntro() {
		return equityIntro;
	}

	public void setEquityIntro(String equityIntro) {
		this.equityIntro = equityIntro;
	}
	
	@NotBlank(message="权益地址不能为空")
	@Length(min=0, max=255, message="权益地址长度不能超过 255 个字符")
	public String getEquityUrl() {
		return equityUrl;
	}

	public void setEquityUrl(String equityUrl) {
		this.equityUrl = equityUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="上市时间不能为空")
	public Date getIntoMarketDate() {
		return intoMarketDate;
	}

	public void setIntoMarketDate(Date intoMarketDate) {
		this.intoMarketDate = intoMarketDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="退市时间不能为空")
	public Date getExitMarketDate() {
		return exitMarketDate;
	}

	public void setExitMarketDate(Date exitMarketDate) {
		this.exitMarketDate = exitMarketDate;
	}
	
	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	
	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getPhoneModelId() {
		return phoneModelId;
	}

	public void setPhoneModelId(String phoneModelId) {
		this.phoneModelId = phoneModelId;
	}

	public Long getMaintenanceFrequency() {
		return maintenanceFrequency;
	}

	public void setMaintenanceFrequency(Long maintenanceFrequency) {
		this.maintenanceFrequency = maintenanceFrequency;
	}
	
	@Length(min=0, max=1, message="维修限额是否有限制1是0否长度不能超过 1 个字符")
	public String getIsRestrict() {
		return isRestrict;
	}

	public void setIsRestrict(String isRestrict) {
		this.isRestrict = isRestrict;
	}
	
	public Double getRestrictPrice() {
		return restrictPrice;
	}

	public void setRestrictPrice(Double restrictPrice) {
		this.restrictPrice = restrictPrice;
	}
	
	public Double getInsuranceRestrictPrice() {
		return insuranceRestrictPrice;
	}

	public void setInsuranceRestrictPrice(Double insuranceRestrictPrice) {
		this.insuranceRestrictPrice = insuranceRestrictPrice;
	}
	

	public Long getChangeFrequency() {
		return changeFrequency;
	}

	public void setChangeFrequency(Long changeFrequency) {
		this.changeFrequency = changeFrequency;
	}
	
	@Length(min=0, max=255, message="换机条件长度不能超过 255 个字符")
	public String getChangeCondition() {
		return changeCondition;
	}

	public void setChangeCondition(String changeCondition) {
		this.changeCondition = changeCondition;
	}
	

	public Double getChangePayment() {
		return changePayment;
	}

	public void setChangePayment(Double changePayment) {
		this.changePayment = changePayment;
	}
	
	public Double getBasisDepreciation() {
		return basisDepreciation;
	}

	public void setBasisDepreciation(Double basisDepreciation) {
		this.basisDepreciation = basisDepreciation;
	}
	
	public Double getMonthlyDepreciation() {
		return monthlyDepreciation;
	}

	public void setMonthlyDepreciation(Double monthlyDepreciation) {
		this.monthlyDepreciation = monthlyDepreciation;
	}
	
	public Double getServeCostPrice() {
		return serveCostPrice;
	}

	public void setServeCostPrice(Double serveCostPrice) {
		this.serveCostPrice = serveCostPrice;
	}
	
	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}
	
	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}
	
	public Double getSuggestedRetailPrice() {
		return suggestedRetailPrice;
	}

	public void setSuggestedRetailPrice(Double suggestedRetailPrice) {
		this.suggestedRetailPrice = suggestedRetailPrice;
	}

	public String getInsuranceProviderId() {
		return insuranceProviderId;
	}

	public void setInsuranceProviderId(String insuranceProviderId) {
		this.insuranceProviderId = insuranceProviderId;
	}

	public String getIntermediaryServiceId() {
		return intermediaryServiceId;
	}

	public void setIntermediaryServiceId(String intermediaryServiceId) {
		this.intermediaryServiceId = intermediaryServiceId;
	}

	public String getExcessRate() {
		return excessRate;
	}

	public void setExcessRate(String excessRate) {
		this.excessRate = excessRate;
	}
	
	public String getFixExcessRate() {
		return fixExcessRate;
	}

	public void setFixExcessRate(String fixExcessRate) {
		this.fixExcessRate = fixExcessRate;
	}
	
	public String getInsureLimit() {
		return insureLimit;
	}

	public void setInsureLimit(String insureLimit) {
		this.insureLimit = insureLimit;
	}
	
	public String getGuaLimt() {
		return guaLimt;
	}

	public void setGuaLimt(String guaLimt) {
		this.guaLimt = guaLimt;
	}
	
	@NotBlank(message="是否立即启用1是0否不能为空")
	@Length(min=0, max=1, message="是否立即启用1是0否长度不能超过 1 个字符")
	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsImmediately() {
		return isImmediately;
	}

	public void setIsImmediately(String isImmediately) {
		this.isImmediately = isImmediately;
	}

	public Long getTakeDay() {
		return takeDay;
	}

	public void setTakeDay(Long takeDay) {
		this.takeDay = takeDay;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Long getValidity() {
		return validity;
	}

	public void setValidity(Long validity) {
		this.validity = validity;
	}

	public String getTerminationChangeFrequency() {
		return terminationChangeFrequency;
	}

	public void setTerminationChangeFrequency(String terminationChangeFrequency) {
		this.terminationChangeFrequency = terminationChangeFrequency;
	}

	public String getTerminationRemainCoverage() {
		return terminationRemainCoverage;
	}

	public void setTerminationRemainCoverage(String terminationRemainCoverage) {
		this.terminationRemainCoverage = terminationRemainCoverage;
	}

	public String getTerminationServeValidity() {
		return terminationServeValidity;
	}

	public void setTerminationServeValidity(String terminationServeValidity) {
		this.terminationServeValidity = terminationServeValidity;
	}

	public String getTerminationMaintenanceFrequency() {
		return terminationMaintenanceFrequency;
	}

	public void setTerminationMaintenanceFrequency(String terminationMaintenanceFrequency) {
		this.terminationMaintenanceFrequency = terminationMaintenanceFrequency;
	}

	public String getTerminationMaintenanceAmount() {
		return terminationMaintenanceAmount;
	}

	public void setTerminationMaintenanceAmount(String terminationMaintenanceAmount) {
		this.terminationMaintenanceAmount = terminationMaintenanceAmount;
	}

	public String getTerminationRecoverFrequency() {
		return terminationRecoverFrequency;
	}

	public void setTerminationRecoverFrequency(String terminationRecoverFrequency) {
		this.terminationRecoverFrequency = terminationRecoverFrequency;
	}
}