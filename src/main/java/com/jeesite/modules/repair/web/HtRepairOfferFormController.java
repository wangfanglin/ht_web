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
import com.jeesite.modules.repair.entity.HtRepairOfferForm;
import com.jeesite.modules.repair.service.HtRepairOfferFormService;

/**
 * 维修工单-待报价Controller
 * @author lichao
 * @version 2020-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/repair/htRepairOfferForm")
public class HtRepairOfferFormController extends BaseController {

	@Autowired
	private HtRepairOfferFormService htRepairOfferFormService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtRepairOfferForm get(String id, boolean isNewRecord) {
		return htRepairOfferFormService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("repair:htRepairOfferForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtRepairOfferForm htRepairOfferForm, Model model) {
		model.addAttribute("htRepairOfferForm", htRepairOfferForm);
		return "modules/repair/htRepairOfferFormList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("repair:htRepairOfferForm:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtRepairOfferForm> listData(HtRepairOfferForm htRepairOfferForm, HttpServletRequest request, HttpServletResponse response) {
		htRepairOfferForm.setPage(new Page<>(request, response));
		Page<HtRepairOfferForm> page = htRepairOfferFormService.findPage(htRepairOfferForm);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("repair:htRepairOfferForm:view")
	@RequestMapping(value = "form")
	public String form(HtRepairOfferForm htRepairOfferForm, Model model) {
		model.addAttribute("htRepairOfferForm", htRepairOfferForm);
		return "modules/repair/htRepairOfferFormForm";
	}

	/**
	 * 保存维修工单-待报价
	 */
	@RequiresPermissions("repair:htRepairOfferForm:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtRepairOfferForm htRepairOfferForm) {
		htRepairOfferFormService.save(htRepairOfferForm);
		return renderResult(Global.TRUE, text("保存维修工单-待报价成功！"));
	}
	
	/**
	 * 删除维修工单-待报价
	 */
	@RequiresPermissions("repair:htRepairOfferForm:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtRepairOfferForm htRepairOfferForm) {
		htRepairOfferFormService.delete(htRepairOfferForm);
		return renderResult(Global.TRUE, text("删除维修工单-待报价成功！"));
	}
	
}