/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.repair.service;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import com.jeesite.modules.bohai.BoHaiInterfaceService;
import com.jeesite.modules.bohai.entity.Result;
import com.jeesite.modules.bohai.entity.ResultStatus;
import com.jeesite.modules.bohai.entity.UpLoadImageParameter;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.common.OperationStatus;
import com.jeesite.modules.deductible.dao.HtDeductibleInfoDao;
import com.jeesite.modules.deductible.entity.HtDeductibleInfo;
import com.jeesite.modules.deductible.service.HtDeductibleInfoService;
import com.jeesite.modules.expressage.entity.HtExpressage;
import com.jeesite.modules.expressage.service.HtExpressageService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.httimeefficiency.entity.HtTimeEfficiency;
import com.jeesite.modules.httimeefficiency.service.HtTimeEfficiencyService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyDetailService;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.receipt.dao.HtReceiptDataDao;
import com.jeesite.modules.receipt.entity.HtReceiptData;
import com.jeesite.modules.receipt.service.HtReceiptDataService;
import com.jeesite.modules.repair.dao.HtRepairClientFormDao;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import com.jeesite.modules.repair.entity.HtRepairEndForm;
import com.jeesite.modules.repair.entity.RepairListEntity;
import com.jeesite.modules.repair.web.imageUtils;
import com.jeesite.modules.sys.entity.*;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.template.utils.NoteTemplateUtils;
import com.jeesite.modules.template.utils.SmsSendUtils;
import com.jeesite.modules.template.utils.WxTemplateUtils;
import com.jeesite.modules.user.entity.HtUserInfo;
import com.jeesite.modules.user.service.HtUserInfoService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 维修工单-待联系客户Service
 *
 * @author lichao
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class HtRepairClientFormService extends CrudService<HtRepairClientFormDao, HtRepairClientForm> {
    @Autowired
    private HtFormInfoService htFormInfoService;
    @Autowired
    private HtReceiptDataService htReceiptDataService;
    @Autowired
    private EmpUserService empUserService;
    @Autowired
    private PolicyInfoService policyInfoService;
    @Autowired
    private HtTimeEfficiencyService htTimeEfficiencyService;
    @Autowired
    private BpmTaskService bpmTaskService;
    @Autowired
    private ActTaskUtils actTaskUtils;
    @Autowired
    private HtReceiptDataDao htReceiptDataDao;
    @Autowired
    private HtDeductibleInfoDao htDeductibleInfoDao;
    @Autowired
    private HtMaintenancePointService htMaintenancePointService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private WxTemplateUtils wxTemplateUtils;
    @Autowired
    private HtUserInfoService htUserInfoService;
    @Autowired
    private BoHaiInterfaceService boHaiInterfaceService;
    @Autowired
    private HtUserApplyInfoService userApplyInfoService;
    @Autowired
    private PolicyDetailService policyDetailService;
    @Autowired
    private HtExpressageService htExpressageService;
    @Autowired
    private HtDeductibleInfoService htDeductibleInfoService;


    /**
     * 获取单条数据
     *
     * @param htRepairClientForm
     * @return
     */
    @Override
    public HtRepairClientForm get(HtRepairClientForm htRepairClientForm) {
        return super.get(htRepairClientForm);
    }

    /**
     * 查询分页数据
     *
     * @param htRepairClientForm      查询条件
     * @return
     */
    @Override
    public Page<HtRepairClientForm> findPage(HtRepairClientForm htRepairClientForm) {
        return super.findPage(htRepairClientForm);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param htRepairClientForm
     */
    @Override
    @Transactional(readOnly = false)
    public void save(HtRepairClientForm htRepairClientForm) {
        BpmParams bpmParams = htRepairClientForm.getBpm();
        BigDecimal num = new BigDecimal(100);


        HtFormInfo htFormInfo = htRepairClientForm.getHtFormInfo();

        // 如果未设置状态，则指定状态为审核状态，以提交审核流程
        if (StringUtils.isBlank(htFormInfo.getStatus())) {
            htFormInfo.setStatus(DataEntity.STATUS_AUDIT);
        }

        // 如果状态为正常，则代表不正常操作，可能前端进行了数据参数修改
        if (DataEntity.STATUS_NORMAL.equals(htFormInfo.getStatus())) {
            throw new ServiceException(text("非法操作，前端数据被劫持！"));
        }

        // 如果状态为草稿或审核状态，才可以保存业务数据
        if (DataEntity.STATUS_DRAFT.equals(htFormInfo.getStatus())
                || DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())
                || DataEntity.STATUS_AUDIT_BACK.equals(htFormInfo.getStatus())) {

            if ("repair_wait".equals(htRepairClientForm.getBpm().getActivityId())) {
                htRepairClientForm.setRepairCount(htRepairClientForm.getRepairCount()+1);
            }

            //待报价处理金额
            if ("repair_charge".equals(htRepairClientForm.getBpm().getActivityId())
            || "repair_offer".equals(htRepairClientForm.getBpm().getActivityId())
                    || "repair_manager".equals(htRepairClientForm.getBpm().getActivityId())
                    || "repair_change_offer".equals(htRepairClientForm.getBpm().getActivityId())) {
            if(htRepairClientForm.getOtherPrice() != null){

                htRepairClientForm.setOtherPrice(htRepairClientForm.getOtherPrice().multiply(num));
            }
            if(htRepairClientForm.getManHourPrice() != null){
                htRepairClientForm.setManHourPrice(htRepairClientForm.getManHourPrice().multiply(num));
            }
            if(htRepairClientForm.getSelfPrice() != null){
                htRepairClientForm.setSelfPrice(htRepairClientForm.getSelfPrice().multiply(num));
            }
            if(htRepairClientForm.getSumPrice() != null){
                htRepairClientForm.setSumPrice(htRepairClientForm.getSumPrice().multiply(num));
            }
            }

            super.save(htRepairClientForm);
            if ("repair_info".equals(htRepairClientForm.getBpm().getActivityId())) {
                FileUploadUtils.saveFileUpload(htRepairClientForm.getId(), "clientData_image");
            }
            if ("repair_offer".equals(htRepairClientForm.getBpm().getActivityId())) {
                FileUploadUtils.saveFileUpload(htRepairClientForm.getId(), "damageImg_image");
            }
            if ("repair_end".equals(htRepairClientForm.getBpm().getActivityId())) {
                FileUploadUtils.saveFileUpload(htRepairClientForm.getId(), "repairEndImage_image");
                FileUploadUtils.saveFileUpload(htRepairClientForm.getId(), "repairFormImage_image");
            }
            if ("repair_wait".equals(htRepairClientForm.getBpm().getActivityId())) {
                FileUploadUtils.saveFileUpload(htRepairClientForm.getId(), "newImage_image");
            }



        }

        // 如果为审核状态，则进行审批流操作
        if (DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())
                || DataEntity.STATUS_AUDIT_BACK.equals(htFormInfo.getStatus())) {

            // 指定流程变量，作为流程条件，决定流转方向
            Map<String, Object> variables = MapUtils.newHashMap();
            //维修待联系客户环节---是否成功联系，1成功联系
            if ("repair_info".equals(htRepairClientForm.getBpm().getActivityId())) {


                if(htRepairClientForm.getOperationType().equals(OperationStatus.SQ_GP)){
                    //申请改派
                    variables.put("checkStatus", '5');
                    htFormInfo.setManageStatus(ManageStatus.WX_SQGP_DSH);
                    htFormInfo.setFormStatus(FormStatus.WX_SQGP_DSH);

                }else{
                //1成功联系
                if ("1".equals(htRepairClientForm.getContactStatus())) {
                    //理赔资料状态1已邮寄2未邮寄3已签收4未确认
                    //未邮寄或者未确认
                    if ("2".equals(htRepairClientForm.getClaimStatus()) || "4".equals(htRepairClientForm.getClaimStatus())) {
                        variables.put("checkStatus", '2');
                        htFormInfo.setManageStatus(ManageStatus.WX_DHSYJZT);
                        htFormInfo.setFormStatus(FormStatus.WX_DQS);

                    }
                    //已邮寄
                    if ("1".equals(htRepairClientForm.getClaimStatus())) {
                        bpmParams.setPriority(TaskFormEnum.TaskFormColorEnum.WRITE.getCode());
                        variables.put("checkStatus", '2');
                        htFormInfo.setManageStatus(ManageStatus.WX_KHYJJ_DQS);
                        htFormInfo.setFormStatus(FormStatus.WX_DQS);
                    }
                    //已签收
                    if ("3".equals(htRepairClientForm.getClaimStatus())) {
                        //资料是否合格1是2否
                        if ("1".equals(htRepairClientForm.getIsQualified())) {
                            //发送短信
                            String content = NoteTemplateUtils.noteTemplateThreeRepair();
                            SmsSendUtils.send(htRepairClientForm.getHtClaimSettlementForm().getPhone(),content);

                            variables.put("checkStatus", '4');
                            htFormInfo.setManageStatus(ManageStatus.WX_DBJ);
                            htFormInfo.setFormStatus(FormStatus.WX_DBJ);
                            //提供查询数据
                            HtTimeEfficiency htTimeEfficiency = new HtTimeEfficiency();
                            htTimeEfficiency.setFormId(htFormInfo.getId());
                            htTimeEfficiency.setStartDate(new Date());
                            htTimeEfficiencyService.save(htTimeEfficiency);
                            //1是返修
                            if("1".equals(htRepairClientForm.getIsRepairBack())){
                                variables.put("checkStatus", '6');
                                htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                                htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                            }
                        } else {
                            HtMaintenancePoint htMaintenancePoint = new HtMaintenancePoint();
                            htMaintenancePoint.setOrganizationId(htFormInfo.getOffice().getId());
                            List<HtMaintenancePoint> list = htMaintenancePointService.findList(htMaintenancePoint);
                            htMaintenancePoint = list.get(0);
                            Area po = areaService.get(htMaintenancePoint.getProvince());
                            Area city = areaService.get(htMaintenancePoint.getCity());
                            Area area = areaService.get(htMaintenancePoint.getArea());

                            String address = po.getAreaName()+" "+city.getAreaName()+" "+area.getAreaName()+" "+htMaintenancePoint.getAddress()
                                    +"，收件人："+ htMaintenancePoint.getContactOne()
                                    +"，电话："+htMaintenancePoint.getContactNumberOne();

                            //缺失的资料
                            String information = htRepairClientForm.getLackClaimData();
                            //不合格发送短信
                            String content = NoteTemplateUtils.noteTemplateFourRepair(information,address);
                            SmsSendUtils.send(htRepairClientForm.getHtClaimSettlementForm().getPhone(),content);

                            //发送微信
                            HtUserInfo htUserInfo = htUserInfoService.get(htFormInfo.getUserId());
                            wxTemplateUtils.sendWxTemplateStatusTwo(htUserInfo.getUserPhone(),htUserInfo.getUserName(),htUserInfo.getOpenId());

                            variables.put("checkStatus", '2');
                            htFormInfo.setManageStatus(ManageStatus.WX_BHG_DHSYJ);
                            htFormInfo.setFormStatus(FormStatus.WX_DLR);

                        }
                    }
                } else {
                    variables.put("checkStatus", '2');
                    htFormInfo.setManageStatus(ManageStatus.WX_DLX);
                    htFormInfo.setFormStatus(FormStatus.WX_DCL);

                }
                }



            }

            //维修确认客户邮寄时间环节1已确认，2未确认未成功联系
            if ("repair_confirm_time".equals(htRepairClientForm.getBpm().getActivityId())) {
                if (htRepairClientForm.getMailDate() != null) {
                    variables.put("checkStatus", '1');
                    htFormInfo.setManageStatus(ManageStatus.WX_KHYJJ_DQS);
                    htFormInfo.setFormStatus(FormStatus.WX_DQS);
                } else {
                    variables.put("checkStatus", '2');
                    htFormInfo.setManageStatus(ManageStatus.WX_WYJ_DHS);
                    htFormInfo.setFormStatus(FormStatus.WX_DQS);
                }
            }

            //维修---等待签收环节，1已签收，2未签收
            if ("repair_sign_for".equals(htRepairClientForm.getBpm().getActivityId())) {
                if ("3".equals(htRepairClientForm.getClaimStatus())) {
                    variables.put("checkStatus", '1');
                    htFormInfo.setManageStatus(ManageStatus.WX_YQS_DLR);
                    htFormInfo.setFormStatus(FormStatus.WX_DLR);
                } else {
                    variables.put("checkStatus", '2');
                    htFormInfo.setManageStatus(ManageStatus.WX_DHSYJZT);
                    htFormInfo.setFormStatus(FormStatus.WX_DQS);
                }
            }

            //维修---核对邮寄资料，录入资料环节，1齐全，2不齐全
            if ("repair_receive".equals(htRepairClientForm.getBpm().getActivityId())) {
                //申请改派
                if(htRepairClientForm.getOperationType().equals(OperationStatus.SQ_GB)){
                    variables.put("checkStatus", '5');
                    htFormInfo.setManageStatus(ManageStatus.WX_SQGP_DSH);
                    htFormInfo.setFormStatus(FormStatus.WX_SQGP_DSH);
                }else{
                    if ("1".equals(htRepairClientForm.getIsQualified())) {
                        variables.put("checkStatus", '1');
                        htFormInfo.setManageStatus(ManageStatus.WX_DBJ);
                        htFormInfo.setFormStatus(FormStatus.WX_DBJ);
                    } else {
                        variables.put("checkStatus", '2');
                        htFormInfo.setManageStatus(ManageStatus.WX_BHG_DHSYJ);
                        htFormInfo.setFormStatus(FormStatus.WX_DLR);
                    }
                }
            }


            //维修---填写待报价工单，
            if ("repair_offer".equals(htRepairClientForm.getBpm().getActivityId())) {
                htFormInfo.setManageStatus(ManageStatus.WX_BJ_DSH);
                htFormInfo.setFormStatus(FormStatus.WX_BJ_DSH);
            }

            //维修---售后主管审批，大于3000售后经理，小于等于3000付款，等于0进入待维修
            if ("repair_charge".equals(htRepairClientForm.getBpm().getActivityId())) {
                    //1全损2非全损
                    if ("1".equals(htRepairClientForm.getIsAll())) {
                        variables.put("checkStatus", '2');
                        HtReceiptData htReceiptData = new HtReceiptData();
                        HtUserApplyInfo userApply = userApplyInfoService.findByFormId(htFormInfo.getId());

                        Map<String, Object> areaMap = userApplyInfoService.finfArea(userApply.getAreaId());
                       // String provinceCode = areaService.get((String) areaMap.get("province_code")).getAreaName();
                       // String cityCode = areaService.get((String) areaMap.get("city_code")).getAreaName();
                        //String areaCode = areaService.get((String) areaMap.get("area_code")).getAreaName();

                        htReceiptData.setHtFormInfo(htFormInfo);

                        List<HtReceiptData> list = htReceiptDataService.findList(htReceiptData);
                        if(list.size() == 0){
                            htReceiptData.setProvinceCode((String) areaMap.get("province_code"));
                            htReceiptData.setCityCode((String) areaMap.get("city_code"));
                            htReceiptData.setDistrictCode((String) areaMap.get("area_code"));
                            htReceiptData.setBdId(htRepairClientForm.getId());
                            htReceiptData.setId(IdGen.nextId());
                            htReceiptData.setCreateDate(new Date());

                            String policyId = htFormInfo.getPolicyInfo().getId();
                            htReceiptData.setInsuranceNumber(policyId);
                            PolicyInfo policyInfo = policyInfoService.get(policyId);
                            htReceiptData.setPayeeIdNumber(policyInfo.getStrCardId());
                            htReceiptDataDao.insert(htReceiptData);
                        }


                        htFormInfo.setManageStatus(ManageStatus.QS_DLR);
                        htFormInfo.setFormStatus(FormStatus.QS_DLR);
                    } else {

                        //是否申请修改维修方案0否1是
                        if("1".equals(htRepairClientForm.getIsYes())){
                            htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                            htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                            variables.put("checkStatus", '1');
                        }else{
                            //1维修点代收2财务代收
                            if("1".equals(htRepairClientForm.getReceiptType())){
                                htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                                htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                                variables.put("checkStatus", '1');
                                //大于300000
                                if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(300000)) == 1){
                                    htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                                    htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                                    variables.put("checkStatus", '3');
                                }
                                //等于0
                                if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(0)) == 0){
                                    htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                                    htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                                    variables.put("checkStatus", '1');
                                }
                            }

                            if("2".equals(htRepairClientForm.getReceiptType())){
                                //大于300000
                                if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(300000)) == 1){
                                    htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                                    htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                                    variables.put("checkStatus", '3');
                                }
                                //大于0并且小于等于300000
                                if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(0)) == 1 && htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(300000)) < 1){
                                    BigDecimal sumPrice = htRepairClientForm.getSumPrice().divide(num);
                                    BigDecimal selfPrice = htRepairClientForm.getSelfPrice().divide(num);


                                    htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG_DFK);
                                    htFormInfo.setFormStatus(FormStatus.WX_DFK);
                                    variables.put("checkStatus", '4');
                                    //存储待付款信息
                                    HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
                                    htDeductibleInfo.setHtFormInfo(htRepairClientForm.getHtFormInfo());
                                    List<HtDeductibleInfo> list = htDeductibleInfoService.findList(htDeductibleInfo);
                                    if(list.size() > 0){
                                        htDeductibleInfo = list.get(0);
                                    }
                                    htDeductibleInfo.setPhone(htRepairClientForm.getHtClaimSettlementForm().getPhone());
                                    htDeductibleInfo.setUserName(htRepairClientForm.getHtClaimSettlementForm().getUserName());
                                    htDeductibleInfo.setHtFormInfo(htFormInfo);
                                    htDeductibleInfo.setBdId(htRepairClientForm.getId());
                                    htDeductibleInfo.setDeductiblesLimit(htRepairClientForm.getSelfPrice().doubleValue());
                                    htDeductibleInfo.setCreateDate(new Date());
                                    PolicyInfo policyInfo = policyInfoService.get(htFormInfo.getPolicyInfo());
                                    htDeductibleInfo.setProductId(policyInfo.getChannelProductId());
                                    htDeductibleInfo.setProductName(policyInfo.getChannelProductName());
                                    htDeductibleInfo.setFormType("2");
                                    htDeductibleInfo.setMaintainTypr("2");
                                    htDeductibleInfoService.save(htDeductibleInfo);

                                    //发送短信
                                    String content = NoteTemplateUtils.noteTemplateSixRepair(sumPrice,selfPrice);
                                    SmsSendUtils.send(htRepairClientForm.getHtClaimSettlementForm().getPhone(),content);
                                    //发送微信通知
                                    String date = DateUtils.formatDateTime(new Date());
                                    HtUserInfo htUserInfo = htUserInfoService.get(htFormInfo.getUserId());
                                    wxTemplateUtils.sendWxTemplateStatusSix("",htFormInfo.getId(),selfPrice.toString(),htUserInfo.getOpenId());
                                }
                                //等于0
                                if(htRepairClientForm.getSelfPrice().compareTo(new BigDecimal(0)) == 0){
                                    htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                                    htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                                    variables.put("checkStatus", '1');
                                }
                            }
                        }
                    }
            }

            //维修---售后经理审批
            if ("repair_manager".equals(htRepairClientForm.getBpm().getActivityId())) {

                //是否申请修改维修方案0否1是
                if("1".equals(htRepairClientForm.getIsYes())){
                    htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                    htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                    variables.put("checkStatus", '1');
                }else{
                    //1维修点代收2财务代收
                    if("1".equals(htRepairClientForm.getReceiptType())){
                        htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG);
                        htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                        variables.put("checkStatus", '2');
                    }else{
                        variables.put("checkStatus", '1');
                        htFormInfo.setManageStatus(ManageStatus.WX_BJ_SHTG_DFK);
                        htFormInfo.setFormStatus(FormStatus.WX_DFK);
                        //存储待付款信息
                        HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
                        htDeductibleInfo.setHtFormInfo(htRepairClientForm.getHtFormInfo());
                        List<HtDeductibleInfo> list = htDeductibleInfoService.findList(htDeductibleInfo);
                        if(list.size() > 0){
                            htDeductibleInfo = list.get(0);
                        }
                        htDeductibleInfo.setPhone(htRepairClientForm.getHtClaimSettlementForm().getPhone());
                        htDeductibleInfo.setUserName(htRepairClientForm.getHtClaimSettlementForm().getUserName());
                        htDeductibleInfo.setHtFormInfo(htFormInfo);
                        htDeductibleInfo.setBdId(htRepairClientForm.getId());
                        htDeductibleInfo.setDeductiblesLimit(htRepairClientForm.getSelfPrice().doubleValue());
                        htDeductibleInfo.setCreateDate(new Date());
                        PolicyInfo policyInfo = policyInfoService.get(htFormInfo.getPolicyInfo());
                        htDeductibleInfo.setProductId(policyInfo.getChannelProductId());
                        htDeductibleInfo.setProductName(policyInfo.getChannelProductName());
                        htDeductibleInfo.setFormType("2");
                        htDeductibleInfo.setMaintainTypr("2");
                        htDeductibleInfoService.save(htDeductibleInfo);

                    }
                }

                BigDecimal sumPrice = htRepairClientForm.getSumPrice().divide(num);
                BigDecimal selfPrice = htRepairClientForm.getSelfPrice().divide(num);
                //发送短信
                String content = NoteTemplateUtils.noteTemplateSixRepair(sumPrice,selfPrice);
                SmsSendUtils.send(htRepairClientForm.getHtClaimSettlementForm().getPhone(),content);
                //发送微信通知
                String date = DateUtils.formatDateTime(new Date());
                HtUserInfo htUserInfo = htUserInfoService.get(htFormInfo.getUserId());
                wxTemplateUtils.sendWxTemplateStatusSix("",htFormInfo.getId(),selfPrice.toString(),htUserInfo.getOpenId());

            }
            //维修---等待付款
            if ("repair_pay".equals(htRepairClientForm.getBpm().getActivityId())) {
                htFormInfo.setManageStatus(ManageStatus.WX_YSZFK_DWX);
                htFormInfo.setFormStatus(FormStatus.WX_WXZ);
            }


            //维修---付款财务审核
            if ("repair_finance".equals(htRepairClientForm.getBpm().getActivityId())) {

                HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
                htDeductibleInfo.setHtFormInfo(htRepairClientForm.getHtFormInfo());

                List<HtDeductibleInfo> list = htDeductibleInfoService.findList(htDeductibleInfo);
                if(list.size()>0){
                    htDeductibleInfo = list.get(0);
                    htDeductibleInfo.setAffirmStatus("0");
                    htDeductibleInfoService.save(htDeductibleInfo);
                }
                HtMaintenancePoint htMaintenancePoint = htMaintenancePointService.findInfoByJG(htFormInfo.getOffice().getOfficeCode());

                //发送短信
                String content = NoteTemplateUtils.noteTemplateSevenRepair(htMaintenancePoint.getContactNumberOne());
                SmsSendUtils.send(htRepairClientForm.getHtClaimSettlementForm().getPhone(),content);

                htFormInfo.setManageStatus(ManageStatus.WX_YSZFK_DWX);
                htFormInfo.setFormStatus(FormStatus.WX_WXZ);
            }


            //维修---审核改派申请
            if ("repair_change_time".equals(htRepairClientForm.getBpm().getActivityId())) {

                htFormInfo.setManageStatus(ManageStatus.WX_GPWC);
                htFormInfo.setFormStatus(FormStatus.WX_GPWC);
            }

            //维修---售后主管关闭审核
            if ("repair_charge_close".equals(htRepairClientForm.getBpm().getActivityId())) {
                htFormInfo.setManageStatus(ManageStatus.WX_SQGB_SHTG);
                htFormInfo.setFormStatus(FormStatus.WX_SQGB_SHTG);

            }


            //维修---已关闭待重启
            if ("repair_wait_restart".equals(htRepairClientForm.getBpm().getActivityId())) {
                htFormInfo.setManageStatus(ManageStatus.WX_SQGB_SHTG);
                htFormInfo.setFormStatus(FormStatus.WX_SQGB_SHTG);
            }




            //维修---待维修工单1维修完成2维修到期
            if ("repair_wait".equals(htRepairClientForm.getBpm().getActivityId())) {
                int count = htRepairClientForm.getRepairCount();
                //1未完成，2维修完成

                if("1".equals(htRepairClientForm.getIsEnd())){
                    //是否修改维修方案1是
                    if("1".equals(htRepairClientForm.getIsYes())){
                        variables.put("checkStatus", "4");
                        htFormInfo.setManageStatus(ManageStatus.WX_SQ_WXFA);
                        htFormInfo.setFormStatus(FormStatus.WX_XGBJ_DSH);
                    }else{
                        variables.put("checkStatus", "2");
                        htFormInfo.setManageStatus(ManageStatus.WX_YXG);
                        htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                        //维修次数大于3提交审核
                        if(count >= 3){
                            variables.put("checkStatus", "3");
                            htFormInfo.setManageStatus(ManageStatus.WX_XGWXSJ_DSH);
                            htFormInfo.setFormStatus(FormStatus.WX_XGWXSJ_DSH);
                        }
                    }

                }

                if("2".equals(htRepairClientForm.getIsEnd())){
                    variables.put("checkStatus", "1");
                    htFormInfo.setManageStatus(ManageStatus.WX_WXWC_DJJ);
                    htFormInfo.setFormStatus(FormStatus.WX_WXZ);
                    //扣减保额
                    policyInfoService.minusCoverage(htFormInfo.getPolicyInfo().getId(),htRepairClientForm.getSumPrice().doubleValue(),htFormInfo.getId());

                }

            }


            //修改维修方案
            if ("repair_change_offer".equals(htRepairClientForm.getBpm().getActivityId())) {
                variables.put("checkStatus", "1");
                htFormInfo.setManageStatus(ManageStatus.WX_SQ_WXFA_SHTG);
                htFormInfo.setFormStatus(FormStatus.WX_DBJ);
            }

            //寄件完成
            if ("repair_end".equals(htRepairClientForm.getBpm().getActivityId())) {
                htFormInfo.setManageStatus(ManageStatus.WX_WXWC_DSH);
                htFormInfo.setFormStatus(FormStatus.WX_WXWC_DSH);

            }

            //维修完成待审核
            if ("repair_end_check".equals(htRepairClientForm.getBpm().getActivityId())) {
                //2客户自取
                String content = "";
                //发送短信
                if(htRepairClientForm.getEquipmentTake().equals("2")){
                    content = NoteTemplateUtils.noteTemplateEight();
                }else{
                    HtExpressage htExpressage = htExpressageService.get(htRepairClientForm.getRepairExpressCompany());
                    content = NoteTemplateUtils.noteTemplateTen(htExpressage.getExprname(),htRepairClientForm.getRepairExpressNo());
                }
                SmsSendUtils.send(htRepairClientForm.getHtClaimSettlementForm().getPhone(),content);
                //发送微信通知维修报告
                String date = DateUtils.formatDate(new Date());
                HtUserInfo htUserInfo = htUserInfoService.get(htFormInfo.getUserId());
                //设备名称
                String equi = htRepairClientForm.getHtBrandInfo().getName()+htRepairClientForm.getHtPhoneModelInfo().getModel();
                wxTemplateUtils.sendWxTemplateStatusSeven(htUserInfo.getUserName(),equi,date,htUserInfo.getOpenId());

                variables.put("checkStatus", "1");
                htFormInfo.setManageStatus(ManageStatus.WX_WXWC);
                htFormInfo.setFormStatus(FormStatus.WX_WXWC);

                HtGroupProductInfo htGroupProductInfo = policyInfoService.findGpBypolicyId(htFormInfo.getPolicyInfo().getId());
                //0单一1复合
                if(htGroupProductInfo.getTerminationRules().equals("0")){
                    policyInfoService.terminate(htFormInfo.getId());
                }else{
                    //复合产品终止
                    String weixiuId = htRepairClientForm.getWeixiuList().get(0).getId();
                    String huanjiId = htRepairClientForm.getHuanjiList().get(0).getId();
                    String yanboId = htRepairClientForm.getYanbaoList().get(0).getId();
                    String shujuId = htRepairClientForm.getShujubaoList().get(0).getId();

                    String childId = htRepairClientForm.getHtClaimSettlementForm().getProductChildId();

                    if(childId.equals(weixiuId)){
                        if("1".equals(htRepairClientForm.getWeixiuList().get(0).getIsEnd())){
                            policyDetailService.overEquity(htFormInfo.getPolicyInfo().getId(),weixiuId);
                        }
                    }
                    if(childId.equals(huanjiId)){
                        if("1".equals(htRepairClientForm.getHuanjiList().get(0).getIsEnd())){
                            policyDetailService.overEquity(htFormInfo.getPolicyInfo().getId(),huanjiId);
                        }
                    }
                    if(childId.equals(yanboId)){
                        if("1".equals(htRepairClientForm.getYanbaoList().get(0).getIsEnd())){
                            policyDetailService.overEquity(htFormInfo.getPolicyInfo().getId(),yanboId);
                        }
                    }
                    if(childId.equals(shujuId)){
                        if("1".equals(htRepairClientForm.getShujubaoList().get(0).getIsEnd())){
                            policyDetailService.overEquity(htFormInfo.getPolicyInfo().getId(),shujuId);
                        }
                    }
                }



                //存储数据提供查询统计
                HtTimeEfficiency htTimeEfficiency = new HtTimeEfficiency();
                htTimeEfficiency.setFormId(htFormInfo.getId());
                htTimeEfficiencyService.updateFirst(htTimeEfficiency);
            }

            //维修结束待返修
            if ("repair_end_restart".equals(htRepairClientForm.getBpm().getActivityId())) {
                variables.put("checkStatus", "2");
                htFormInfo.setManageStatus(ManageStatus.WX_SQFX);
                htFormInfo.setFormStatus(FormStatus.WX_SQFX);
            }


            //提交任务
            bpmParams.setComment(htRepairClientForm.getRemarks());

            //指定环节进行人员分配
            if ("repair_info".equals(htRepairClientForm.getBpm().getActivityId())
                || "repair_charge_over".equals(htRepairClientForm.getBpm().getActivityId())
                || "repair_finance".equals(htRepairClientForm.getBpm().getActivityId())) {

                String userCodes = getUserCodes(htFormInfo.getOffice().getOfficeCode());
                bpmParams.setNextUserCodes(userCodes);
            }



            if("repair_wait".equals(htRepairClientForm.getBpm().getActivityId())){
                int count = htRepairClientForm.getRepairCount();
                if("1".equals(variables.get("checkStatus").toString()) || "2".equals(variables.get("checkStatus").toString())){
                    String userCodes = getUserCodes(htFormInfo.getOffice().getOfficeCode());
                    bpmParams.setNextUserCodes(userCodes);
                }
            }

            //维修--直接进入待维修 直接进行指派维修网点人员
            if ("repair_charge".equals(htRepairClientForm.getBpm().getActivityId())) {
                if(variables.get("checkStatus").toString().equals("1")){
                    String userCodes = getUserCodes(htFormInfo.getOffice().getOfficeCode());
                    bpmParams.setNextUserCodes(userCodes);
                }
            }
            //维修-申请修改维修方案 直接进行指派维修网点人员
            if ("repair_change_offer".equals(htRepairClientForm.getBpm().getActivityId())) {
                if(variables.get("checkStatus").toString().equals("1")){
                    String userCodes = getUserCodes(htFormInfo.getOffice().getOfficeCode());
                    bpmParams.setNextUserCodes(userCodes);
                }
            }
            htFormInfo.setBpm(bpmParams);
            BpmUtils.complete(htFormInfo, variables, null);
            htFormInfoService.update(htFormInfo);
        }
    }

    //获取机构下的所有用户
    public String getUserCodes(String officeCode){
        StringBuilder userCodes = new StringBuilder();
        Employee employee = new Employee();
        employee.setOffice(new Office(officeCode));
        EmpUser empUser = new EmpUser();
        empUser.setEmployee(employee);

        List<EmpUser> employeeList = empUserService.findList(empUser);
        for (int i = 0; i < employeeList.size(); i++) {
            userCodes.append(employeeList.get(i).getId());
            userCodes.append(",");
        }
        if(employeeList.size() > 0){
            userCodes.deleteCharAt(userCodes.length() - 1);
        }

        return userCodes.toString();
    }

    /**
     * 更新状态
     *
     * @param htRepairClientForm
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(HtRepairClientForm htRepairClientForm) {
        super.updateStatus(htRepairClientForm);
    }

    /**
     * 删除数据
     *
     * @param htRepairClientForm
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(HtRepairClientForm htRepairClientForm) {
        super.delete(htRepairClientForm);
    }


    /**
     * 查询流程列表
     * @return
     */
    public Page<RepairListEntity> findRepairList(RepairListEntity repairListEntity, HttpServletRequest request, HttpServletResponse response) {

        Page<RepairListEntity> page = new Page<>(request, response);
        repairListEntity.setPage(page);
        return page.setList(dao.findRepairList(repairListEntity));
    }


    /**
     * 返修开启
     *
     * @param htRepairClientForm
     *
     * 返回 officeCode
     */
    @Transactional(readOnly = false)
    public void repairBack(HtRepairClientForm htRepairClientForm) {
        List<HtRepairClientForm> list = super.findList(htRepairClientForm);
        if(list.size() > 0){
            htRepairClientForm = list.get(0);
        }

        HtFormInfo htFormInfo = htFormInfoService.get(htRepairClientForm.getHtFormInfo());
        String officeCode = htFormInfo.getOffice().getOfficeCode();
        Map<String, Object> variables = MapUtils.newHashMap();
        variables.put("checkStatus","2");
        Task task = actTaskUtils.getTask(htFormInfo.getId());
        BpmTask bpmTask = new BpmTask(task);
        bpmTask.setNextUserCodes(getUserCodes(officeCode));
        bpmTask.setVariables(variables);
        User user = UserUtils.get("system");
        bpmTask.setCurrentUser(user);
        bpmTaskService.completeTask(bpmTask);
        htRepairClientForm.setIsRepairBack("1");
        super.save(htRepairClientForm);
        htFormInfo.setManageStatus(ManageStatus.WX_DLX);
        htFormInfo.setFormStatus(FormStatus.LP_DCL);
        htFormInfoService.update(htFormInfo);

    }

    /**
     * 查询历史维修记录
     * @param htFormInfo
     * @return
     */
    @Transactional(readOnly = false)
    public List<HtRepairClientForm> findHistory(HtFormInfo htFormInfo){

        return dao.findHistory(htFormInfo);
    }


    /**
     * 查询理赔已选择的资料
     * @param claimId
     * @return
     */

    public List<Map<String, Object>> findClaimData(String claimId) {

        return  dao.findClaimData(claimId);
    }
    /**
     * 影像资料上传
     * @param imgUrl 图片数组多张
     * @param clmNo  渤海案件号
     * @return
     */
    public boolean uploadImage(String imgUrl,String clmNo){
        String[] imgArr= StringUtils.split(imgUrl,"|");
        for (int i = 0; i < imgArr.length; i++) {
            String imageType = StringUtils.substringAfterLast(imgArr[i],".");
            String base64Image = imageUtils.imageToBase64Html(imgArr[i],imageType);
            Result resultImage = boHaiInterfaceService.upLoadImage(new UpLoadImageParameter(base64Image,clmNo,clmNo));
            if(!resultImage.getStatus().equals(ResultStatus.SUCCESS)){
                return false;
            }
        }
        return  true;
    }


    /**
     * 查询部件
     * @param bujianId
     * @return
     */

    public List<HtAssemblyUnit> findBuJian(String bujianId) {

        return  dao.findBuJian(bujianId);
    }


    /**
     * 查询维修工单列表
     * @param htRepairEndForm
     * @return
     */
    public Page<HtRepairEndForm> findEndList(HtRepairEndForm htRepairEndForm) {
        Page<HtRepairEndForm> page = (Page<HtRepairEndForm>) htRepairEndForm.getPage();
        page.setList(dao.findEndList(htRepairEndForm));
        return page;
    }

    /**
     * 查询维修工单列表
     * @param htRepairEndForm
     * @return
     */
    public Page<HtRepairEndForm> findAllList(HtRepairEndForm htRepairEndForm) {
        Page<HtRepairEndForm> page = (Page<HtRepairEndForm>) htRepairEndForm.getPage();
        page.setList(dao.findAllList(htRepairEndForm));
        return page;
    }
}