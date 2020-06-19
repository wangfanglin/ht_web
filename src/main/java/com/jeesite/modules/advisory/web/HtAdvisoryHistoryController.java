/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.advisory.entity.HtAdvisoryInfo;
import com.jeesite.modules.advisory.service.HtAdvisoryInfoService;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.settlementform.htcalllog.entity.HtCallLog;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.utils.UserUtils;
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
import com.jeesite.modules.advisory.entity.HtAdvisoryHistory;
import com.jeesite.modules.advisory.service.HtAdvisoryHistoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * ht_advisory_historyController
 * @author zhaohaifeng
 * @version 2020-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/advisory/htAdvisoryHistory")
public class HtAdvisoryHistoryController extends BaseController {

	@Autowired
	private HtAdvisoryHistoryService htAdvisoryHistoryService;
	@Autowired
	private HtBrandInfoService brandInfoService;
	@Autowired
	private HtFormInfoService formInfoService;
	@Autowired
	private HtPhoneModelInfoService phoneModelInfoService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private HtCallLogService htCallLogService;
	@Autowired
	private HtAdvisoryInfoService htAdvisoryInfoService;
	@Autowired
	private HtPhoneModelInfoService htPhoneModelInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtAdvisoryHistory get(String id, boolean isNewRecord) {
		return htAdvisoryHistoryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("advisory:htAdvisoryHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtAdvisoryHistory htAdvisoryHistory, Model model) {
		model.addAttribute("htAdvisoryHistory", htAdvisoryHistory);
		return "modules/advisory/htAdvisoryHistoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("advisory:htAdvisoryHistory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtAdvisoryHistory> listData(HtAdvisoryHistory htAdvisoryHistory, HttpServletRequest request, HttpServletResponse response) {
		htAdvisoryHistory.setPage(new Page<>(request, response));
		Page<HtAdvisoryHistory> page = htAdvisoryHistoryService.findPage(htAdvisoryHistory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(HtAdvisoryHistory htAdvisoryHistory, Model model) {
		String callId = htAdvisoryHistory.getCallId();
		HtCallLog callLog = htCallLogService.get(callId);
		model.addAttribute("htCallLog", callLog);

		htAdvisoryHistory = htAdvisoryHistoryService.get(htAdvisoryHistory.getId());
		model.addAttribute("htAdvisoryInfo", htAdvisoryHistory);

		String officeId = htAdvisoryHistory.getOfficeId();
		if (officeId!=null&&!"".equals(officeId)){
			Office office = officeService.get(officeId);
			htAdvisoryHistory.setOfficeId(office.getOfficeName());
		}
		Employee employee = new Employee();
		List<Employee> employeeList = employeeService.findList(employee);
		model.addAttribute("employeeList",employeeList);

		String policyId = htAdvisoryHistory.getPolicyId();
		if (!"".equals(policyId)&&null!=policyId){
			//1是有保单进来的
			//保单的 姓名，联系电话，证件类型 ，证件号码，手机品牌，手机型号，
			HtFormInfo formInfo = formInfoService.get(htAdvisoryHistory.getFormId());
			String strPhoneBrand = formInfo.getPolicyInfo().getStrPhoneBrand();
			String strPhoneModel = formInfo.getPolicyInfo().getStrPhoneModel();
			formInfo.getPolicyInfo().setStrPhoneBrand(brandInfoService.get(strPhoneBrand).getName());
			formInfo.getPolicyInfo().setStrPhoneModel( phoneModelInfoService.get(strPhoneModel).getModel());
			List<HtFormInfo> list = formInfoService.findListByPolicyId(policyId);
			model.addAttribute("list",list);
			model.addAttribute("form",formInfo);
			model.addAttribute("commonFromId",htAdvisoryHistory.getFormId());
            model.addAttribute("commonApply", true);
            model.addAttribute("commonApplyId", htAdvisoryHistory.getFormId());
			model.addAttribute("flag","1");
		}else{
			model.addAttribute("flag","0");
		}
		//手机品牌 型号需要带过去
		List<HtBrandInfo> brandList = brandInfoService.getBrandList();
		model.addAttribute("brandList", brandList);
		model.addAttribute("callPhone", htAdvisoryHistory.getCallPhone());
		//根据type值判断该走进那个历史页面
		//其他部门再次联系时为1  投诉联系页面 为2

		String type = htAdvisoryHistory.getType();
		if ("0".equals(type)){
			return "modules/advisory/htAdvisoryInfoFormRedonly";
		}
		if ("1".equals(type)){
			return "modules/advisory/htAdvisoryFormRedonly";
		}
		if ("2".equals(type)){
			return "modules/advisory/htDutyFormRedonly";
		}
		return null;
	}

	/**
	 * 保存咨询工单历史
	 */
	@RequiresPermissions("advisory:htAdvisoryHistory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtAdvisoryHistory htAdvisoryHistory) {
		htAdvisoryHistoryService.save(htAdvisoryHistory);
		return renderResult(Global.TRUE, text("保存咨询工单历史成功！"));
	}


	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "advForm")
	public String form(HtAdvisoryInfo htAdvisoryInfo, Model model, String flag, String formId) {
		htAdvisoryInfo = htAdvisoryInfoService.get(htAdvisoryInfo.getId());
		String callId = htAdvisoryInfo.getCallId();
		HtCallLog callLog = htCallLogService.get(callId);

		model.addAttribute("htCallLog", callLog);
		String callPhone = htAdvisoryInfo.getCallPhone();
		model.addAttribute("callPhone", callPhone);

		String officeId = htAdvisoryInfo.getOfficeId();
		if (StringUtils.isNotBlank(officeId)){
			Office office = officeService.get(officeId);
			htAdvisoryInfo.setOfficeName(office.getOfficeName());

			Employee employee = new Employee();
			employee.setOffice(new Office(officeId));
			List<Employee> employeeList = employeeService.findList(employee);
			model.addAttribute("employeeList", employeeList);
		}else{
			model.addAttribute("employeeList", new ArrayList<>());
		}

		if ("1".equals(flag)) {
			HtFormInfo formInfo = formInfoService.get(formId);
			if (formInfo != null) {
				String strPhoneBrand = formInfo.getPolicyInfo().getStrPhoneBrand();
				String strPhoneModel = formInfo.getPolicyInfo().getStrPhoneModel();
				formInfo.getPolicyInfo().setStrPhoneBrand(brandInfoService.get(strPhoneBrand).getName());
				formInfo.getPolicyInfo().setStrPhoneModel(phoneModelInfoService.get(strPhoneModel).getModel());
				String policyId = formInfo.getPolicyInfo().getId();

				htAdvisoryInfo.setPolicyId(policyId);
				List<HtFormInfo> list = formInfoService.findListByPolicyId(policyId);
				model.addAttribute("list", list);
				model.addAttribute("form", formInfo);
				model.addAttribute("commonFromId", formId);
				model.addAttribute("commonApply", true);
				model.addAttribute("commonApplyId", formId);
				model.addAttribute("flag", "1");
			} else {
				model.addAttribute("flag", "0");
			}
		} else {
			model.addAttribute("flag", "0");
		}
		model.addAttribute("commonButtonId", formId);
		//手机品牌 型号需要带过去
		List<HtBrandInfo> brandList = brandInfoService.getBrandList();
		model.addAttribute("brandList", brandList);

		HtPhoneModelInfo htPhoneModelInfo = new HtPhoneModelInfo();
		htPhoneModelInfo.setBrandId(htAdvisoryInfo.getPhoneBrand());
		List<HtPhoneModelInfo> phoneModelInfos = htPhoneModelInfoService.findList(htPhoneModelInfo);
		model.addAttribute("phoneModelInfos", phoneModelInfos);

		model.addAttribute("htAdvisoryInfo", htAdvisoryInfo);
		return "modules/advisory/htAdvisoryInfoFormAdv";
	}
}