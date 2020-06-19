/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htwaitapplyform.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyFormHistory;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.entity.HtClaimSettlementFormHistory;
import com.jeesite.modules.sys.entity.Office;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyForm;
import com.jeesite.modules.htwaitapplyform.dao.HtWaitApplyFormDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 待申请工单Service
 *
 * @author hongmengzhong
 * @version 2020-04-01
 */
@Service
@Transactional(readOnly = true)
public class HtWaitApplyFormService extends CrudService<HtWaitApplyFormDao, HtWaitApplyForm> {

    @Autowired
    TaskService taskService;
    @Autowired
    HtWaitApplyFormHistoryService htWaitApplyFormHistoryService;
    @Autowired
    HtRepairClientFormService htRepairClientFormService;
    @Autowired
    HtFormInfoDao htFormInfoDao;
    @Autowired
    HtHistoryService htHistoryService;

    /**
     * 获取单条数据
     *
     * @param htWaitApplyForm
     * @return
     */
    @Override
    public HtWaitApplyForm get(HtWaitApplyForm htWaitApplyForm) {
        return super.get(htWaitApplyForm);
    }

    /**
     * 查询分页数据
     *
     * @param htWaitApplyForm      查询条件
     * @param htWaitApplyForm.page 分页对象
     * @return
     */
    @Override
    public Page<HtWaitApplyForm> findPage(HtWaitApplyForm htWaitApplyForm) {
        return super.findPage(htWaitApplyForm);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param htWaitApplyForm
     */
    @Override
    @Transactional(readOnly = false)
    public void save(HtWaitApplyForm htWaitApplyForm) {

        Task task = taskService.createTaskQuery().taskId(htWaitApplyForm.getBpm().getTaskId()).singleResult();
        HtFormInfo htFormInfo = htWaitApplyForm.getHtFormInfo();
        HtHistory htHistory = new HtHistory();
        // 指定流程变量，作为流程条件，决定流转方向
        Map<String, Object> variables = MapUtils.newHashMap();
        if ("1".equals(htWaitApplyForm.getContactStatus()) && htWaitApplyForm.getWhetherApply().equals("1")) {
            variables.put("checkStatus", "1");
            htFormInfo.setManageStatus(ManageStatus.DSQ_LXCG_DFP);
            htFormInfo.setFormStatus(FormStatus.DSQ_CG_DFP);
            htFormInfo.setFormType("0");
        } else {
            variables.put("checkStatus", "2");
            htFormInfo.setManageStatus(ManageStatus.DSQ_WLXCG_DFP);
            htFormInfo.setFormStatus(FormStatus.DSQ_SB_DFP);
        }
        BpmParams bpmParams = htWaitApplyForm.getBpm();
        bpmParams.setComment(htWaitApplyForm.getRemarks());
        //提交任务
        htFormInfo.setBpm(bpmParams);
        BpmUtils.complete(htFormInfo, variables, null);

        super.save(htWaitApplyForm);
        HtWaitApplyFormHistory history = new HtWaitApplyFormHistory();
        BeanUtils.copyProperties(htWaitApplyForm, history);
        String activityId = htWaitApplyForm.getBpm().getActivityId();
        String operatingStatus = htFormInfo.getManageStatus();
        String orderType = htFormInfo.getFormType();
        String createBy = htWaitApplyForm.getCurrentUser().getUserName();
        String rescription = task.getName();
        history.setOperatingStatus(operatingStatus);
        history.setTaskAssigenee(createBy);
        history.setTaskRescription(rescription);
        history.setActivityId(activityId);
        history.setFormId(htFormInfo.getId());
        history.setFormType("4");
        history.setId(null);
        history.setIsNewRecord(true);
        history.setTaskTime(new Date());
        history.setRemarks(htWaitApplyForm.getRemarks());
        htWaitApplyFormHistoryService.save(history);
        Task taskNew = taskService.createTaskQuery().processInstanceId(htWaitApplyForm.getBpm().getProcInsId()).singleResult();

        htHistory.setAfterActivityId(taskNew.getTaskDefinitionKey());
        htHistory.setHistoryFormId(history.getId());
        htHistory.setOperationStatus(operatingStatus);
        htHistory.setFormId(htWaitApplyForm.getId());
        htHistory.setWorkOrderId(htFormInfo.getId());
        htHistory.setActivityId(activityId);
        htHistory.setActivityName(task.getName());
        htHistory.setFormType("4");
        htHistory.setDisposeUserId(htWaitApplyForm.getCurrentUser().getUserCode());

        htHistory.setCmsVisible("1");
        htHistoryService.save(htHistory);
        htFormInfo.setUpdateDate(new Date());
        htFormInfoDao.update(htFormInfo);
    }

    /**
     * 更新状态
     *
     * @param htWaitApplyForm
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(HtWaitApplyForm htWaitApplyForm) {
        super.updateStatus(htWaitApplyForm);
    }

    /**
     * 删除数据
     *
     * @param htWaitApplyForm
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(HtWaitApplyForm htWaitApplyForm) {
        super.delete(htWaitApplyForm);
    }

    public Page<ProcessListingEntity> selectProcessInstanceByQueryCriteria(ProcessListingEntity processListingEntity, HttpServletRequest request, HttpServletResponse response) {
        Page<ProcessListingEntity> page = new Page<>(request,response);
        processListingEntity.setPage(page);
        /*page.setCount(count);
        page.setPageNo(page.getPageNo()-1);*/
        return page.setList(dao.selectProcessInstanceByQueryCriteria(processListingEntity));
    }

    public String getBypolicyId(String policyId) {
        return dao.getBypolicyId(policyId);
    }

    public HtWaitApplyForm findByFormId(String formId) {
        return dao.findByFormId(formId);
    }
}