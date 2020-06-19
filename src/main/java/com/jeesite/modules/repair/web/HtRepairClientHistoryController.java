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
import com.jeesite.modules.repair.entity.HtRepairClientHistory;
import com.jeesite.modules.repair.service.HtRepairClientHistoryService;

/**
 * 维修工单操作历史Controller
 * @author lihao
 * @version 2020-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/repair/htRepairClientHistory")
public class HtRepairClientHistoryController extends BaseController {

	@Autowired
	private HtRepairClientHistoryService htRepairClientHistoryService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtRepairClientHistory get(String id, boolean isNewRecord) {
		return htRepairClientHistoryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("repair:htRepairClientHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtRepairClientHistory htRepairClientHistory, Model model) {
		model.addAttribute("htRepairClientHistory", htRepairClientHistory);
		return "modules/repair/htRepairClientHistoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("repair:htRepairClientHistory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtRepairClientHistory> listData(HtRepairClientHistory htRepairClientHistory, HttpServletRequest request, HttpServletResponse response) {
		htRepairClientHistory.setPage(new Page<>(request, response));
		Page<HtRepairClientHistory> page = htRepairClientHistoryService.findPage(htRepairClientHistory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("repair:htRepairClientHistory:view")
	@RequestMapping(value = "form")
	public String form(HtRepairClientHistory htRepairClientHistory, Model model) {
		model.addAttribute("htRepairClientHistory", htRepairClientHistory);
		return "modules/repair/htRepairClientHistoryForm";
	}

	/**
	 * 保存维修工单操作历史
	 */
	@RequiresPermissions("repair:htRepairClientHistory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtRepairClientHistory htRepairClientHistory) {
		htRepairClientHistoryService.save(htRepairClientHistory);
		return renderResult(Global.TRUE, text("保存维修工单操作历史成功！"));
	}
	
	/**
	 * 删除维修工单操作历史
	 */
	@RequiresPermissions("repair:htRepairClientHistory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtRepairClientHistory htRepairClientHistory) {
		htRepairClientHistoryService.delete(htRepairClientHistory);
		return renderResult(Global.TRUE, text("删除维修工单操作历史成功！"));
	}
	
}