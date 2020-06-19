/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.product.service.HtGroupProductInfoService;
import com.jeesite.modules.sys.service.OfficeService;
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
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 保单Controller
 * @author zhaohaifeng
 * @version 2020-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/policy/policyInfo")
public class PolicyInfoController extends BaseController {

	@Autowired
	private PolicyInfoService policyInfoService;
	@Autowired
    private ChannelProductInfoService channelProductService;
	@Autowired
	private HtBrandInfoService brandInfoService;
	@Autowired
	private HtFormInfoService formInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private HtPhoneModelInfoService htPhoneModelInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PolicyInfo get(String id, boolean isNewRecord) {
		return policyInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("policy:policyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PolicyInfo policyInfo, Model model) {
		model.addAttribute("policyInfo", policyInfo);
		List<HtBrandInfo> list = htBrandInfoService.findList(new HtBrandInfo());
		model.addAttribute("brandList", list);
		return "modules/policy/policyInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("policy:policyInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PolicyInfo> listData(PolicyInfo policyInfo, HttpServletRequest request, HttpServletResponse response) {
		policyInfo.setPage(new Page<>(request, response));
		Page<PolicyInfo> page = policyInfoService.findPage(policyInfo);
        List<PolicyInfo> list = page.getList();
        for (PolicyInfo info : list) {
           policyInfoService.updateUnit(info,"0");
			String strPhoneModel = info.getStrPhoneModel();
			String strPhoneBrand = info.getStrPhoneBrand();
			info.setStrPhoneModel(htPhoneModelInfoService.get(strPhoneModel).getModel());
			info.setStrPhoneBrand(htBrandInfoService.get(strPhoneBrand).getName());

		}
        page.setList(list);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("policy:policyInfo:view")
	@RequestMapping(value = "form")
	public String form(PolicyInfo policy, Model model) {
        PolicyInfo policyInfo = policyInfoService.updateUnit(policy,"0");
        List channelProductList= new ArrayList<> ();
		model.addAttribute("channelProductList", channelProductList);
		model.addAttribute("policyInfo", policyInfo);
		List<HtBrandInfo> brandList = brandInfoService.getBrandList();
		model.addAttribute("brandList", brandList);
		return "modules/policy/policyInfoForm";
	}

    /**
     * 查看表单
     */
    @RequiresPermissions("policy:policyInfo:view")
    @RequestMapping(value = "view")
    public String view(PolicyInfo policy, Model model) {
        PolicyInfo policyInfo = policyInfoService.updateUnit(policy, "0");
		String strPhoneBrand = policy.getStrPhoneBrand();
		String strPhoneModel = policy.getStrPhoneModel();
		if (strPhoneBrand!=null&&!"".equals(strPhoneBrand)){
			HtBrandInfo htBrandInfo = htBrandInfoService.get(strPhoneBrand);
			if (htBrandInfo!=null){policy.setStrPhoneBrand(htBrandInfo.getName()); }

		}
		if (strPhoneModel!=null && !"".equals(strPhoneModel)){
			HtPhoneModelInfo htPhoneModelInfo = htPhoneModelInfoService.get(strPhoneModel);
			if (htPhoneModelInfo!=null){
				policy.setStrPhoneModel(htPhoneModelInfo.getModel());
			}
		}
        model.addAttribute("policyInfo", policyInfo);
        //查出 保单的工单
		HtFormInfo formInfo = new HtFormInfo();
		formInfo.setPolicyInfo(policyInfo);
		model.addAttribute("formInfo", formInfo);
        return "modules/policy/policyInfoView";
    }
	/**
	 * 查看表单
	 */
	@ResponseBody
	@RequestMapping(value = "formData")
	public Page<HtFormInfo> formData( HtFormInfo formInfo, HttpServletRequest request, HttpServletResponse response) {
		Page<HtFormInfo> page = new Page<>(request, response);
		formInfo.setPage(page);
		List<HtFormInfo> list = formInfoService.findPageByPolicyId(formInfo);
		page.setList(list);
		return page;
	}
    /**
     * 1.0历史表单
     */
    @ResponseBody
    @RequestMapping(value = "oldFormData")
    public Page<HtFormInfo> oldFormData( HtFormInfo formInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<HtFormInfo> page = new Page<>(request, response);
        formInfo.setPage(page);
        List<HtFormInfo> list = formInfoService.findOldFormByPolicyId(formInfo);
        page.setList(list);
        return page;
    }


    /**
     * 根据渠道商查询渠道产品
     */
    @RequestMapping(value = "findChannelProduct")
    @ResponseBody
    public HashMap<Object, Object> findChannelProduct(String distributionId) {
//		//渠道产品信息带过去
        HashMap<Object, Object> map = null;
        try {
            ChannelProductInfo channelProduct = new ChannelProductInfo();
            channelProduct.setDistributionId(distributionId);
            channelProduct.setStatus("0");
            List<ChannelProductInfo> list = channelProductService.findList(channelProduct);
            map = MapUtils.newHashMap();
            map.put("msg","调用成功");
            map.put("status","success");
            map.put("data",list);
            return map;
        } catch (Exception e) {
           logger.error(e.getMessage());
            map.put("msg","调用失败");
            map.put("status","error");
            return map;
        }
    }


	/**
	 * 保存保单
	 */
	@RequiresPermissions("policy:policyInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PolicyInfo policyInfo) {
		policyInfo.setFromtype("0");
		policyInfoService.save(policyInfo);
		return renderResult(Global.TRUE, text("保存保单成功！"));
	}
	
	/**
	 * 停用保单
	 */
	@RequiresPermissions("policy:policyInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(PolicyInfo policyInfo) {
		policyInfo.setStatus(PolicyInfo.STATUS_DISABLE);
		policyInfoService.updateStatus(policyInfo);
		return renderResult(Global.TRUE, text("停用保单成功"));
	}
	
	/**
	 * 启用保单
	 */
	@RequiresPermissions("policy:policyInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(PolicyInfo policyInfo) {
		policyInfo.setStatus(PolicyInfo.STATUS_NORMAL);
		policyInfoService.updateStatus(policyInfo);
		return renderResult(Global.TRUE, text("启用保单成功"));
	}
	
	/**
	 * 删除保单
	 */
	@RequiresPermissions("policy:policyInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(PolicyInfo policyInfo) {
		policyInfoService.delete(policyInfo);
		return renderResult(Global.TRUE, text("删除保单成功！"));
	}
	
}