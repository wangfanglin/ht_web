/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.service;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bh.entity.BhFormInfo;
import com.jeesite.modules.bh.service.BhFormInfoService;
import com.jeesite.modules.bohai.BoHaiInterfaceService;
import com.jeesite.modules.bohai.entity.Result;
import com.jeesite.modules.bohai.entity.ResultStatus;
import com.jeesite.modules.bohai.entity.SynRepairInfoParameter;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.common.OperationStatus;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.dao.HtFormOperationDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.entity.HtFormOperation;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.renew.dao.HtRenewFormDao;
import com.jeesite.modules.renew.entity.HtRenewForm;
import com.jeesite.modules.renew.service.HtRenewFormService;
import com.jeesite.modules.repair.dao.HtRepairClientFormDao;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.template.utils.WxTemplateUtils;
import com.jeesite.modules.user.entity.HtUserInfo;
import com.jeesite.modules.user.service.HtUserInfoService;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * 工单操作表Service
 *
 * @author zhaohaifeng
 * @version 2020-03-02
 */
@Service
@Transactional(readOnly = true)
public class HtFormOperationService extends CrudService<HtFormOperationDao, HtFormOperation> {

    @Autowired
    private HtHistoryService historyService;
    @Autowired
    private HtFormOperationDao formOperationDao;
    @Autowired
    private HtFormInfoService formInfoService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HtRepairClientFormService repairClientFormService;
    @Autowired
    private HtFormInfoDao formInfoDao;
    @Autowired
    private BpmTaskService bpmTaskService;
    @Autowired
    private BoHaiInterfaceService boHaiInterfaceService;
    @Autowired
    private BhFormInfoService bhFormInfoService;
    @Autowired
    private WxTemplateUtils wxTemplateUtils;
    @Autowired
    private HtUserInfoService htUserInfoService;
    @Autowired
    private HtRenewFormService htRenewFormService;
    @Autowired
    private HtRenewFormDao htRenewFormDao;
    @Autowired
    private HtRepairClientFormDao htRepairClientFormDao;


    /**
     * 获取单条数据
     *
     * @param htFormOperation
     * @return
     */
    @Override
    public HtFormOperation get(HtFormOperation htFormOperation) {
        return super.get(htFormOperation);
    }

    /**
     * 查询分页数据
     *
     * @param htFormOperation 查询条件
     * @return
     */
    @Override
    public Page<HtFormOperation> findPage(HtFormOperation htFormOperation) {
        return super.findPage(htFormOperation);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param htFormOperation
     */
    @Transactional(readOnly = false)
    public void save(HtFormOperation htFormOperation, String reopen) {
        String type = htFormOperation.getType();
        switch (type) {
            case "1":
                this.applyFor(htFormOperation, reopen);
                break;
            case "2":
                this.check(htFormOperation, reopen);
                break;
            default:
        }
    }

    /**
     * 改派，关闭，重启 申请执行流程
     *
     * @param htFormOperation
     */
    @Transactional(readOnly = false)
    public void applyFor(HtFormOperation htFormOperation, String reopen) {
        String uuid = IdGen.randomLong() + "";
        htFormOperation.setId(uuid);
        //本环节的ID
        //查出工单类型
        BpmParams bpm = htFormOperation.getBpm();
        String activityId = bpm.getActivityId();
        String taskId = bpm.getTaskId();
        BpmTask task = bpmTaskService.getTask(taskId);
        HtFormInfo formInfo = formInfoService.get(htFormOperation.getFormId());
        formInfo.setBpm(bpm);
        String formType = formInfo.getFormType();
        FileUploadUtils.saveFileUpload(htFormOperation.getId(), "HtFormOperation_image");
        formOperationDao.insert(htFormOperation);

        HtHistory htHistory = new HtHistory();
        htHistory.setUniquenessId(uuid);
        htHistory.setWorkOrderId(htFormOperation.getFormId());
        htHistory.setFormId(htFormOperation.getBdId());
        htHistory.setFormType(formType);
        htHistory.setUserVisible("0");
        htHistory.setCmsVisible("1");
        htHistory.setActivityId(activityId);
        //这里是操作人
        htHistory.setDisposeUserId(UserUtils.getUser().getId());

        String operationType = htFormOperation.getOperationType();
        HashMap<String, Object> variables = new HashMap<>();

        switch (operationType) {
            case OperationStatus.SQ_GB:

                if ("0".equals(formType)) {
                    //理赔申请关闭
                    htHistory.setActivityName("在线核赔-申请关闭");
                    htHistory.setOperationStatus(ManageStatus.LP_SQGB_DSH);
                    variables.put("checkStatus", '3');
                    formInfo.setManageStatus(ManageStatus.LP_SQGB_DSH);
                    formInfo.setFormStatus(FormStatus.LP_SQGB_DSH);
                }
                if ("1".equals(formType)) {
                    //换新
                    htHistory.setActivityName("换新-申请关闭");
                    htHistory.setOperationStatus(ManageStatus.HX_SQGB);
                    variables.put("checkStatus", '3');
                    formInfo.setManageStatus(ManageStatus.HX_SQGB);
                    formInfo.setFormStatus(FormStatus.HX_SQGB);
                }
                if ("2".equals(formType)) {
                    //维修
                    htHistory.setActivityName("维修-申请关闭");
                    htHistory.setOperationStatus(ManageStatus.WX_SQGB_DSH);
                    variables.put("checkStatus", '3');
                    formInfo.setManageStatus(ManageStatus.WX_SQGB_DSH);
                    formInfo.setFormStatus(FormStatus.WX_SQGB_DSH);
                }
                if ("4".equals(formType)) {
                    //待申请
                    htHistory.setActivityName("待申请-申请关闭");
                    htHistory.setOperationStatus(ManageStatus.DSQ_SQGB);
                    variables.put("checkStatus", '3');
                    formInfo.setManageStatus(ManageStatus.DSQ_SQGB);
                    formInfo.setFormStatus(FormStatus.DSQ_SQGB);
                }
                break;
            case OperationStatus.SQ_GP:

                if ("0".equals(formType)) {
                    //在线理赔没有改派
                }
                if ("1".equals(formType)) {
                    /*换新没有改派*/
                }
                if ("2".equals(formType)) {
                    htHistory.setActivityName("维修-申请改派");
                    htHistory.setOperationStatus(ManageStatus.WX_SQGP_DSH);
                    variables.put("checkStatus", '5');
                    formInfo.setManageStatus(ManageStatus.WX_SQGP_DSH);
                    formInfo.setFormStatus(FormStatus.WX_SQGP_DSH);
                    formInfo.setOffice(new Office(htFormOperation.getIntermediaryServiceId()));
                }
                if ("4".equals(formType)) {
                    /*待申请环节 没有 改派*/
                }
                break;
            case OperationStatus.SQ_CQ:
                //申请重启的
                if ("0".equals(formType)) {
                    htHistory.setActivityName("在线核赔-申请重启");
                    htHistory.setOperationStatus(ManageStatus.LP_SQCQ_DSH);
                    formInfo.setManageStatus(ManageStatus.LP_SQCQ_DSH);
                    formInfo.setFormStatus(FormStatus.LP_SQCQ_DSH);
                    formInfo.setIsRun("2");
                }
                if ("1".equals(formType)) {
                    htHistory.setActivityName("换新-申请重启");
                    htHistory.setOperationStatus(ManageStatus.HX_SQCQ);
                    formInfo.setManageStatus(ManageStatus.HX_SQCQ);
                    formInfo.setFormStatus(FormStatus.HX_SQCQ);
                    formInfo.setIsRun("2");
                }
                if ("2".equals(formType)) {
                    htHistory.setActivityName("维修-申请重启");
                    htHistory.setOperationStatus(ManageStatus.WX_SQCQ_DSH);
                    formInfo.setManageStatus(ManageStatus.WX_SQCQ_DSH);
                    formInfo.setFormStatus(FormStatus.WX_SQCQ_DSH);
                    formInfo.setIsRun("2");
                }
                if ("4".equals(formType)) {
                    htHistory.setActivityName("待申请-申请重启");
                    htHistory.setOperationStatus(ManageStatus.DSQ_SQCQ);
                    formInfo.setManageStatus(ManageStatus.DSQ_SQCQ);
                    formInfo.setFormStatus(FormStatus.DSQ_SQCQ);
                    formInfo.setIsRun("2");
                }
                break;
            default:
        }
        if ("1".equals(reopen)) {
            User currentUser = UserUtils.getUser();//当前操作用户

            BpmTask bpmTask = new BpmTask();
            bpmTask.setDelegateState("");
            bpmTask.setId(bpm.getTaskId());
            bpmTask.setComment("服务器转办任务");
            bpmTask.setVariableList(new ArrayList<>());
            bpmTask.setUserCode(currentUser.getUserCode());//转办到的usercode
            User user = UserUtils.get("0_server_skus");//这里是服务器的user对象
            bpmTask.setCurrentUser(user);//这里是流程发起人的user对象
            bpmTaskService.turnTask(bpmTask);//转办流转
            BpmTask claim = new BpmTask();
            claim.setId(bpm.getTaskId());
            // User charge = UserUtils.get("0_claim_charge_qrk6");//这里是核赔主管的user对象
            claim.setCurrentUser(currentUser);
            bpmTaskService.claimTask(claim);//在这里用 当前用户把工签收手掉
        }

        BpmUtils.complete(formInfo, variables, null);
        //再次拿本环节ID

        Task taskNew = taskService.createTaskQuery().processInstanceId(htFormOperation.getBpm().getProcInsId()).singleResult();
        htHistory.setAfterActivityId(taskNew.getTaskDefinitionKey());
        historyService.save(htHistory);
        formInfoDao.update(formInfo);
    }

    /**
     * 改派，关闭，重启 审核执行流程
     *
     * @param htFormOperation
     */
    @Transactional(readOnly = false)
    public void check(HtFormOperation htFormOperation, String reopen) {
        htFormOperation.setAuditResult("1");
        String uuid = IdGen.uuid();
        htFormOperation.setId(uuid);
        //本环节的ID
        String activityId = htFormOperation.getBpm().getActivityId();
        //查出工单类型
        BpmParams bpm = htFormOperation.getBpm();
        String formId = htFormOperation.getFormId();
        HtFormInfo formInfo = formInfoService.get(formId);
        formInfo.setBpm(bpm);
        //"工单类型，1换新，2维修，3投诉长度不能超过 10 个字符")
        String formType = formInfo.getFormType();
        HtHistory htHistory = new HtHistory();
        htHistory.setUniquenessId(uuid);
        htHistory.setWorkOrderId(htFormOperation.getFormId());
        htHistory.setFormId(htFormOperation.getBdId());
        htHistory.setFormType(formType);
        htHistory.setUserVisible("0");
        htHistory.setCmsVisible("1");
        htHistory.setActivityId(activityId);
        htHistory.setDisposeUserId(UserUtils.getUser().getId());

        //需要判断是 改派  关闭重启
        HashMap<String, Object> variables = new HashMap<>();

        String operationType = htFormOperation.getOperationType();
        switch (operationType) {
            case OperationStatus.SQ_GB:
                //关闭*/
                if ("0".equals(formType)) {
                    htHistory.setActivityName("在线理赔-关闭审核通过");
                    formInfo.setManageStatus(ManageStatus.LP_SQGB_SHTG);
                    formInfo.setFormStatus(FormStatus.LP_SQGB_SHTG);
                    htHistory.setOperationStatus(ManageStatus.LP_SQGB_SHTG);
                    formInfo.setIsRun(FormStatus.GD_GB);
                    htHistory.setUserVisible("1");
                }
                if ("1".equals(formType)) {
                    //换新
                    htHistory.setActivityName("换新-关闭审核通过");
                    formInfo.setManageStatus(ManageStatus.HX_YGB);
                    formInfo.setFormStatus(FormStatus.HX_YGB);
                    htHistory.setOperationStatus(ManageStatus.HX_YGB);
                    formInfo.setIsRun(FormStatus.GD_GB);
                    htHistory.setUserVisible("1");
                }
                if ("2".equals(formType)) {
                    htHistory.setActivityName("维修-关闭审核通过");
                    formInfo.setManageStatus(ManageStatus.WX_SQGB_SHTG);
                    formInfo.setFormStatus(FormStatus.WX_SQGB_SHTG);
                    htHistory.setOperationStatus(ManageStatus.WX_SQGB_SHTG);
                    formInfo.setIsRun(FormStatus.GD_GB);
                    htHistory.setUserVisible("1");
                }
                if ("4".equals(formType)) {
                    htHistory.setActivityName("待申请-关闭审核通过");
                    formInfo.setManageStatus(ManageStatus.DSQ_GB_DCQ);
                    formInfo.setFormStatus(FormStatus.DSQ_GB);
                    htHistory.setOperationStatus(ManageStatus.DSQ_GB_DCQ);
                    formInfo.setIsRun(FormStatus.GD_GB);
                    htHistory.setUserVisible("1");
                }
                break;
            case OperationStatus.SQ_GP:
                /*在审核改派时，需要把审核人查出来  指定维修机构下的人*/
                String intermediaryServiceId = htFormOperation.getIntermediaryServiceId();
                String userIds = historyService.findUserByOffer(intermediaryServiceId);
                htFormOperation.setVerifier(userIds);
                //改派
                if ("0".equals(formType)) {
                    /*在线申请没有改派*/
                }
                if ("1".equals(formType)) {
                    /*换新没有改派*/
                }
                if ("2".equals(formType)) {
                    htHistory.setActivityName("维修-改派审核通过");
                    formInfo.setManageStatus(ManageStatus.WX_GPWC);
                    formInfo.setFormStatus(FormStatus.WX_GPWC);
                    htHistory.setOperationStatus(ManageStatus.WX_GPWC);
                    String userCodes = repairClientFormService.getUserCodes(htFormOperation.getIntermediaryServiceId());
                    formInfo.getBpm().setNextUserCodes(userCodes);
                    formInfo.setOffice(new Office(intermediaryServiceId));
/*                        Office office = new Office();
                        office.setOfficeCode();
                        formInfo.setOffice(office);*/
                    htHistory.setUserVisible("1");
                }
                break;
            case OperationStatus.SQ_CQ:
                if ("0".equals(formType)) {
                    htHistory.setActivityName("在线理赔-工单重新开启");
                    formInfo.setManageStatus(ManageStatus.LP_SQCQ_SHTG);
                    formInfo.setFormStatus(FormStatus.LP_SQCQ_SHTG);
                    htHistory.setOperationStatus(ManageStatus.LP_SQCQ_SHTG);
                    formInfo.setIsRun(FormStatus.GD_KQ);
                }
                if ("1".equals(formType)) {
                    htHistory.setActivityName("换新-工单重新开启");
                    formInfo.setManageStatus(ManageStatus.HX_FA_DSH);
                    formInfo.setFormStatus(FormStatus.HX_FA_DTJ);
                    htHistory.setOperationStatus(ManageStatus.HX_CXKQ);
                    formInfo.setIsRun(FormStatus.GD_KQ);
                }
                if ("2".equals(formType)) {
                    htHistory.setActivityName("维修-工单重新开启");
                    formInfo.setManageStatus(ManageStatus.WX_SQCQ_SHTG);
                    formInfo.setFormStatus(FormStatus.WX_SQCQ_SHTG);
                    htHistory.setOperationStatus(ManageStatus.WX_SQCQ_SHTG);
                    formInfo.setIsRun(FormStatus.GD_KQ);
                    //需要指人
                    String userCodes = repairClientFormService.getUserCodes(formInfo.getOffice().getOfficeCode());
                    formInfo.getBpm().setNextUserCodes(userCodes);
                }
                if ("4".equals(formType)) {
                    htHistory.setActivityName("待申请-工单重新开启");
                    formInfo.setManageStatus(ManageStatus.DSQ_WLXCG_DFP);
                    formInfo.setFormStatus(FormStatus.DSQ_SB_DFP);
                    htHistory.setOperationStatus(ManageStatus.DSQ_WLXCG_DFP);
                    formInfo.setIsRun(FormStatus.GD_KQ);
                }
                break;
            default:
        }
        variables.put("checkStatus", '1');
        BpmUtils.complete(formInfo, variables, null);
        //再次拿本环节ID
        Task taskNew = taskService.createTaskQuery().processInstanceId(htFormOperation.getBpm().getProcInsId()).singleResult();
        htHistory.setAfterActivityId(taskNew.getTaskDefinitionKey());
        formOperationDao.insert(htFormOperation);
        historyService.save(htHistory);
        formInfoDao.update(formInfo);
//        if (OperationStatus.SQ_GB.equals(operationType)&&"2".equals(formType)){
//            //需要的是维修关闭关闭审核通过的情况
//                BhFormInfo bhFormInfo = new BhFormInfo();
//                bhFormInfo.setFormId(formInfo.getId());
//                List<BhFormInfo> bhList = bhFormInfoService.findList(bhFormInfo);
//                bhFormInfo = bhList.get(0);
//                //维修同步接口
//                String operationTime = DateUtils.formatDateTime(new Date());
//                Result<String> result = boHaiInterfaceService.synRepairInfo(new SynRepairInfoParameter(bhFormInfo.getClnNo(), bhFormInfo.getOrderId(), bhFormInfo.getDeviceCode(), "N", operationTime, "和德信通"));
//                if(!result.getStatus().equals(ResultStatus.SUCCESS)){
//                    throw new RuntimeException("202");
//                }
//        }
        if (OperationStatus.SQ_GP.equals(operationType)&&"2".equals(formType)){
            //需要的是维修改派审核通过的情况  给用户微信通知
            //发送微信通知维修报告
            String date = DateUtils.formatDate(new Date());
            try {
                HtUserInfo htUserInfo = htUserInfoService.get(formInfo.getUserId());
                wxTemplateUtils.sendWxTemplateStatusFive(formInfo.getId(),date,htUserInfo.getOpenId());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("101");
            }
        }
        if (OperationStatus.SQ_GB.equals(operationType)){//防止没有数据的情况下，工单关闭列表没有数据
            HtFormInfo search = new HtFormInfo(formId);
            if ("repair_charge_close".equals(activityId)){
                HtRepairClientForm repairClientForm = new HtRepairClientForm();
                repairClientForm.setHtFormInfo(search);
                List<HtRepairClientForm> list = repairClientFormService.findList(repairClientForm);
                if (list==null|| list.size()<=0){
                   htRepairClientFormDao.insert(repairClientForm);
                }
            }
            if ("renew_close".equals(activityId)){
                HtRenewForm htRenewForm = new HtRenewForm();
                htRenewForm.setHtFormInfo(search);
                List<HtRenewForm> list = htRenewFormService.findList(htRenewForm);
                if (list==null|| list.size()<=0){
                htRenewFormDao.insert(htRenewForm);
                }
            }
        }
    }


    /**
     * 更新状态
     *
     * @param htFormOperation
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(HtFormOperation htFormOperation) {
        super.updateStatus(htFormOperation);
    }

    /**
     * 删除数据
     *
     * @param htFormOperation
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(HtFormOperation htFormOperation) {
        super.delete(htFormOperation);
    }


    public HtFormOperation findByFormId(String formId) {
        return dao.findByFormId(formId);
    }

    public ArrayList<Map<String, String>> findUser(String repair_charge) {
        return dao.findUser(repair_charge);
    }

    /**
     * 修改工单的状态
     *
     * @param activityId
     * @param form
     * @return
     */
    public HtFormInfo updateFormStatus(String activityId, HtFormInfo form) {
        switch (activityId) {
            case "claim_wait":
                form.setManageStatus(ManageStatus.LP_ZLYGX_DLX);
                form.setFormStatus(FormStatus.LP_DCL);
                break;
            case "claim_info":
                form.setManageStatus(ManageStatus.LP_ZLYGX_DLX);
                form.setFormStatus(FormStatus.LP_DCL);
                break;
            case "repair_info":
                form.setFormStatus(FormStatus.WX_DQS);
                form.setManageStatus(ManageStatus.WX_DHSYJZT);
                break;
            case "repair_confirm_time":
                form.setFormStatus(FormStatus.WX_DQS);
                form.setManageStatus(ManageStatus.WX_KHYJJ_DQS);
                break;
            case "repair_sign_for":
                form.setFormStatus(FormStatus.WX_DQS);
                form.setManageStatus(ManageStatus.WX_KHYJJ_DQS);
                break;
            case "repair_receive":
                form.setFormStatus(FormStatus.WX_DLR);
                form.setManageStatus(ManageStatus.WX_YQS_DLR);
                break;
            case "repair_offer":
                form.setFormStatus(FormStatus.WX_DBJ);
                form.setManageStatus(ManageStatus.WX_DBJ);
                break;
            case "repair_charge":
                form.setFormStatus(FormStatus.WX_BJ_DSH);
                form.setManageStatus(ManageStatus.WX_BJ_DSH);
                break;
            case "repair_wait":
                form.setFormStatus(FormStatus.WX_WXZ);
                form.setManageStatus(ManageStatus.WX_BJ_SHTG);
                break;
            case "repair_end":
                form.setFormStatus(FormStatus.WX_DCL);
                form.setManageStatus(ManageStatus.WX_WXWC_DSH);
                break;
            case "repair_end_check":
                form.setFormStatus(FormStatus.WX_WXWC);
                form.setManageStatus(ManageStatus.WX_WXWC_DSH);
                break;
            case "claim_close":
                form.setFormStatus(FormStatus.LP_SQGB_DSH);
                form.setManageStatus(ManageStatus.LP_SQGB_DSH);
                break;
            case "claim_wait_restart":
                form.setFormStatus(FormStatus.LP_SQGB_SHTG);
                form.setManageStatus(ManageStatus.LP_SQGB_SHTG);
                break;
            case "claim_check_restart":
                form.setFormStatus(FormStatus.LP_SQCQ_DSH);
                form.setManageStatus(ManageStatus.LP_SQCQ_DSH);
                break;
            case "repair_change_time":
                form.setFormStatus(FormStatus.WX_SQGP_DSH);
                form.setManageStatus(ManageStatus.WX_SQGP_DSH);
                break;
            case "repair_charge_close":
                form.setFormStatus(FormStatus.WX_SQGB_DSH);
                form.setManageStatus(ManageStatus.WX_SQGB_DSH);
                break;
            case "repair_wait_restart":
                form.setFormStatus(FormStatus.WX_SQGB_SHTG);
                form.setManageStatus(ManageStatus.WX_SQGB_SHTG);
                break;
            case "repair_check_restart":
                form.setFormStatus(FormStatus.WX_SQCQ_DSH);
                form.setManageStatus(ManageStatus.WX_SQCQ_DSH);
                break;
            case "repair_change_receive":
                form.setFormStatus(FormStatus.WX_SQGP_DSH);
                form.setManageStatus(ManageStatus.WX_SQGP_DSH);
                break;
            case "repair_change_wait":
                form.setFormStatus(FormStatus.WX_SQGP_DSH);
                form.setManageStatus(ManageStatus.WX_SQGP_DSH);
                break;
            default:
        }
        return form;
    }

    public HtFormOperation findOpByFormId(String formId) {
        return dao.findOpByFormId(formId);
    }
}