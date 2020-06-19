/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.image.ImageUtils;
import com.jeesite.common.io.FileUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.ueditor.upload.Base64Uploader;
import com.jeesite.modules.bh.entity.BhFaultplandict;
import com.jeesite.modules.bh.entity.BhFormInfo;
import com.jeesite.modules.bh.service.BhFaultplandictService;
import com.jeesite.modules.bh.service.BhFormInfoService;
import com.jeesite.modules.bohai.BoHaiInterfaceService;
import com.jeesite.modules.bohai.entity.*;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.deductible.entity.HtDeductibleInfo;
import com.jeesite.modules.deductible.service.HtDeductibleInfoService;
import com.jeesite.modules.expressage.entity.HtExpressage;
import com.jeesite.modules.expressage.service.HtExpressageService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.htbreakdowninfo.entity.HtBreakdownInfo;
import com.jeesite.modules.htbreakdowninfo.service.HtBreakdownInfoService;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.httimeefficiency.entity.HtTimeEfficiency;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyDetailService;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.receipt.entity.HtReceiptData;
import com.jeesite.modules.renew.entity.HtRenewEndForm;
import com.jeesite.modules.renew.entity.HtRenewForm;
import com.jeesite.modules.repair.entity.*;
import com.jeesite.modules.repair.service.HtRepairClientHistoryService;
import com.jeesite.modules.repair.service.HtRepairOfferPartHistoryService;
import com.jeesite.modules.repair.service.HtRepairOfferPartService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;
import com.jeesite.modules.settlementform.htclaimsettlementform.web.HtClaimSettlementFormController;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.template.utils.NoteTemplateUtils;
import com.jeesite.modules.template.utils.SmsSendUtils;
import com.jeesite.modules.template.utils.WxTemplateUtils;
import com.jeesite.modules.user.entity.HtUserInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.repair.service.HtRepairClientFormService;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.SocketOption;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 维修工单-待联系客户Controller
 * @author lichao
 * @version 2020-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/repair/htRepairClientForm")
public class HtRepairClientFormController extends BaseController {
	@Autowired
	private HtBreakdownInfoService htBreakdownInfoService;
	@Autowired
	private HtRepairClientFormService htRepairClientFormService;
	@Autowired
	private HtRepairOfferPartService htRepairOfferPartService;
	@Autowired
	private HtClaimSettlementFormService htClaimSettlementFormService;
	@Autowired
	private ChannelProductInfoService channelProductInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private ActTaskUtils actTaskUtils;
	@Autowired
	private HtFormInfoService htFormInfoService;
	@Autowired
	private HtPhoneModelInfoService htPhoneModelInfoService;
	@Autowired
	private HtExpressageService htExpressageService;
	@Autowired
	private HtMaintenancePointService htMaintenancePointService;
	@Autowired
	private HtAssemblyUnitService htAssemblyUnitService;
	@Autowired
	private BpmTaskService bpmTaskService;
	@Autowired
	private HtRepairClientHistoryService htRepairClientHistoryService;
	@Autowired
	private HtHistoryService htHistoryService;
	@Autowired
	private HtRepairOfferPartHistoryService htRepairOfferPartHistoryService;
	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@Autowired
	private PolicyInfoService policyInfoService;
	@Autowired
	private HtDeductibleInfoService htDeductibleInfoService;
	@Autowired
	private PolicyDetailService policyDetailService;

	@Autowired
	private BoHaiInterfaceService boHaiInterfaceService;
	@Autowired
	private BhFormInfoService bhFormInfoService;
	@Autowired
	private BhFaultplandictService bhFaultplandictService;
	@Autowired
	private AreaService areaService;

	@Autowired
	private WxTemplateUtils wxTemplateUtils;



	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtRepairClientForm get(String id, boolean isNewRecord) {
		return htRepairClientFormService.get(id, isNewRecord);
	}

	/**
	 * 维修完成列表
	 */
	@RequestMapping(value = "listEnd")
	public String listEnd(HtRepairEndForm htRepairEndForm, Model model) {

		model.addAttribute("htRepairEndForm", htRepairEndForm);
		return "modules/repair/htRepairFormListEnd";
	}
	/**
	 * 维修完成列表数据
	 */
	@RequestMapping(value = "listDataEnd")
	@ResponseBody
	public Page<HtRepairEndForm> listDataEnd(HtRepairEndForm htRepairEndForm, HttpServletRequest request, HttpServletResponse response) {

		htRepairEndForm.setPage(new Page<>(request, response));
		htRepairEndForm.setFormStatus(FormStatus.WX_WXWC);
		htRepairEndForm.setFormType("2");
		Page<HtRepairEndForm> page = htRepairClientFormService.findEndList(htRepairEndForm);

		return page;
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("repair:htRepairClientForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtRepairEndForm htRepairClientForm, Model model) {

		model.addAttribute("HtRepairEndForm", htRepairClientForm);
		return "modules/repair/htRepairClientFormList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("repair:htRepairClientForm:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtRepairEndForm> listData(HtRepairEndForm htRepairEndForm, HttpServletRequest request, HttpServletResponse response) {
		htRepairEndForm.setPage(new Page<>(request, response));
		Page<HtRepairEndForm> page = htRepairClientFormService.findAllList(htRepairEndForm);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("repair:htRepairClientForm:view")
	@RequestMapping(value = "form")
	public String form(HtRepairClientForm htRepairClientForm, Model model) {



		HtFormInfo htFormInfo= htFormInfoService.get(htRepairClientForm.getHtFormInfo());
		model.addAttribute("commonFromId",htFormInfo.getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htFormInfo.getId());

		//品牌
		List<HtBrandInfo> brandList = htBrandInfoService.findList(new HtBrandInfo());
		//型号
		List<HtPhoneModelInfo> typeList = htPhoneModelInfoService.findList(new HtPhoneModelInfo());
		//快递公司
		List<HtExpressage> exList = htExpressageService.findList(new HtExpressage());

		BpmParams bpmParams= htRepairClientForm.getBpm();
		String formType = htRepairClientForm.getRepairFormType();

		List<HtRepairClientForm> list = htRepairClientFormService.findList(htRepairClientForm);
		if(list.size() > 0){
			htRepairClientForm = list.get(0);
			htRepairClientForm.setBpm(bpmParams);
		}
		//理赔数据
		HtClaimSettlementForm htClaimSettlementForm = new HtClaimSettlementForm();
		htClaimSettlementForm.setHtFormInfo(htRepairClientForm.getHtFormInfo());
		List<HtClaimSettlementForm> setList = htClaimSettlementFormService.findList(htClaimSettlementForm);
		htRepairClientForm.setHtClaimSettlementForm(setList.get(0));


		//理赔资料
		List<Map<String, Object>> claimDataList = htRepairClientFormService.findClaimData(setList.get(0).getSettlementDataId());
		//修理类型
		List<HtBreakdownInfo> breakDownList = htBreakdownInfoService.findList(new HtBreakdownInfo());
		//主副部件
		List<HtAssemblyUnit> unitZhuList = htAssemblyUnitService.findListByStart("1");
		List<HtAssemblyUnit> unitFuList = htAssemblyUnitService.findListByStart("0");

		//把维修网点信息带过去

		List<HtMaintenancePoint> maintenancePointList = htMaintenancePointService.findList(new HtMaintenancePoint());
		model.addAttribute("maintenancePointList", maintenancePointList);

		//获取主副部件名称
		Map<String,String> map = htClaimSettlementFormService.getUnitStrInfo(htFormInfo.getId());
		model.addAttribute("master_unit_str",(String)map.get("master_unit_str"));
		model.addAttribute("vice_parts_str",(String)map.get("vice_parts_str"));



		model.addAttribute("unitZhuList", unitZhuList);
		model.addAttribute("unitFuList", unitFuList);
		model.addAttribute("breakDownList", breakDownList);
		model.addAttribute("exList", exList);
		model.addAttribute("claimDataList", claimDataList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("htFormInfo", htFormInfo);
		model.addAttribute("htRepairClientForm", htRepairClientForm);


		if (StringUtils.isNotBlank(htRepairClientForm.getHtClaimSettlementForm().getDamageImgs())){
			List<String> damageImgsList = Arrays.asList(htRepairClientForm.getHtClaimSettlementForm().getDamageImgs().split("\\|"));
			model.addAttribute("damageImgsList", damageImgsList);
		}
		if (StringUtils.isNotBlank(htRepairClientForm.getHtClaimSettlementForm().getIdentityCardImgs())){
			List<String> identityCardImgsList = Arrays.asList(htRepairClientForm.getHtClaimSettlementForm().getIdentityCardImgs().split("\\|"));
			model.addAttribute("identityCardImgsList", identityCardImgsList);
		}
		if (StringUtils.isNotBlank(htRepairClientForm.getHtClaimSettlementForm().getPurchaseImgs())){
			List<String> purchaseImgsList = Arrays.asList(htRepairClientForm.getHtClaimSettlementForm().getPurchaseImgs().split("\\|"));
			model.addAttribute("purchaseImgsList", purchaseImgsList);
		}


		//2为跳转待报价表单
		if("2".equals(formType)){

			//报价损坏部位

			List<HtAssemblyUnit> assemblyUnitList = htAssemblyUnitService.findList(new HtAssemblyUnit());
			model.addAttribute("assemblyUnitList", assemblyUnitList);


			HtRepairOfferPart htRepairOfferPart = new HtRepairOfferPart();
			htRepairOfferPart.setFormId(htRepairClientForm.getHtFormInfo().getId());
			List<HtRepairOfferPart> offerPartList = htRepairOfferPartService.findList(htRepairOfferPart);

			model.addAttribute("offerPartList", offerPartList);
			return "modules/repair/htRepairOfferFormForm";

		}
		//3为跳转待维修表单
		if("3".equals(formType)){

			HtRepairOfferPart htRepairOfferPart = new HtRepairOfferPart();
			htRepairOfferPart.setFormId(htRepairClientForm.getHtFormInfo().getId());
			List<HtRepairOfferPart> offerPartList = htRepairOfferPartService.findList(htRepairOfferPart);

			model.addAttribute("offerPartList", offerPartList);
			return "modules/repair/htRepairWaitFormForm";

		}

		//4为跳转待审核报价表单
		if("4".equals(formType)){
			HtRepairOfferPart htRepairOfferPart = new HtRepairOfferPart();
			htRepairOfferPart.setFormId(htRepairClientForm.getHtFormInfo().getId());
			List<HtRepairOfferPart> offerPartList = htRepairOfferPartService.findList(htRepairOfferPart);
			model.addAttribute("offerPartList", offerPartList);
			List<HtAssemblyUnit> assemblyUnitList = htAssemblyUnitService.findList(new HtAssemblyUnit());
			model.addAttribute("assemblyUnitList", assemblyUnitList);
			return "modules/repair/htRepairOfferFormOnly";

		}
		//5为跳转维修完成待寄件
		if("5".equals(formType)){
			String treeNames = "";
			if(StringUtils.isNotEmpty(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode())){
				Area area = areaService.get(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode());
				treeNames = StringUtils.replace(area.getTreeNames(),"/"," ");
			}
			model.addAttribute("treeNames", treeNames);

			return "modules/repair/htRepairEndForm";

		}

		//6为跳转维修业务只读表单
		if("6".equals(formType)){
			Role role = new Role();
			role.setRoleCode("claim_info");
			List<User> userList = userService.findListByRoleCode(new User(role));
			Task task =  actTaskUtils.getTask(htFormInfo.getId());
			model.addAttribute("task", task);
			model.addAttribute("list", userList);
			return "modules/repair/htRepairClientFormOnly";

		}
		//7为跳转维修完成待审核
		if("7".equals(formType)){
			//理赔选中的子产品才可手动终止
			String childId = htRepairClientForm.getHtClaimSettlementForm().getProductChildId();
			model.addAttribute("childId", childId);

			String treeNames = "";
			if(StringUtils.isNotEmpty(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode())){
				Area area = areaService.get(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode());
				treeNames = StringUtils.replace(area.getTreeNames(),"/"," ");
			}
			model.addAttribute("treeNames", treeNames);

			String[] imageArr = StringUtils.split(htRepairClientForm.getRepairFormImage(),"|");
			//终止规则和是否复合产品1复合
			HtGroupProductInfo htGroupProductInfo = policyInfoService.findGpBypolicyId(htFormInfo.getPolicyInfo().getId());
			model.addAttribute("HtDeductibleInfo", htGroupProductInfo);
			if(htGroupProductInfo.getTerminationRules().equals("1")){
				//历史维修记录
				HtFormInfo oldForm = new HtFormInfo();
				oldForm.setPolicyInfo(htFormInfo.getPolicyInfo());
				oldForm.setFormStatus(FormStatus.WX_WXWC);
				oldForm.setFormType("2");
				List<HtRepairClientForm> historyList = htRepairClientFormService.findHistory(oldForm);
				model.addAttribute("historyList", historyList);

				//复合产品
				PolicyDetail policyDetail = new PolicyDetail();
				PolicyInfo policyInfo = new PolicyInfo();
				policyInfo.setId(htFormInfo.getPolicyInfo().getId());
				policyDetail.setPolicyInfo(policyInfo);
				List<PolicyDetail> policyDetailList = policyDetailService.findList(policyDetail);


				List<PolicyDetail> weixiuList = new ArrayList<>();
				List<PolicyDetail> huanjiList = new ArrayList<>();
				List<PolicyDetail> yanbaoList = new ArrayList<>();
				List<PolicyDetail> shujubaoList = new ArrayList<>();

				for (int i = 0; i < policyDetailList.size() ; i++) {
					PolicyDetail poDetail = policyDetailList.get(i);
					//维修
					if("0".equals(poDetail.getProductInfo().getProductType())){

						String[] unitArr = StringUtils.split(poDetail.getProductInfo().getAssemblyId(),",") ;
						String unitName = "";
						for (int j = 0; j < unitArr.length; j++) {
							HtAssemblyUnit htAssemblyUnit = htAssemblyUnitService.get(unitArr[j]);
							unitName += htAssemblyUnit.getName()+",";
						}

						unitName = StringUtils.substring(unitName, 0,unitName.length() - 1);
						poDetail.setUnitName(unitName);
						poDetail.setHtGroupProductInfo(htGroupProductInfo);
						weixiuList.add(poDetail);
					}
					//换机
					if("1".equals(poDetail.getProductInfo().getProductType())){
						poDetail.setHtGroupProductInfo(htGroupProductInfo);
						huanjiList.add(poDetail);
					}
					//延保
					if("2".equals(poDetail.getProductInfo().getProductType())){
						poDetail.setHtGroupProductInfo(htGroupProductInfo);
						yanbaoList.add(poDetail);
					}
					//数据保
					if("3".equals(poDetail.getProductInfo().getProductType())){
						poDetail.setHtGroupProductInfo(htGroupProductInfo);
						shujubaoList.add(poDetail);
					}
				}

				model.addAttribute("weixiuList", weixiuList);
				model.addAttribute("huanjiList", huanjiList);
				model.addAttribute("yanbaoList", yanbaoList);
				model.addAttribute("shujubaoList", shujubaoList);

				return "modules/repair/htRepairEndFormUploadOnly";

			}
			return "modules/repair/htRepairEndFormOnly";

		}
		//8修改维修时间待审核
		if("8".equals(formType)){
			HtRepairClientHistory htRepairClientHistory = new HtRepairClientHistory();
			htRepairClientHistory.setHtFormInfo(htFormInfo);
			htRepairClientHistory.setActivityId("repair_wait");
			htRepairClientHistory.setOrderBy("a.create_date ASC");
			List<HtRepairClientHistory> repairCountList = htRepairClientHistoryService.findList(htRepairClientHistory);
			model.addAttribute("repairCountList",repairCountList);

			return "modules/repair/htRepairWaitFormOnly";

		}
		//9财务确认自付款
		if("9".equals(formType)){
			HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
			htDeductibleInfo.setHtFormInfo(htFormInfo);
			htDeductibleInfo.setBdId(htRepairClientForm.getId());
			List<HtDeductibleInfo> HtDeductibleInfoList = htDeductibleInfoService.findList(htDeductibleInfo);
			if(HtDeductibleInfoList.size() > 0 ){
				model.addAttribute("HtDeductibleInfo", HtDeductibleInfoList.get(0));
			}else{
				model.addAttribute("HtDeductibleInfo", htDeductibleInfo);
			}

			return "modules/repair/htRepairOfferFormPay";

		}

		return "modules/repair/htRepairClientFormForm";
	}

	/**
	 * 保存维修工单-待联系客户
	 */
	@RequiresPermissions("repair:htRepairClientForm:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtRepairClientForm htRepairClientForm) {
		//如果是待报价提交则修改新增报价明细
		String clientHistoryId = IdGen.nextId();
		if("repair_offer".equals(htRepairClientForm.getBpm().getActivityId())
			|| "repair_charge".equals(htRepairClientForm.getBpm().getActivityId())
			|| "repair_manager".equals(htRepairClientForm.getBpm().getActivityId())
				){
			List<HtRepairOfferPart> partList = htRepairClientForm.getOfferPartList();
			if("repair_charge".equals(htRepairClientForm.getBpm().getActivityId())
					||"repair_manager".equals(htRepairClientForm.getBpm().getActivityId())){
				HtRepairOfferPart htRepairOfferPart = new HtRepairOfferPart();
				htRepairOfferPart.setFormId(htRepairClientForm.getHtFormInfo().getId());
				partList = htRepairOfferPartService.findList(htRepairOfferPart);
			}

			for (HtRepairOfferPart part : partList) {
				HtAssemblyUnit htAssemblyUnit = htAssemblyUnitService.get(part.getDamageId());
				part.setDamageName(htAssemblyUnit.getName());
				part.setFormId(htRepairClientForm.getHtFormInfo().getId());

				if("repair_offer".equals(htRepairClientForm.getBpm().getActivityId())){
					part.setDamagePrice(part.getDamagePrice().multiply(new BigDecimal(100)));
					part.setSalvagePrice(part.getSalvagePrice().multiply(new BigDecimal(100)));
					if(part.getStatus().equals("1")){
						htRepairOfferPartService.delete(part);
					}else{
						htRepairOfferPartService.save(part);
					}
				}

				HtRepairOfferPartHistory htRepairOfferPartHistory = new HtRepairOfferPartHistory();
				BeanUtils.copyProperties(part,htRepairOfferPartHistory);
				htRepairOfferPartHistory.setClientHistoryId(clientHistoryId);
				htRepairOfferPartHistory.setIsNewRecord(true);
				htRepairOfferPartHistory.setId(null);
				htRepairOfferPartHistoryService.save(htRepairOfferPartHistory);
			}
		}

		//查询理赔数据
		HtClaimSettlementForm htClaimSettlementForm = new HtClaimSettlementForm();
		htClaimSettlementForm.setHtFormInfo(htRepairClientForm.getHtFormInfo());
		List<HtClaimSettlementForm> setList = htClaimSettlementFormService.findList(htClaimSettlementForm);
		htRepairClientForm.setHtClaimSettlementForm(setList.get(0));


		HtFormInfo svcHtForm= htFormInfoService.get(htRepairClientForm.getHtFormInfo());
		//1为渤海工单
		if(svcHtForm.getBhFlag().equals("1") && DataEntity.STATUS_AUDIT.equals(htRepairClientForm.getHtFormInfo().getStatus())){
			HtRepairClientForm repairObj = htRepairClientFormService.get(htRepairClientForm.getId());




			BhFormInfo bhFormInfo = new BhFormInfo();
			bhFormInfo.setFormId(htRepairClientForm.getHtFormInfo().getId());
			List<BhFormInfo> bhList = bhFormInfoService.findList(bhFormInfo);
			bhFormInfo = bhList.get(0);
			//维修---售后主管审批调用估损接口
			if ("repair_charge".equals(htRepairClientForm.getBpm().getActivityId())) {
				BigDecimal sumPrice = repairObj.getSumPrice().divide(new BigDecimal(100));
				//1维修点代收2财务代收
				if("1".equals(htRepairClientForm.getReceiptType())){
					//估损接口
					Result<String> result = boHaiInterfaceService.policyDamage(new PolicyDamageParameter(bhFormInfo.getClnNo(),sumPrice.toString()));
					if(!result.getStatus().equals(ResultStatus.SUCCESS)){
						return renderResult(Global.FALSE, text("估损接口调用失败！"));
					}
				}else{
					//小于等于300000
					if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(300000)) < 1){
						//估损接口
						Result<String> result = boHaiInterfaceService.policyDamage(new PolicyDamageParameter(bhFormInfo.getClnNo(),sumPrice.toString()));
						if(!result.getStatus().equals(ResultStatus.SUCCESS)){
							return renderResult(Global.FALSE, text("估损接口调用失败！"));
						}
					}
				}
			}

			//维修---售后经理审批
			if ("repair_manager".equals(htRepairClientForm.getBpm().getActivityId())) {
				BigDecimal sumPrice = repairObj.getSumPrice().divide(new BigDecimal(100));

				//估损接口
				Result<String> result = boHaiInterfaceService.policyDamage(new PolicyDamageParameter(bhFormInfo.getClnNo(),sumPrice.toString()));
				if(!result.getStatus().equals(ResultStatus.SUCCESS)){
					return renderResult(Global.FALSE, text("估损接口调用失败！"));
				}
			}


			//维修完成，调用影像同步接口传入维修前照片
			if ("repair_wait".equals(htRepairClientForm.getBpm().getActivityId())) {
				boolean isSuccess = true;
				if("2".equals(htRepairClientForm.getIsEnd())){
					//维修待联系-快递单、设备、及相关理赔资料照片（多张)
					isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getImage(),bhFormInfo.getClnNo());
					if(!isSuccess){
						return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
					}
					//维修待报价-损害部位相关照片（多张)
					isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getDamageImg(),bhFormInfo.getClnNo());
					if(!isSuccess){
						return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
					}
				}

			}


			//维修完成待审核
			if ("repair_end_check".equals(htRepairClientForm.getBpm().getActivityId())) {
				//维修同步接口
				String operationTime = DateUtils.formatDateTime(new Date());
				Result<String> result = boHaiInterfaceService.synRepairInfo(new SynRepairInfoParameter(bhFormInfo.getClnNo(),bhFormInfo.getOrderId(),
						bhFormInfo.getDeviceCode(),"Y",operationTime,"和德信通"));
				if(!result.getStatus().equals(ResultStatus.SUCCESS)){
//					return renderResult(Global.FALSE, text("维修同步接口调用失败！"));
				}


				//影像资料接口
				boolean isSuccess = true;
				//理赔环节图片-身份证（多张)
				isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getHtClaimSettlementForm().getIdentityCardImgs(),bhFormInfo.getClnNo());
				if(!isSuccess){
					return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
				}
				//理赔环节图片-购买凭证（多张)
				isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getHtClaimSettlementForm().getPurchaseImgs(),bhFormInfo.getClnNo());
				if(!isSuccess){
					return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
				}
				//理赔环节图片-损坏部位（多张)
				isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getHtClaimSettlementForm().getDamageImgs(),bhFormInfo.getClnNo());
				if(!isSuccess){
					return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
				}
				//维修待联系-快递单、设备、及相关理赔资料照片（多张)
				isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getImage(),bhFormInfo.getClnNo());
				if(!isSuccess){
					return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
				}
				//维修待报价-损害部位相关照片（多张)
				isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getDamageImg(),bhFormInfo.getClnNo());
				if(!isSuccess){
					return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
				}
				//上传的维修工单照片（单张）
				isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getUploadImage(),bhFormInfo.getClnNo());
				if(!isSuccess){
					return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
				}
				//上传的留存照片（多张）
				isSuccess = htRepairClientFormService.uploadImage(htRepairClientForm.getUploadEndImg(),bhFormInfo.getClnNo());
				if(!isSuccess){
					return renderResult(Global.FALSE, text("影像资料接口调用失败！"));
				}


				//查勘接口
				//查勘结果集合
				List<SurveyLoss> surList = new ArrayList<>();
				//残值列表
				List<ResidualInfo> residualInfoList = new ArrayList<>();
				//费用列表
				List<SurveyFee> surveyFeeList = new ArrayList<>();

				ResidualInfo residualInfo = new ResidualInfo("", "", "", "", "", "", "", "", "", "", "");
				SurveyFee surveyFee = new SurveyFee("", "", "", "", "", "", "", "");


				HtRepairOfferPart htRepairOfferPart = new HtRepairOfferPart();
				htRepairOfferPart.setFormId(htRepairClientForm.getHtFormInfo().getId());
				List<HtRepairOfferPart> offerPartList = htRepairOfferPartService.findList(htRepairOfferPart);
				for (int i = 0; i < offerPartList.size(); i++) {
					HtRepairOfferPart offerPart = offerPartList.get(i);
					String bhId = offerPart.getBhProjectId();
					BhFaultplandict bhFaultplandict = bhFaultplandictService.get(bhId);
					BigDecimal num = new BigDecimal(offerPartList.size());
					//工时费
					BigDecimal repairTimeAmt = htRepairClientForm.getManHourPrice().divide(num.multiply(new BigDecimal(100)));
					//其他费
					BigDecimal otherAmt = htRepairClientForm.getOtherPrice().divide(num.multiply(new BigDecimal(100)));
					//维修价格
					BigDecimal planAmt = htRepairClientForm.getSumPrice().divide(num.multiply(new BigDecimal(100)));
					//实际价格配件+工时+杂费
					BigDecimal realAmt = htRepairClientForm.getOtherPrice().add(htRepairClientForm.getManHourPrice());

					realAmt = realAmt.add(offerPart.getDamagePrice().divide(new BigDecimal(100)));

					SurveyLoss surveyLoss = new SurveyLoss(bhFaultplandict.getMalfunctionid(),bhFaultplandict.getMalfunctionname(),
							bhFaultplandict.getSolutionid(),bhFaultplandict.getSolution(),
							bhFaultplandict.getTopid(),bhFaultplandict.getTopname(),planAmt.toString(),realAmt.toString(),bhFaultplandict.getPrice(),repairTimeAmt.toString(),otherAmt.toString());

					surList.add(surveyLoss);
				}



				BigDecimal sumPrice = repairObj.getSumPrice().divide(new BigDecimal(100));
				BigDecimal selfPrice = repairObj.getSelfPrice().divide(new BigDecimal(100));

				Result<String> resultSur = boHaiInterfaceService.survey(new SurveyParameter(bhFormInfo.getOrderId(),bhFormInfo.getClnNo(),bhFormInfo.getDeviceCode(),
						operationTime,sumPrice.toString(),selfPrice.toString(),bhFormInfo.getDeviceType(),bhFormInfo.getDeviceBrand(),
						bhFormInfo.getDeviceModel(),bhFormInfo.getDeviceAttr(),surList));
				if(!resultSur.getStatus().equals(ResultStatus.SUCCESS)){
					return renderResult(Global.FALSE, text("查勘接口调用失败！"));
				}
			}
		}

		HtFormInfo htFormInfo= htFormInfoService.get(htRepairClientForm.getHtFormInfo());
		htRepairClientForm.setHtFormInfo(htFormInfo);

		htRepairClientFormService.save(htRepairClientForm);

		//存储历史
		BpmTask bpmTask = bpmTaskService.getTask(htRepairClientForm.getBpm().getTaskId());
		String activityId = htRepairClientForm.getBpm().getActivityId();
		String operatingStatus = htFormInfo.getManageStatus();
		String createBy = htRepairClientForm.getCurrentUser().getUserName();
		String taskRemarks = bpmTask.getName();
		HtRepairClientHistory htRepairClientHistory = new HtRepairClientHistory();
		BeanUtils.copyProperties(htRepairClientForm,htRepairClientHistory);
		htRepairClientHistory.setManageStatus(operatingStatus);
		htRepairClientHistory.setTaskRemarks(taskRemarks);
		htRepairClientHistory.setTaskBy(createBy);
		htRepairClientHistory.setActivityId(activityId);
		htRepairClientHistory.setIsNewRecord(true);
		htRepairClientHistory.setId(clientHistoryId);
		// 如果未设置状态，则指定状态为审核状态，以提交审核流程
		htRepairClientHistoryService.save(htRepairClientHistory);


		//总历史
		HtHistory htHistory = new HtHistory();
		htHistory.setHistoryFormId(htRepairClientHistory.getId());
		htHistory.setOperationStatus(operatingStatus);
		htHistory.setFormId(htRepairClientForm.getId());
		htHistory.setWorkOrderId(htFormInfo.getId());
		htHistory.setActivityId(activityId);
		htHistory.setFormType("2");
		htHistory.setDisposeUserId(htRepairClientForm.getCurrentUser().getUserCode());

		//0不可见1可见
		htHistory.setUserVisible("0");
		//1、维修网点签收
		if ("repair_info".equals(htRepairClientForm.getBpm().getActivityId())) {
			//已签收
			if ("3".equals(htRepairClientForm.getClaimStatus())) {
				//资料是否合格1是2否
				if ("1".equals(htRepairClientForm.getIsQualified())) {
					htHistory.setUserVisible("1");
				}
			}
		}
		//2、报价通过后
		if ("repair_charge".equals(htRepairClientForm.getBpm().getActivityId())) {
			//1全损2非全损
			if ("1".equals(htRepairClientForm.getIsAll())) {
				htHistory.setUserVisible("1");
			} else {
				//是否申请修改维修方案0否1是
				if("1".equals(htRepairClientForm.getIsYes())){

				}else{
					//1维修点代收2财务代收
					if("1".equals(htRepairClientForm.getReceiptType())){
						//大于0并且小于等于300000
						if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(0)) == 1 && htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(300000)) < 1){
							htHistory.setUserVisible("1");
						}
						//等于0
						if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(0)) == 0){
							htHistory.setUserVisible("1");
						}
					}
					if("2".equals(htRepairClientForm.getReceiptType())){
						//大于0并且小于等于300000
						if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(0)) == 1 && htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(300000)) < 1){
							htHistory.setUserVisible("1");
						}
						//等于0
						if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(0)) == 0){
							htHistory.setUserVisible("1");
						}
					}
				}
			}
		}

		//维修---售后经理审批
		if ("repair_manager".equals(htRepairClientForm.getBpm().getActivityId())) {
			//是否申请修改维修方案0否1是
			if ("1".equals(htRepairClientForm.getIsYes())) {

			} else {
				//1维修点代收2财务代收
				htHistory.setUserVisible("1");

//				if("1".equals(htRepairClientForm.getReceiptType())){
//				}
			}
		}
		//3、当有自付款后
		//维修---付款财务审核
		if ("repair_finance".equals(htRepairClientForm.getBpm().getActivityId())) {
			htHistory.setUserVisible("1");
		}

		//4、当定为全损后


		//5、当修改维修完成时间
		//维修---待维修工单1维修完成2维修到期
		if ("repair_wait".equals(htRepairClientForm.getBpm().getActivityId())) {
			//1未完成，2维修完成

			if("1".equals(htRepairClientForm.getIsEnd())){
				//是否修改维修方案1是
				if("1".equals(htRepairClientForm.getIsYes())){

				}else{
					htHistory.setUserVisible("1");
				}
			}
		}

		//6、维修完成审核通过后

		if ("repair_end_check".equals(htRepairClientForm.getBpm().getActivityId())) {
			htHistory.setUserVisible("1");
		}



		htHistory.setCmsVisible("1");
		htHistory.setActivityName(taskRemarks);
		htHistory.setRemarks(htRepairClientForm.getRemarks());
		if(StringUtils.isNotEmpty(htRepairClientForm.getCallId())){
			htHistory.setCallId(htRepairClientForm.getCallId());
		}

		//下一活动
		Task task = taskService.createTaskQuery().processInstanceId(htRepairClientForm.getBpm().getProcInsId()).singleResult();
		if(task != null){
			htHistory.setAfterActivityId(task.getTaskDefinitionKey());
		}
		htHistoryService.save(htHistory);

		return renderResult(Global.TRUE, text("提交成功！"));
	}
	
	/**
	 * 删除维修工单-待联系客户
	 */
	@RequiresPermissions("repair:htRepairClientForm:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtRepairClientForm htRepairClientForm) {

		htRepairClientFormService.delete(htRepairClientForm);
		return renderResult(Global.TRUE, text("删除维修工单-待联系客户成功！"));
	}


	/**
	 * 维修完成详情
	 */
	@RequestMapping(value = "repairOverForm")
	public String repairOverForm(String formId, Model model) {
		model.addAttribute("commonFromId",formId);
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", formId);
		return "modules/repair/htRepairOverForm";
	}
	/**
	 * 维修完成待审核详情
	 */
	@RequestMapping(value = "repairOverAuditForm")
	public String repairOverAuditForm(String fromId) {

		return "modules/repair/htRepairOverAuditForm";
	}







	//和德方案选择
	@RequestMapping(value = "htSelect")
	public String htSelect(String assemblyId, String phoneModelId,String hdProjectId, String selectData, Model model) {
		String selectDataJson = EncodeUtils.decodeUrl(selectData);
		if (JsonMapper.fromJson(selectDataJson, Map.class) != null) {
			model.addAttribute("selectData", selectDataJson);
		}


		model.addAttribute("assemblyId", assemblyId);
		model.addAttribute("phoneModelId", phoneModelId);
		return "modules/repair/htSelect";
	}
	//渤海方案选择
	@RequestMapping(value = "bhSelect")
	public String bhSelect(String formId, String selectData, Model model) {
		String selectDataJson = EncodeUtils.decodeUrl(selectData);
		if (JsonMapper.fromJson(selectDataJson, Map.class) != null) {
			model.addAttribute("selectData", selectDataJson);
		}

		model.addAttribute("formId", formId);
		return "modules/repair/bhSelect";
	}


	//和德方案数据
	@RequestMapping(value = "selectListData")
	@ResponseBody
	public Page<Map<String,Object>> selectListData(String assemblyId, String phoneModelId, HttpServletRequest request, HttpServletResponse response) {

		List<Map<String,Object>> listMap = htClaimSettlementFormService.getAccessoriesInfoList(assemblyId,phoneModelId);
		Page<Map<String,Object>> page = new Page<>(request, response);
		page.setList(listMap);

		return page;
	}

	//渤海方案数据
	@RequestMapping(value = "bhSelectListData")
	@ResponseBody
	public Page<BhFaultplandict> bhSelectListData( String formId, HttpServletRequest request, HttpServletResponse response) {
		BhFormInfo bhFormInfo = new BhFormInfo();
		bhFormInfo.setFormId(formId);
		List<BhFormInfo> bhInfoList= bhFormInfoService.findList(bhFormInfo);
		if(bhInfoList.size() > 0){
			bhFormInfo = bhInfoList.get(0);
		}
		BhFaultplandict bhFaultplandict = new BhFaultplandict();
		bhFaultplandict.setModelid(bhFormInfo.getDeviceModel());
		bhFaultplandict.setPage(new Page<>(request, response));

		Page<BhFaultplandict> page = bhFaultplandictService.findPage(bhFaultplandict);

		return page;
	}


	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "clientFormOnly")
	public String clientFormOnly(HtRepairClientHistory htRepairClientForm, Model model) throws UnsupportedEncodingException {

		htRepairClientForm = htRepairClientHistoryService.get(htRepairClientForm);
		HtFormInfo htFormInfo= htFormInfoService.get(htRepairClientForm.getHtFormInfo());

		model.addAttribute("commonFromId",htFormInfo.getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htFormInfo.getId());

		//品牌
		List<HtBrandInfo> brandList = htBrandInfoService.findList(new HtBrandInfo());
		//型号
		List<HtPhoneModelInfo> typeList = htPhoneModelInfoService.findList(new HtPhoneModelInfo());
		//快递公司
		List<HtExpressage> exList = htExpressageService.findList(new HtExpressage());

		String formType = htRepairClientForm.getRepairFormType();
		HtClaimSettlementForm htClaimSettlementForm = new HtClaimSettlementForm();

		htClaimSettlementForm.setHtFormInfo(htRepairClientForm.getHtFormInfo());
		List<HtClaimSettlementForm> setList = htClaimSettlementFormService.findList(htClaimSettlementForm);
		htRepairClientForm.setHtClaimSettlementForm(setList.get(0));
		//理赔资料
		List<Map<String, Object>> claimDataList = htRepairClientFormService.findClaimData(setList.get(0).getSettlementDataId());

		//修理类型
		List<HtBreakdownInfo> breakDownList = htBreakdownInfoService.findList(new HtBreakdownInfo());
		//主副部件
		List<HtAssemblyUnit> unitZhuList = htAssemblyUnitService.findListByStart("1");
		List<HtAssemblyUnit> unitFuList = htAssemblyUnitService.findListByStart("0");
		//把维修网点信息带过去

		List<HtMaintenancePoint> maintenancePointList = htMaintenancePointService.findList(new HtMaintenancePoint());
		model.addAttribute("maintenancePointList", maintenancePointList);

		//获取主副部件名称
		Map<String,String> map = htClaimSettlementFormService.getUnitStrInfo(htFormInfo.getId());
		model.addAttribute("master_unit_str",(String)map.get("master_unit_str"));
		model.addAttribute("vice_parts_str",(String)map.get("vice_parts_str"));


		model.addAttribute("unitZhuList", unitZhuList);
		model.addAttribute("unitFuList", unitFuList);
		model.addAttribute("breakDownList", breakDownList);
		model.addAttribute("exList", exList);
		model.addAttribute("claimDataList", claimDataList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("htFormInfo", htFormInfo);
		model.addAttribute("htRepairClientForm", htRepairClientForm);


		//待联系图片
		if (StringUtils.isNotBlank(htRepairClientForm.getImage())){
			List<String> imageList = Arrays.asList(htRepairClientForm.getImage().split("\\|"));
			model.addAttribute("imageList", imageList);
		}

		//维修完成图片
		if (StringUtils.isNotBlank(htRepairClientForm.getRepairEndImage())){
			List<String> repairEndImageList = Arrays.asList(htRepairClientForm.getRepairEndImage().split("\\|"));
			model.addAttribute("repairEndImageList", repairEndImageList);
		}
		//上传的留存图片
		if (StringUtils.isNotBlank(htRepairClientForm.getUploadEndImg())){
			List<String> uploadEndImageList = Arrays.asList(htRepairClientForm.getUploadEndImg().split("\\|"));
			model.addAttribute("uploadEndImageList", uploadEndImageList);
		}
		//上传的维修工单图片
		if (StringUtils.isNotBlank(htRepairClientForm.getUploadImage())){
			List<String> uploadImageList = Arrays.asList(htRepairClientForm.getUploadImage().split("\\|"));
			model.addAttribute("uploadImageList", uploadImageList);
		}

		//维修完成图片
		if (StringUtils.isNotBlank(htRepairClientForm.getRepairFormImage())){
			List<String> repairFormImageList = Arrays.asList(htRepairClientForm.getRepairFormImage().split("\\|"));
			model.addAttribute("repairFormImageList", repairFormImageList);
		}


		//修改维修方案图片
		if (StringUtils.isNotBlank(htRepairClientForm.getNewImage())){
			List<String> newImageList = Arrays.asList(htRepairClientForm.getNewImage().split("\\|"));
			model.addAttribute("newImageList", newImageList);
		}


		//报价损坏部位相关图片
		if (StringUtils.isNotBlank(htRepairClientForm.getDamageImg())){
			List<String> offerDamageImgList = Arrays.asList(htRepairClientForm.getDamageImg().split("\\|"));
			model.addAttribute("offerDamageImgList", offerDamageImgList);
		}

		if (StringUtils.isNotBlank(htRepairClientForm.getHtClaimSettlementForm().getDamageImgs())){
			List<String> damageImgsList = Arrays.asList(htRepairClientForm.getHtClaimSettlementForm().getDamageImgs().split("\\|"));
			model.addAttribute("damageImgsList", damageImgsList);
		}
		if (StringUtils.isNotBlank(htRepairClientForm.getHtClaimSettlementForm().getIdentityCardImgs())){
			List<String> identityCardImgsList = Arrays.asList(htRepairClientForm.getHtClaimSettlementForm().getIdentityCardImgs().split("\\|"));
			model.addAttribute("identityCardImgsList", identityCardImgsList);
		}
		if (StringUtils.isNotBlank(htRepairClientForm.getHtClaimSettlementForm().getPurchaseImgs())){
			List<String> purchaseImgsList = Arrays.asList(htRepairClientForm.getHtClaimSettlementForm().getPurchaseImgs().split("\\|"));
			model.addAttribute("purchaseImgsList", purchaseImgsList);
		}
		//repair_offer为跳转待报价表单
		if("repair_charge".equals(htRepairClientForm.getActivityId())
				|| "repair_manager".equals(htRepairClientForm.getActivityId())
				|| "repair_pay".equals(htRepairClientForm.getActivityId())
				|| "repair_offer".equals(htRepairClientForm.getActivityId())){
			HtRepairOfferPartHistory htRepairOfferPart = new HtRepairOfferPartHistory();
			htRepairOfferPart.setClientHistoryId(htRepairClientForm.getId());

			List<HtRepairOfferPartHistory> offerPartList = htRepairOfferPartHistoryService.findList(htRepairOfferPart);
			model.addAttribute("offerPartList", offerPartList);

			List<HtAssemblyUnit> assemblyUnitList = htAssemblyUnitService.findList(new HtAssemblyUnit());
			model.addAttribute("assemblyUnitList", assemblyUnitList);
			return "modules/repair/htRepairOfferFormHistory";

		}
		//跳转财务确认自付款
		if("repair_finance".equals(htRepairClientForm.getActivityId())){
			HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
			htDeductibleInfo.setHtFormInfo(htFormInfo);
			List<HtDeductibleInfo> HtDeductibleInfoList = htDeductibleInfoService.findList(htDeductibleInfo);
			if(HtDeductibleInfoList.size() > 0 ){
				model.addAttribute("HtDeductibleInfo", HtDeductibleInfoList.get(0));
			}
			return "modules/repair/htRepairOfferFormPayHistory";

		}

		//跳转待维修历史
		if("repair_wait".equals(htRepairClientForm.getActivityId())){

			return "modules/repair/htRepairWaitFormHistory";

		}
		//跳转第三次提交维修时间历史
		if("repair_charge_over".equals(htRepairClientForm.getActivityId())){

			return "modules/repair/htRepairWaitFormOnly";

		}

		//跳转维修完成待审核
		if("repair_end".equals(htRepairClientForm.getActivityId())){
			String treeNames = "";
			if(StringUtils.isNotEmpty(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode())){
				Area area = areaService.get(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode());
				treeNames = StringUtils.replace(area.getTreeNames(),"/"," ");
			}
			model.addAttribute("treeNames", treeNames);
			return "modules/repair/htRepairEndFormHistory";
		}
		if("repair_end_check".equals(htRepairClientForm.getActivityId())){

			HtGroupProductInfo htGroupProductInfo = policyInfoService.findGpBypolicyId(htFormInfo.getPolicyInfo().getId());
			model.addAttribute("HtDeductibleInfo", htGroupProductInfo);
			String treeNames = "";
			if(StringUtils.isNotEmpty(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode())){
				Area area = areaService.get(htRepairClientForm.getHtClaimSettlementForm().getReturnAreaCode());
				treeNames = StringUtils.replace(area.getTreeNames(),"/"," ");
			}
			model.addAttribute("treeNames", treeNames);

			String[] imageArr = StringUtils.split(htRepairClientForm.getRepairFormImage(),"|");
			if(imageArr.length >= 2){
				return "modules/repair/htRepairEndFormUploadHistory";
			}

			return "modules/repair/htRepairEndFormHistory";
		}

		return "modules/repair/htRepairClientFormHistory";
	}


	@RequestMapping({"redeployTask"})
	@ResponseBody
	public String redeployTask(HtRepairClientForm htRepairClientForm,String verifier,String redeployRemark) {
		BpmTask params = new BpmTask();
		Task task =  actTaskUtils.getTask(htRepairClientForm.getHtFormInfo().getId());
		params.setComment(redeployRemark);
		params.setUserCode(verifier);
		params.setId(task.getId());
		try {
			bpmTaskService.turnTask(params);
			return this.renderResult("true", text("改派成功", new String[0]));
		} catch (Exception var4) {
			return this.renderResult("false", text("改派失败", new String[0]), var4);
		}
	}

	@RequestMapping(value = "redeploy")
	public String redeploy(Model model, HtRepairClientForm htRepairClientForm) {
		Role role = new Role();
		role.setRoleCode("repair_info");
		List<User> userList = userService.findListByRoleCode(new User(role));
		Task task =  actTaskUtils.getTask(htRepairClientForm.getHtFormInfo().getId());
		model.addAttribute("task", task);
		model.addAttribute("htRepairClientForm", htRepairClientForm);
		model.addAttribute("list", userList);
		return "modules/repair/htRepairClientForm/redeploy";
	}

	@RequestMapping(value = "formInfo")
	public String formInfo(Model model, HtRepairClientForm htRepairClientForm) {

		HtFormInfo htFormInfo= htFormInfoService.get(htRepairClientForm.getHtFormInfo());
		model.addAttribute("commonFromId",htFormInfo.getId());
		model.addAttribute("commonApply", true);
		model.addAttribute("commonApplyId", htFormInfo.getId());
		model.addAttribute("htFormInfo", htFormInfo);
		return "modules/repair/htRepairFormInfo";
	}






}