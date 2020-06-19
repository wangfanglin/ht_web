/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.web;

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
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.service.HtGroupProductChildService;

/**
 * 组合产品子表Controller
 * @author zhaofaifeng
 * @version 2020-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/product/htGroupProductChild")
public class HtGroupProductChildController extends BaseController {

	@Autowired
	private HtGroupProductChildService htGroupProductChildService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtGroupProductChild get(String id, boolean isNewRecord) {
		return htGroupProductChildService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:htGroupProductChild:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtGroupProductChild htGroupProductChild, Model model) {
		model.addAttribute("htGroupProductChild", htGroupProductChild);
		return "modules/product/htGroupProductChildList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:htGroupProductChild:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtGroupProductChild> listData(HtGroupProductChild htGroupProductChild, HttpServletRequest request, HttpServletResponse response) {
		htGroupProductChild.setPage(new Page<>(request, response));
		Page<HtGroupProductChild> page = htGroupProductChildService.findPage(htGroupProductChild);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:htGroupProductChild:view")
	@RequestMapping(value = "form")
	public String form(HtGroupProductChild htGroupProductChild, Model model) {
		model.addAttribute("htGroupProductChild", htGroupProductChild);
		return "modules/product/htGroupProductChildForm";
	}

	/**
	 * 保存组合产品子表
	 */
	@RequiresPermissions("product:htGroupProductChild:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtGroupProductChild htGroupProductChild) {
		htGroupProductChildService.save(htGroupProductChild);
		return renderResult(Global.TRUE, text("保存组合产品子表成功！"));
	}
	
	/**
	 * 停用组合产品子表
	 */
	@RequiresPermissions("product:htGroupProductChild:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtGroupProductChild htGroupProductChild) {
		htGroupProductChild.setStatus(HtGroupProductChild.STATUS_DISABLE);
		htGroupProductChildService.updateStatus(htGroupProductChild);
		return renderResult(Global.TRUE, text("停用组合产品子表成功"));
	}
	
	/**
	 * 启用组合产品子表
	 */
	@RequiresPermissions("product:htGroupProductChild:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtGroupProductChild htGroupProductChild) {
		htGroupProductChild.setStatus(HtGroupProductChild.STATUS_NORMAL);
		htGroupProductChildService.updateStatus(htGroupProductChild);
		return renderResult(Global.TRUE, text("启用组合产品子表成功"));
	}
	
	/**
	 * 删除组合产品子表
	 */
	@RequiresPermissions("product:htGroupProductChild:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtGroupProductChild htGroupProductChild) {
		htGroupProductChildService.delete(htGroupProductChild);
		return renderResult(Global.TRUE, text("删除组合产品子表成功！"));
	}
	
}