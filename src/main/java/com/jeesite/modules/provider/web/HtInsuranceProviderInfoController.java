/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.provider.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.provider.entity.HtInsuranceProviderInfo;
import com.jeesite.modules.provider.service.HtInsuranceProviderInfoService;

/**
 * 保险供应商Controller
 * @author tangchao
 * @version 2020-02-21
 */
@Controller
@RequestMapping(value = "${adminPath}/provider/htInsuranceProviderInfo")
public class HtInsuranceProviderInfoController extends BaseController {

	@Autowired
	private HtInsuranceProviderInfoService htInsuranceProviderInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtInsuranceProviderInfo get(String id, boolean isNewRecord) {
		return htInsuranceProviderInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("provider:htInsuranceProviderInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtInsuranceProviderInfo htInsuranceProviderInfo, Model model) {
		model.addAttribute("htInsuranceProviderInfo", htInsuranceProviderInfo);
		return "modules/provider/htInsuranceProviderInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("provider:htInsuranceProviderInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtInsuranceProviderInfo> listData(HtInsuranceProviderInfo htInsuranceProviderInfo, HttpServletRequest request, HttpServletResponse response) {
		htInsuranceProviderInfo.setPage(new Page<>(request, response));
		Page<HtInsuranceProviderInfo> page = htInsuranceProviderInfoService.findPage(htInsuranceProviderInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("provider:htInsuranceProviderInfo:view")
	@RequestMapping(value = "form")
	public String form(HtInsuranceProviderInfo htInsuranceProviderInfo, Model model) {
		model.addAttribute("htInsuranceProviderInfo", htInsuranceProviderInfo);
		return "modules/provider/htInsuranceProviderInfoForm";
	}

	/**
	 * 保存保险供应商
	 */
	@RequiresPermissions("provider:htInsuranceProviderInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtInsuranceProviderInfo htInsuranceProviderInfo) {
		htInsuranceProviderInfoService.save(htInsuranceProviderInfo);
		return renderResult(Global.TRUE, text("保存保险供应商成功！"));
	}
	
	/**
	 * 删除保险供应商
	 */
	@RequiresPermissions("provider:htInsuranceProviderInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtInsuranceProviderInfo htInsuranceProviderInfo) {
		htInsuranceProviderInfoService.delete(htInsuranceProviderInfo);
		return renderResult(Global.TRUE, text("删除保险供应商成功！"));
	}
	
}