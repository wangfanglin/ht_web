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
import com.jeesite.modules.channel.entity.ChannelSale;
import com.jeesite.modules.channel.service.ChannelSaleService;

/**
 * 1.0销售渠道Controller
 * @author tangchao
 * @version 2020-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/channelSale")
public class ChannelSaleController extends BaseController {

	@Autowired
	private ChannelSaleService channelSaleService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ChannelSale get(String id, boolean isNewRecord) {
		return channelSaleService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("channel:channelSale:view")
	@RequestMapping(value = {"list", ""})
	public String list(ChannelSale channelSale, Model model) {
		model.addAttribute("channelSale", channelSale);
		return "modules/channel/channelSaleList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("channel:channelSale:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ChannelSale> listData(ChannelSale channelSale, HttpServletRequest request, HttpServletResponse response) {
		channelSale.setPage(new Page<>(request, response));
		Page<ChannelSale> page = channelSaleService.findPage(channelSale);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("channel:channelSale:view")
	@RequestMapping(value = "form")
	public String form(ChannelSale channelSale, Model model) {
		model.addAttribute("channelSale", channelSale);
		return "modules/channel/channelSaleForm";
	}

	/**
	 * 保存1.0销售渠道
	 */
	@RequiresPermissions("channel:channelSale:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ChannelSale channelSale) {
		channelSaleService.save(channelSale);
		return renderResult(Global.TRUE, text("保存1.0销售渠道成功！"));
	}
	
	/**
	 * 删除1.0销售渠道
	 */
	@RequiresPermissions("channel:channelSale:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ChannelSale channelSale) {
		channelSaleService.delete(channelSale);
		return renderResult(Global.TRUE, text("删除1.0销售渠道成功！"));
	}
	
}