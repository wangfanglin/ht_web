/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.channel.web;

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
import com.jeesite.modules.channel.entity.HtPurchasingChannels;
import com.jeesite.modules.channel.service.HtPurchasingChannelsService;

/**
 * 采购渠道表Controller
 * @author tangchao
 * @version 2020-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/htPurchasingChannels")
public class HtPurchasingChannelsController extends BaseController {

	@Autowired
	private HtPurchasingChannelsService htPurchasingChannelsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtPurchasingChannels get(String id, boolean isNewRecord) {
		return htPurchasingChannelsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("channel:htPurchasingChannels:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtPurchasingChannels htPurchasingChannels, Model model) {
		model.addAttribute("htPurchasingChannels", htPurchasingChannels);
		return "modules/channel/htPurchasingChannelsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("channel:htPurchasingChannels:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtPurchasingChannels> listData(HtPurchasingChannels htPurchasingChannels, HttpServletRequest request, HttpServletResponse response) {
		htPurchasingChannels.setPage(new Page<>(request, response));
		Page<HtPurchasingChannels> page = htPurchasingChannelsService.findPage(htPurchasingChannels);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("channel:htPurchasingChannels:view")
	@RequestMapping(value = "form")
	public String form(HtPurchasingChannels htPurchasingChannels, Model model) {
		model.addAttribute("htPurchasingChannels", htPurchasingChannels);
		return "modules/channel/htPurchasingChannelsForm";
	}

	/**
	 * 保存采购渠道表
	 */
	@RequiresPermissions("channel:htPurchasingChannels:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtPurchasingChannels htPurchasingChannels) {
		htPurchasingChannelsService.save(htPurchasingChannels);
		return renderResult(Global.TRUE, text("保存采购渠道成功！"));
	}
	
	/**
	 * 删除采购渠道表
	 */
	@RequiresPermissions("channel:htPurchasingChannels:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtPurchasingChannels htPurchasingChannels) {
		htPurchasingChannelsService.delete(htPurchasingChannels);
		return renderResult(Global.TRUE, text("删除采购渠道成功！"));
	}
	
}