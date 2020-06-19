/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.user.web;

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
import com.jeesite.modules.user.entity.HtUserInfo;
import com.jeesite.modules.user.service.HtUserInfoService;

/**
 * ht_user_infoController
 * @author lichao
 * @version 2020-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/user/htUserInfo")
public class HtUserInfoController extends BaseController {

	@Autowired
	private HtUserInfoService htUserInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtUserInfo get(String id, boolean isNewRecord) {
		return htUserInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("user:htUserInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtUserInfo htUserInfo, Model model) {
		model.addAttribute("htUserInfo", htUserInfo);
		return "modules/user/htUserInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("user:htUserInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtUserInfo> listData(HtUserInfo htUserInfo, HttpServletRequest request, HttpServletResponse response) {
		htUserInfo.setPage(new Page<>(request, response));
		Page<HtUserInfo> page = htUserInfoService.findPage(htUserInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("user:htUserInfo:view")
	@RequestMapping(value = "form")
	public String form(HtUserInfo htUserInfo, Model model) {
		model.addAttribute("htUserInfo", htUserInfo);
		return "modules/user/htUserInfoForm";
	}

	/**
	 * 保存ht_user_info
	 */
	@RequiresPermissions("user:htUserInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtUserInfo htUserInfo) {
		htUserInfoService.save(htUserInfo);
		return renderResult(Global.TRUE, text("保存ht_user_info成功！"));
	}
	
	/**
	 * 删除ht_user_info
	 */
	@RequiresPermissions("user:htUserInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtUserInfo htUserInfo) {
		htUserInfoService.delete(htUserInfo);
		return renderResult(Global.TRUE, text("删除ht_user_info成功！"));
	}
	
}