/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.deductible.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.deductible.dao.HtDeductibleInfoDao;
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
import com.jeesite.modules.deductible.entity.HtDeductibleInfo;
import com.jeesite.modules.deductible.service.HtDeductibleInfoService;

/**
 * 自付款信息表Controller
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/deductible/htDeductibleInfo")
public class HtDeductibleInfoController extends BaseController {

	@Autowired
	private HtDeductibleInfoService htDeductibleInfoService;
	@Autowired
	private HtDeductibleInfoDao deductibleInfoDao;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtDeductibleInfo get(String id, boolean isNewRecord) {
		return htDeductibleInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("deductible:htDeductibleInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtDeductibleInfo htDeductibleInfo, Model model) {
		model.addAttribute("htDeductibleInfo", htDeductibleInfo);
		return "modules/deductible/htDeductibleInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("deductible:htDeductibleInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtDeductibleInfo> listData(HtDeductibleInfo htDeductibleInfo, HttpServletRequest request, HttpServletResponse response) {
		htDeductibleInfo.setPage(new Page<>(request, response));
		Page<HtDeductibleInfo> page = htDeductibleInfoService.findPage(htDeductibleInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(HtDeductibleInfo htDeductibleInfo, Model model) {
		model.addAttribute("htDeductibleInfo", htDeductibleInfo);
		model.addAttribute("commonFromId", htDeductibleInfo.getHtFormInfo().getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htDeductibleInfo.getHtFormInfo().getId());
		return "modules/deductible/htDeductibleInfoForm";
	}

	/**
	 * 保存自付款信息表
	 */
	@RequiresPermissions("deductible:htDeductibleInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtDeductibleInfo htDeductibleInfo) {
		htDeductibleInfo.setIsNewRecord(false);
		htDeductibleInfo.setAffirmStatus("0");
		htDeductibleInfoService.save(htDeductibleInfo);
		return renderResult(Global.TRUE, text("保存自付款信息表成功！"));
	}
	
}