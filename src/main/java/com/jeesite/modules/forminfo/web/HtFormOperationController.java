/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.dao.HtFormOperationDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.entity.HtFormOperation;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.forminfo.service.HtFormOperationService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 工单操作表Controller
 *
 * @author zhaohaifeng
 * @version 2020-03-02
 */
@Controller
@RequestMapping(value = "${adminPath}/forminfo/htFormOperation")
public class HtFormOperationController extends BaseController {

    @Autowired
    private HtFormOperationService htFormOperationService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private HtFormInfoService formInfoService;
    @Autowired
    private BpmTaskService bpmTaskService;
    @Autowired
    private HtHistoryService historyService;
    @Autowired
    private HtFormOperationDao formOperationDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ActTaskUtils actTaskUtils;
    @Autowired
    private HtClaimSettlementFormService claimSettlementFormService;
    @Autowired
    private HtFormInfoDao formInfoDao;
    @Autowired
    private HtRepairClientFormService repairClientFormService;
    @Autowired
    private AreaService areaService;





    @RequestMapping(value = "reassignment")
    public String reassignment(Model model, HtFormOperation htFormOperation, String formId, String taskId, String procInsId, String activityId, String id) {
        //此处id为表单id
        BpmParams bpm = htFormOperation.getBpm();
        bpm.setActivityId(activityId);
        bpm.setTaskId(taskId);
        bpm.setProcInsId(procInsId);
        htFormOperation.setBpm(bpm);
        htFormOperation.setFormId(formId);

        htFormOperation.setBdId(id);

        model.addAttribute("htFormOperation", htFormOperation);
        return "modules/forminfo/reassignment";
    }

    @RequestMapping(value = "close")
    public String close(Model model, HtFormOperation htFormOperation, String formId, String taskId, String procInsId, String activityId, String id) {
        BpmParams bpm = htFormOperation.getBpm();
        bpm.setActivityId(activityId);
        bpm.setTaskId(taskId);
        htFormOperation.setFormId(formId);
        bpm.setProcInsId(procInsId);
        htFormOperation.setBpm(bpm);
        htFormOperation.setBdId(id);
        model.addAttribute("htFormOperation", htFormOperation);
        HtFormInfo formInfo = formInfoService.get(formId);
        String formType = formInfo.getFormType();
        String role="";
        if ("0".equals(formType)){role="claim_charge";}//核赔经理
        if ("2".equals(formType)){role="repair_charge";}//维修经理
        if ("1".equals(formType)){role="renew_manager";}//换新经理
        if ("4".equals(formType)){role="client_charge";}//客服经理
        ArrayList<Map<String, String>> list = htFormOperationService.findUser(role);
        model.addAttribute("list", list);
        return "modules/forminfo/close";
    }

    @RequestMapping(value = "reopen")
    public String reopen(Model model, HtFormOperation htFormOperation, String formId, String id) {
        Task task = actTaskUtils.getTask(formId);
        String taskId = task.getId();
        BpmTask bpmTask = bpmTaskService.getTask(taskId);
        String activityId = bpmTask.getActivityId();
        String procInsId = bpmTask.getProcIns().getId();
        BpmParams bpm = htFormOperation.getBpm();
        bpm.setActivityId(activityId);
        bpm.setTaskId(taskId);
        bpm.setProcInsId(procInsId);
        htFormOperation.setBpm(bpm);
        htFormOperation.setFormId(formId);
        htFormOperation.setBdId(id);

        model.addAttribute("htFormOperation", htFormOperation);
        HtFormInfo formInfo = formInfoService.get(formId);
        String formType = formInfo.getFormType();
        String role="";
        if ("0".equals(formType)){role="claim_charge";}
        if ("2".equals(formType)){role="repair_charge";}
        if ("1".equals(formType)){role="renew_manager";}//换新经理
        if ("4".equals(formType)){role="client_charge";}//客服经理
        ArrayList<Map<String, String>> list = htFormOperationService.findUser(role);
        model.addAttribute("list", list);
        return "modules/forminfo/reopen";
    }


    /**
     * 工单详情（审核改派）
     */
    @RequestMapping(value = "repairToAuditForm")
    public String repairToAuditForm(HtFormOperation formOperation, Model model) {
        //把维修网点信息带过去
//
        BpmParams bpm = formOperation.getBpm();
        formOperation = htFormOperationService.findByFormId(formOperation.getFormId());
        formOperation.setBpm(bpm);
        String intermediaryServiceId = formOperation.getIntermediaryServiceId();
        if (!"".equals(intermediaryServiceId)&&null!=intermediaryServiceId){
            Office office = officeService.get(intermediaryServiceId);
            String officeName = office.getOfficeName();
            formOperation.setOfficeName(officeName);
        }else{
            HtFormOperation fromOp = htFormOperationService.findOpByFormId(formOperation.getFormId());
            if (fromOp!=null){
                Office office = officeService.get(fromOp.getIntermediaryServiceId());
                String officeName = office.getOfficeName();
                formOperation.setOfficeName(officeName);
                formOperation.setIntermediaryServiceId(fromOp.getIntermediaryServiceId());
            }
        }
        model.addAttribute("formOperation", formOperation);
        model.addAttribute("commonFromId", formOperation.getFormId());
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", formOperation.getFormId());

        return "modules/formOperation/htRepairReassignmentAuditForm";
    }

    /**
     * 工单详情（审核重启）
     */
    @RequestMapping(value = "repairReopenAuditForm")
    public String repairReopenAuditForm(HtFormOperation formOperation, Model model) {
        BpmParams bpm = formOperation.getBpm();
        formOperation = htFormOperationService.findByFormId(formOperation.getFormId());
        formOperation.setBpm(bpm);
        model.addAttribute("formOperation", formOperation);
        model.addAttribute("commonFromId", formOperation.getFormId());
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", formOperation.getFormId());
        return "modules/formOperation/htRepairReopenAuditForm";
    }

    /**
     *   核赔的 工单详情（审核关闭）   备注： 核赔的关闭待审核 和 维修的关闭待审核长得不一样
     */
    @RequestMapping(value = "repairCloseAuditForm")
    public String repairCloseAuditForm(HtFormOperation formOperation, Model model) {
        BpmParams bpm = formOperation.getBpm();
        formOperation = htFormOperationService.findByFormId(formOperation.getFormId());
        formOperation.setBpm(bpm);
        model.addAttribute("formOperation", formOperation);
        model.addAttribute("commonFromId", formOperation.getFormId());
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", formOperation.getFormId());
        return "modules/formOperation/htRepairCloseAuditForm";
    }


    /**
     *   维修的 工单详情（审核关闭）
     */
    @RequestMapping(value = "repairCloseAuditFormWx")
    public String repairCloseAuditFormWx(HtFormOperation formOperation, Model model) {
        BpmParams bpm = formOperation.getBpm();
        String formId = formOperation.getFormId();
        formOperation = htFormOperationService.findByFormId(formId);
        formOperation.setBpm(bpm);
       // String bdId = formOperation.getBdId();

        HtClaimSettlementForm claimSettlementForm = claimSettlementFormService.getByFormId(formId);
        Area area = areaService.get(new Area(claimSettlementForm.getReturnAreaCode()));
        String treeNames = area.getTreeNames();
        model.addAttribute("treeNames", treeNames.replace("/"," "));
        model.addAttribute("name", claimSettlementForm.getUserName());
        model.addAttribute("phone", claimSettlementForm.getPhone());
        model.addAttribute("address", claimSettlementForm.getReturnAddress());
        model.addAttribute("formOperation", formOperation);
        model.addAttribute("commonFromId", formId);
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", formId);
        return "modules/formOperation/htRepairCloseAuditFormWx";
    }


    /**
     *   待申请的 工单（审核关闭）
     */
    @RequestMapping(value = "repairCloseAuditFormDsh")
    public String repairCloseAuditFormDsh(HtFormOperation formOperation, Model model) {
        BpmParams bpm = formOperation.getBpm();
        String formId = formOperation.getFormId();
        formOperation = htFormOperationService.findByFormId(formId);
        formOperation.setBpm(bpm);
        // String bdId = formOperation.getBdId();

        model.addAttribute("formOperation", formOperation);
        model.addAttribute("commonFromId", formId);
        model.addAttribute("commonApply", false);
        model.addAttribute("commonApplyId", formId);
        return "modules/formOperation/htRepairCloseAuditFormDsh";
    }











    @RequestMapping({"back"})
    public String back(BpmTask params, Model model,String formId,String bdId,String operationType,String procInsId) {
        BpmTask task = bpmTaskService.getTask(params.getId());
        if (task != null) {
            List<Map<String, String>> backActivity = bpmTaskService.getBackActivity(task.getProcIns().getId());
            model.addAttribute("backActivity", backActivity);
            params = task;
        }
        model.addAttribute("formId", formId);
        model.addAttribute("operationType", operationType);
        model.addAttribute("bdId", bdId);
        model.addAttribute("task", params);
        model.addAttribute("procInsId", procInsId);
        return "modules/formOperation/back";
    }

    @RequestMapping({"backOne"})
    public String backOne(String activity, Model model,String formId) {

        Task task = actTaskUtils.getTask(formId);

        model.addAttribute("activity", activity);
        model.addAttribute("formId", formId);
        model.addAttribute("name", task.getName());

        return "modules/formOperation/backOne";
    }

    @RequestMapping({"backOneTask"})
    @ResponseBody
    public String backOneTask(BpmTask params,String formId,String comment) {
        HtFormInfo formInfo = formInfoService.get(formId);
        Office office = formInfo.getOffice();
        //nowActivity    当前的 环节

        Task task = actTaskUtils.getTask(formId);
        String taskId = task.getId();//任务ID
        BpmTask bpmTask = bpmTaskService.getTask(taskId);
      //  String currentUserCode = bpmTask.getCurrentUser().getUserCode();//当前的任务处理人
        String nowActivity = bpmTask.getActivityId();//当前环节ID
        String procInsId = bpmTask.getProcIns().getId();//实例ID
        BpmParams bpmParams = new BpmParams();
        bpmParams.setTaskId(taskId);
        bpmParams.setProcInsId(procInsId);
        bpmParams.setActivityId(nowActivity);

        HtHistory htHistory = new HtHistory();
        String formType = formInfo.getFormType();
        //服务器用户
        String server = UserUtils.get("0_server_skus").getUserCode();


        String userCodes ="";
        if ("nowActivity".equals(nowActivity)){
        //待申请环节申请关闭  驳回     回到上一个处理人
            formInfo.setFormStatus(FormStatus.DSQ_SB_DFP);
            formInfo.setManageStatus(ManageStatus.DSQ_WLXCG_DFP);
            //查出client_info  角色下所有人
            ArrayList<Map<String, String>> list = htFormOperationService.findUser("client_info");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i==0){
                    str.append(list.get(i).get("user_code"));
                }else{
                    str.append(",").append(list.get(i).get("user_code"));
                }
            }
            userCodes = str.toString();
            htHistory.setOperationStatus(ManageStatus.DSQ_WLXCG_DFP);
        }
        if ("client_check_restart".equals(nowActivity)){
            //在线申请   重启驳回     回到流程发起人
            formInfo.setFormStatus(FormStatus.DSQ_GB);
            formInfo.setManageStatus(ManageStatus.DSQ_GB_DCQ);
            userCodes = server;
            formInfo.setIsRun("1");
            htHistory.setOperationStatus(ManageStatus.DSQ_GB_DCQ);
        }
        if ("claim_close".equals(nowActivity)){
            //在线理赔 申请关闭 驳回    回到上一个处理人
            formInfo.setFormStatus(FormStatus.LP_DCL);
            formInfo.setManageStatus(ManageStatus.LP_ZLYGX_DLX);
            //需要退回到 核赔角色下的用户
            ArrayList<Map<String, String>> list = htFormOperationService.findUser("claim_info");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i==0){
                    str.append(list.get(i).get("user_code"));
                }else{
                    str.append(",").append(list.get(i).get("user_code"));
                }
            }
            userCodes=str.toString();
            htHistory.setOperationStatus(ManageStatus.LP_ZLYGX_DLX);
        }
        if ("claim_check_restart".equals(nowActivity)){
            //在线理赔 申请重启  驳回   这里暂时需要退回到服务器哪里（流程发起人）
            formInfo.setFormStatus(FormStatus.LP_SQGB_SHTG);
            formInfo.setManageStatus(ManageStatus.LP_SQGB_SHTG);
            userCodes = server;
            formInfo.setIsRun("1");
            htHistory.setOperationStatus(ManageStatus.LP_SQGB_SHTG);
        }


        if ("repair_charge_close".equals(nowActivity)||"repair_change_time".equals(nowActivity)){
            //维修申请关闭 / 申请改派  驳回   回到上一个处理人
            formInfo.setFormStatus(FormStatus.WX_DCL);
            formInfo.setManageStatus(ManageStatus.WX_DHSYJZT);
            String officeUserCodes = repairClientFormService.getUserCodes(office.getOfficeCode());
            userCodes = officeUserCodes;
            htHistory.setOperationStatus(ManageStatus.WX_DHSYJZT);
        }
        if ("repair_check_restart".equals(nowActivity)){
            //维修申请重启 驳回 回到已关闭状态 这里暂时需要退回到服务器哪里（流程发起人）
            formInfo.setFormStatus(FormStatus.WX_SQGB_SHTG);
            formInfo.setManageStatus(ManageStatus.WX_SQGB_SHTG);
            userCodes = server;
            formInfo.setIsRun("1");
            htHistory.setOperationStatus(ManageStatus.WX_SQGB_SHTG);
        }

        if ("repair_change_wait".equals(nowActivity)){
            //维修申请改派 驳回 回到上一个处理人
            formInfo.setFormStatus(FormStatus.WX_DCL);
            formInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
            String officeUserCodes = repairClientFormService.getUserCodes(office.getOfficeCode());
            userCodes = officeUserCodes;
            htHistory.setOperationStatus(ManageStatus.WX_BJ_SHTG);
        }
        if ("repair_change_receive".equals(nowActivity)){
            //维修 申请改派 审核驳回  回到上一个处理人
            formInfo.setFormStatus(FormStatus.WX_DLR);
            formInfo.setManageStatus(ManageStatus.WX_YQS_DLR);
            String officeUserCodes = repairClientFormService.getUserCodes(office.getOfficeCode());
             userCodes = officeUserCodes;
            htHistory.setOperationStatus(ManageStatus.WX_YQS_DLR);
        }
        if ("repair_charge".equals(nowActivity)){
            //报价待审核    回到上一个处理人
            formInfo.setFormStatus(FormStatus.WX_DBJ);
            formInfo.setManageStatus(ManageStatus.WX_DBJ);
            String officeUserCodes = repairClientFormService.getUserCodes(office.getOfficeCode());
             userCodes = officeUserCodes;
            htHistory.setOperationStatus(ManageStatus.WX_DBJ);
        }

        if ("repair_end_check".equals(nowActivity)){
            //维修完成审核  驳回     回到上一个处理人
            formInfo.setFormStatus(FormStatus.WX_GPWC);
            formInfo.setManageStatus(ManageStatus.WX_WXWC_DSH);
            String officeUserCodes = repairClientFormService.getUserCodes(office.getOfficeCode());
             userCodes = officeUserCodes;
            htHistory.setOperationStatus(ManageStatus.WX_WXWC_DSH);
        }
        if ("repair_change_offer".equals(nowActivity)){
            //修改维修方案审核 驳回     回到上一个处理人
            formInfo.setFormStatus(FormStatus.WX_DCL);
            formInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
            String officeUserCodes = repairClientFormService.getUserCodes(office.getOfficeCode());
            userCodes = officeUserCodes;
            htHistory.setOperationStatus(ManageStatus.WX_BJ_SHTG);

        }
        if ("renew_close".equals(nowActivity)){
            //换新申请关闭 审核 驳回     回到上一个处理人
            formInfo.setFormStatus(FormStatus.LP_DCL);
            formInfo.setManageStatus(ManageStatus.HX_SHTG);
            ArrayList<Map<String, String>> list = htFormOperationService.findUser("renew_info");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i==0){
                    str.append(list.get(i).get("user_code"));
                }else{
                    str.append(",").append(list.get(i).get("user_code"));
                }
            }
            userCodes = str.toString();
            htHistory.setOperationStatus(ManageStatus.HX_SHTG);

        }
        if ("renew_check_restart".equals(nowActivity)){
            //换新申请重启 审核 驳回     流程发起人
            formInfo.setFormStatus(FormStatus.HX_YGB);
            formInfo.setManageStatus(ManageStatus.HX_YGB);
            userCodes = server;
            formInfo.setIsRun("1");
            htHistory.setOperationStatus(ManageStatus.HX_YGB);

        }
        if ("renew_manager".equals(nowActivity)){
            //换新方案 审核 驳回     回到上一个处理人
            formInfo.setFormStatus(FormStatus.HX_FA_DTJ);
            formInfo.setManageStatus(ManageStatus.HX_SHTG);
            ArrayList<Map<String, String>> list = htFormOperationService.findUser("renew_info");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i==0){
                    str.append(list.get(i).get("user_code"));
                }else{
                    str.append(",").append(list.get(i).get("user_code"));
                }
            }
            userCodes = str.toString();
            htHistory.setOperationStatus(ManageStatus.HX_SHTG);

        }
        if ("renew_director".equals(nowActivity)){
            //换新方案 审核 驳回     回到上一个处理人
            ArrayList<Map<String, String>> list = htFormOperationService.findUser("renew_info");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i==0){
                    str.append(list.get(i).get("user_code"));
                }else{
                    str.append(",").append(list.get(i).get("user_code"));
                }
            }
            formInfo.setFormStatus(FormStatus.HX_FA_DTJ);
            formInfo.setManageStatus(ManageStatus.HX_SHTG);
            userCodes = str.toString();
            htHistory.setOperationStatus(ManageStatus.HX_SHTG);

        }
        if ("repair_manager".equals(nowActivity)){
            //售后经理 审核 驳回
            formInfo.setFormStatus(FormStatus.WX_DBJ);
            formInfo.setManageStatus(ManageStatus.WX_DBJ);
            String officeUserCodes = repairClientFormService.getUserCodes(office.getOfficeCode());
            userCodes = officeUserCodes;
            htHistory.setOperationStatus(ManageStatus.WX_DBJ);
        }

        if ("renew_offer_close".equals(nowActivity)){
            //换新申请关闭 审核 驳回     回到上一个处理人
            formInfo.setFormStatus(FormStatus.LP_DCL);
            formInfo.setManageStatus(ManageStatus.HX_SHTG);
            ArrayList<Map<String, String>> list = htFormOperationService.findUser("renew_info");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i==0){
                    str.append(list.get(i).get("user_code"));
                }else{
                    str.append(",").append(list.get(i).get("user_code"));
                }
            }
            userCodes = str.toString();
            htHistory.setOperationStatus(ManageStatus.HX_SHTG);

        }
        if ("renew_offer_restart".equals(nowActivity)){
            //换新 -申请重启 审核 驳回     流程发起人
            formInfo.setFormStatus(FormStatus.HX_YGB);
            formInfo.setManageStatus(ManageStatus.HX_CXKQ);
            userCodes = server;
            formInfo.setIsRun("1");
            htHistory.setOperationStatus(ManageStatus.HX_CXKQ);
        }

        if ("client_close".equals(nowActivity)){
            //待申请 - 申请关闭 审核 驳回
            ArrayList<Map<String, String>> list = htFormOperationService.findUser("client_info");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i==0){
                    str.append(list.get(i).get("user_code"));
                }else{
                    str.append(",").append(list.get(i).get("user_code"));
                }
            }
            formInfo.setFormStatus(FormStatus.DSQ_CG_DFP);
            formInfo.setManageStatus(ManageStatus.DSQ_LXCG_DFP);
            userCodes = str.toString();
            htHistory.setOperationStatus(ManageStatus.DSQ_LXCG_DFP);
        }
        if ("client_check_restart".equals(nowActivity)){
            //待申请 -申请重启 审核 驳回     流程发起人
            formInfo.setFormStatus(FormStatus.DSQ_GB);
            formInfo.setManageStatus(ManageStatus.DSQ_GB_DCQ);
            userCodes = server;
            formInfo.setIsRun("1");
            htHistory.setOperationStatus(ManageStatus.DSQ_GB_DCQ);
        }



        bpmParams.setNextUserCodes(userCodes);
        bpmParams.setPriority(TaskFormEnum.TaskFormColorEnum.RED.getCode());
        formInfo.setBpm(bpmParams);

        HtFormOperation formOperation = new HtFormOperation();
        String uuid = IdGen.uuid();
        formOperation.setId(uuid);
        formOperation.setFormId(formId);
        formOperation.setVerifier(UserUtils.getUser().getUserCode());
        formOperation.setAuditResult("2");
        formOperation.setAuditRemark(comment);
        formOperation.setType("2");

            htHistory.setActivityName("审核驳回");
        htHistory.setDisposeUserId(UserUtils.getUser().getUserCode());
        htHistory.setCmsVisible("1");
        htHistory.setUserVisible("0");
        htHistory.setActivityId(nowActivity);
        htHistory.setWorkOrderId(formId);
        htHistory.setRemarks(comment);
        htHistory.setFormType(formType);
        htHistory.setIsBack("1");
        htHistory.setUniquenessId(uuid);


        HashMap<String, Object> variables = new HashMap<>();
        variables.put("checkStatus",'9');
        try {
            formOperationDao.insert(formOperation);
            BpmUtils.complete(formInfo, variables, null);
            Task taskNew = taskService.createTaskQuery().processInstanceId(formInfo.getBpm().getProcInsId()).singleResult();
            htHistory.setAfterActivityId(taskNew.getTaskDefinitionKey());
            historyService.save(htHistory);
            formInfoDao.update(formInfo);
            return this.renderResult("true", text("退回成功", new String[0]));
        } catch (Exception var3) {
            logger.error(var3.getMessage());
            return this.renderResult("false", text("退回失败", new String[0]), var3);
        }
    }


    @RequestMapping({"backTask"})
    @ResponseBody
    public String backTask(BpmTask params,String formId,String bdId,String operationType,String manageStatus,String procInsId,String nowActivity) {
       // nowActivity 这个是当前活动的ID  BpmTask params  这个里面的是指派到的活动ID
        String uuid = IdGen.uuid();
        Global.assertDemoMode();
        HtHistory htHistory = new HtHistory();
        //退回原因
        String comment = params.getComment();
        HtFormOperation formOperation = new HtFormOperation();
        String activityId = params.getActivityId();//这个是指派到的活动ID
        try {
            HtFormInfo formInfo = formInfoService.get(formId);
            htHistory.setDisposeUserId(UserUtils.getUser().getUserCode());
            htHistory.setCmsVisible("1");
            htHistory.setUserVisible("1");
            htHistory.setActivityId(nowActivity);
            htHistory.setWorkOrderId(formId);
            htHistory.setFormId(bdId);
            htHistory.setRemarks(comment);
            htHistory.setFormType(formInfo.getFormType());

            if (!"".equals(operationType)&&null!=operationType) {
                String formType = formInfo.getFormType();
                if ("0".equals(formType)){
                    if ("1".equals(operationType)) {
                        htHistory.setOperationStatus(ManageStatus.LP_SQGB_SHBH);
                        htHistory.setActivityName("在线核赔-审核驳回");
                    }
                    if ("2".equals(operationType)) {
                        htHistory.setOperationStatus(ManageStatus.LP_SQCQ_SHBH);
                        htHistory.setActivityName("在线核赔-审核驳回");
                    }
                }
                if ("2".equals(formType)){
                    if ("0".equals(operationType)) {
                    htHistory.setOperationStatus(ManageStatus.WX_SQGP_SHBH);
                    htHistory.setActivityName("维修-审核驳回");
                }
                    if ("1".equals(operationType)) {
                        htHistory.setOperationStatus(ManageStatus.WX_SQGB_SHBH);
                        htHistory.setActivityName("维修-审核驳回");
                    }
                    if ("2".equals(operationType)) {
                        htHistory.setOperationStatus(ManageStatus.WX_SQCQ_SHBH);
                        htHistory.setActivityName("维修-审核驳回");
                    }
                }

                htHistory.setUniquenessId(uuid);
                formOperation.setAuditResult("0");
                formOperation.setId(uuid);
                formOperation.setFormId(formId);
                formOperation.setBdId(bdId);
                formOperation.setOperationType(operationType);
                formOperation.setType("2");
                formOperation.setAuditRemark(comment);
                formOperationDao.insert(formOperation);
            }
//            String userCodes = repairClientFormService.getUserCodes();
//            formInfo.getBpm().setNextUserCodes(userCodes);
            bpmTaskService.backTask(params);
            htHistory.setAfterActivityId(activityId);
            historyService.save(htHistory);
           HtFormInfo form =  htFormOperationService.updateFormStatus(activityId,formInfo);
            formInfoDao.update(form);
            return this.renderResult("true", text("退回成功", new String[0]));
        } catch (Exception var3) {
            logger.error(var3.getMessage());
            return this.renderResult("false", text("退回失败", new String[0]), var3);
        }
    }

    /**
     * 保存工单操作表
     */
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated HtFormOperation htFormOperation,String reopen) {
        String taskId = htFormOperation.getBpm().getTaskId();
        BpmTask task = bpmTaskService.getTask(taskId);
        String assignee = task.getAssignee();
        if (!"3".equals(htFormOperation.getOperationType())){
        if ("".equals(assignee)||null==assignee){
            return this.renderResult("false", text("请先签收任务", new String[0]));
        }
        }
        String type = htFormOperation.getType();
        String text = "";
        if ("1".equals(type)){text = "申请";}
        if ("2".equals(type)){text = "审核";}
        if ("2".equals(type)&&htFormOperation.getIntermediaryServiceId()!=null&&!"".equals(htFormOperation.getIntermediaryServiceId())){
            String userCodes = repairClientFormService.getUserCodes(htFormOperation.getIntermediaryServiceId());
            if (userCodes==null||"".equals(userCodes)){
                return this.renderResult("false", text("此机构下暂无可用可用人员，请重新选择!", new String[0]));
            }
        }
        try {
            htFormOperationService.save(htFormOperation,reopen);
            return this.renderResult("true", text(text + "成功", new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
           if ("101".equals(e.getMessage())){
               return this.renderResult("false", text("发送微信通知失败!", new String[0]));
           }
            if ("202".equals(e.getMessage())){
                return this.renderResult("false", text("维修同步接口调用失败!", new String[0]));
            }

            return this.renderResult("false", text(text + "失败", new String[0]));
        }
    }

    /**
     * 改派、重启、关闭 申请历史 只读页面
     */
    @RequestMapping(value = "readonly")
    public String readonly(String uniquenessId, String activityId, String afterActivityId, Model model,String flag) {
        //需要一个分支操作的唯一标识
        HtFormOperation htFormOperation = htFormOperationService.get(uniquenessId);
        if (StringUtils.isNotBlank(htFormOperation.getUrl())){
            List<String> damageImgsList = Arrays.asList(htFormOperation.getUrl().split("\\|"));
            model.addAttribute("damageImgsList", damageImgsList);
        }

        String formId = htFormOperation.getFormId();
        HtFormInfo formInfo = formInfoService.get(formId);
        String formType = formInfo.getFormType();
        String role="";
        if ("0".equals(formType)){role="claim_charge";}
        if ("2".equals(formType)){role="repair_charge";}
        if ("1".equals(formType)){role="renew_manager";}
        ArrayList<Map<String, String>> list = htFormOperationService.findUser(role);
        model.addAttribute("list", list);
        model.addAttribute("formOperation",htFormOperation); //这个是审核的
        model.addAttribute("htFormOperation",htFormOperation); //这是申请的


        if("1".equals(flag)){}


        if (
            ("client_info".equals(activityId)&&"client_close".equals(afterActivityId))||
            ("repair_info".equals(activityId)&&"repair_charge_close".equals(afterActivityId))||
            ("claim_info".equals(activityId)&&"claim_close".equals(afterActivityId))||
            ("renew_info".equals(activityId)&&"renew_close".equals(afterActivityId))||
            ("renew_offer".equals(activityId)&&"renew_offer_close".equals(afterActivityId))
                ){
            //申请-关闭
            return "modules/forminfo/applyForCloseReadonly";
        }
        if (
         ("client_wait_restart".equals(activityId)&&"client_check_restart".equals(afterActivityId))||
         ("claim_wait_restart".equals(activityId)&&"claim_check_restart".equals(afterActivityId))||
         ("repair_wait_restart".equals(activityId)&&"repair_check_restart".equals(afterActivityId))||
         ("renew_wait_restart".equals(activityId)&&"renew_check_restart".equals(afterActivityId))||
         ("renew_offer_wait".equals(activityId)&&"renew_offer_restart".equals(afterActivityId))
          ){
            return "modules/forminfo/applyForReopenReadonly";//申请-重启
        }

        if (
           ("repair_info".equals(activityId)&&"repair_change_time".equals(afterActivityId))||
          ("repair_wait".equals(activityId)&&"repair_change_wait".equals(afterActivityId))||
          ("repair_charge".equals(activityId)&&"repair_change_wait".equals(afterActivityId))
         ){
            return "modules/forminfo/applyForReassignmentReadonly";//申请-改派
        }

        if (
          ("client_close".equals(activityId)&&"client_wait".equals(afterActivityId))||
          ("claim_close".equals(activityId)&&"claim_wait_restart".equals(afterActivityId))||
          ("repair_charge_close".equals(activityId)&&"repair_wait_restart".equals(afterActivityId))||
          ("renew_close".equals(activityId)&&"renew_wait_restart".equals(afterActivityId))||
          ("renew_offer_close".equals(activityId)&&"renew_offer_wait".equals(afterActivityId))||
          ("client_close".equals(activityId)&&"client_wait_restart".equals(afterActivityId))
          ){
            return "modules/formOperation/auditCloseReadonly";//审核-关闭
        }

        if (
         ("client_check_restar".equals(activityId)&&"client_info".equals(afterActivityId))||
         ("claim_check_restart".equals(activityId)&&"claim_info".equals(afterActivityId))||
         ("renew_check_restart".equals(activityId)&&"renew_info".equals(afterActivityId))||
         ("repair_check_restart".equals(activityId)&&"repair_info".equals(afterActivityId))||
         ("renew_offer_restart".equals(activityId)&&"renew_offer".equals(afterActivityId))||
         ("client_check_restart".equals(activityId)&&"client_info".equals(afterActivityId))
                ){
            return "modules/formOperation/auditReopenReadonly";//审核-重启
        }

        if (
        ("repair_change_wait".equals(activityId)&&"repair_info".equals(afterActivityId))||
        ("repair_change_time".equals(activityId)&&"repair_info".equals(afterActivityId))
        ){
            return "modules/formOperation/audGaiPaiReadonly";//审核-改派
        }

        return null;
    }


}