/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.service.AreaService;
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
import com.jeesite.modules.receipt.entity.HtReceiptDataHistory;
import com.jeesite.modules.receipt.service.HtReceiptDataHistoryService;

/**
 * 收款人信息历史表Controller
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/receipt/htReceiptDataHistory")
public class HtReceiptDataHistoryController extends BaseController {

	@Autowired
	private HtReceiptDataHistoryService htReceiptDataHistoryService;
	@Autowired
	private AreaService areaService;


	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtReceiptDataHistory get(String id, boolean isNewRecord) {
		return htReceiptDataHistoryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("receipt:htReceiptDataHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtReceiptDataHistory htReceiptDataHistory, Model model) {
		model.addAttribute("htReceiptDataHistory", htReceiptDataHistory);
		return "modules/receipt/htReceiptDataHistoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("receipt:htReceiptDataHistory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtReceiptDataHistory> listData(HtReceiptDataHistory htReceiptDataHistory, HttpServletRequest request, HttpServletResponse response) {
		htReceiptDataHistory.setPage(new Page<>(request, response));
		Page<HtReceiptDataHistory> page = htReceiptDataHistoryService.findPage(htReceiptDataHistory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(HtReceiptDataHistory htReceiptDataHistory, Model model) {
		model.addAttribute("htReceiptData", htReceiptDataHistory);
		String provinceCode = htReceiptDataHistory.getProvinceCode();
		Area area = areaService.get(provinceCode);
		String areaName = area.getAreaName();
		htReceiptDataHistory.setProvinceCode(areaName);
		htReceiptDataHistory.setCityCode(areaService.get(htReceiptDataHistory.getCityCode()).getAreaName());
		htReceiptDataHistory.setDistrictCode(areaService.get(htReceiptDataHistory.getDistrictCode()).getAreaName());
		return "modules/receipt/htReceiptDataHistoryForm";
	}

	/**
	 * 保存收款人信息历史表
	 */
	@RequiresPermissions("receipt:htReceiptDataHistory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtReceiptDataHistory htReceiptDataHistory) {
		htReceiptDataHistoryService.save(htReceiptDataHistory);
		return renderResult(Global.TRUE, text("保存收款人信息历史表成功！"));
	}
	
}