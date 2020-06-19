/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.intermediary.web;

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
import com.jeesite.modules.intermediary.entity.HtIntermediaryService;
import com.jeesite.modules.intermediary.service.HtIntermediaryServiceService;

/**
 * 中介服务商Controller
 * @author tangchao
 * @version 2020-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/intermediary/htIntermediaryService")
public class HtIntermediaryServiceController extends BaseController {

	@Autowired
	private HtIntermediaryServiceService htIntermediaryServiceService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtIntermediaryService get(String id, boolean isNewRecord) {
		return htIntermediaryServiceService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("intermediary:htIntermediaryService:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtIntermediaryService htIntermediaryService, Model model) {
		model.addAttribute("htIntermediaryService", htIntermediaryService);
		return "modules/intermediary/htIntermediaryServiceList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("intermediary:htIntermediaryService:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtIntermediaryService> listData(HtIntermediaryService htIntermediaryService, HttpServletRequest request, HttpServletResponse response) {
		htIntermediaryService.setPage(new Page<>(request, response));
		Page<HtIntermediaryService> page = htIntermediaryServiceService.findPage(htIntermediaryService);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("intermediary:htIntermediaryService:view")
	@RequestMapping(value = "form")
	public String form(HtIntermediaryService htIntermediaryService, Model model) {
		model.addAttribute("htIntermediaryService", htIntermediaryService);
		return "modules/intermediary/htIntermediaryServiceForm";
	}

	/**
	 * 保存中介服务商
	 */
	@RequiresPermissions("intermediary:htIntermediaryService:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtIntermediaryService htIntermediaryService) {
		htIntermediaryServiceService.save(htIntermediaryService);
		return renderResult(Global.TRUE, text("保存中介服务商成功！"));
	}
	
	/**
	 * 删除中介服务商
	 */
	@RequiresPermissions("intermediary:htIntermediaryService:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtIntermediaryService htIntermediaryService) {
		htIntermediaryServiceService.delete(htIntermediaryService);
		return renderResult(Global.TRUE, text("删除中介服务商成功！"));
	}
	
}