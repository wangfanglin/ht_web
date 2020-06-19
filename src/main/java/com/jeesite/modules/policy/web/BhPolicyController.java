/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.web;

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
import com.jeesite.modules.policy.entity.BhPolicy;
import com.jeesite.modules.policy.service.BhPolicyService;

/**
 * bh_policyController
 * @author tangchao
 * @version 2020-05-07
 */
@Controller
@RequestMapping(value = "${adminPath}/policy/bhPolicy")
public class BhPolicyController extends BaseController {

	@Autowired
	private BhPolicyService bhPolicyService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BhPolicy get(String id, boolean isNewRecord) {
		return bhPolicyService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("policy:bhPolicy:view")
	@RequestMapping(value = {"list", ""})
	public String list(BhPolicy bhPolicy, Model model) {
		model.addAttribute("bhPolicy", bhPolicy);
		return "modules/policy/bhPolicyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("policy:bhPolicy:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BhPolicy> listData(BhPolicy bhPolicy, HttpServletRequest request, HttpServletResponse response) {
		bhPolicy.setPage(new Page<>(request, response));
		Page<BhPolicy> page = bhPolicyService.findPage(bhPolicy);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("policy:bhPolicy:view")
	@RequestMapping(value = "form")
	public String form(BhPolicy bhPolicy, Model model) {
		model.addAttribute("bhPolicy", bhPolicy);
		return "modules/policy/bhPolicyForm";
	}

	/**
	 * 保存bh_policy
	 */
	@RequiresPermissions("policy:bhPolicy:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BhPolicy bhPolicy) {
		bhPolicyService.save(bhPolicy);
		return renderResult(Global.TRUE, text("保存bh_policy成功！"));
	}
	
	/**
	 * 删除bh_policy
	 */
	@RequiresPermissions("policy:bhPolicy:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BhPolicy bhPolicy) {
		bhPolicyService.delete(bhPolicy);
		return renderResult(Global.TRUE, text("删除bh_policy成功！"));
	}
	
}