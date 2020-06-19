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
import com.jeesite.modules.bh.entity.BhFormInfo;
import com.jeesite.modules.bh.service.BhFormInfoService;

/**
 * bh_form_infoController
 * @author lichao
 * @version 2020-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/bh/bhFormInfo")
public class BhFormInfoController extends BaseController {

	@Autowired
	private BhFormInfoService bhFormInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BhFormInfo get(String id, boolean isNewRecord) {
		return bhFormInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bh:bhFormInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(BhFormInfo bhFormInfo, Model model) {
		model.addAttribute("bhFormInfo", bhFormInfo);
		return "modules/bh/bhFormInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bh:bhFormInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BhFormInfo> listData(BhFormInfo bhFormInfo, HttpServletRequest request, HttpServletResponse response) {
		bhFormInfo.setPage(new Page<>(request, response));
		Page<BhFormInfo> page = bhFormInfoService.findPage(bhFormInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bh:bhFormInfo:view")
	@RequestMapping(value = "form")
	public String form(BhFormInfo bhFormInfo, Model model) {
		model.addAttribute("bhFormInfo", bhFormInfo);
		return "modules/bh/bhFormInfoForm";
	}

	/**
	 * 保存bh_form_info
	 */
	@RequiresPermissions("bh:bhFormInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BhFormInfo bhFormInfo) {
		bhFormInfoService.save(bhFormInfo);
		return renderResult(Global.TRUE, text("保存bh_form_info成功！"));
	}
	
	/**
	 * 删除bh_form_info
	 */
	@RequiresPermissions("bh:bhFormInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BhFormInfo bhFormInfo) {
		bhFormInfoService.delete(bhFormInfo);
		return renderResult(Global.TRUE, text("删除bh_form_info成功！"));
	}
	
}