/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htmaintenancepoint.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.htbrandmapinfo.entity.HtBrandMapInfo;
import com.jeesite.modules.htbreakdowninfo.entity.HtBreakdownInfo;
import com.jeesite.modules.htbreakdowninfo.service.HtBreakdownInfoService;
import com.jeesite.modules.htmaintenancepoint.dao.HtMaintenancePointDao;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintainPointKpi;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintainPointKpiService;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.util.CommaJointUtil;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维修网点表Controller
 * @author hongmengzhong
 * @version 2020-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/htmaintenancepoint/htMaintenancePoint")
public class HtMaintenancePointController extends BaseController {

	@Autowired
	private HtMaintenancePointService htMaintenancePointService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private HtBreakdownInfoService htBreakdownInfoService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private HtMaintainPointKpiService htMaintainPointKpiService;
	@Autowired
	HtMaintenancePointDao htMaintenancePointDao;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtMaintenancePoint get(String id, boolean isNewRecord) {
		return htMaintenancePointService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtMaintenancePoint htMaintenancePoint, Model model) {
		List<HtBrandInfo> brandInfoList = htBrandInfoService.getBrandList();
		List<Map<String,Object>> provinceList = htBreakdownInfoService.getAreaByLevel("1");
		List<Map<String,Object>> officeList = htBreakdownInfoService.getOfficeList();
		model.addAttribute("officeList", officeList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("brandInfoList", brandInfoList);
		model.addAttribute("htMaintenancePoint", htMaintenancePoint);
		return "modules/htmaintenancepoint/htMaintenancePointList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtMaintenancePoint> listData(HtMaintenancePoint htMaintenancePoint, HttpServletRequest request, HttpServletResponse response) {
		Page<HtMaintenancePoint> page = new Page<>(request, response);
		htMaintenancePoint.setPage(page);
		//htMaintenancePointService.findPage(htMaintenancePoint);
		page.setList(htMaintenancePointService.findListRewrite(htMaintenancePoint));
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:view")
	@RequestMapping(value = "form")
	public String form(HtMaintenancePoint htMaintenancePoint, Model model) {
		initMaintenancePoint(htMaintenancePoint);
		Area area = areaService.get(htMaintenancePoint.getCity());
		htMaintenancePoint.setAreas(area);
		List<HtBrandInfo> brandInfoList = htBrandInfoService.getBrandList();
		List<HtBreakdownInfo> htBreakdownInfoList = htBreakdownInfoService.findList(new HtBreakdownInfo());
		List<Map<String,Object>> provinceList = htBreakdownInfoService.getAreaByLevel("1");
		List<Map<String,Object>> cityList = htBreakdownInfoService.getAreaByLevel("2");
		List<Map<String,Object>> officeList = htBreakdownInfoService.getOfficeList();
		HtMaintainPointKpi htMaintainPointKpi1 = htMaintainPointKpiService.getTableInfo(htMaintenancePoint.getId());
		model.addAttribute("htMaintainPointKpi", htMaintainPointKpi1);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("officeList", officeList);
		model.addAttribute("brandInfoList", brandInfoList);
		model.addAttribute("htBreakdownInfoList", htBreakdownInfoList);
		model.addAttribute("htMaintenancePoint", htMaintenancePoint);
		return "modules/htmaintenancepoint/htMaintenancePointForm";
	}

	/**
	 * 初始化维修网点关联数据
	 * @param htMaintenancePoint
	 */
	private void initMaintenancePoint(HtMaintenancePoint htMaintenancePoint) {
		String maintenancePointId = htMaintenancePoint.getId();
		if (StringUtils.isNotBlank(maintenancePointId)){
			List<String> maintainBrandStrlist = htMaintenancePointService.getStrListByTableName("ht_maintain_brand_middle","brand_id",maintenancePointId);
			List<String> distributorStrlist = htMaintenancePointService.getStrListByTableName("ht_distributor_middle","distributor_id",maintenancePointId);
			List<String> brandAuthorizingStr = htMaintenancePointService.getStrListByTableName("ht_brand_authorizing_middle","authoriz_brand_id",maintenancePointId);
			List<String> serviceModeStrlist = htMaintenancePointService.getStrListByTableName("ht_service_mode_middle","service_mode_id",maintenancePointId);
			List<String> maintainQualityStrlist = htMaintenancePointService.getStrListByTableName("ht_maintain_quality_middle","maintain_quality_id",maintenancePointId);
			List<String> maintainTypeStrlist = htMaintenancePointService.getStrListByTableName("ht_maintain_type_middle","maintain_type_id",maintenancePointId);
			if (maintainBrandStrlist.size()>0)
				htMaintenancePoint.setMaintainBrandStr(CommaJointUtil.commaJointString(maintainBrandStrlist));   //维修品牌
			if (distributorStrlist.size()>0)
			    htMaintenancePoint.setDistributorStr(CommaJointUtil.commaJointString(distributorStrlist));   //渠道商
			if (brandAuthorizingStr.size()>0)
			    htMaintenancePoint.setBrandAuthorizingStr(CommaJointUtil.commaJointString(brandAuthorizingStr));   //授权品牌
			if (serviceModeStrlist.size()>0)
				htMaintenancePoint.setServiceModeStr(CommaJointUtil.commaJointString(serviceModeStrlist));   //服务方式
			if (maintainQualityStrlist.size()>0)
				htMaintenancePoint.setMaintainQualityStr(CommaJointUtil.commaJointString(maintainQualityStrlist));   //维修品质
			if (maintainTypeStrlist.size()>0)
				htMaintenancePoint.setMaintainTypeStr(CommaJointUtil.commaJointString(maintainTypeStrlist));   //维修类型
		}
	}

	/**
	 * 保存维修网点表
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtMaintenancePoint htMaintenancePoint) {
		boolean isTrue = organizationExist(htMaintenancePoint);
		if (!isTrue){
			return renderResult(Global.FALSE,text("机构未选择/已存在当前机构维修网点"));
		}
		if (StringUtils.isNotBlank(htMaintenancePoint.getArea())){
			//获取区县得上级省市code
			String codeStr = htMaintenancePointService.getProvinceCityCode(htMaintenancePoint.getArea());
			String[] codeStrSplit = codeStr.split(",");
			htMaintenancePoint.setProvince(codeStrSplit[2]);
			htMaintenancePoint.setCity(codeStrSplit[3]);
		}
		htMaintenancePointService.save(htMaintenancePoint);
		saveOrUpdatePointMiddle(htMaintenancePoint);
		return renderResult(Global.TRUE, text("保存维修网点表成功！"));
	}

	/**
	 * 判断选择机构是否已存在维修网点
	 * @param htMaintenancePoint
	 * @return
	 */
	private boolean organizationExist(@Validated HtMaintenancePoint htMaintenancePoint) {
		boolean isTrues = true;
		if (StringUtils.isNotBlank(htMaintenancePoint.getOrganizationId())){
			HtMaintenancePoint htMain = htMaintenancePointService.findInfoByJG(htMaintenancePoint.getOrganizationId());
			if (htMain!=null){
				isTrues = false;
			}
			if (!isTrues&&StringUtils.isNotBlank(htMaintenancePoint.getId())){
				HtMaintenancePoint htMaintenancePointUpdate = htMaintenancePointService.get(htMaintenancePoint.getId());
				if (htMaintenancePointUpdate!=null&&htMaintenancePointUpdate.getOrganizationId()!=null&&!"".equals(htMaintenancePointUpdate.getOrganizationId())){
					if (htMaintenancePointUpdate.getOrganizationId().equals(htMaintenancePoint.getOrganizationId())){
						isTrues = true;
					}
				}else{
					isTrues = false;
				}

			}
		}else {
			isTrues = false;
		}
		return isTrues;
	}

	/**
	 * 新增和修改维修网点中间表
	 * @param htMaintenancePoint
	 */
	private void saveOrUpdatePointMiddle(@Validated HtMaintenancePoint htMaintenancePoint) {
		String maintenancePointId = htMaintenancePoint.getId();
		String maintainBrandStr = htMaintenancePoint.getMaintainBrandStr();   //维修品牌
		String distributorStr = htMaintenancePoint.getDistributorStr();   //渠道商
		String brandAuthorizingStr =htMaintenancePoint.getBrandAuthorizingStr();   //授权品牌
		String serviceModeStr = htMaintenancePoint.getServiceModeStr();   //服务方式
		String maintainQualityStr =htMaintenancePoint.getMaintainQualityStr();   //维修品质
		String maintainTypeStr =htMaintenancePoint.getMaintainTypeStr();   //维修类型
		htMaintenancePointService.deleteByTableName("ht_maintain_brand_middle",maintenancePointId);
		htMaintenancePointService.deleteByTableName("ht_distributor_middle",maintenancePointId);
		htMaintenancePointService.deleteByTableName("ht_brand_authorizing_middle",maintenancePointId);
		htMaintenancePointService.deleteByTableName("ht_service_mode_middle",maintenancePointId);
		htMaintenancePointService.deleteByTableName("ht_maintain_quality_middle",maintenancePointId);
		htMaintenancePointService.deleteByTableName("ht_maintain_type_middle",maintenancePointId);
		if (StringUtils.isNotBlank(maintainBrandStr)){
			List<String> maintainBrandList = Arrays.asList(maintainBrandStr.split(","));
			for (String maintainBrandId : maintainBrandList) {
				Map<String,Object> maintainBrandMap = new HashMap<>();
				maintainBrandMap.put("tableName","ht_maintain_brand_middle");
				maintainBrandMap.put("columnName","brand_id");
				maintainBrandMap.put("maintenance_point_id",maintenancePointId);
				maintainBrandMap.put("columnValue",maintainBrandId);
				htMaintenancePointService.saveTableInfo(maintainBrandMap);
			}
		}
		if (StringUtils.isNotBlank(distributorStr)){
			List<String> distributorList = Arrays.asList(distributorStr.split(","));
			for (String distributor : distributorList) {
				Map<String,Object> maintainBrandMap = new HashMap<>();
				maintainBrandMap.put("tableName","ht_distributor_middle");
				maintainBrandMap.put("columnName","distributor_id");
				maintainBrandMap.put("maintenance_point_id",maintenancePointId);
				maintainBrandMap.put("columnValue",distributor);
				htMaintenancePointService.saveTableInfo(maintainBrandMap);
			}
		}
		if (StringUtils.isNotBlank(brandAuthorizingStr)){
			List<String> brandAuthorizingList = Arrays.asList(brandAuthorizingStr.split(","));
			for (String brandAuthorizingId : brandAuthorizingList) {
				Map<String,Object> maintainBrandMap = new HashMap<>();
				maintainBrandMap.put("tableName","ht_brand_authorizing_middle");
				maintainBrandMap.put("columnName","authoriz_brand_id");
				maintainBrandMap.put("maintenance_point_id",maintenancePointId);
				maintainBrandMap.put("columnValue",brandAuthorizingId);
				htMaintenancePointService.saveTableInfo(maintainBrandMap);
			}
		}
		if (StringUtils.isNotBlank(serviceModeStr)){
			List<String> serviceModeList = Arrays.asList(serviceModeStr.split(","));
			for (String serviceModeid : serviceModeList) {
				Map<String,Object> maintainBrandMap = new HashMap<>();
				maintainBrandMap.put("tableName","ht_service_mode_middle");
				maintainBrandMap.put("columnName","service_mode_id");
				maintainBrandMap.put("maintenance_point_id",maintenancePointId);
				maintainBrandMap.put("columnValue",serviceModeid);
				htMaintenancePointService.saveTableInfo(maintainBrandMap);
			}
		}
		if (StringUtils.isNotBlank(maintainQualityStr)){
			List<String> maintainQualityList = Arrays.asList(maintainQualityStr.split(","));
			for (String maintainQualityId : maintainQualityList) {
				Map<String,Object> maintainBrandMap = new HashMap<>();
				maintainBrandMap.put("tableName","ht_maintain_quality_middle");
				maintainBrandMap.put("columnName","maintain_quality_id");
				maintainBrandMap.put("maintenance_point_id",maintenancePointId);
				maintainBrandMap.put("columnValue",maintainQualityId);
				htMaintenancePointService.saveTableInfo(maintainBrandMap);
			}
		}
		if (StringUtils.isNotBlank(maintainTypeStr)){
			List<String> maintainTypeList = Arrays.asList(maintainTypeStr.split(","));
			for (String maintainTypeId : maintainTypeList) {
				Map<String,Object> maintainBrandMap = new HashMap<>();
				maintainBrandMap.put("tableName","ht_maintain_type_middle");
				maintainBrandMap.put("columnName","maintain_type_id");
				maintainBrandMap.put("maintenance_point_id",maintenancePointId);
				maintainBrandMap.put("columnValue",maintainTypeId);
				htMaintenancePointService.saveTableInfo(maintainBrandMap);
			}
		}
	}

	/**
	 * 停用维修网点表
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtMaintenancePoint htMaintenancePoint) {
		htMaintenancePoint.setStatus(HtMaintenancePoint.STATUS_DISABLE);
		htMaintenancePointService.updateStatus(htMaintenancePoint);
		return renderResult(Global.TRUE, text("停用维修网点表成功"));
	}
	
	/**
	 * 启用维修网点表
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtMaintenancePoint htMaintenancePoint) {
		htMaintenancePoint.setStatus(HtMaintenancePoint.STATUS_NORMAL);
		htMaintenancePointService.updateStatus(htMaintenancePoint);
		return renderResult(Global.TRUE, text("启用维修网点表成功"));
	}
	
	/**
	 * 删除维修网点表
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtMaintenancePoint htMaintenancePoint) {
		htMaintenancePointService.delete(htMaintenancePoint);
		return renderResult(Global.TRUE, text("删除维修网点表成功！"));
	}

	@RequestMapping("getAreaLinkage")
	@ResponseBody
	public List<Area> getAreaLinkage(String prdStrs){
		if (StringUtils.isNotBlank(prdStrs)){
			List<String> stringList = Arrays.asList(prdStrs.split(","));
			List<Area> arealist = htBreakdownInfoService.getAreaLinkage(stringList);
			return arealist;
		}
		return null;
	}
	/**
	 * 维修网点导出功能
	 * @return
	 */
	@RequiresPermissions("htmaintenancepoint:htMaintenancePoint:view")
	@RequestMapping("exportBrandMap")
	public void exportBrandMap(HtMaintenancePoint htMaintenancePoint, HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
	  List<HtMaintenancePoint> list=htMaintenancePointDao.getStatus(htMaintenancePoint);
		String fileName = "维修网点列表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		ExcelExport ee = new ExcelExport("维修网点列表", HtMaintenancePoint.class);
		Throwable var8 = null;
		try {
			ee.setDataList(list).write(response, fileName);
		} catch (Throwable var17) {
			var8 = var17;
			throw var17;
		} finally {
			if (ee != null) {
				if (var8 != null) {
					try {
						ee.close();
					} catch (Throwable var16) {
						var8.addSuppressed(var16);
					}
				} else {
					ee.close();
				}
			}
		}
	}
	/**
	 * 维修网点导入功能
	 * @param file
	 * @return
	 */
	@ResponseBody
	@PostMapping({"importData"})
	public String importData(MultipartFile file) {
		try {
			String message = this.htMaintenancePointService.importMaintenancePointData(file);
			return this.renderResult("true", "posfull:" + message);
		} catch (Exception var5) {
			return this.renderResult("false", "posfull:" + var5.getMessage());
		}
	}


	/**
	 * 品牌映射导入功能
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping({"importTemplate"})
	public void importTemplate(HttpServletResponse response) {
		HtMaintenancePoint htMaintenancePoint = new HtMaintenancePoint();
		htMaintenancePoint.setStatus("6");
		List<HtMaintenancePoint> list = htMaintenancePointService.findList(htMaintenancePoint);
		String fileName = "维修网点数据模板.xlsx";
		ExcelExport ee = new ExcelExport("维修网点模板", HtMaintenancePoint.class, ExcelField.Type.IMPORT, new String[0]);
		Throwable var7 = null;
		try {
			ee.setDataList(list).write(response, fileName);
		} catch (Throwable var16) {
			var7 = var16;
			throw var16;
		} finally {
			if (ee != null) {
				if (var7 != null) {
					try {
						ee.close();
					} catch (Throwable var15) {
						var7.addSuppressed(var15);
					}
				} else {
					ee.close();
				}
			}

		}
	}



}