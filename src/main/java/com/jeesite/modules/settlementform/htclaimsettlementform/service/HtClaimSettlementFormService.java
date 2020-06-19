/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementform.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import com.jeesite.modules.settlementform.htclaimdisqualificationlog.entity.HtClaimDisqualificationLog;
import com.jeesite.modules.settlementform.htclaimdisqualificationlog.service.HtClaimDisqualificationLogService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.entity.HtClaimSettlementFormHistory;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.service.HtClaimSettlementFormHistoryService;
import com.jeesite.modules.sforderinfo.entity.SfOrderInfo;
import com.jeesite.modules.sforderinfo.service.SfOrderInfoService;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.template.utils.NoteTemplateUtils;
import com.jeesite.modules.template.utils.SmsSendUtils;
import com.jeesite.modules.template.utils.WxTemplateUtils;
import com.jeesite.modules.user.entity.HtUserInfo;
import com.jeesite.modules.user.service.HtUserInfoService;
import net.bytebuddy.asm.Advice;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.RepositoryServiceImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.dao.HtClaimSettlementFormDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在线理赔表Service
 *
 * @author hongmengzhong
 * @version 2020-02-28
 */
@Service
@Transactional(readOnly = true)
public class HtClaimSettlementFormService extends CrudService<HtClaimSettlementFormDao, HtClaimSettlementForm> {

    @Autowired
    private HtClaimSettlementFormDao htClaimSettlementFormDao;
    @Autowired
    private HtFormInfoDao htFormInfoDao;
    @Autowired
    private HtClaimSettlementFormHistoryService htClaimSettlementFormHistoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HtHistoryService htHistoryService;
    @Autowired
    private HtCallLogService htCallLogService;
    @Autowired
    HtRepairClientFormService htRepairClientFormService;
    @Autowired
    HtClaimDisqualificationLogService htClaimDisqualificationLogService;
    @Autowired
    HtUserApplyInfoService htUserApplyInfoService;
    @Autowired
    HtMaintenancePointService htMaintenancePointService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private WxTemplateUtils wxTemplateUtils;
    @Autowired
    private SfOrderInfoService sfOrderInfoService;
    @Autowired
    private HtUserInfoService htUserInfoService;

    /**
     * 获取单条数据
     *
     * @param htClaimSettlementForm
     * @return
     */
    @Override
    public HtClaimSettlementForm get(HtClaimSettlementForm htClaimSettlementForm) {
        return super.get(htClaimSettlementForm);
    }

    /**
     * 查询分页数据
     *
     * @param htClaimSettlementForm 查询条件
     * @return
     */
    @Override
    public Page<HtClaimSettlementForm> findPage(HtClaimSettlementForm htClaimSettlementForm) {
        return super.findPage(htClaimSettlementForm);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param htClaimSettlementForm
     */
    @Override
    @Transactional(readOnly = false)
    public void save(HtClaimSettlementForm htClaimSettlementForm) {
        //  repositoryService.createDeploymentQuery().processDefinitionKey(htClaimSettlementForm.getBpm().getProcInsId())
        Task task = taskService.createTaskQuery().taskId(htClaimSettlementForm.getBpm().getTaskId()).singleResult();
        HtFormInfo htFormInfo = htClaimSettlementForm.getHtFormInfo();
        HtUserApplyInfo htUserApplyInfo = htUserApplyInfoService.findByFormId(htFormInfo.getId());
        HtUserInfo htUserInfo = htUserInfoService.get(htFormInfo.getUserId());
        String userId = "";//htFormInfo.getUserId();
        HtHistory htHistory = new HtHistory();
        String fromTypes = htFormInfo.getFormType();
        htClaimSettlementForm.setFormId(htFormInfo.getId());
        if (htClaimSettlementForm.getHtUserApplyInfo() != null) {
            htClaimSettlementForm.setUserName(htClaimSettlementForm.getHtUserApplyInfo().getCardName());
            htClaimSettlementForm.setPhone(htClaimSettlementForm.getHtUserApplyInfo().getUserPhone());
            htClaimSettlementForm.setPhoneBrand(htClaimSettlementForm.getHtUserApplyInfo().getFacilityBrandId());
            htClaimSettlementForm.setPhoneModel(htClaimSettlementForm.getHtUserApplyInfo().getFacilityModelId());
            htClaimSettlementForm.setIdNumber(htClaimSettlementForm.getHtUserApplyInfo().getCardId());
        }
        if (htClaimSettlementForm.getArea() != null) {
            htClaimSettlementForm.setReturnAreaCode(htClaimSettlementForm.getArea().getAreaCode());
        }
        if (StringUtils.isBlank(htFormInfo.getStatus())) {
            htFormInfo.setStatus(DataEntity.STATUS_AUDIT);
        }
        //RepositoryServiceImpl
        // 如果状态为正常，则代表不正常操作，可能前端进行了数据参数修改
        if (DataEntity.STATUS_NORMAL.equals(htFormInfo.getStatus())) {
            throw new ServiceException(text("非法操作，前端数据被劫持！"));
        }


        // 如果为审核状态，则进行审批流操作
        if (DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {

            // 指定流程变量，作为流程条件，决定流转方向
            Map<String, Object> variables = MapUtils.newHashMap();
            //维修待联系客户环节---是否成功联系，1成功联系
            if ("claim_info".equals(htClaimSettlementForm.getBpm().getActivityId())) {

                //1成功联系
                if ("1".equals(htClaimSettlementForm.getContactStatus())) {
                    //理赔资料状态1已邮寄2未邮寄3已签收4未确认
                    //未邮寄或者未确认
                    if ("1".equals(htClaimSettlementForm.getIsQualified())) {
                        if (htUserApplyInfo != null) {
                            StringBuilder damageImgStr = new StringBuilder(htUserApplyInfo.getBadSideWimg());
                            damageImgStr.append("|" + htUserApplyInfo.getBadSideOimg());
                            damageImgStr.append("|" + htUserApplyInfo.getBadReverseImg());
                            damageImgStr.append("|" + htUserApplyInfo.getBadFrontImg());
                            damageImgStr.append("|" + htClaimSettlementForm.getDamageImgs());
                            StringBuilder idCardImgStr = new StringBuilder(htUserApplyInfo.getCardReverseImg());
                            idCardImgStr.append("|" + htUserApplyInfo.getCardFrontImg());
                            idCardImgStr.append("|" + htUserApplyInfo.getCardHandImg());
                            idCardImgStr.append("|" + htClaimSettlementForm.getIdentityCardImgs());
                            StringBuilder voucherImgStr = new StringBuilder(htUserApplyInfo.getVoucherImg());
                            voucherImgStr.append("|" + htClaimSettlementForm.getPurchaseImgs());
                            htClaimSettlementForm.setDamageImgs(damageImgStr.toString());
                            htClaimSettlementForm.setIdentityCardImgs(idCardImgStr.toString());
                            htClaimSettlementForm.setPurchaseImgs(htUserApplyInfo.getVoucherImg());
                        }

                        String master = "";
                        String phone = "";
                        String address = "";
                        String conton = "";
                        String date = "";
                        String template = "";
                        if ("2".equals(htClaimSettlementForm.getMaintainType())) {
                            variables.put("checkStatus", "12");
                            htFormInfo.setManageStatus(ManageStatus.WX_DHSYJZT);
                            htFormInfo.setFormStatus(FormStatus.WX_DCL);
                            htFormInfo.setFormType("2");
                            htFormInfo.setOffice(new Office(htClaimSettlementForm.getMaintainBranchId()));
                            htHistory.setUserVisible("1");
                            HtMaintenancePoint maintenancePoint = htMaintenancePointService.findInfoByJG(htClaimSettlementForm.getMaintainBranchId());
                            if (maintenancePoint != null) {
                                Area area = areaService.get(maintenancePoint.getArea());
                                address = area.getTreeNames() + maintenancePoint.getAddress().replace("/", "");
                                master = maintenancePoint.getContactOne() == null ? maintenancePoint.getContactTwo() : maintenancePoint.getContactOne();
                                phone = maintenancePoint.getContactNumberOne() == null ? maintenancePoint.getContactNumberTwo() : maintenancePoint.getContactNumberOne();
                                String[] split = htClaimSettlementForm.getClaimDataNameStr().split(",");
                                template = NoteTemplateUtils.noteTemplateOneRenew(split, address, master, phone);
                                SmsSendUtils.send(htClaimSettlementForm.getPhone(), template);
                            }
                        } else if ("1".equals(htClaimSettlementForm.getMaintainType())) {
                            variables.put("checkStatus", "11");
                            htFormInfo.setManageStatus(ManageStatus.HX_DYJ);
                            htFormInfo.setFormStatus(FormStatus.WX_DCL);
                            htFormInfo.setFormType("1");
                            htFormInfo.setOffice(new Office(htClaimSettlementForm.getMaintainBranchId()));
                            htHistory.setUserVisible("1");
                            String[] split = htClaimSettlementForm.getClaimDataNameStr().split(",");
                            template = NoteTemplateUtils.noteTemplateOneRepair(split);
                            SmsSendUtils.send(htClaimSettlementForm.getPhone(), template);
                        }
                        try {
                            wxTemplateUtils.sendWxTemplateStatusThree(master, address, phone, conton, date, htUserInfo.getOpenId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        HtClaimDisqualificationLog htClaimDisqualificationLog = new HtClaimDisqualificationLog();
                        htClaimDisqualificationLog.setFormId(htFormInfo.getId());
                        htClaimDisqualificationLog.setRemark(htClaimSettlementForm.getDisqualificationDisqualification());
                        htClaimDisqualificationLogService.save(htClaimDisqualificationLog);
                        variables.put("checkStatus", '2');
                        htFormInfo.setManageStatus(ManageStatus.LP_ZLBHG_DLX);
                        htFormInfo.setFormStatus(FormStatus.LP_DCL);
                        htFormInfo.setBpm(htClaimSettlementForm.getBpm());
                        BpmUtils.complete(htFormInfo, variables, null);
                        htHistory.setUserVisible("1");
                        try {
                            wxTemplateUtils.sendWxTemplateStatusTwo(/*htUserInfo.getUserPhone()*/htUserApplyInfo.getUserPhone(), htUserInfo.getUserName(), htUserInfo.getOpenId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    variables.put("checkStatus", '2');
                    htFormInfo.setManageStatus(ManageStatus.WX_DLX);
                    htFormInfo.setFormStatus(FormStatus.LP_DCL);
                    htFormInfo.setBpm(htClaimSettlementForm.getBpm());
                    BpmUtils.complete(htFormInfo, variables, null);
                    htHistory.setUserVisible("0");
                }
            }
            // 如果状态为草稿或审核状态，才可以保存业务数据
            if (DataEntity.STATUS_DRAFT.equals(htFormInfo.getStatus())
                    || DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {

                // String callLogId = htCallLogService.getCallLogId(UserUtils.getUser().getUserCode());
                // htClaimSettlementForm.setCallInfoId(callLogId);

                super.save(htClaimSettlementForm);
                // HtFormInfo htClaForm = super.get(htClaimSettlementForm.getId()).getHtFormInfo();
                //htClaimSettlementForm.setFormId(htClaForm.getId());
                String activityId = htClaimSettlementForm.getBpm().getActivityId();
                String operatingStatus = htFormInfo.getManageStatus();
                String orderType = htFormInfo.getFormType();
                String createBy = htClaimSettlementForm.getCurrentUser().getUserName();
                String rescription = task.getName();
                HtClaimSettlementFormHistory history = new HtClaimSettlementFormHistory();
                BeanUtils.copyProperties(htClaimSettlementForm, history);
                history.setOperatingStatus(operatingStatus);
                history.setTaskAssigenee(createBy);
                history.setTaskRescription(rescription);
                history.setActivityId(activityId);
                history.setFormType(orderType);
                history.setId(null);
                history.setIsNewRecord(true);
                history.setTaskTime(new Date());
                history.setRemarks(htClaimSettlementForm.getRemarks());
                // 如果未设置状态，则指定状态为审核状态，以提交审核流程q
                htClaimSettlementFormHistoryService.save(history);
                if (StringUtils.isNotBlank(htClaimSettlementForm.getIsExpress()) && htClaimSettlementForm.getIsExpress().equals("0")) {
                    SfOrderInfo sfOrderInfo = new SfOrderInfo();
                    sfOrderInfo.setOrderId(htFormInfo.getId());
                    sfOrderInfoService.save(sfOrderInfo);
                    htHistory.setExteriorId(sfOrderInfo.getId());
                }
                Task taskNew = taskService.createTaskQuery().processInstanceId(htClaimSettlementForm.getBpm().getProcInsId()).singleResult();
                htHistory.setAfterActivityId(taskNew.getTaskDefinitionKey());
                htHistory.setHistoryFormId(history.getId());
                htHistory.setOperationStatus(operatingStatus);
                htHistory.setFormId(htClaimSettlementForm.getId());
                htHistory.setWorkOrderId(htFormInfo.getId());
                htHistory.setActivityId(activityId);
                htHistory.setActivityName(task.getName());
                htHistory.setFormType(fromTypes == null ? "0" : fromTypes);
                htHistory.setDisposeUserId(htClaimSettlementForm.getCurrentUser().getUserCode());

                htHistory.setCmsVisible("1");
                htHistoryService.save(htHistory);
                htFormInfo.setProductChildId(htClaimSettlementForm.getProductChildId());
                htFormInfo.setUpdateDate(new Date());
                htFormInfoDao.update(htFormInfo);

                BpmParams bpmParams = htClaimSettlementForm.getBpm();
                bpmParams.setComment(htClaimSettlementForm.getRemarks());
                if ("1".equals(htClaimSettlementForm.getContactStatus()) && "claim_info".equals(htClaimSettlementForm.getBpm().getActivityId())
                        && "1".equals(htClaimSettlementForm.getIsQualified())) {
                    if (variables.get("checkStatus").toString().equals("12")) {
                        String userCodes = htRepairClientFormService.getUserCodes(htClaimSettlementForm.getMaintainBranchId());
                        bpmParams.setNextUserCodes(userCodes);
                    }
                    //提交任务
                    htFormInfo.setBpm(bpmParams);
                    BpmUtils.complete(htFormInfo, variables, null);
                }
                // 保存上传图片
                FileUploadUtils.saveFileUpload(htClaimSettlementForm.getId(), "damage_image");
                FileUploadUtils.saveFileUpload(htClaimSettlementForm.getId(), "identityCard_image");
                FileUploadUtils.saveFileUpload(htClaimSettlementForm.getId(), "purchase_image");
            }
        }
    }

    /**
     * 更新状态
     *
     * @param htClaimSettlementForm
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(HtClaimSettlementForm htClaimSettlementForm) {
        super.updateStatus(htClaimSettlementForm);
    }

    /**
     * 删除数据
     *
     * @param htClaimSettlementForm
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(HtClaimSettlementForm htClaimSettlementForm) {
        super.delete(htClaimSettlementForm);
    }

    public void saveMiddleInfo(String dataid, String settlementData) {
        htClaimSettlementFormDao.saveMiddleInfo(dataid, settlementData);
    }

    public List<String> getSettlementDataList(String settlementDataId) {
        return htClaimSettlementFormDao.getSettlementDataList(settlementDataId);
    }

    /**
     * 获取表单部件类型和部件id
     *
     * @param formId
     * @return
     */
    public Map<String, String> getUnitStrInfo(String formId) {
        return htClaimSettlementFormDao.getUnitStrInfo(formId);
    }

    /**
     * 根据部件id 和 类型查询配件信息
     *
     * @param assemblyId
     * @param phoneModelId
     * @return
     */
    public List<Map<String, Object>> getAccessoriesInfoList(String assemblyId, String phoneModelId) {
        return htClaimSettlementFormDao.getAccessoriesInfoList(assemblyId, phoneModelId);
    }


    /**
     * 查询流程列表
     *
     * @param processListingEntity
     * @return
     */
    public Page<ProcessListingEntity> selectProcessInstanceByQueryCriteria(ProcessListingEntity processListingEntity, HttpServletRequest request, HttpServletResponse response) {
        //int count = htClaimSettlementFormDao.selectProcessInstanceByQueryCriteria(processListingEntity).size();
        Page<ProcessListingEntity> page = new Page<>(request, response);
        processListingEntity.setPage(page);
        /*page.setCount(count);
        page.setPageNo(page.getPageNo()-1);*/
        return page.setList(htClaimSettlementFormDao.selectProcessInstanceByQueryCriteria(processListingEntity));
    }

    public HtClaimSettlementForm getByFormId(String formId) {
        return dao.getByFormId(formId);
    }
}