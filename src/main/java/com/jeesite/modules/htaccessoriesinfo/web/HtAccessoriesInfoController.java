/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htaccessoriesinfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.htaccessoriesinfo.dao.HtAccessoriesPhoneBrandDao;
import com.jeesite.modules.htaccessoriesinfo.dao.HtAccessoriesPhoneModelDao;
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesPhoneBrand;
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesPhoneModel;
import com.jeesite.modules.htaccessoriesinfo.service.HtAccessoriesPhoneBrandService;
import com.jeesite.modules.htaccessoriesinfo.service.HtAccessoriesPhoneModelService;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import lombok.val;
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
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesInfo;
import com.jeesite.modules.htaccessoriesinfo.service.HtAccessoriesInfoService;

import java.util.Arrays;
import java.util.List;

/**
 * 配件表Controller
 * @author hongmengzhong
 * @version 2020-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/htaccessoriesinfo/htAccessoriesInfo")
public class HtAccessoriesInfoController extends BaseController {

	@Autowired
	private HtAccessoriesInfoService htAccessoriesInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private HtPhoneModelInfoService htPhoneModelInfoService;
	@Autowired
	private HtAssemblyUnitService htAssemblyUnitService;
	@Autowired
	private HtAccessoriesPhoneBrandService htAccessoriesPhoneBrandService;
	@Autowired
	private HtAccessoriesPhoneModelService htAccessoriesPhoneModelService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtAccessoriesInfo get(String id, boolean isNewRecord) {
		return htAccessoriesInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htaccessoriesinfo:htAccessoriesInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtAccessoriesInfo htAccessoriesInfo, Model model) {
		model.addAttribute("htAccessoriesInfo", htAccessoriesInfo);
		return "modules/htaccessoriesinfo/htAccessoriesInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htaccessoriesinfo:htAccessoriesInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtAccessoriesInfo> listData(HtAccessoriesInfo htAccessoriesInfo, HttpServletRequest request, HttpServletResponse response) {
		Page<HtAccessoriesInfo> page =  new Page<>(request, response);
		htAccessoriesInfo.setPage(page);
		List<HtAccessoriesInfo> list = htAccessoriesInfoService.getAccessoriesInfoList(htAccessoriesInfo);
		page.setList(list);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htaccessoriesinfo:htAccessoriesInfo:view")
	@RequestMapping(value = "form")
	public String form(HtAccessoriesInfo htAccessoriesInfo, Model model) {
		if (htAccessoriesInfo.getPrice()!=null){
			htAccessoriesInfo.setPrice(htAccessoriesInfo.getPrice()/100);
		}
		List<HtBrandInfo> brandList = htBrandInfoService.getBrandList();
		List<HtPhoneModelInfo> phoneModelInfoList = htPhoneModelInfoService.findList(new HtPhoneModelInfo());
		List<HtAssemblyUnit> htAssemblyUnitList = htAssemblyUnitService.findList(new HtAssemblyUnit());
		String brandStr = htAccessoriesPhoneBrandService.getBrandStr(htAccessoriesInfo.getId());
		String modelStr = htAccessoriesPhoneModelService.getModelStr(htAccessoriesInfo.getId());
		htAccessoriesInfo.setBrandStr(brandStr);
		htAccessoriesInfo.setPhoneModelStr(modelStr);
		model.addAttribute("brandList", brandList);
		model.addAttribute("phoneModelInfoList", phoneModelInfoList);
		model.addAttribute("htAssemblyUnitList", htAssemblyUnitList);
		model.addAttribute("htAccessoriesInfo", htAccessoriesInfo);
		return "modules/htaccessoriesinfo/htAccessoriesInfoForm";
	}

	/**
	 * 保存配件表
	 */
	@RequiresPermissions("htaccessoriesinfo:htAccessoriesInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtAccessoriesInfo htAccessoriesInfo) {
		htAccessoriesInfo.setPrice(htAccessoriesInfo.getPrice()*100);
		htAccessoriesInfoService.save(htAccessoriesInfo);
		String accessoriesInfoId = htAccessoriesInfo.getId();
		if (StringUtils.isNotBlank(htAccessoriesInfo.getBrandStr())){
			List<String> brandList = Arrays.asList(htAccessoriesInfo.getBrandStr().split(","));
				htAccessoriesPhoneBrandService.deleteBuAccessId(accessoriesInfoId);
			for (String brandid : brandList) {
				HtAccessoriesPhoneBrand htAccessoriesPhoneBrand = new HtAccessoriesPhoneBrand();
				htAccessoriesPhoneBrand.setAccessoriesInfoId(accessoriesInfoId);
				htAccessoriesPhoneBrand.setBrandId(brandid);
				htAccessoriesPhoneBrandService.save(htAccessoriesPhoneBrand);
			}
		}
		if (StringUtils.isNotBlank(htAccessoriesInfo.getPhoneModelStr())){
			List<String> phoneModelList = Arrays.asList(htAccessoriesInfo.getPhoneModelStr().split(","));
				htAccessoriesPhoneModelService.deleteBuAccessId(accessoriesInfoId);
			for (String phoneModelId : phoneModelList) {
				HtAccessoriesPhoneModel htAccessoriesPhoneModel = new HtAccessoriesPhoneModel();
				htAccessoriesPhoneModel.setAccessoriesInfoId(accessoriesInfoId);
				htAccessoriesPhoneModel.setPhoneModelId(phoneModelId);
				htAccessoriesPhoneModelService.save(htAccessoriesPhoneModel);
			}
		}

		return renderResult(Global.TRUE, text("保存配件表成功！"));
	}
	
	/**
	 * 停用配件表
	 */
	@RequiresPermissions("htaccessoriesinfo:htAccessoriesInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtAccessoriesInfo htAccessoriesInfo) {
		htAccessoriesInfo.setStatus(HtAccessoriesInfo.STATUS_DISABLE);
		htAccessoriesInfoService.updateStatus(htAccessoriesInfo);
		return renderResult(Global.TRUE, text("停用配件表成功"));
	}
	
	/**
	 * 启用配件表
	 */
	@RequiresPermissions("htaccessoriesinfo:htAccessoriesInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtAccessoriesInfo htAccessoriesInfo) {
		htAccessoriesInfo.setStatus(HtAccessoriesInfo.STATUS_NORMAL);
		htAccessoriesInfoService.updateStatus(htAccessoriesInfo);
		return renderResult(Global.TRUE, text("启用配件表成功"));
	}
	
	/**
	 * 删除配件表
	 */
	@RequiresPermissions("htaccessoriesinfo:htAccessoriesInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtAccessoriesInfo htAccessoriesInfo) {
		htAccessoriesInfoService.delete(htAccessoriesInfo);
		return renderResult(Global.TRUE, text("删除配件表成功！"));
	}
	
}