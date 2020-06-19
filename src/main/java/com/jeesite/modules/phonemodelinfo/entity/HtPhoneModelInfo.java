
/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.phonemodelinfo.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

	/**
	 * 机型库表Entity
	 * @author hongmengzhong
	 * @version 2020-02-17
	 */
	@Table(name="ht_phone_model_info", alias="a", columns={
			@Column(name="id", attrName="id", label="id", isPK=true, isQuery=false),
			@Column(name="brand_id", attrName="brandId", label="设备品牌"),
			@Column(name="distribution_id", attrName="distributionId", label="渠道商"),
			@Column(name="model", attrName="model", label="型号"),
			@Column(name="price_low", attrName="priceLow", label="区间最低价"),
			@Column(name="price_high", attrName="priceHigh", label="区间最高价"),
			@Column(name="phone_price", attrName="phonePrice", label="手机价格"),
			@Column(name="screen_price", attrName="screenPrice", label="屏幕价格", isQuery=false),
			@Column(name="sort", attrName="sort", label="排序", isQuery=false),
			@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
			@Column(name="create_by", attrName="createBy", label="create_by", isUpdate=false, isQuery=false),
			@Column(name="update_date", attrName="updateDate", label="修改时间", isQuery=false),
			@Column(name="update_by", attrName="updateBy", label="update_by", isQuery=false),
			@Column(name="remark", attrName="remark", label="备注"),
			@Column(name="status", attrName="status", label="0正常 1删除 2停用", isUpdate=false),
	},
			joinTable={
					@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= HtBrandInfo.class, alias="b",
							on="b.id = a.brand_id",
							columns={@Column(includeEntity=HtBrandInfo.class)}),
					@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Office.class, alias="f",
							on="f.office_code = a.distribution_id",
							columns={@Column(includeEntity=Office.class)}),
			},
			orderBy="a.sort ASC"
	)
	public class HtPhoneModelInfo extends DataEntity<com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo> {

		private static final long serialVersionUID = 1L;
		private String brandId;		// 设备品牌
		private String distributionId;		// 渠道商
		private String model;		// 型号
		private Double priceLow;		// 区间最低价
		private Double priceHigh;		// 区间最高价
		private Double phonePrice;		// 手机价格
		private Double screenPrice;		// 屏幕价格
		private Integer sort;		// 排序
		private String remark;		// 备注
		private HtBrandInfo htBrandInfo;
		private Office office;
		@ExcelField(title="区间最低价",sort=50,
				align = ExcelField.Align.LEFT)
		public Double getPriceLow() {
			return priceLow;
		}
		public void setPriceLow(Double priceLow) {
			this.priceLow = priceLow;
		}

		@ExcelField(title="区间最高价",sort=60,
				align = ExcelField.Align.LEFT)
		public Double getPriceHigh() {
			return priceHigh;
		}
		public void setPriceHigh(Double priceHigh) {
			this.priceHigh = priceHigh;
		}

		@ExcelField(title="手机价格",sort=40,
				align = ExcelField.Align.LEFT)
		public Double getPhonePrice() {
			return phonePrice;
		}
		public void setPhonePrice(Double phonePrice) {
			this.phonePrice = phonePrice;
		}

		@ExcelField(title="备注",sort=70,
				align = ExcelField.Align.LEFT)
		@Length(min=0, max=255, message="备注长度不能超过 255 个字符")
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}

		@Length(min=0, max=64, message="设备品牌长度不能超过 64 个字符")
		public String getBrandId() {
			return brandId;
		}
		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}

		@Length(min=0, max=64, message="渠道商长度不能超过 64 个字符")
		public String getDistributionId() {
			return distributionId;
		}
		public void setDistributionId(String distributionId) {
			this.distributionId = distributionId;
		}

		@ExcelField(title="型号",sort=30,

				align = ExcelField.Align.LEFT)
		@Length(min=0, max=255, message="型号长度不能超过 255 个字符")
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}

		@ExcelField(title="渠道商",sort=20,
				fieldType = Office.class,
				attrName = "office.officeName",
				align = ExcelField.Align.LEFT)
		public Office getOffice() {
			return office;
		}

		public void setOffice(Office office) {
			this.office = office;
		}

		@ExcelField(title="设备品牌",sort=20,
				fieldType = HtBrandInfo.class,
				attrName = "htBrandInfo.name",
				align = ExcelField.Align.LEFT)
		public HtBrandInfo getHtBrandInfo() {
			return htBrandInfo;
		}

		public void setHtBrandInfo(HtBrandInfo htBrandInfo) {
			this.htBrandInfo = htBrandInfo;
		}

		public HtPhoneModelInfo() {
			this(null);
		}

		public HtPhoneModelInfo(String id){
			super(id);
		}

		public Double getScreenPrice() {
			return screenPrice;
		}

		public void setScreenPrice(Double screenPrice) {
			this.screenPrice = screenPrice;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}


	}