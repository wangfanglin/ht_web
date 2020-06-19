/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
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
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.service.PolicyDetailService;

import java.util.Map;

/**
 * 保单详情表Controller
 * @author zhaohaifeng
 * @version 2020-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/policy/policyDetail")
public class PolicyDetailController extends BaseController {

	@Autowired
	private PolicyDetailService policyDetailService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PolicyDetail get(String id, boolean isNewRecord) {
		return policyDetailService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("policy:policyDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(PolicyDetail policyDetail, Model model) {
		model.addAttribute("policyDetail", policyDetail);
		return "modules/policy/policyDetailList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("policy:policyDetail:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PolicyDetail> listData(PolicyDetail policyDetail, HttpServletRequest request, HttpServletResponse response) {
		policyDetail.setPage(new Page<>(request, response));
		Page<PolicyDetail> page = policyDetailService.findPage(policyDetail);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("policy:policyDetail:view")
	@RequestMapping(value = "form")
	public String form(PolicyDetail policyDetail, Model model) {
		model.addAttribute("policyDetail", policyDetail);
		return "modules/policy/policyDetailForm";
	}

	/**
	 * 保存保单详情表
	 */
	@RequiresPermissions("policy:policyDetail:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PolicyDetail policyDetail) {
		policyDetailService.save(policyDetail);
		return renderResult(Global.TRUE, text("保存保单详情表成功！"));
	}

	/**
	 * 保单详情信息
	 */
	@PostMapping(value = "policyTab")
	public String policyTab(Model model,String commonFromId) {
		model.addAttribute("commonFromId", commonFromId);
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", commonFromId);
		return "modules/policy/policyTab";
	}

	/**
	 * 保单详情信息 1.0 工单
	 */
	@PostMapping(value = "policyTabOld")
	public String policyTabOld(Model model,String commonFromId) {
		Map<String ,Object> map =policyDetailService.findOrder(commonFromId);
		model.addAttribute("map",map);
		return "modules/policy/policyTabOld";
	}

	/**
	 * 根据id获取维修类型
	 * @param chaildId
	 * @return
	 */
	@RequestMapping({"getPolicyMaintainType"})
	@ResponseBody
	public String getPolicyMaintainType(String chaildId) {
		PolicyDetail policyDetail = policyDetailService.get(chaildId);
		String productType = policyDetail.getProductInfo().getProductType();
		System.out.println(productType);
		if (productType.equals("1")){ //换机
			return "1";
		}else{
			return "2";
		}
	}

}