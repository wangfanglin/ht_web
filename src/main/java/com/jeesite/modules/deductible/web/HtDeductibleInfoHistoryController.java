/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.deductible.web;

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
import com.jeesite.modules.deductible.entity.HtDeductibleInfoHistory;
import com.jeesite.modules.deductible.service.HtDeductibleInfoHistoryService;

/**
 * 自付款信息历史表Controller
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/deductible/htDeductibleInfoHistory")
public class HtDeductibleInfoHistoryController extends BaseController {

	@Autowired
	private HtDeductibleInfoHistoryService htDeductibleInfoHistoryService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtDeductibleInfoHistory get(String id, boolean isNewRecord) {
		return htDeductibleInfoHistoryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("deductible:htDeductibleInfoHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtDeductibleInfoHistory htDeductibleInfoHistory, Model model) {
		model.addAttribute("htDeductibleInfoHistory", htDeductibleInfoHistory);
		return "modules/deductible/htDeductibleInfoHistoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("deductible:htDeductibleInfoHistory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtDeductibleInfoHistory> listData(HtDeductibleInfoHistory htDeductibleInfoHistory, HttpServletRequest request, HttpServletResponse response) {
		htDeductibleInfoHistory.setPage(new Page<>(request, response));
		Page<HtDeductibleInfoHistory> page = htDeductibleInfoHistoryService.findPage(htDeductibleInfoHistory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("deductible:htDeductibleInfoHistory:view")
	@RequestMapping(value = "form")
	public String form(HtDeductibleInfoHistory htDeductibleInfoHistory, Model model) {
		model.addAttribute("htDeductibleInfo", htDeductibleInfoHistory);
		return "modules/deductible/htDeductibleInfoHistoryForm";
	}

	/**
	 * 保存自付款信息历史表
	 */
	@RequiresPermissions("deductible:htDeductibleInfoHistory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtDeductibleInfoHistory htDeductibleInfoHistory) {
		htDeductibleInfoHistoryService.save(htDeductibleInfoHistory);
		return renderResult(Global.TRUE, text("保存自付款信息历史表成功！"));
	}
	
}