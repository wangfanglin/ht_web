/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.web;

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
import com.jeesite.modules.repair.entity.HtRepairOfferPartHistory;
import com.jeesite.modules.repair.service.HtRepairOfferPartHistoryService;

/**
 * 维修工单-待报价-损害部位表Controller
 * @author lichao
 * @version 2020-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/repair/htRepairOfferPartHistory")
public class HtRepairOfferPartHistoryController extends BaseController {

	@Autowired
	private HtRepairOfferPartHistoryService htRepairOfferPartHistoryService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtRepairOfferPartHistory get(String id, boolean isNewRecord) {
		return htRepairOfferPartHistoryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("repair:htRepairOfferPartHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtRepairOfferPartHistory htRepairOfferPartHistory, Model model) {
		model.addAttribute("htRepairOfferPartHistory", htRepairOfferPartHistory);
		return "modules/repair/htRepairOfferPartHistoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("repair:htRepairOfferPartHistory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtRepairOfferPartHistory> listData(HtRepairOfferPartHistory htRepairOfferPartHistory, HttpServletRequest request, HttpServletResponse response) {
		htRepairOfferPartHistory.setPage(new Page<>(request, response));
		Page<HtRepairOfferPartHistory> page = htRepairOfferPartHistoryService.findPage(htRepairOfferPartHistory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("repair:htRepairOfferPartHistory:view")
	@RequestMapping(value = "form")
	public String form(HtRepairOfferPartHistory htRepairOfferPartHistory, Model model) {
		model.addAttribute("htRepairOfferPartHistory", htRepairOfferPartHistory);
		return "modules/repair/htRepairOfferPartHistoryForm";
	}

	/**
	 * 保存维修工单-待报价-损害部位表
	 */
	@RequiresPermissions("repair:htRepairOfferPartHistory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtRepairOfferPartHistory htRepairOfferPartHistory) {
		htRepairOfferPartHistoryService.save(htRepairOfferPartHistory);
		return renderResult(Global.TRUE, text("保存维修工单-待报价-损害部位表成功！"));
	}
	
	/**
	 * 删除维修工单-待报价-损害部位表
	 */
	@RequiresPermissions("repair:htRepairOfferPartHistory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtRepairOfferPartHistory htRepairOfferPartHistory) {
		htRepairOfferPartHistoryService.delete(htRepairOfferPartHistory);
		return renderResult(Global.TRUE, text("删除维修工单-待报价-损害部位表成功！"));
	}
	
}