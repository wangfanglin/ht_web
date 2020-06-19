/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 1.0产品类型表Entity
 * @author tangchao
 * @version 2020-05-26
 */
@Table(name="channel_protype", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="pro_code", attrName="proCode", label="产品编码"),
		@Column(name="useful_life", attrName="usefulLife", label="期限"),
		@Column(name="pro_name", attrName="proName", label="产品名称", queryType=QueryType.LIKE),
		@Column(name="gua_type", attrName="guaType", label="保障类容"),
		@Column(name="excess_rate", attrName="excessRate", label="免赔率"),
		@Column(name="gua_limt", attrName="guaLimt", label="系统限额，原保障限额"),
		@Column(name="gua_limt_new", attrName="guaLimtNew", label="gua_limt_new"),
		@Column(name="insure_limit", attrName="insureLimit", label="投保限额"),
		@Column(name="fix_excess_rate", attrName="fixExcessRate", label="固定免赔额"),
		@Column(name="inst_file_url", attrName="instFileUrl", label="服务说明文档路径"),
		@Column(name="inst_summary", attrName="instSummary", label="服务说明文档摘要"),
		@Column(name="stuff_id", attrName="stuffId", label="所需理赔资料清单"),
		@Column(name="supplier_id", attrName="supplierId", label="保险供应商"),
		@Column(name="insur_expense", attrName="insurExpense", label="保费"),
		@Column(name="repair_part_type", attrName="repairPartType", label="维修部件类型，原厂/非原厂"),
		@Column(name="remarks", attrName="remarks", label="remarks", queryType=QueryType.LIKE),
		@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="del_flag"),
	}, orderBy="a.update_date DESC"
)
public class ChannelProtype extends DataEntity<ChannelProtype> {
	
	private static final long serialVersionUID = 1L;
	private String proCode;		// 产品编码
	private String usefulLife;		// 期限
	private String proName;		// 产品名称
	private String guaType;		// 保障类容
	private String excessRate;		// 免赔率
	private String guaLimt;		// 系统限额，原保障限额
	private String guaLimtNew;		// gua_limt_new
	private String insureLimit;		// 投保限额
	private String fixExcessRate;		// 固定免赔额
	private String instFileUrl;		// 服务说明文档路径
	private String instSummary;		// 服务说明文档摘要
	private String stuffId;		// 所需理赔资料清单
	private String supplierId;		// 保险供应商
	private String insurExpense;		// 保费
	private String repairPartType;		// 维修部件类型，原厂/非原厂
	private String delFlag;		// del_flag
	
	public ChannelProtype() {
		this(null);
	}

	public ChannelProtype(String id){
		super(id);
	}
	
	@NotBlank(message="产品编码不能为空")
	@Length(min=0, max=64, message="产品编码长度不能超过 64 个字符")
	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	
	@NotBlank(message="期限不能为空")
	@Length(min=0, max=64, message="期限长度不能超过 64 个字符")
	public String getUsefulLife() {
		return usefulLife;
	}

	public void setUsefulLife(String usefulLife) {
		this.usefulLife = usefulLife;
	}
	
	@NotBlank(message="产品名称不能为空")
	@Length(min=0, max=64, message="产品名称长度不能超过 64 个字符")
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@NotBlank(message="保障类容不能为空")
	@Length(min=0, max=32, message="保障类容长度不能超过 32 个字符")
	public String getGuaType() {
		return guaType;
	}

	public void setGuaType(String guaType) {
		this.guaType = guaType;
	}
	
	@Length(min=0, max=64, message="免赔率长度不能超过 64 个字符")
	public String getExcessRate() {
		return excessRate;
	}

	public void setExcessRate(String excessRate) {
		this.excessRate = excessRate;
	}
	
	@Length(min=0, max=64, message="系统限额，原保障限额长度不能超过 64 个字符")
	public String getGuaLimt() {
		return guaLimt;
	}

	public void setGuaLimt(String guaLimt) {
		this.guaLimt = guaLimt;
	}
	
	@Length(min=0, max=64, message="gua_limt_new长度不能超过 64 个字符")
	public String getGuaLimtNew() {
		return guaLimtNew;
	}

	public void setGuaLimtNew(String guaLimtNew) {
		this.guaLimtNew = guaLimtNew;
	}
	
	@Length(min=0, max=64, message="投保限额长度不能超过 64 个字符")
	public String getInsureLimit() {
		return insureLimit;
	}

	public void setInsureLimit(String insureLimit) {
		this.insureLimit = insureLimit;
	}
	
	@Length(min=0, max=64, message="固定免赔额长度不能超过 64 个字符")
	public String getFixExcessRate() {
		return fixExcessRate;
	}

	public void setFixExcessRate(String fixExcessRate) {
		this.fixExcessRate = fixExcessRate;
	}
	
	@Length(min=0, max=128, message="服务说明文档路径长度不能超过 128 个字符")
	public String getInstFileUrl() {
		return instFileUrl;
	}

	public void setInstFileUrl(String instFileUrl) {
		this.instFileUrl = instFileUrl;
	}
	
	public String getInstSummary() {
		return instSummary;
	}

	public void setInstSummary(String instSummary) {
		this.instSummary = instSummary;
	}
	
	@Length(min=0, max=256, message="所需理赔资料清单长度不能超过 256 个字符")
	public String getStuffId() {
		return stuffId;
	}

	public void setStuffId(String stuffId) {
		this.stuffId = stuffId;
	}
	
	@Length(min=0, max=8, message="保险供应商长度不能超过 8 个字符")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@Length(min=0, max=32, message="保费长度不能超过 32 个字符")
	public String getInsurExpense() {
		return insurExpense;
	}

	public void setInsurExpense(String insurExpense) {
		this.insurExpense = insurExpense;
	}
	
	@Length(min=0, max=2, message="维修部件类型，原厂/非原厂长度不能超过 2 个字符")
	public String getRepairPartType() {
		return repairPartType;
	}

	public void setRepairPartType(String repairPartType) {
		this.repairPartType = repairPartType;
	}
	
	@NotBlank(message="del_flag不能为空")
	@Length(min=0, max=1, message="del_flag长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}