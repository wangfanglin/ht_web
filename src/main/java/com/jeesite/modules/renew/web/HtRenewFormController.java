/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.renew.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.channel.entity.HtPurchasingChannels;
import com.jeesite.modules.channel.service.HtPurchasingChannelsService;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.deductible.entity.HtDeductibleInfo;
import com.jeesite.modules.deductible.service.HtDeductibleInfoService;
import com.jeesite.modules.expressage.entity.HtExpressage;
import com.jeesite.modules.expressage.service.HtExpressageService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.entity.ProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.product.service.ProductInfoService;
import com.jeesite.modules.renew.entity.HtRenewEndForm;
import com.jeesite.modules.renew.entity.HtRenewHistory;
import com.jeesite.modules.renew.service.HtRenewHistoryService;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.template.utils.NoteTemplateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
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
import com.jeesite.modules.renew.entity.HtRenewForm;
import com.jeesite.modules.renew.service.HtRenewFormService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 换新工单Controller
 * @author lichao
 * @version 2020-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/renew/htRenewForm")
public class HtRenewFormController extends BaseController {

	@Autowired
	private HtRenewFormService htRenewFormService;
	@Autowired
	private HtFormInfoService htFormInfoService;
	@Autowired
	private HtPhoneModelInfoService htPhoneModelInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private HtClaimSettlementFormService htClaimSettlementFormService;
	@Autowired
	private ChannelProductInfoService channelProductInfoService;
	@Autowired
	private HtExpressageService htExpressageService;
	@Autowired
	private HtPurchasingChannelsService htPurchasingChannelsService;
	@Autowired
	private HtDeductibleInfoService htDeductibleInfoService;
	@Autowired
	private BpmTaskService bpmTaskService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HtHistoryService htHistoryService;
	@Autowired
	private PolicyInfoService policyInfoService;
	@Autowired
	private HtRenewHistoryService htRenewHistoryService;
	@Autowired
	private HtRepairClientFormService htRepairClientFormService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private HtMaintenancePointService htMaintenancePointService;
	@Autowired
	private ProductInfoService productInfoService;


	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtRenewForm get(String id, boolean isNewRecord) {
		return htRenewFormService.get(id, isNewRecord);
	}


	/**
	 * 换新完成列表
	 */
	@RequiresPermissions("renew:htRenewForm:view")
	@RequestMapping(value = "listEnd")
	public String listEnd(HtRenewEndForm htRenewForm, Model model) {



		List<HtBrandInfo> brandList = htBrandInfoService.findList(new HtBrandInfo());
		model.addAttribute("brandList", brandList);

		model.addAttribute("htRenewForm", htRenewForm);
		return "modules/renew/htRenewFormListEnd";
	}
	/**
	 * 换新完成列表数据
	 */
	@RequiresPermissions("renew:htRenewForm:view")
	@RequestMapping(value = "listDataEnd")
	@ResponseBody
	public Page<HtRenewEndForm> listDataEnd(HtRenewEndForm htRenewEndForm, HttpServletRequest request, HttpServletResponse response) {

		htRenewEndForm.setPage(new Page<>(request, response));
		htRenewEndForm.setFormStatus(FormStatus.WX_WXWC);
		htRenewEndForm.setFormType("1");
		Page<HtRenewEndForm> page = new Page<>();
		if(htRenewEndForm.getHtRenewForm().getSelfPrice() != null){
			BigDecimal num = new BigDecimal(100);
			HtRenewForm htRenewForm = htRenewEndForm.getHtRenewForm();
			htRenewForm.setSelfPrice(htRenewForm.getSelfPrice().multiply(num));
			htRenewEndForm.setHtRenewForm(htRenewForm);
		}
		page.setList(htRenewFormService.findEndList(htRenewEndForm));

		return page;
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("renew:htRenewForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtRenewEndForm htRenewForm, Model model) {
		model.addAttribute("htRenewForm", htRenewForm);
		return "modules/renew/htRenewFormList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("renew:htRenewForm:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtRenewEndForm> listData(HtRenewEndForm htRenewEndForm, HttpServletRequest request, HttpServletResponse response) {

		htRenewEndForm.setPage(new Page<>(request, response));
		Page<HtRenewEndForm> page = htRenewFormService.findAllList(htRenewEndForm);

		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("renew:htRenewForm:view")
	@RequestMapping(value = "form")
	public String form(HtRenewForm htRenewForm, Model model) {
		HtFormInfo htFormInfo= htFormInfoService.get(htRenewForm.getHtFormInfo());
		model.addAttribute("commonFromId",htFormInfo.getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htFormInfo.getId());

		//品牌
		List<HtBrandInfo> brandList = htBrandInfoService.findList(new HtBrandInfo());
		//型号
		List<HtPhoneModelInfo> typeList = htPhoneModelInfoService.findList(new HtPhoneModelInfo());
		//快递公司
		List<HtExpressage> exList = htExpressageService.findList(new HtExpressage());
		//采购渠道
		List<HtPurchasingChannels> channelsList = htPurchasingChannelsService.findList(new HtPurchasingChannels());

		String formType = htRenewForm.getRepairFormType();
		BpmParams bpmParams= htRenewForm.getBpm();

		List<HtRenewForm> list = htRenewFormService.findList(htRenewForm);
		if(list.size() > 0){
			htRenewForm = list.get(0);
			htRenewForm.setBpm(bpmParams);
		}

		//理赔数据
		HtClaimSettlementForm htClaimSettlementForm = new HtClaimSettlementForm();
		htClaimSettlementForm.setHtFormInfo(htRenewForm.getHtFormInfo());
		List<HtClaimSettlementForm> setList = htClaimSettlementFormService.findList(htClaimSettlementForm);
		htRenewForm.setHtClaimSettlementForm(setList.get(0));
		//理赔资料
		List<Map<String, Object>> claimDataList = htRepairClientFormService.findClaimData(setList.get(0).getSettlementDataId());

		if(StringUtils.isEmpty(htRenewForm.getChangeBrand())){
			htRenewForm.setChangeBrand(htRenewForm.getHtClaimSettlementForm().getPhoneBrand());
		}
		if(StringUtils.isEmpty(htRenewForm.getChangeModel())){
			htRenewForm.setChangeModel(htRenewForm.getHtClaimSettlementForm().getPhoneModel());
		}
		if(StringUtils.isEmpty(htRenewForm.getReceivePhone())){
			if(StringUtils.isNotEmpty(htRenewForm.getHtClaimSettlementForm().getReturnPhone())){
				htRenewForm.setReceivePhone(htRenewForm.getHtClaimSettlementForm().getReturnPhone());
			}
		}
		if(StringUtils.isEmpty(htRenewForm.getReceiveName())){
			if(StringUtils.isNotEmpty(htRenewForm.getHtClaimSettlementForm().getReturnName())){
				htRenewForm.setReceiveName(htRenewForm.getHtClaimSettlementForm().getReturnName());
			}
		}
		if(StringUtils.isEmpty(htRenewForm.getReceiveAddress())){
			if(StringUtils.isNotEmpty(htRenewForm.getHtClaimSettlementForm().getReturnAreaCode())){
				Area area = areaService.get(htRenewForm.getHtClaimSettlementForm().getReturnAreaCode());
				if(area != null){
					String treeNames = StringUtils.replace(area.getTreeNames(),"/"," ");
					htRenewForm.setReceiveAddress(treeNames);
				}

			}

		}
		if(StringUtils.isEmpty(htRenewForm.getReceiveAddressDetail())){
			htRenewForm.setReceiveAddressDetail(htRenewForm.getHtClaimSettlementForm().getReturnAddress());
		}


		model.addAttribute("channelsList", channelsList);
		model.addAttribute("exList", exList);
		model.addAttribute("claimDataList", claimDataList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("htFormInfo", htFormInfo);
		model.addAttribute("htRenewForm", htRenewForm);


		//2跳转待报价
		if("2".equals(formType)){
			//根据保单查询产品自付额
			Double selfPrice = channelProductInfoService.findDeductible(htFormInfo.getPolicyInfo().getId());
			model.addAttribute("selfPrice", selfPrice);

			PolicyInfo policyInfo = policyInfoService.get(htFormInfo.getPolicyInfo());
			//原购机价格
			BigDecimal intSellPrice = policyInfo.getIntSellPrice();
			model.addAttribute("intSellPrice", intSellPrice);

			//折旧月数=客户申请理赔的时间-和德保单生效时间 --参照原系统计算捷信的方式来算，考虑特殊月，1月20日申请，2月19日为第一月，2月20算2个月

			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			DateTime start = formatter.parseDateTime(sdf.format(policyInfo.getDateEffectiveDate()));
			DateTime end = formatter.parseDateTime(sdf.format(htRenewForm.getHtClaimSettlementForm().getCreateDate()));
			int oldMonth = Months.monthsBetween(start, end).getMonths()+1;

			model.addAttribute("oldMonth", oldMonth);
			//折旧金额=原购机金额*（20%基础折旧率+N*2%每月折旧率），N为折旧月数
			ProductInfo productInfo = productInfoService.findRenew(policyInfo.getId());
			//基础折旧率
			model.addAttribute("basisDepreciation",productInfo.getBasisDepreciation());
			//每月折旧率
			model.addAttribute("monthlyDepreciation",productInfo.getMonthlyDepreciation());

			BigDecimal oldPrice = intSellPrice.multiply(new BigDecimal(productInfo.getBasisDepreciation()+oldMonth*productInfo.getMonthlyDepreciation()));

			//折旧后新机价格=原购机价格-折旧金额
			BigDecimal oldNewPrice = intSellPrice.subtract(oldPrice);
			model.addAttribute("oldNewPrice", oldNewPrice);

			return "modules/renew/htRenewOfferForm";
		}
		//3跳转待审核
		if("3".equals(formType)){

			return "modules/renew/htRenewOfferFormOnly";
		}
		//4跳转待邮寄
		if("4".equals(formType)){
			PolicyInfo policyInfo = policyInfoService.get(htFormInfo.getPolicyInfo());
			List<ProductInfo> productInfoList= channelProductInfoService.findProductLsit(policyInfo.getChannelProductId());
			model.addAttribute("productInfoList", productInfoList);


			return "modules/renew/htRenewOfferFormEnd";
		}
		//5跳转自付款待确认
		if("5".equals(formType)){
			HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
			htDeductibleInfo.setHtFormInfo(htFormInfo);
			htDeductibleInfo.setBdId(htRenewForm.getId());
			List<HtDeductibleInfo> HtDeductibleInfoList = htDeductibleInfoService.findList(htDeductibleInfo);
			if(HtDeductibleInfoList.size() > 0 ){
				model.addAttribute("HtDeductibleInfo", HtDeductibleInfoList.get(0));
			}

			return "modules/renew/htRenewOfferFormPay";
		}


		return "modules/renew/htRenewFormForm";

	}



	/**
	 * 保存换新工单
	 */
	@RequiresPermissions("renew:htRenewForm:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtRenewForm htRenewForm) {
		//换新-待报价
		if (htRenewForm.getBpm().getActivityId().equals("renew_offer")) {
			HtClaimSettlementForm htClaimSettlementForm = new HtClaimSettlementForm();

			htClaimSettlementForm.setHtFormInfo(htRenewForm.getHtFormInfo());
			List<HtClaimSettlementForm> setList = htClaimSettlementFormService.findList(htClaimSettlementForm);
			//同型号
			if("1".equals(htRenewForm.getIsModel())){
				htRenewForm.setChangeBrand(setList.get(0).getPhoneBrand());
				htRenewForm.setChangeModel(setList.get(0).getPhoneModel());
			}
		}

		HtClaimSettlementForm htClaimSettlementForm = new HtClaimSettlementForm();
		htClaimSettlementForm.setHtFormInfo(htRenewForm.getHtFormInfo());
		List<HtClaimSettlementForm> setList = htClaimSettlementFormService.findList(htClaimSettlementForm);
		htRenewForm.setHtClaimSettlementForm(setList.get(0));

		HtFormInfo htFormInfo= htFormInfoService.get(htRenewForm.getHtFormInfo());
		htRenewForm.setHtFormInfo(htFormInfo);

		htRenewFormService.save(htRenewForm);

		//存储业务历史
		BpmTask bpmTask = bpmTaskService.getTask(htRenewForm.getBpm().getTaskId());
		String activityId = htRenewForm.getBpm().getActivityId();
		String operatingStatus = htFormInfo.getManageStatus();
		String createBy = htRenewForm.getCurrentUser().getUserName();
		String taskRemarks = bpmTask.getName();
		HtRenewHistory htRenewHistory = new HtRenewHistory();
		//查询最新记录拷贝历史
		HtRenewForm history = htRenewFormService.get(htRenewForm);
		BeanUtils.copyProperties(history,htRenewHistory);
		htRenewHistory.setManageStatus(operatingStatus);
		htRenewHistory.setTaskRemarks(taskRemarks);
		htRenewHistory.setTaskBy(createBy);
		htRenewHistory.setActivityId(activityId);
		htRenewHistory.setIsNewRecord(true);
		htRenewHistory.setId(IdGen.nextId());
		htRenewHistoryService.save(htRenewHistory);

		//总历史
		HtHistory htHistory = new HtHistory();
		htHistory.setHistoryFormId(htRenewHistory.getId());
		htHistory.setOperationStatus(operatingStatus);
		htHistory.setFormId(htRenewForm.getId());
		htHistory.setWorkOrderId(htFormInfo.getId());
		htHistory.setActivityId(activityId);
		htHistory.setFormType("1");
		htHistory.setDisposeUserId(htRenewForm.getCurrentUser().getUserCode());
		if(StringUtils.isNotEmpty(htRenewForm.getCallId())){
			htHistory.setCallId(htRenewForm.getCallId());
		}
		//对用户是否可见0是1否
		htHistory.setUserVisible("0");

		//经理审核通过
		if ("renew_manager".equals(htRenewForm.getBpm().getActivityId())) {
			BigDecimal sum = htRenewForm.getSelfPrice().add(htRenewForm.getOtherPrice());
			if(sum.compareTo(new BigDecimal(0)) == 1 && sum.compareTo(new BigDecimal(1000000)) < 1){
				htHistory.setUserVisible("1");
			}
			if(sum.compareTo(new BigDecimal(0)) == 0){
				htHistory.setUserVisible("1");
			}

		}
		//总监审核通过
		if ("renew_director".equals(htRenewForm.getBpm().getActivityId())) {
			htHistory.setUserVisible("1");
		}

		//后台系统是否可见1是0否
		htHistory.setCmsVisible("1");
		htHistory.setActivityName(taskRemarks);
		htHistory.setRemarks(htRenewForm.getRemarks());

		//下一活动
		Task task = taskService.createTaskQuery().processInstanceId(htRenewForm.getBpm().getProcInsId()).singleResult();
		if(task != null){
			htHistory.setAfterActivityId(task.getTaskDefinitionKey());
		}
		htHistoryService.save(htHistory);


		return renderResult(Global.TRUE, text("提交换新工单成功！"));
	}
	
	/**
	 * 删除换新工单
	 */
	@RequiresPermissions("renew:htRenewForm:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtRenewForm htRenewForm) {
		htRenewFormService.delete(htRenewForm);
		return renderResult(Global.TRUE, text("删除换新工单成功！"));
	}

	/**
	 * 查看历史表单
	 */
	@RequiresPermissions("renew:htRenewForm:view")
	@RequestMapping(value = "formHistory")
	public String formHistory(HtRenewHistory htRenewForm, Model model) {

		htRenewForm = htRenewHistoryService.get(htRenewForm);

		HtFormInfo htFormInfo= htFormInfoService.get(htRenewForm.getHtFormInfo());
		model.addAttribute("commonFromId",htFormInfo.getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htFormInfo.getId());
		//品牌
		List<HtBrandInfo> brandList = htBrandInfoService.findList(new HtBrandInfo());
		//型号
		List<HtPhoneModelInfo> typeList = htPhoneModelInfoService.findList(new HtPhoneModelInfo());
		//快递公司
		List<HtExpressage> exList = htExpressageService.findList(new HtExpressage());
		//采购渠道
		List<HtPurchasingChannels> channelsList = htPurchasingChannelsService.findList(new HtPurchasingChannels());



		//理赔数据
		HtClaimSettlementForm htClaimSettlementForm = new HtClaimSettlementForm();
		htClaimSettlementForm.setHtFormInfo(htRenewForm.getHtFormInfo());
		List<HtClaimSettlementForm> setList = htClaimSettlementFormService.findList(htClaimSettlementForm);
		htRenewForm.setHtClaimSettlementForm(setList.get(0));

		//理赔资料
		List<Map<String, Object>> claimDataList = htRepairClientFormService.findClaimData(setList.get(0).getSettlementDataId());


		model.addAttribute("channelsList", channelsList);
		model.addAttribute("exList", exList);
		model.addAttribute("claimDataList", claimDataList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("htFormInfo", htFormInfo);
		model.addAttribute("htRenewForm", htRenewForm);

		//待联系图片
		if (StringUtils.isNotBlank(htRenewForm.getImage())){
			List<String> imageList = Arrays.asList(htRenewForm.getImage().split("\\|"));
			model.addAttribute("imageList", imageList);
		}
		//留存图片
		if (StringUtils.isNotBlank(htRenewForm.getRenewImage())){
			List<String> renewImageList = Arrays.asList(htRenewForm.getRenewImage().split("\\|"));
			model.addAttribute("renewImageList", renewImageList);
		}



		//跳转待报价
		if("renew_offer".equals(htRenewForm.getActivityId())
			||"renew_manager".equals(htRenewForm.getActivityId())
				||"renew_director".equals(htRenewForm.getActivityId())){


			return "modules/renew/htRenewOfferFormHistory";
		}

		//跳转待邮寄
		if("renew_end".equals(htRenewForm.getActivityId())){
			//getTerminationRules，1复合产品，0单一产品
			HtGroupProductInfo htGroupProductInfo = policyInfoService.findGpBypolicyId(htFormInfo.getPolicyInfo().getId());
			model.addAttribute("HtDeductibleInfo", htGroupProductInfo);
			PolicyInfo policyInfo = policyInfoService.get(htFormInfo.getPolicyInfo());
			List<ProductInfo> productInfoList= channelProductInfoService.findProductLsit(policyInfo.getChannelProductId());
			model.addAttribute("productInfoList", productInfoList);

			return "modules/renew/htRenewOfferFormEndHistory";
		}
		//5跳转自付款待确认
		if("renew_pay".equals(htRenewForm.getActivityId())){
			HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
			htDeductibleInfo.setHtFormInfo(htFormInfo);
			List<HtDeductibleInfo> HtDeductibleInfoList = htDeductibleInfoService.findList(htDeductibleInfo);
			if(HtDeductibleInfoList.size() > 0 ){
				model.addAttribute("HtDeductibleInfo", HtDeductibleInfoList.get(0));
			}else{
				model.addAttribute("HtDeductibleInfo", htDeductibleInfo);
			}

			return "modules/renew/htRenewOfferFormPayHistory";
		}


		return "modules/renew/htRenewFormFormHistory";

	}

	//短信模板
	@RequestMapping(value = "getSmsString")
	@ResponseBody
	public String getSmsString(String isQualified,String formId,String lackClaimData ) {
		String content = "";
		//合格
		if(("1").equals(isQualified)){
			//合格短信模板
			content = NoteTemplateUtils.noteTemplateThreeRenew();
		}else if(("2").equals(isQualified)){

			//不合格短信模板
			content = NoteTemplateUtils.noteTemplateFourRenew(lackClaimData);

		}

		return content;
	}


	@RequestMapping(value = "formInfo")
	public String formInfo(Model model, HtRenewForm htRenewForm) {

		HtFormInfo htFormInfo= htFormInfoService.get(htRenewForm.getHtFormInfo());
		model.addAttribute("commonFromId",htFormInfo.getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htFormInfo.getId());
		model.addAttribute("htFormInfo", htFormInfo);
		return "modules/renew/htRenewFormInfo";
	}

}