/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;
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
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;

import java.util.List;

/**
 * 工单主表Controller
 * @author lichao
 * @version 2020-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/forminfo/htFormInfo")
public class HtFormInfoController extends BaseController {

	@Autowired
	private HtFormInfoService htFormInfoService;





	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtFormInfo get(String id, boolean isNewRecord) {
		return htFormInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("forminfo:htFormInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtFormInfo htFormInfo, Model model) {
		model.addAttribute("htFormInfo", htFormInfo);
		return "modules/forminfo/htFormInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("forminfo:htFormInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtFormInfo> listData(HtFormInfo htFormInfo, HttpServletRequest request, HttpServletResponse response) {
		htFormInfo.setPage(new Page<>(request, response));
		Page<HtFormInfo> page = htFormInfoService.findPage(htFormInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
//	@RequiresPermissions("forminfo:htFormInfo:view")
	@RequestMapping(value = "form")
	public String form(HtFormInfo htFormInfo, Model model) {
		htFormInfo.setPolicyInfo(new PolicyInfo("1232217095095058432"));

		model.addAttribute("htFormInfo", htFormInfo);

		return "modules/forminfo/htFormInfoForm";
	}

	/**
	 * 保存工单主表
	 */
	@RequiresPermissions("forminfo:htFormInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtFormInfo htFormInfo) {
		//测试代码
		if(htFormInfo.getIsNewRecord()){
			htFormInfo.setFormStatus(FormStatus.LP_DCL);
			htFormInfo.setManageStatus(ManageStatus.LP_ZLYGX_DLX);
		}
		//测试代码

		htFormInfoService.save(htFormInfo);
		return renderResult(Global.TRUE, text("保存工单主表成功！"));
	}
	
	/**
	 * 删除工单主表
	 */
	@RequiresPermissions("forminfo:htFormInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtFormInfo htFormInfo) {
		htFormInfoService.delete(htFormInfo);
		return renderResult(Global.TRUE, text("删除工单主表成功！"));
	}

	/**
	 * 查询激活工单列表
	 */
	@RequestMapping(value = "putUpList")
	public String putUplist(HtFormInfo htFormInfo, Model model) {
		model.addAttribute("htFormInfo", htFormInfo);
		return "modules/forminfo/htFormInfoPutUpList";
	}

	/**
	 * 查询激活工单列表数据
	 */
	@RequestMapping(value = "putUpListData")
	@ResponseBody
	public Page<HtFormInfo> putUpListData(HtFormInfo htFormInfo, HttpServletRequest request, HttpServletResponse response) {
		Page<HtFormInfo> page = new Page<>(request,response);
		int pageNo = page.getPageNo() - 1;
		int pageSize = page.getPageSize();
		List<HtFormInfo> list = htFormInfoService.findputUpPage(htFormInfo,pageNo,pageSize);
		page.setList(list);
		return page;
	}


	/**
	 * 查看激活工单
	 */
	@RequestMapping(value = "putUpForm")
	public String putUpForm(HtFormInfo htFormInfo, Model model) {
		model.addAttribute("commonFromId", htFormInfo.getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htFormInfo.getId());

		model.addAttribute("htFormInfo", htFormInfo);

		return "modules/forminfo/htFormInfoPutUpForm";
	}
	/**
	 * 保存工单主表
	 */
	@PostMapping(value = "upDatePutUp")
	@ResponseBody
	public String upDatePutUp(HtFormInfo htFormInfo) {
		htFormInfoService.formStart(htFormInfo.getId());
		return renderResult(Global.TRUE, text("激活工单成功！"));
	}

	
}