/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htuserapplyinfo.web;

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
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;

/**
 * 用户在线申请表Controller
 * @author tangchao
 * @version 2020-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/htuserapplyinfo/htUserApplyInfo")
public class HtUserApplyInfoController extends BaseController {

	@Autowired
	private HtUserApplyInfoService htUserApplyInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtUserApplyInfo get(String id, boolean isNewRecord) {
		return htUserApplyInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htuserapplyinfo:htUserApplyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtUserApplyInfo htUserApplyInfo, Model model) {
		model.addAttribute("htUserApplyInfo", htUserApplyInfo);
		return "modules/htuserapplyinfo/htUserApplyInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htuserapplyinfo:htUserApplyInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtUserApplyInfo> listData(HtUserApplyInfo htUserApplyInfo, HttpServletRequest request, HttpServletResponse response) {
		htUserApplyInfo.setPage(new Page<>(request, response));
		Page<HtUserApplyInfo> page = htUserApplyInfoService.findPage(htUserApplyInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htuserapplyinfo:htUserApplyInfo:view")
	@RequestMapping(value = "form")
	public String form(HtUserApplyInfo htUserApplyInfo, Model model) {
		model.addAttribute("htUserApplyInfo", htUserApplyInfo);
		return "modules/htuserapplyinfo/htUserApplyInfoForm";
	}

	/**
	 * 保存用户在线申请表
	 */
	@RequiresPermissions("htuserapplyinfo:htUserApplyInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtUserApplyInfo htUserApplyInfo) {
		htUserApplyInfoService.save(htUserApplyInfo);
		return renderResult(Global.TRUE, text("保存用户在线申请表成功！"));
	}
	
	/**
	 * 删除用户在线申请表
	 */
	@RequiresPermissions("htuserapplyinfo:htUserApplyInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtUserApplyInfo htUserApplyInfo) {
		htUserApplyInfoService.delete(htUserApplyInfo);
		return renderResult(Global.TRUE, text("删除用户在线申请表成功！"));
	}
	
}