/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htassemblyunit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.product.entity.ProductInfo;
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
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;

import java.util.List;

/**
 * 维修部件表Controller
 * @author hongmengzhong
 * @version 2020-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/htassemblyunit/htAssemblyUnit")
public class HtAssemblyUnitController extends BaseController {

	@Autowired
	private HtAssemblyUnitService htAssemblyUnitService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtAssemblyUnit get(String id, boolean isNewRecord) {
		return htAssemblyUnitService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htassemblyunit:htAssemblyUnit:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtAssemblyUnit htAssemblyUnit, Model model) {
		model.addAttribute("htAssemblyUnit", htAssemblyUnit);
		return "modules/htassemblyunit/htAssemblyUnitList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htassemblyunit:htAssemblyUnit:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtAssemblyUnit> listData(HtAssemblyUnit htAssemblyUnit, HttpServletRequest request, HttpServletResponse response) {
		htAssemblyUnit.setPage(new Page<>(request, response));
		Page<HtAssemblyUnit> page = htAssemblyUnitService.findPage(htAssemblyUnit);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htassemblyunit:htAssemblyUnit:view")
	@RequestMapping(value = "form")
	public String form(HtAssemblyUnit htAssemblyUnit, Model model) {
		model.addAttribute("htAssemblyUnit", htAssemblyUnit);
		return "modules/htassemblyunit/htAssemblyUnitForm";
	}

	/**
	 * 保存维修部件表
	 */
	@RequiresPermissions("htassemblyunit:htAssemblyUnit:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtAssemblyUnit htAssemblyUnit) {
		htAssemblyUnitService.save(htAssemblyUnit);
		return renderResult(Global.TRUE, text("保存维修部件表成功！"));
	}
	
	/**
	 * 停用维修部件表
	 */
	@RequiresPermissions("htassemblyunit:htAssemblyUnit:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtAssemblyUnit htAssemblyUnit) {
		htAssemblyUnit.setStatus(HtAssemblyUnit.STATUS_DISABLE);
		htAssemblyUnitService.updateStatus(htAssemblyUnit);
		return renderResult(Global.TRUE, text("停用维修部件表成功"));
	}
	
	/**
	 * 启用维修部件表
	 */
	@RequiresPermissions("htassemblyunit:htAssemblyUnit:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtAssemblyUnit htAssemblyUnit) {
		htAssemblyUnit.setStatus(HtAssemblyUnit.STATUS_NORMAL);
		htAssemblyUnitService.updateStatus(htAssemblyUnit);
		return renderResult(Global.TRUE, text("启用维修部件表成功"));
	}
	
	/**
	 * 删除维修部件表
	 */
	@RequiresPermissions("htassemblyunit:htAssemblyUnit:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtAssemblyUnit htAssemblyUnit) {
		htAssemblyUnitService.delete(htAssemblyUnit);
		return renderResult(Global.TRUE, text("删除维修部件表成功！"));
	}


	/**
	 * 查询类目下的部件信息
	 */
	@RequestMapping(value = "findAssemblyByCategory")
	@ResponseBody
	public List<HtAssemblyUnit> findAssemblyByCategory(HtAssemblyUnit htAssemblyUnit) {
		List<HtAssemblyUnit> list = htAssemblyUnitService.findList(htAssemblyUnit);
		return list;
	}
	
}