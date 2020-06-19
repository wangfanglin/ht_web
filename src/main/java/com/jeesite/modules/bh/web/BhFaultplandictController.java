/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bh.web;

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
import com.jeesite.modules.bh.entity.BhFaultplandict;
import com.jeesite.modules.bh.service.BhFaultplandictService;

/**
 * 渤海方案库Controller
 * @author lichao
 * @version 2020-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/bh/bhFaultplandict")
public class BhFaultplandictController extends BaseController {

	@Autowired
	private BhFaultplandictService bhFaultplandictService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BhFaultplandict get(String id, boolean isNewRecord) {
		return bhFaultplandictService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bh:bhFaultplandict:view")
	@RequestMapping(value = {"list", ""})
	public String list(BhFaultplandict bhFaultplandict, Model model) {
		model.addAttribute("bhFaultplandict", bhFaultplandict);
		return "modules/bh/bhFaultplandictList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bh:bhFaultplandict:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BhFaultplandict> listData(BhFaultplandict bhFaultplandict, HttpServletRequest request, HttpServletResponse response) {
		bhFaultplandict.setPage(new Page<>(request, response));
		Page<BhFaultplandict> page = bhFaultplandictService.findPage(bhFaultplandict);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bh:bhFaultplandict:view")
	@RequestMapping(value = "form")
	public String form(BhFaultplandict bhFaultplandict, Model model) {
		model.addAttribute("bhFaultplandict", bhFaultplandict);
		return "modules/bh/bhFaultplandictForm";
	}

	/**
	 * 保存渤海方案库
	 */
	@RequiresPermissions("bh:bhFaultplandict:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BhFaultplandict bhFaultplandict) {
		bhFaultplandictService.save(bhFaultplandict);
		return renderResult(Global.TRUE, text("保存渤海方案库成功！"));
	}
	
	/**
	 * 删除渤海方案库
	 */
	@RequiresPermissions("bh:bhFaultplandict:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BhFaultplandict bhFaultplandict) {
		bhFaultplandictService.delete(bhFaultplandict);
		return renderResult(Global.TRUE, text("删除渤海方案库成功！"));
	}
	
}