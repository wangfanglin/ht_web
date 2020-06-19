/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementform.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.cs.enetity.OutBoundParameter;
import com.jeesite.modules.cs.enetity.OutBoundResult;
import com.jeesite.modules.cs.serivce.CsService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.htbreakdowninfo.entity.HtBreakdownInfo;
import com.jeesite.modules.htbreakdowninfo.service.HtBreakdownInfoService;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyDetailService;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htcalllog.entity.HtCallLog;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import com.jeesite.modules.sys.entity.*;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.template.utils.NoteTemplateUtils;
import com.jeesite.modules.util.CommaJointUtil;
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
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;

import java.util.*;

/**
 * 在线理赔表Controller
 *
 * @author hongmengzhong
 * @version 2020-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/htclaimsettlementform/htClaimSettlementForm")
public class HtClaimSettlementFormController extends BaseController {

    @Autowired
    private HtClaimSettlementFormService htClaimSettlementFormService;
    @Autowired
    private HtAssemblyUnitService htAssemblyUnitService;
    @Autowired
    private HtBreakdownInfoService htBreakdownInfoService;
    @Autowired
    private ChannelProductInfoService channelProductInfoService;
    @Autowired
    private HtBrandInfoService htBrandInfoService;
    @Autowired
    private HtPhoneModelInfoService htPhoneModelInfoService;
    @Autowired
    private BpmTaskService bpmTaskService;
    @Autowired
    private ActTaskUtils actTaskUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private HtCallLogService htCallLogService;
    @Autowired
    private CsService csService;
    @Autowired
    private HtRepairClientFormService htRepairClientFormService;
    @Autowired
    private HtFormInfoService htFormInfoService;
    @Autowired
    private HtMaintenancePointService htMaintenancePointService;
    @Autowired
    private PolicyDetailService policyDetailService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private HtUserApplyInfoService htUserApplyInfoService;
    @Autowired
    private EmployeeService employeeService;


    /**
     * findListByStart
     * 获取数据
     */
    @ModelAttribute
    public HtClaimSettlementForm get(String id, boolean isNewRecord) {
        return htClaimSettlementFormService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("htclaimsettlementform:htClaimSettlementForm:view")
    @RequestMapping(value = {"list", ""})
    public String list(HtClaimSettlementForm htClaimSettlementForm, Model model, String type) {
        htClaimSettlementForm.setTypeClose(type);
        List<ChannelProductInfo> productInfoList = channelProductInfoService.findList(new ChannelProductInfo());
        model.addAttribute("productInfoList", productInfoList);
        model.addAttribute("htClaimSettlementForm", htClaimSettlementForm);
        return "modules/settlementform/htclaimsettlementform/htClaimSettlementFormList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("htclaimsettlementform:htClaimSettlementForm:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<ProcessListingEntity> listData(HtClaimSettlementForm htClaimSettlementForm, HttpServletRequest request, HttpServletResponse response) {
        htClaimSettlementForm.setPage(new Page<>(request, response));
        ProcessListingEntity processListingEntity = new ProcessListingEntity();
        processListingEntity.setHtClaimSettlementForm(htClaimSettlementForm);
        Page<ProcessListingEntity> processInstanceList = htClaimSettlementFormService.selectProcessInstanceByQueryCriteria(processListingEntity, request, response);
        return processInstanceList;
    }

    /**
     * 查看编辑表单
     */
   // @RequiresPermissions("htclaimsettlementform:htClaimSettlementForm:view")
    @RequestMapping(value = "form")
    public String form(HtClaimSettlementForm htClaimSettlementForm, Model model) {
        BpmParams bpmParams = htClaimSettlementForm.getBpm();
        HtFormInfo formInfo = htFormInfoService.get(htClaimSettlementForm.getHtFormInfo());
        HtUserApplyInfo htUserApplyInfo = formInfo.getHtUserApplyInfo();
        Map<String, Object> finfArea = new HashMap<>();
        Area area = new Area();
        if (htUserApplyInfo==null){
            model.addAttribute("status","error");
        }else{
            finfArea = htUserApplyInfoService.finfArea(htUserApplyInfo.getAreaId());

              if (finfArea.get("province_code")!=null){
                area = areaService.get(finfArea.get("province_code").toString());
            }
            if(finfArea.get("city_code")!=null){
                  area = areaService.get(finfArea.get("city_code").toString());
              }
              if (finfArea.get("area_code")!=null){
                  area = areaService.get(finfArea.get("area_code").toString());
              }
        }
        //htClaimSettlementForm.setFormId(htClaimSettlementForm.getHtFormInfo().getId());
        PolicyDetail policyDetail = new PolicyDetail();
        policyDetail.setPolicyInfo(new PolicyInfo(formInfo.getPolicyInfo().getId()));
        List<PolicyDetail> policyDetails = policyDetailService.findList(policyDetail);
        List<HtClaimSettlementForm> list = htClaimSettlementFormService.findList(htClaimSettlementForm);
        if (list.size() > 0) {
            htClaimSettlementForm = list.get(0);
            htClaimSettlementForm.setBpm(bpmParams);
        }
        List<HtBrandInfo> brandInfoList = htBrandInfoService.getBrandList();
        List<HtBreakdownInfo> breakDownList = htBreakdownInfoService.findList(new HtBreakdownInfo());
        List<HtAssemblyUnit> unitZhuList = htAssemblyUnitService.findListByStart("1");
        List<HtAssemblyUnit> unitFuList = htAssemblyUnitService.findListByStart("0");
        List<Map<String, Object>> claimDataList = channelProductInfoService.findClaimData();
        if (htClaimSettlementForm.getPolicyInfo() != null) {
            List<Map<String, Object>> claimDataListWant = channelProductInfoService.findClaimDataByChannelProductId(htClaimSettlementForm.getPolicyInfo().getChannelProductId());
            List<String> claimDataStr = new ArrayList<>();
            if (claimDataListWant.size() > 0) {
                for (Map<String, Object> map : claimDataListWant) {
                    claimDataStr.add((String) map.get("clain_data_id"));
                }
            }
            if (claimDataStr.size() > 0) {
                htClaimSettlementForm.setClaimDataStr(CommaJointUtil.commaJointString(claimDataStr));
            }
        }
        //channelProductInfoService.findClaimDataByChannelProductId();

        htClaimSettlementForm.setReturnAddress(htClaimSettlementForm.getReturnAddress());
        List<HtPhoneModelInfo> htPhoneModelInfoList = htPhoneModelInfoService.findList(new HtPhoneModelInfo());
        if (htUserApplyInfo!=null){
            StringBuilder damageImgStr = new StringBuilder(htUserApplyInfo.getBadSideWimg());
            damageImgStr.append("|" + htUserApplyInfo.getBadSideOimg());
            damageImgStr.append("|" + htUserApplyInfo.getBadReverseImg());
            damageImgStr.append("|" + htUserApplyInfo.getBadFrontImg());
            StringBuilder idCardImgStr = new StringBuilder(htUserApplyInfo.getCardReverseImg());
            idCardImgStr.append("|" + htUserApplyInfo.getCardFrontImg());
            idCardImgStr.append("|" + htUserApplyInfo.getCardHandImg());
            htClaimSettlementForm.setDamageImgs(damageImgStr.toString());
            htClaimSettlementForm.setIdentityCardImgs(idCardImgStr.toString());
            htClaimSettlementForm.setPurchaseImgs(htUserApplyInfo.getVoucherImg());
        }
        if (StringUtils.isNotBlank(htClaimSettlementForm.getDamageImgs())) {
            List<String> damageImgsList = Arrays.asList(htClaimSettlementForm.getDamageImgs().split("\\|"));
            model.addAttribute("damageImgsList", damageImgsList);
        }
        if (StringUtils.isNotBlank(htClaimSettlementForm.getIdentityCardImgs())) {
            List<String> identityCardImgsList = Arrays.asList(htClaimSettlementForm.getIdentityCardImgs().split("\\|"));
            model.addAttribute("identityCardImgsList", identityCardImgsList);
        }
        if (StringUtils.isNotBlank(htClaimSettlementForm.getPurchaseImgs())) {
            List<String> purchaseImgsList = Arrays.asList(htClaimSettlementForm.getPurchaseImgs().split("\\|"));
            model.addAttribute("purchaseImgsList", purchaseImgsList);
        }
        htClaimSettlementForm.setHtUserApplyInfo(htUserApplyInfo);
        htClaimSettlementForm.setArea(area);
        model.addAttribute("finfArea", finfArea);
        model.addAttribute("policyDetails", policyDetails);
        model.addAttribute("htUserApplyInfo", htUserApplyInfo);
        model.addAttribute("htPhoneModelInfoList", htPhoneModelInfoList);
        model.addAttribute("brandInfoList", brandInfoList);
        model.addAttribute("claimDataList", claimDataList);
        model.addAttribute("breakDownList", breakDownList);
        model.addAttribute("unitZhuList", unitZhuList);
        model.addAttribute("unitFuList", unitFuList);
        model.addAttribute("htClaimSettlementForm", htClaimSettlementForm);
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", htClaimSettlementForm.getHtFormInfo().getId());
        return "modules/settlementform/htclaimsettlementform/htClaimSettlementFormForm";
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("htclaimsettlementform:htClaimSettlementForm:view")
    @RequestMapping(value = "formInfo")
    public String formInfo(HtClaimSettlementForm htClaimSettlementForm, Model model, String formId) {
        HtFormInfo htFormInfo = htFormInfoService.get(formId);
        model.addAttribute("htFormInfo", htFormInfo);
        Task task = actTaskUtils.getTask(formId);
        String id = task.getId();
        BpmTask bpmTask = bpmTaskService.getTask(id);
        BpmParams bpmParams = new BpmParams();
        bpmParams.setActivityId(bpmTask.getActivityId());
        bpmParams.setProcInsId(bpmTask.getProcIns().getId());
        bpmParams.setTaskId(id);
        htClaimSettlementForm.setBpm(bpmParams);
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", formId);
        model.addAttribute("htClaimSettlementForm", htClaimSettlementForm);
        model.addAttribute("task", bpmTask);
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", formId);
        return "modules/settlementform/htclaimsettlementform/htClaimSettlementFormInfo";
    }

    /**
     * 保存在线理赔表
     */
    //@RequiresPermissions("htclaimsettlementform:htClaimSettlementForm:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated HtClaimSettlementForm htClaimSettlementForm) {
		/*if (StringUtils.isNotBlank(htClaimSettlementForm.getReturnAreaCode())){
			Area area = new Area();
			area.setAreaCode(htClaimSettlementForm.getReturnAreaCode());
			Area areaInfo = areaService.get(area);
			String returnAddress = areaInfo.getTreeNames()+" "+htClaimSettlementForm.getReturnAddress();
			htClaimSettlementForm.setReturnAddress(returnAddress);
		}*/
        String contactStatus = htClaimSettlementForm.getContactStatus();
        Date againContactDate = htClaimSettlementForm.getAgainContactDate();
        if (!"1".equals(contactStatus)){
            if (againContactDate==null){
                return renderResult(Global.FALSE, text("未联系成功时，请选择再次联系时间！"));
            }
        }
        String isQualified = htClaimSettlementForm.getIsQualified();
        String maintainType = htClaimSettlementForm.getMaintainType();
        if ("1".equals(isQualified)){
        if("2".equals(maintainType)){ //需要是维修的情况下判断是否选择维修网点 且 机构下必须有操作人员
            String maintainBranchId = htClaimSettlementForm.getMaintainBranchId();
            if (maintainBranchId == null || "".equals(maintainBranchId)) {
                return renderResult(Global.FALSE, text("请选择维修网点！"));
            }
            String userCodes = htRepairClientFormService.getUserCodes(maintainBranchId);
            if (userCodes == null || "".equals(userCodes)) {
                return renderResult(Global.FALSE, text("选择机构下暂无操作人员！"));
            }
            }
        }
//        if (!"1".equals(htClaimSettlementForm.getContactStatus()) || (StringUtils.isNotBlank(htClaimSettlementForm.getMaintainId()) && StringUtils.isNotBlank(htRepairClientFormService.getUserCodes(htClaimSettlementForm.getMaintainBranchId())))) {

          //  htClaimSettlementForm.setSettlementDataId(htClaimSettlementForm.getClaimDataStr());
            htClaimSettlementFormService.save(htClaimSettlementForm);
            return renderResult(Global.TRUE, text("保存在线理赔表成功！"));
        // }
    }

    /**
     * 删除在线理赔表
     */
    @RequiresPermissions("htclaimsettlementform:htClaimSettlementForm:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(HtClaimSettlementForm htClaimSettlementForm) {
        htClaimSettlementFormService.delete(htClaimSettlementForm);
        return renderResult(Global.TRUE, text("删除在线理赔表成功！"));
    }

    @RequestMapping(value = "redeploy")
    public String redeploy(Model model, HtClaimSettlementForm htClaimSettlementForm, String formId, String type) {
        Role role = new Role();
        role.setRoleCode(type);
        List<User> userList = userService.findListByRoleCode(new User(role));
        Task task = actTaskUtils.getTask(formId);
        model.addAttribute("task", task);
        model.addAttribute("htClaimSettlementForm", htClaimSettlementForm);
        model.addAttribute("list", userList);
        return "modules/settlementform/htclaimsettlementform/redeploy";
    }

    @RequestMapping({"recommendMainPoint"})
    @ResponseBody
    public List<Map<String, String>> recommendMainPoint(String formId,String childId) {
        Map<String, String> condition = htFormInfoService.findMainPointCondition(formId,childId);
        List<Map<String, String>> pointConditionResult = htMaintenancePointService.findPointConditionResult();
        List<Map<String, String>> eligibilityList = new ArrayList<>();
        inieligibilityMainPoint(condition, pointConditionResult, eligibilityList);
        for (Map<String, String> stringMap : eligibilityList) {
            Integer formCount = htFormInfoService.findFormCount(stringMap.get("organizationId"));
            if (formCount > 0) {
                stringMap.put("formCount", String.valueOf(formCount));
            }else{
                stringMap.put("formCount", "0");
            }
        }
        return eligibilityList;
    }

    private void inieligibilityMainPoint(Map<String, String> condition, List<Map<String, String>> pointConditionResult, List<Map<String, String>> eligibilityList) {
        if (pointConditionResult != null && condition != null) {
            for (Map<String, String> stringMap : pointConditionResult) {
                boolean isTrue = true;
                int index = 0;
                if (StringUtils.isNotBlank(stringMap.get("channelId")) && StringUtils.isNotBlank(condition.get("channelId")) && stringMap.get("channelId").indexOf(condition.get("channelId")) == -1) {
                    isTrue = false;
                }
                if (StringUtils.isNotBlank(stringMap.get("phoneBrand")) && StringUtils.isNotBlank(condition.get("phoneBrand")) && stringMap.get("phoneBrand").indexOf(condition.get("phoneBrand")) == -1) {
                    isTrue = false;
                }
                if (StringUtils.isNotBlank(stringMap.get("provinceCode")) && StringUtils.isNotBlank(condition.get("provinceCode")) && stringMap.get("provinceCode").indexOf(condition.get("provinceCode")) == -1) {
                    isTrue = false;
                }
                if (StringUtils.isNotBlank(stringMap.get("cityCode")) && StringUtils.isNotBlank(condition.get("cityCode")) && stringMap.get("cityCode").indexOf(condition.get("cityCode")) == -1) {
                    isTrue = false;
                }
                if (StringUtils.isNotBlank(stringMap.get("maintainStandard")) && StringUtils.isNotBlank(condition.get("maintainStandard")) && stringMap.get("maintainStandard").indexOf(condition.get("maintainStandard")) == -1) {
                    isTrue = false;
                }
                if (stringMap.get("channelId") == null || stringMap.get("phoneBrand") == null || stringMap.get("provinceCode") == null
                        || stringMap.get("cityCode") == null || stringMap.get("maintainStandard") == null ||
                        condition.get("channelId") == null || condition.get("phoneBrand") == null || condition.get("provinceCode") == null
                        || condition.get("cityCode") == null || condition.get("maintainStandard") == null) {
                    isTrue = false;
                }
                if (isTrue) {
                    eligibilityList.add(stringMap);
                }
            }
        }
    }


    @RequestMapping({"redeployTask"})
    @ResponseBody
    public String redeployTask(HtClaimSettlementForm htClaimSettlementForm, String verifier, String redeployRemark) {

        Task task = actTaskUtils.getTask(htClaimSettlementForm.getFormId());

        String assignee = task.getAssignee();




        BpmTask bpmTask = new BpmTask();
        bpmTask.setDelegateState("");
        bpmTask.setId(task.getId());
        bpmTask.setComment(redeployRemark);
        bpmTask.setVariableList(new ArrayList<>());




        User currentUser;


        if(StringUtils.isBlank(assignee)){
            currentUser = UserUtils.getUser();
            bpmTask.setCurrentUser(currentUser);
            bpmTaskService.claimTask(bpmTask);
        }else{
            currentUser = new User(assignee);
        }

        bpmTask.setCurrentUser(currentUser);

        bpmTask.setUserCode(verifier);//转办到的usercode




        try {
            bpmTaskService.turnTask(bpmTask);
            return this.renderResult("true", text("成功", new String[0]));
        } catch (Exception var4) {
            return this.renderResult("false", text("失败", new String[0]), var4);
        }
    }

    @RequestMapping({"callInfoSave"})
    @ResponseBody
    public Map<String,String> callInfoSave(String phoneNum) {
        String userCode = UserUtils.getUser().getUserCode();
        HtCallLog htCallLog = new HtCallLog();
        htCallLog.setUserId(userCode);
        htCallLog.setUserPhone(phoneNum);
        htCallLogService.save(htCallLog);
        Employee employee = new Employee();
        employee.setEmpCode(userCode);
         employee = employeeService.get(employee);

        OutBoundParameter boundParameter = new OutBoundParameter(employee.getEmpNo(), phoneNum, "Local", htCallLog.getId());
        OutBoundResult outBoundResult = csService.outbound(boundParameter);
        HashMap<String, String> result = MapUtils.newHashMap();
        if ("true".equals(outBoundResult.getStatus())) {
            result.put("result","true");
            result.put("message","成功");
            result.put("id",htCallLog.getId());
            return result;
        } else {
            result.put("result", "false");
            result.put("message", "失败" + outBoundResult.getMessage());
            result.put("id", null);
            return result;
        }
    }

    @RequestMapping({"getSmsString"})
    @ResponseBody
    public  HashMap<String, String> getSmsString(String officeId, String [] arr,String maintainType) {
        HashMap<String, String> resulet = MapUtils.newHashMap();
        String template = "";
        if ("1".equals(maintainType)){
            template = NoteTemplateUtils.noteTemplateOneRepair(arr);
        }
        if (maintainType.equals("2")){
            HtMaintenancePoint maintenancePoint = htMaintenancePointService.findInfoByJG(officeId);
            if (maintenancePoint==null){
                resulet.put("data",template);
                resulet.put("status","error");
                resulet.put("msg","当前维修网点不可用！");
                return resulet;
            }
            Area area = areaService.get(maintenancePoint.getArea());
            String address = area.getTreeNames()+maintenancePoint.getAddress();
            address = address.replace("/","");
            String master = maintenancePoint.getContactOne()==null?maintenancePoint.getContactTwo():maintenancePoint.getContactOne();
            String phone = maintenancePoint.getContactNumberOne()==null?maintenancePoint.getContactNumberTwo():maintenancePoint.getContactNumberOne();
            template = NoteTemplateUtils.noteTemplateOneRenew(arr, address, master, phone.toString());
        }
        resulet.put("data",template);
        resulet.put("status","success");
        resulet.put("msg","获取短信成功！");
        return resulet;
    }
}