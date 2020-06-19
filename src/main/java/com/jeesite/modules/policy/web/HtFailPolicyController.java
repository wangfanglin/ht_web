/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.service.OfficeService;
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
import com.jeesite.modules.policy.entity.HtFailPolicy;
import com.jeesite.modules.policy.service.HtFailPolicyService;

import java.util.ArrayList;
import java.util.List;

/**
 * 映射失败保单Controller
 * @author zhaohaifeng
 * @version 2020-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/policy/htFailPolicy")
public class HtFailPolicyController extends BaseController {

	@Autowired
	private HtFailPolicyService htFailPolicyService;
	@Autowired
	private HtBrandInfoService brandInfoService;
	@Autowired
	private ChannelProductInfoService channelProductService;
	@Autowired
	private PolicyInfoService policyInfoService;
	@Autowired
	private HtPhoneModelInfoService phoneModelInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private OfficeService officeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtFailPolicy get(String id, boolean isNewRecord) {
		return htFailPolicyService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("policy:htFailPolicy:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtFailPolicy htFailPolicy, Model model) {
		model.addAttribute("htFailPolicy", htFailPolicy);
		return "modules/policy/htFailPolicyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("policy:htFailPolicy:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtFailPolicy> listData(HtFailPolicy htFailPolicy, HttpServletRequest request, HttpServletResponse response) {
		htFailPolicy.setPage(new Page<>(request, response));
		Page<HtFailPolicy> page = htFailPolicyService.findPage(htFailPolicy);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("policy:htFailPolicy:view")
	@RequestMapping(value = "form")
	public String form(HtFailPolicy htFailPolicy, Model model) {

		String newestChannel = htFailPolicy.getNewestChannel();
		if (!"".equals(newestChannel)&&null!=newestChannel){
			Office office = officeService.get(newestChannel);
			htFailPolicy.setNewestChannelName(office.getOfficeName());
		}

		List<HtBrandInfo> brandList = brandInfoService.getBrandList();
		model.addAttribute("brandList", brandList);

		List<HtPhoneModelInfo> phoneModelList = phoneModelInfoService.findList(new HtPhoneModelInfo());
		model.addAttribute("phoneModelList", phoneModelList);

		List<ChannelProductInfo> list = channelProductService.findList(new ChannelProductInfo());
		model.addAttribute("channelProductList", list);

		model.addAttribute("htFailPolicy", htFailPolicy);
		return "modules/policy/htFailPolicyForm";
	}

	/**
	 * 保存映射失败保单
	 */
	@RequiresPermissions("policy:htFailPolicy:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtFailPolicy htFailPolicy) {
		htFailPolicyService.save(htFailPolicy);
		return renderResult(Global.TRUE, text("保存映射保单成功！"));
	}
	
}