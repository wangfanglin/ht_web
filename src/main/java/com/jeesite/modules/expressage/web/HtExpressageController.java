/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expressage.web;

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
import com.jeesite.modules.expressage.entity.HtExpressage;
import com.jeesite.modules.expressage.service.HtExpressageService;

/**
 * 快递渠道Controller
 * @author tangchao
 * @version 2020-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/expressage/htExpressage")
public class HtExpressageController extends BaseController {

	@Autowired
	private HtExpressageService htExpressageService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtExpressage get(String id, boolean isNewRecord) {
		return htExpressageService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("expressage:htExpressage:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtExpressage htExpressage, Model model) {
		model.addAttribute("htExpressage", htExpressage);
		return "modules/expressage/htExpressageList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("expressage:htExpressage:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtExpressage> listData(HtExpressage htExpressage, HttpServletRequest request, HttpServletResponse response) {
		htExpressage.setPage(new Page<>(request, response));
		Page<HtExpressage> page = htExpressageService.findPage(htExpressage);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("expressage:htExpressage:view")
	@RequestMapping(value = "form")
	public String form(HtExpressage htExpressage, Model model) {
		model.addAttribute("htExpressage", htExpressage);
		return "modules/expressage/htExpressageForm";
	}

	/**
	 * 保存快递渠道
	 */
	@RequiresPermissions("expressage:htExpressage:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtExpressage htExpressage) {
		htExpressageService.save(htExpressage);
		return renderResult(Global.TRUE, text("保存快递渠道成功！"));
	}
	
	/**
	 * 删除快递渠道
	 */
	@RequiresPermissions("expressage:htExpressage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtExpressage htExpressage) {
		htExpressageService.delete(htExpressage);
		return renderResult(Global.TRUE, text("删除快递渠道成功！"));
	}
	
}