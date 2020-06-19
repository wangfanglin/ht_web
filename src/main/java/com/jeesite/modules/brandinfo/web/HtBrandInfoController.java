/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.brandinfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.product.entity.ChannelProductInfo;
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
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;

/**
 * 设备品牌表Controller
 * @author hongmengzhong
 * @version 2020-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/brandinfo/htBrandInfo")
public class HtBrandInfoController extends BaseController {

	@Autowired
	private HtBrandInfoService htBrandInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtBrandInfo get(String id, boolean isNewRecord) {
		return htBrandInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("brandinfo:htBrandInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtBrandInfo htBrandInfo, Model model) {
		model.addAttribute("htBrandInfo", htBrandInfo);
		return "modules/brandinfo/htBrandInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("brandinfo:htBrandInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtBrandInfo> listData(HtBrandInfo htBrandInfo, HttpServletRequest request, HttpServletResponse response) {
		htBrandInfo.setPage(new Page<>(request, response));
		Page<HtBrandInfo> page = htBrandInfoService.findPage(htBrandInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("brandinfo:htBrandInfo:view")
	@RequestMapping(value = "form")
	public String form(HtBrandInfo htBrandInfo, Model model) {
		model.addAttribute("htBrandInfo", htBrandInfo);
		return "modules/brandinfo/htBrandInfoForm";
	}

	/**
	 * 保存设备品牌表
	 */
	@RequiresPermissions("brandinfo:htBrandInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtBrandInfo htBrandInfo) {
		try {
			htBrandInfoService.save(htBrandInfo);
		} catch (Exception e) {
			e.printStackTrace();

			HtBrandInfo search = new HtBrandInfo();
			search.setName(htBrandInfo.getName());
			long count = htBrandInfoService.findCount(search);
			if (count==1){
				return renderResult(Global.FALSE, text("品牌输入重复，请重新输入！"));
			}else{
				return renderResult(Global.FALSE, text("保存设备品牌失败！"));
			}
		}
		return renderResult(Global.TRUE, text("保存设备品牌表成功！"));
	}
	
	/**
	 * 停用设备品牌表
	 */
	@RequiresPermissions("brandinfo:htBrandInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtBrandInfo htBrandInfo) {
		htBrandInfo.setStatus(HtBrandInfo.STATUS_DISABLE);
		htBrandInfoService.updateStatus(htBrandInfo);
		return renderResult(Global.TRUE, text("停用设备品牌表成功"));
	}
	
	/**
	 * 启用设备品牌表
	 */
	@RequiresPermissions("brandinfo:htBrandInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtBrandInfo htBrandInfo) {
		htBrandInfo.setStatus(HtBrandInfo.STATUS_NORMAL);
		htBrandInfoService.updateStatus(htBrandInfo);
		return renderResult(Global.TRUE, text("启用设备品牌表成功"));
	}
	
	/**
	 * 删除设备品牌表
	 */
	@RequiresPermissions("brandinfo:htBrandInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtBrandInfo htBrandInfo) {
		htBrandInfoService.delete(htBrandInfo);
		return renderResult(Global.TRUE, text("删除设备品牌表成功！"));
	}
	
}