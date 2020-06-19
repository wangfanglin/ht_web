/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.entity;

import javax.validation.constraints.NotBlank;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 组合产品表Entity
 * @author zhaoheifeng
 * @version 2020-02-18
 */
@Table(name="ht_group_product_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="product_name", attrName="productName", label="product_name", queryType=QueryType.LIKE),
		@Column(name="combination_type", attrName="combinationType", label="组合方式"),
		@Column(name="is_single_validity", attrName="isSingleValidity", label="是否单独设置1是0否"),
		@Column(name="is_start", attrName="isStart", label="是否自立启用1是0否"),
		@Column(name="termination_rules", attrName="terminationRules", label="终止规则  复合规则 0单一规则"),
		@Column(name="min_price", attrName="minPrice", label="销售价格下限"),
		@Column(name="max_price", attrName="maxPrice", label="销售价格上限"),
		@Column(name="coverage", attrName="coverage", label="产品保额"),
		@Column(name="price_flag", attrName="priceFlag", label="保额是否为主商品金额1是0否"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="is_accordance", attrName="isAccordance", label="有效期是否一致"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
		@Column(name="termination_rules_sketch", attrName="terminationRulesSketch", label="简述终止规则"),
		@Column(name="termination_rules_item", attrName="terminationRulesItem", label="终止规则检测项"),}
	, orderBy="a.update_date DESC"
)
public class HtGroupProductInfo extends DataEntity<HtGroupProductInfo> {
	
	private static final long serialVersionUID = 1L;
	private String productName;		// product_name
	private String combinationType;		// 组合方式
	private String isSingleValidity;		// 是否单独设置1是0否
	private String isStart;		// 是否自立启用1是0否
	private String terminationRules;		// 终止规则  复合规则 0单一规则
	private String remark;		// remark
	private String terminationRulesSketch;		// 简述终止规则
	private String terminationRulesItem;// 终止检测项
	private Double minPrice;		// 销售价格下限
	private Double maxPrice;		// 销售价格上限
	private String phoneModelId;		// 机型
	private String priceFlag;		// 保额是否为主商品金额1是0否
	private BigDecimal coverage;  //产品保额
	private String isAccordance;//  有效期是否一致1是0否


	private String weiXiuProductIds;//这个维修产品id
	private String huanJiProductIds;//这个换机产品id
	private String shuJuBaoProductIds;//这个数据保产品id
	private String yanBaoProductIds;//这个延保产品id

    private HtGroupProductChild weiXiuOverResult;//这个维修产品终止规则
    private HtGroupProductChild huanJiOverResult;//这个换机产品终止规则
    private HtGroupProductChild shuJuBaoOverResult;//这个数据保产品终止规则
    private HtGroupProductChild yanBaoOverResult;//这个延保产品终止规则

	//出现不是单独配置的情况会出现的字段
	private String isImmediately;		// 是否购买立即生效1是0否
	private Long   takeDay;		// 购买。。。天后生效
	private Long   validity;		// 有效期


	private String weiXiuName;		// 维修产品名称
	private String huanJiName;		// 换新产品名称
	private String shuJuBaoName;	// 数据保产品名称
	private String yanBaoName;		// 延保产品名称

	public String getIsAccordance() {
		return isAccordance;
	}

	public void setIsAccordance(String isAccordance) {
		this.isAccordance = isAccordance;
	}

	public String getPriceFlag() {
		return priceFlag;
	}

	public void setPriceFlag(String priceFlag) {
		this.priceFlag = priceFlag;
	}

	public BigDecimal getCoverage() {
		return coverage;
	}

	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
	}

	public String getPhoneModelId() {
		return phoneModelId;
	}

	public void setPhoneModelId(String phoneModelId) {
		this.phoneModelId = phoneModelId;
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

	public String getTerminationRulesItem() {
		return terminationRulesItem;
	}

	public void setTerminationRulesItem(String terminationRulesItem) {
		this.terminationRulesItem = terminationRulesItem;
	}

	public String getWeiXiuName() {
		return weiXiuName;
	}

	public void setWeiXiuName(String weiXiuName) {
		this.weiXiuName = weiXiuName;
	}

	public String getHuanJiName() {
		return huanJiName;
	}

	public void setHuanJiName(String huanJiName) {
		this.huanJiName = huanJiName;
	}

	public String getShuJuBaoName() {
		return shuJuBaoName;
	}

	public void setShuJuBaoName(String shuJuBaoName) {
		this.shuJuBaoName = shuJuBaoName;
	}

	public String getYanBaoName() {
		return yanBaoName;
	}

	public void setYanBaoName(String yanBaoName) {
		this.yanBaoName = yanBaoName;
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

	public Long getValidity() {
		return validity;
	}

	public void setValidity(Long validity) {
		this.validity = validity;
	}

	public HtGroupProductChild getWeiXiuOverResult() {
        return weiXiuOverResult;
    }

    public void setWeiXiuOverResult(HtGroupProductChild weiXiuOverResult) {
        this.weiXiuOverResult = weiXiuOverResult;
    }

    public HtGroupProductChild getHuanJiOverResult() {
        return huanJiOverResult;
    }

    public void setHuanJiOverResult(HtGroupProductChild huanJiOverResult) {
        this.huanJiOverResult = huanJiOverResult;
    }

    public HtGroupProductChild getShuJuBaoOverResult() {
        return shuJuBaoOverResult;
    }

    public void setShuJuBaoOverResult(HtGroupProductChild shuJuBaoOverResult) {
        this.shuJuBaoOverResult = shuJuBaoOverResult;
    }

    public HtGroupProductChild getYanBaoOverResult() {
        return yanBaoOverResult;
    }

    public void setYanBaoOverResult(HtGroupProductChild yanBaoOverResult) {
        this.yanBaoOverResult = yanBaoOverResult;
    }

    public String getWeiXiuProductIds() {
        return weiXiuProductIds;
    }

    public void setWeiXiuProductIds(String weiXiuProductIds) {
        this.weiXiuProductIds = weiXiuProductIds;
    }

    public String getHuanJiProductIds() {
        return huanJiProductIds;
    }

    public void setHuanJiProductIds(String huanJiProductIds) {
        this.huanJiProductIds = huanJiProductIds;
    }

    public String getShuJuBaoProductIds() {
        return shuJuBaoProductIds;
    }

    public void setShuJuBaoProductIds(String shuJuBaoProductIds) {
        this.shuJuBaoProductIds = shuJuBaoProductIds;
    }

    public String getYanBaoProductIds() {
        return yanBaoProductIds;
    }

    public void setYanBaoProductIds(String yanBaoProductIds) {
        this.yanBaoProductIds = yanBaoProductIds;
    }


    public HtGroupProductInfo() {
		this(null);
	}

	public HtGroupProductInfo(String id){
		super(id);
	}


	public String getCombinationType() {
		return combinationType;
	}

	public void setCombinationType(String combinationType) {
		this.combinationType = combinationType;
	}

	@NotBlank(message="product_name不能为空")
	@Length(min=0, max=255, message="product_name长度不能超过 255 个字符")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	@NotBlank(message="是否单独设置1是0否不能为空")
	@Length(min=0, max=1, message="是否单独设置1是0否长度不能超过 1 个字符")
	public String getIsSingleValidity() {
		return isSingleValidity;
	}

	public void setIsSingleValidity(String isSingleValidity) {
		this.isSingleValidity = isSingleValidity;
	}
	
	@NotBlank(message="是否自立启用1是0否不能为空")
	@Length(min=0, max=1, message="是否自立启用1是0否长度不能超过 1 个字符")
	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	
	@NotBlank(message="终止规则  复合规则 0单一规则不能为空")
	@Length(min=0, max=1, message="终止规则  复合规则 0单一规则长度不能超过 1 个字符")
	public String getTerminationRules() {
		return terminationRules;
	}

	public void setTerminationRules(String terminationRules) {
		this.terminationRules = terminationRules;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getTerminationRulesSketch() {
		return terminationRulesSketch;
	}

	public void setTerminationRulesSketch(String terminationRulesSketch) {
		this.terminationRulesSketch = terminationRulesSketch;
	}
	
}