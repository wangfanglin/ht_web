/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.web;

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
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyFormHistory;
import com.jeesite.modules.htwaitapplyform.service.HtWaitApplyFormHistoryService;

/**
 * 待申请工单历史表Controller
 * @author zhaohaifeng
 * @version 2020-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/htwaitapplyform/htWaitApplyFormHistory")
public class HtWaitApplyFormHistoryController extends BaseController {

	@Autowired
	private HtWaitApplyFormHistoryService htWaitApplyFormHistoryService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtWaitApplyFormHistory get(String id, boolean isNewRecord) {
		return htWaitApplyFormHistoryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htwaitapplyform:htWaitApplyFormHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtWaitApplyFormHistory htWaitApplyFormHistory, Model model) {
		model.addAttribute("htWaitApplyFormHistory", htWaitApplyFormHistory);
		return "modules/htwaitapplyform/htWaitApplyFormHistoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htwaitapplyform:htWaitApplyFormHistory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtWaitApplyFormHistory> listData(HtWaitApplyFormHistory htWaitApplyFormHistory, HttpServletRequest request, HttpServletResponse response) {
		htWaitApplyFormHistory.setPage(new Page<>(request, response));
		Page<HtWaitApplyFormHistory> page = htWaitApplyFormHistoryService.findPage(htWaitApplyFormHistory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	//@RequiresPermissions("htwaitapplyform:htWaitApplyFormHistory:view")
	@RequestMapping(value = "form")
	public String form(HtWaitApplyFormHistory htWaitApplyFormHistory, Model model) {
		model.addAttribute("htWaitApplyFormHistory", htWaitApplyFormHistory);
		return "modules/htwaitapplyform/htWaitApplyFormHistoryForm";
	}

	/**
	 * 保存待申请工单历史表
	 */
	@RequiresPermissions("htwaitapplyform:htWaitApplyFormHistory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtWaitApplyFormHistory htWaitApplyFormHistory) {
		htWaitApplyFormHistoryService.save(htWaitApplyFormHistory);
		return renderResult(Global.TRUE, text("保存待申请工单历史表成功！"));
	}
	
}