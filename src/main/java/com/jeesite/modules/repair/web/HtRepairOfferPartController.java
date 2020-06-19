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
import com.jeesite.modules.repair.entity.HtRepairOfferPart;
import com.jeesite.modules.repair.service.HtRepairOfferPartService;

/**
 * 维修工单-待报价-损害部位表Controller
 * @author lichao
 * @version 2020-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/repair/htRepairOfferPart")
public class HtRepairOfferPartController extends BaseController {

	@Autowired
	private HtRepairOfferPartService htRepairOfferPartService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtRepairOfferPart get(String id, boolean isNewRecord) {
		return htRepairOfferPartService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("repair:htRepairOfferPart:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtRepairOfferPart htRepairOfferPart, Model model) {
		model.addAttribute("htRepairOfferPart", htRepairOfferPart);
		return "modules/repair/htRepairOfferPartList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("repair:htRepairOfferPart:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtRepairOfferPart> listData(HtRepairOfferPart htRepairOfferPart, HttpServletRequest request, HttpServletResponse response) {
		htRepairOfferPart.setPage(new Page<>(request, response));
		Page<HtRepairOfferPart> page = htRepairOfferPartService.findPage(htRepairOfferPart);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("repair:htRepairOfferPart:view")
	@RequestMapping(value = "form")
	public String form(HtRepairOfferPart htRepairOfferPart, Model model) {
		model.addAttribute("htRepairOfferPart", htRepairOfferPart);
		return "modules/repair/htRepairOfferPartForm";
	}

	/**
	 * 保存维修工单-待报价-损害部位表
	 */
	@RequiresPermissions("repair:htRepairOfferPart:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtRepairOfferPart htRepairOfferPart) {
		htRepairOfferPartService.save(htRepairOfferPart);
		return renderResult(Global.TRUE, text("保存维修工单-待报价-损害部位表成功！"));
	}
	
	/**
	 * 删除维修工单-待报价-损害部位表
	 */
	@RequiresPermissions("repair:htRepairOfferPart:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtRepairOfferPart htRepairOfferPart) {
		htRepairOfferPartService.delete(htRepairOfferPart);
		return renderResult(Global.TRUE, text("删除维修工单-待报价-损害部位表成功！"));
	}
	
}