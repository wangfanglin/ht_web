/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.log.web;

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
import com.jeesite.modules.log.entity.ThirdInterfaceLog;
import com.jeesite.modules.log.service.ThirdInterfaceLogService;

/**
 * 第三方接口调用日志Controller
 * @author tangchao
 * @version 2020-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/log/thirdInterfaceLog")
public class ThirdInterfaceLogController extends BaseController {

	@Autowired
	private ThirdInterfaceLogService thirdInterfaceLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ThirdInterfaceLog get(String id, boolean isNewRecord) {
		return thirdInterfaceLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("log:thirdInterfaceLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(ThirdInterfaceLog thirdInterfaceLog, Model model) {
		model.addAttribute("thirdInterfaceLog", thirdInterfaceLog);
		return "modules/log/thirdInterfaceLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("log:thirdInterfaceLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ThirdInterfaceLog> listData(ThirdInterfaceLog thirdInterfaceLog, HttpServletRequest request, HttpServletResponse response) {
		thirdInterfaceLog.setPage(new Page<>(request, response));
		Page<ThirdInterfaceLog> page = thirdInterfaceLogService.findPage(thirdInterfaceLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("log:thirdInterfaceLog:view")
	@RequestMapping(value = "form")
	public String form(ThirdInterfaceLog thirdInterfaceLog, Model model) {
		model.addAttribute("thirdInterfaceLog", thirdInterfaceLog);
		return "modules/log/thirdInterfaceLogForm";
	}

	/**
	 * 保存第三方接口调用日志
	 */
	@RequiresPermissions("log:thirdInterfaceLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ThirdInterfaceLog thirdInterfaceLog) {
		thirdInterfaceLogService.save(thirdInterfaceLog);
		return renderResult(Global.TRUE, text("保存第三方接口调用日志成功！"));
	}
	
	/**
	 * 删除第三方接口调用日志
	 */
	@RequiresPermissions("log:thirdInterfaceLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ThirdInterfaceLog thirdInterfaceLog) {
		thirdInterfaceLogService.delete(thirdInterfaceLog);
		return renderResult(Global.TRUE, text("删除第三方接口调用日志成功！"));
	}
	
}