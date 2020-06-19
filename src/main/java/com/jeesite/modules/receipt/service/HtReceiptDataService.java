/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.service;

import java.util.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.bh.entity.BhFaultplandict;
import com.jeesite.modules.bh.entity.BhFormInfo;
import com.jeesite.modules.bh.service.BhFaultplandictService;
import com.jeesite.modules.bh.service.BhFormInfoService;
import com.jeesite.modules.bohai.BoHaiInterfaceService;
import com.jeesite.modules.bohai.entity.*;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.FormType;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.history.dao.HtHistoryDao;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.receipt.dao.HtReceiptDataHistoryDao;
import com.jeesite.modules.receipt.entity.HtReceiptDataHistory;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import com.jeesite.modules.repair.entity.HtRepairOfferPart;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.repair.service.HtRepairOfferPartService;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.entity.HtClaimSettlementFormHistory;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.receipt.entity.HtReceiptData;
import com.jeesite.modules.receipt.dao.HtReceiptDataDao;

/**
 * 收款人信息表Service
 *
 * @author zhaohaifeng
 * @version 2020-03-23
 */
@Service
@Transactional(readOnly = true)
public class HtReceiptDataService extends CrudService<HtReceiptDataDao, HtReceiptData> {
    @Autowired
    private TaskService taskService;
    @Autowired
    private HtHistoryDao historyDao;
    @Autowired
    private HtFormInfoDao formInfoDao;
    @Autowired
    private HtReceiptDataHistoryDao receiptDataHistoryDao;
    @Autowired
    private BoHaiInterfaceService boHaiInterfaceService;
    @Autowired
    private BhFormInfoService bhFormInfoService;
    @Autowired
    private BhFaultplandictService bhFaultplandictService;
    @Autowired
    private HtRepairOfferPartService htRepairOfferPartService;
    @Autowired
    private HtRepairClientFormService htRepairClientFormService;
    @Autowired
    private HtUserApplyInfoService userApplyInfoService;
    @Autowired
    private AreaService areaService;

    /**
     * 获取单条数据
     *
     * @param htReceiptData
     * @return
     */
    @Override
    public HtReceiptData get(HtReceiptData htReceiptData) {
        return super.get(htReceiptData);
    }

    /**
     * 查询分页数据
     *
     * @param htReceiptData 查询条件
     * @return
     */
    @Override
    public Page<HtReceiptData> findPage(HtReceiptData htReceiptData) {
        return super.findPage(htReceiptData);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param htReceiptData
     */
    @Override
    @Transactional(readOnly = false)
    public void save(HtReceiptData htReceiptData) {
        htReceiptData.setIsNewRecord(false);
        //判断财务审核审核是否通过   驳回的时候 就不流转了 只修改状态
        String auditStatus = htReceiptData.getAuditStatus();

        HtFormInfo formInfo = htReceiptData.getHtFormInfo();
        BpmParams bpm = htReceiptData.getBpm();
        String name = taskService.createTaskQuery().processInstanceId(bpm.getProcInsId()).singleResult().getName();
        HtHistory htHistory = new HtHistory();
        HashMap<String, Object> variables = new HashMap<>();
        if ("0".equals(auditStatus)) {
            //驳回
            variables.put("checkStatus", '9');
            formInfo.setManageStatus(ManageStatus.QS_SHBH);
            formInfo.setFormStatus(FormStatus.QS_DLR);
            htReceiptData.setStatus("0");
            htReceiptData.setAuditStatus("2");
            htReceiptData.setExamineDate(new Date());
            htHistory.setAfterActivityId(bpm.getActivityId());
        } else if ("1".equals(auditStatus)) {
            //通过
            //htReceiptData.setStatus("1");
            htReceiptData.setAuditStatus("1");
            htReceiptData.setExamineDate(new Date());
            variables.put("checkStatus", '1');
            formInfo.setManageStatus(ManageStatus.QS_CW_SHTG);
            formInfo.setFormStatus(FormStatus.QS_DSH); //待渤海审核
            BpmUtils.complete(htReceiptData, variables, null);
            //Task taskNew = taskService.createTaskQuery().processInstanceId(bpm.getProcInsId()).singleResult();
            htHistory.setAfterActivityId("receipt_over");
        }

        HtReceiptDataHistory htReceiptDataHistory = new HtReceiptDataHistory();
        BeanUtils.copyProperties(htReceiptData, htReceiptDataHistory);
        String historyId = IdGen.randomLong() + "";
        htReceiptDataHistory.setId(historyId);
        super.save(htReceiptData);
        receiptDataHistoryDao.insert(htReceiptDataHistory);

        htHistory.setHistoryFormId(historyId);
        htHistory.setOperationStatus(formInfo.getManageStatus());
        htHistory.setFormId(htReceiptData.getId());
        htHistory.setWorkOrderId(formInfo.getId());
        htHistory.setActivityId(bpm.getActivityId());
        htHistory.setActivityName(name);
        htHistory.setFormType(FormType.QSGD);
        htHistory.setDisposeUserId(UserUtils.getUser().getUserCode());
        htHistory.setUserVisible("1");
        htHistory.setCmsVisible("1");
        historyDao.insert(htHistory);
        formInfoDao.update(formInfo);
        FileUploadUtils.saveFileUpload(htReceiptData.getId(), "HtReceiptData_image");
        // 审核之后 调用 查勘接口 和 增加收款人
//        if ("1".equals(auditStatus)) {
//            BhFormInfo bhFormInfo = new BhFormInfo();
//            bhFormInfo.setFormId(formInfo.getId());
//            List<BhFormInfo> bhList = bhFormInfoService.findList(bhFormInfo);
//            bhFormInfo = bhList.get(0);
//            //查勘接口
//            List<SurveyLoss> surList = new ArrayList<>();
//
//            HtRepairOfferPart htRepairOfferPart = new HtRepairOfferPart();
//            htRepairOfferPart.setFormId(formInfo.getId());
//            List<HtRepairOfferPart> offerPartList = htRepairOfferPartService.findList(htRepairOfferPart);
//            for (int i = 0; i < offerPartList.size(); i++) {
//                HtRepairOfferPart offerPart = offerPartList.get(i);
//                String bhId = offerPart.getBhProjectId();
//                BhFaultplandict bhFaultplandict = bhFaultplandictService.get(bhId);
//                SurveyLoss surveyLoss = new SurveyLoss(bhFaultplandict.getMalfunctionid(), bhFaultplandict.getMalfunctionname(),
//                        bhFaultplandict.getSolutionid(), bhFaultplandict.getSolution(),
//                        bhFaultplandict.getTopid(), bhFaultplandict.getTopname(), "", "", bhFaultplandict.getPrice(), "", "");
//
//                surList.add(surveyLoss);
//            }
//            String operationTime = DateUtils.formatDateTime(new Date());
//            HtRepairClientForm repairClientForm = new HtRepairClientForm();
//            repairClientForm.setHtFormInfo(new HtFormInfo(formInfo.getId()));
//            List<HtRepairClientForm> list = htRepairClientFormService.findList(repairClientForm);
//            HtRepairClientForm repair = list.get(0);
//            Double sumPrice = repair.getSumPrice();
//            Double selfPrice = repair.getSelfPrice();
//            Result<String> resultSur = boHaiInterfaceService.survey(new SurveyParameter(bhFormInfo.getOrderId(), bhFormInfo.getClnNo(), bhFormInfo.getDeviceCode(),
//                    operationTime, sumPrice.toString(), selfPrice.toString(), bhFormInfo.getDeviceType(), bhFormInfo.getDeviceBrand(),
//                    bhFormInfo.getDeviceModel(), bhFormInfo.getDeviceAttr(), surList));
//            if (!resultSur.getStatus().equals(ResultStatus.SUCCESS)) {
//                throw new RuntimeException("101");
//            }
//
//            Result result = boHaiInterfaceService.savePayeeInformation(new PayeeInformationParameter(bhFormInfo.getClnNo(), htReceiptData.getInsuranceNumber(), htReceiptData.getProvinceCode(), htReceiptData.getCityCode(), htReceiptData.getDistrictCode(), htReceiptData.getPayeeName(), htReceiptData.getPayeeIdNumber(), htReceiptData.getBankName(), htReceiptData.getBankType(), htReceiptData.getBankNumber()));
//            if (!result.getStatus().equals(ResultStatus.SUCCESS)) {
//                throw new RuntimeException("202");
//            }
//            //回传后修改回传状态
//            HtReceiptData receiptData = new HtReceiptData(htReceiptData.getId());
//            receiptData.setPassBack("1");
//            dao.update(receiptData);
//        }
    }

    /**
     * 更新状态
     *
     * @param htReceiptData
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(HtReceiptData htReceiptData) {
        super.updateStatus(htReceiptData);
    }

    /**
     * 删除数据
     *
     * @param htReceiptData
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(HtReceiptData htReceiptData) {
        super.delete(htReceiptData);
    }

    public HtReceiptData findByFormId(String formId) {
        return dao.findByFormId(formId);
    }
}