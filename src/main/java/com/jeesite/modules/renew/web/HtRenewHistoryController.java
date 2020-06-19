/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.renew.web;

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
import com.jeesite.modules.renew.entity.HtRenewHistory;
import com.jeesite.modules.renew.service.HtRenewHistoryService;

/**
 * 换新工单历史Controller
 * @author lichao
 * @version 2020-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/renew/htRenewHistory")
public class HtRenewHistoryController extends BaseController {

	@Autowired
	private HtRenewHistoryService htRenewHistoryService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtRenewHistory get(String id, boolean isNewRecord) {
		return htRenewHistoryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("renew:htRenewHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtRenewHistory htRenewHistory, Model model) {
		model.addAttribute("htRenewHistory", htRenewHistory);
		return "modules/renew/htRenewHistoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("renew:htRenewHistory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtRenewHistory> listData(HtRenewHistory htRenewHistory, HttpServletRequest request, HttpServletResponse response) {
		htRenewHistory.setPage(new Page<>(request, response));
		Page<HtRenewHistory> page = htRenewHistoryService.findPage(htRenewHistory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("renew:htRenewHistory:view")
	@RequestMapping(value = "form")
	public String form(HtRenewHistory htRenewHistory, Model model) {
		model.addAttribute("htRenewHistory", htRenewHistory);
		return "modules/renew/htRenewHistoryForm";
	}

	/**
	 * 保存换新工单历史
	 */
	@RequiresPermissions("renew:htRenewHistory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtRenewHistory htRenewHistory) {
		htRenewHistoryService.save(htRenewHistory);
		return renderResult(Global.TRUE, text("保存换新工单历史成功！"));
	}
	
	/**
	 * 删除换新工单历史
	 */
	@RequiresPermissions("renew:htRenewHistory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtRenewHistory htRenewHistory) {
		htRenewHistoryService.delete(htRenewHistory);
		return renderResult(Global.TRUE, text("删除换新工单历史成功！"));
	}
	
}