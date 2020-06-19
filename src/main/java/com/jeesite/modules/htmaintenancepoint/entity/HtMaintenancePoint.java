/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htmaintenancepoint.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;
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
 * 维修网点表Entity
 * @author hongmengzhong
 * @version 2020-02-22
 */
@Table(name="ht_maintenance_point", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="maintenance_point_name", attrName="maintenancePointName", label="维修网点名称", queryType=QueryType.LIKE),
		@Column(name="contact_one", attrName="contactOne", label="联系人1", isQuery=false),
		@Column(name="contact_number_one", attrName="contactNumberOne", label="联系电话1", isQuery=false),
		@Column(name="contact_two", attrName="contactTwo", label="联系人2", isQuery=false),
		@Column(name="contact_number_two", attrName="contactNumberTwo", label="联系电话2", isQuery=false),
		@Column(name="province", attrName="province", label="省", isQuery=false),
		@Column(name="city", attrName="city", label="市", isQuery=false),
		@Column(name="area", attrName="area", label="区", isQuery=false),
		@Column(name="address", attrName="address", label="地址", isQuery=false),
		@Column(name="longitude_latitude", attrName="longitudeLatitude", label="经纬度", isQuery=false),
		@Column(name="coverage_province", attrName="coverageProvince", label="覆盖省", isQuery=false),
		@Column(name="coverage_city", attrName="coverageCity", label="覆盖市", isQuery=false),
		@Column(name="contract_life_start", attrName="contractLifeStart", label="合同有效开始"),
		@Column(name="contract_life_end", attrName="contractLifeEnd", label="合同有效结束"),
		@Column(name="door_picture", attrName="doorPicture", label="门头照片", isQuery=false),
		@Column(name="whether_order", attrName="whetherOrder", label="是否接单"),
		@Column(name="organization_id", attrName="organizationId", label="机构id"),
		@Column(includeEntity=DataEntity.class),
	},joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Office.class, alias="of",
				on="of.office_code = a.organization_id",
				columns={@Column(includeEntity=Office.class)}),
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= Area.class, alias="areas",
				on="areas.area_code = a.area",
				columns={@Column(includeEntity=Area.class)}),
},  orderBy="a.update_date DESC"
)
public class HtMaintenancePoint extends DataEntity<HtMaintenancePoint> {
	
	private static final long serialVersionUID = 1L;
	private String maintenancePointName;		// 维修网点名称
	private String contactOne;		// 联系人1
	private String contactNumberOne;		// 联系电话1
	private String contactTwo;		// 联系人2
	private String contactNumberTwo;		// 联系电话2
	private String province;		// 省
	private String city;		// 市
	private String area;		// 区
	private String address;		// 地址
	private String longitudeLatitude;		// 经纬度
	private String coverageProvince;		// 覆盖省
	private String coverageCity;		// 覆盖市
	private Date contractLifeStart;		// 合同有效开始
	private Date contractLifeEnd;		// 合同有效结束
	private String doorPicture;		// 门头照片
	private String whetherOrder;		// 是否接单
	private String organizationId;		// 机构id
	private String maintainBrandStr;   //维修品牌
	private String distributorStr;   //渠道商
	private String brandAuthorizingStr;   //授权品牌
	private String serviceModeStr;   //服务方式
	private String maintainQualityStr;   //维修品质
	private String maintainTypeStr;   //维修类型


	//查询条件属性
	private String timeStatus;  //合同状态


	private String phoneBrand;
	private String maintainQuality;
	private String brandSearch;
	private Date contractLifeStartSearch;
	private Date contractLifeEndSearch;
	private String provinceSearch;
	private String channelSerch;

	private Office office;
	private Area areas;


	//@Length(min=0, max=64, message="维修网点名称长度不能超过 64 个字符")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Area getAreas() {
		return areas;
	}

	public void setAreas(Area areas) {
		this.areas = areas;
	}

	@ExcelField(title="合同状态",sort=50,
			attrName = "timeStatus",
			align = ExcelField.Align.LEFT)
	@Length(min=0, max=64, message="合同状态长度不能超过 64 个字符")
	public String getTimeStatus() {
		return timeStatus;
	}

	public void setTimeStatus(String timeStatus) {
		this.timeStatus = timeStatus;
	}

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getMaintainQuality() {
		return maintainQuality;
	}

	public void setMaintainQuality(String maintainQuality) {
		this.maintainQuality = maintainQuality;
	}

	public String getChannelSerch() {
		return channelSerch;
	}

	public void setChannelSerch(String channelSerch) {
		this.channelSerch = channelSerch;
	}

	public String getBrandSearch() {
		return brandSearch;
	}

	public void setBrandSearch(String brandSearch) {
		this.brandSearch = brandSearch;
	}


	public Date getContractLifeStartSearch() {
		return contractLifeStartSearch;
	}

	public void setContractLifeStartSearch(Date contractLifeStartSearch) {
		this.contractLifeStartSearch = contractLifeStartSearch;
	}


	public Date getContractLifeEndSearch() {
		return contractLifeEndSearch;
	}

	public void setContractLifeEndSearch(Date contractLifeEndSearch) {
		this.contractLifeEndSearch = contractLifeEndSearch;
	}

	public String getProvinceSearch() {
		return provinceSearch;
	}

	public void setProvinceSearch(String provinceSearch) {
		this.provinceSearch = provinceSearch;
	}


	@Length(min=0, max=255, message="机构id长度不能超过 255 个字符")
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	//@Length(min=0, max=255, message="产品品牌长度不能超过 255 个字符")
	public String getMaintainBrandStr() {
		return maintainBrandStr;
	}

	public void setMaintainBrandStr(String maintainBrandStr) {
		this.maintainBrandStr = maintainBrandStr;
	}

	public String getDistributorStr() {
		return distributorStr;
	}

	public void setDistributorStr(String distributorStr) {
		this.distributorStr = distributorStr;
	}


	public String getBrandAuthorizingStr() {
		return brandAuthorizingStr;
	}

	public void setBrandAuthorizingStr(String brandAuthorizingStr) {
		this.brandAuthorizingStr = brandAuthorizingStr;
	}

	public String getServiceModeStr() {
		return serviceModeStr;
	}

	public void setServiceModeStr(String serviceModeStr) {
		this.serviceModeStr = serviceModeStr;
	}

	public String getMaintainQualityStr() {
		return maintainQualityStr;
	}

	public void setMaintainQualityStr(String maintainQualityStr) {
		this.maintainQualityStr = maintainQualityStr;
	}

	public String getMaintainTypeStr() {
		return maintainTypeStr;
	}

	public void setMaintainTypeStr(String maintainTypeStr) {
		this.maintainTypeStr = maintainTypeStr;
	}

	public HtMaintenancePoint() {
		this(null);
	}

	public HtMaintenancePoint(String id){
		super(id);
	}
	@ExcelField(title="维修网点名称",sort=10,
			align = ExcelField.Align.LEFT)
	@Length(min=0, max=255, message="维修网点名称长度不能超过 255 个字符")
	public String getMaintenancePointName() {
		return maintenancePointName;
	}

	public void setMaintenancePointName(String maintenancePointName) {
		this.maintenancePointName = maintenancePointName;
	}
	
	@Length(min=0, max=64, message="联系人1长度不能超过 64 个字符")
	public String getContactOne() {
		return contactOne;
	}

	public void setContactOne(String contactOne) {
		this.contactOne = contactOne;
	}
	
	public String getContactNumberOne() {
		return contactNumberOne;
	}

	public void setContactNumberOne(String contactNumberOne) {
		this.contactNumberOne = contactNumberOne;
	}
	
	@Length(min=0, max=64, message="联系人2长度不能超过 64 个字符")
	public String getContactTwo() {
		return contactTwo;
	}

	public void setContactTwo(String contactTwo) {
		this.contactTwo = contactTwo;
	}
	
	public String getContactNumberTwo() {
		return contactNumberTwo;
	}

	public void setContactNumberTwo(String contactNumberTwo) {
		this.contactNumberTwo = contactNumberTwo;
	}
	
	@Length(min=0, max=64, message="省长度不能超过 64 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=64, message="市长度不能超过 64 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=64, message="区长度不能超过 64 个字符")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="地址长度不能超过 255 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLongitudeLatitude() {
		return longitudeLatitude;
	}

	public void setLongitudeLatitude(String longitudeLatitude) {
		this.longitudeLatitude = longitudeLatitude;
	}
	
	//@Length(min=0, max=255, message="覆盖省长度不能超过 255 个字符")
	public String getCoverageProvince() {
		return coverageProvince;
	}

	public void setCoverageProvince(String coverageProvince) {
		this.coverageProvince = coverageProvince;
	}
	
	//@Length(min=0, max=255, message="覆盖市长度不能超过 255 个字符")
	public String getCoverageCity() {
		return coverageCity;
	}

	public void setCoverageCity(String coverageCity) {
		this.coverageCity = coverageCity;
	}
	@ExcelField(title="合同接收时间",sort=30,
			attrName="contractLifeStart",
			align = ExcelField.Align.LEFT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getContractLifeStart() {
		return contractLifeStart;
	}

	public void setContractLifeStart(Date contractLifeStart) {
		this.contractLifeStart = contractLifeStart;
	}
	@ExcelField(title="合同到期时间",sort=40,
			attrName="contractLifeEnd",
			align = ExcelField.Align.LEFT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getContractLifeEnd() {
		return contractLifeEnd;
	}

	public void setContractLifeEnd(Date contractLifeEnd) {
		this.contractLifeEnd = contractLifeEnd;
	}
	
	//@Length(min=0, max=255, message="门头照片长度不能超过 255 个字符")
	public String getDoorPicture() {
		return doorPicture;
	}

	public void setDoorPicture(String doorPicture) {
		this.doorPicture = doorPicture;
	}

	@ExcelField(title="是否接单",sort=20,
			dictType="sys_yes_no",
			align = ExcelField.Align.LEFT)
	@Length(min=0, max=1, message="是否接单长度不能超过 1 个字符")
	public String getWhetherOrder() {
		return whetherOrder;
	}

	public void setWhetherOrder(String whetherOrder) {
		this.whetherOrder = whetherOrder;
	}


}