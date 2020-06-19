/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.task.api.Task;
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
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyForm;
import com.jeesite.modules.htwaitapplyform.service.HtWaitApplyFormService;

import java.util.Date;
import java.util.List;

/**
 * 待申请工单Controller
 * @author hongmengzhong
 * @version 2020-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/htwaitapplyform/htWaitApplyForm")
public class HtWaitApplyFormController extends BaseController {

	@Autowired
	private HtWaitApplyFormService htWaitApplyFormService;
	@Autowired
	private ChannelProductInfoService channelProductInfoService;
	@Autowired
	private HtFormInfoService htFormInfoService;
	@Autowired
	private ActTaskUtils actTaskUtils;
	@Autowired
	private BpmTaskService bpmTaskService;
	@Autowired
	private UserService userService;
	@Autowired
	private HtUserApplyInfoService htUserApplyInfoService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtWaitApplyForm get(String id, boolean isNewRecord) {
		return htWaitApplyFormService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htwaitapplyform:htWaitApplyForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtWaitApplyForm htWaitApplyForm, Model model,String type) {
		htWaitApplyForm.setTypeClose(type);
		List<ChannelProductInfo> productInfoList =channelProductInfoService.findList(new ChannelProductInfo());
		model.addAttribute("productInfoList", productInfoList);
		model.addAttribute("htWaitApplyForm", htWaitApplyForm);
		return "modules/htwaitapplyform/htWaitApplyFormList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htwaitapplyform:htWaitApplyForm:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ProcessListingEntity> listData(HtWaitApplyForm htWaitApplyForm, HttpServletRequest request, HttpServletResponse response) {
		htWaitApplyForm.setPage(new Page<>(request, response));
		ProcessListingEntity processListingEntity = new ProcessListingEntity();
		processListingEntity.setHtWaitApplyForm(htWaitApplyForm);
		Page<ProcessListingEntity> processInstanceList = htWaitApplyFormService.selectProcessInstanceByQueryCriteria(processListingEntity,request,response);
		//Page<HtWaitApplyForm> page = htWaitApplyFormService.findPage(htWaitApplyForm);
		return processInstanceList;
	}

	/**
	 * 查看编辑表单
	 */
	//@RequiresPermissions("htwaitapplyform:htWaitApplyForm:view")
	@RequestMapping(value = "form")
	public String form(HtWaitApplyForm htWaitApplyForm, Model model) {
		HtFormInfo htFormInfo = htFormInfoService.get(htWaitApplyForm.getHtFormInfo().getId());
		BpmParams bpm = htWaitApplyForm.getBpm();
		HtWaitApplyForm	htWaitApply = htWaitApplyFormService.findByFormId(htWaitApplyForm.getHtFormInfo().getId());
		if (htWaitApply!=null){htWaitApplyForm=htWaitApply;htWaitApplyForm.setBpm(bpm);}
		htWaitApplyForm.setHtFormInfo(htFormInfo);
		HtUserApplyInfo search = new HtUserApplyInfo();
		search.setPolicyId(htFormInfo.getPolicyInfo().getId());
		List<HtUserApplyInfo> list = htUserApplyInfoService.findList(search);
		if (list!=null&list.size()>0){
			model.addAttribute("commonApply", true);

		}else{
			model.addAttribute("commonApply", false);

		}

		model.addAttribute("commonApplyId", htWaitApplyForm.getHtFormInfo().getId());
		model.addAttribute("htWaitApplyForm", htWaitApplyForm);
		return "modules/htwaitapplyform/htWaitApplyFormForm";
	}
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htwaitapplyform:htWaitApplyForm:view")
	@RequestMapping(value = "formInfo")
	public String formInfo(HtWaitApplyForm htWaitApplyForm, Model model, String formId) {
		HtFormInfo htFormInfo = htFormInfoService.get(formId);
		model.addAttribute("htFormInfo", htFormInfo);
		Task task = actTaskUtils.getTask(formId);
		String id = task.getId();
		BpmTask bpmTask = bpmTaskService.getTask(id);
		BpmParams bpmParams = new BpmParams();
		bpmParams.setActivityId(bpmTask.getActivityId());
		bpmParams.setProcInsId(bpmTask.getProcIns().getId());
		bpmParams.setTaskId(id);
		model.addAttribute("commonApply", true);
		model.addAttribute("task", task);
		model.addAttribute("commonApplyId", formId);
		model.addAttribute("htWaitApplyForm", htWaitApplyForm);
		model.addAttribute("bpmParams", bpmParams);
		return "modules/htwaitapplyform/htWaitApplyFormInfo";
	}

	/**
	 * 保存待申请工单
	 */
	//@RequiresPermissions("htwaitapplyform:htWaitApplyForm:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtWaitApplyForm htWaitApplyForm) {
		String contactStatus = htWaitApplyForm.getContactStatus();
		Date againContactDate = htWaitApplyForm.getAgainContactDate();
		if (!"1".equals(contactStatus)){
			if (againContactDate==null){
				return renderResult(Global.FALSE, text("未联系成功时，请选择再次联系时间！"));
			}
		}


		htWaitApplyFormService.save(htWaitApplyForm);
		return renderResult(Global.TRUE, text("保存待申请工单成功！"));
	}
	
	/**
	 * 删除待申请工单
	 */
	@RequiresPermissions("htwaitapplyform:htWaitApplyForm:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtWaitApplyForm htWaitApplyForm) {
		htWaitApplyFormService.delete(htWaitApplyForm);
		return renderResult(Global.TRUE, text("删除待申请工单成功！"));
	}
	
}