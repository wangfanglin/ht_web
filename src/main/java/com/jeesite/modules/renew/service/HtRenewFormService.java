/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.renew.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.deductible.dao.HtDeductibleInfoDao;
import com.jeesite.modules.deductible.entity.HtDeductibleInfo;
import com.jeesite.modules.deductible.service.HtDeductibleInfoService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.httimeefficiency.service.HtTimeEfficiencyService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.renew.entity.HtRenewEndForm;
import com.jeesite.modules.renew.entity.RenewListEntity;
import com.jeesite.modules.repair.entity.HtRepairEndForm;
import com.jeesite.modules.repair.entity.RepairListEntity;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.template.utils.NoteTemplateUtils;
import com.jeesite.modules.template.utils.SmsSendUtils;
import com.jeesite.modules.template.utils.WxTemplateUtils;
import com.jeesite.modules.user.entity.HtUserInfo;
import com.jeesite.modules.user.service.HtUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.renew.entity.HtRenewForm;
import com.jeesite.modules.renew.dao.HtRenewFormDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 换新工单Service
 * @author lichao
 * @version 2020-03-25
 */
@Service
@Transactional(readOnly=true)
public class HtRenewFormService extends CrudService<HtRenewFormDao, HtRenewForm> {


	@Autowired
	private HtDeductibleInfoDao htDeductibleInfoDao;
	@Autowired
	private HtFormInfoDao htFormInfoDao;
	@Autowired
	private PolicyInfoService policyInfoService;

	@Autowired
	private HtMaintenancePointService htMaintenancePointService;
	@Autowired
	private AreaService areaService;

	@Autowired
	private WxTemplateUtils wxTemplateUtils;

	@Autowired
	private HtUserInfoService htUserInfoService;
	@Autowired
	private HtDeductibleInfoService htDeductibleInfoService;

	/**
	 * 获取单条数据
	 * @param htRenewForm
	 * @return
	 */
	@Override
	public HtRenewForm get(HtRenewForm htRenewForm) {
		return super.get(htRenewForm);
	}
	
	/**
	 * 查询分页数据
	 * @param htRenewForm 查询条件
	 * @return
	 */
	@Override
	public Page<HtRenewForm> findPage(HtRenewForm htRenewForm) {
		return super.findPage(htRenewForm);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htRenewForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtRenewForm htRenewForm) {
		HtFormInfo htFormInfo = htRenewForm.getHtFormInfo();
		BigDecimal num = new BigDecimal(100);


		// 如果未设置状态，则指定状态为审核状态，以提交审核流程
		if (StringUtils.isBlank(htFormInfo.getStatus())){
			htRenewForm.setStatus(htFormInfo.STATUS_AUDIT);
		}

		// 如果状态为正常，则代表不正常操作，可能前端进行了数据参数修改
		if (htFormInfo.STATUS_NORMAL.equals(htFormInfo.getStatus())){
			throw new ServiceException(text("非法操作，前端数据被劫持！"));
		}

		// 如果状态为草稿或审核状态，才可以保存业务数据
		if (DataEntity.STATUS_DRAFT.equals(htFormInfo.getStatus())
				|| DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())
				|| DataEntity.STATUS_AUDIT_BACK.equals(htFormInfo.getStatus())) {

			//待报价处理金额
			if ("renew_offer".equals(htRenewForm.getBpm().getActivityId())
					|| "renew_manager".equals(htRenewForm.getBpm().getActivityId())
					|| "renew_director".equals(htRenewForm.getBpm().getActivityId())) {
				if(htRenewForm.getOldPhonePrice() != null){
					htRenewForm.setOldPhonePrice(htRenewForm.getOldPhonePrice().multiply(num));
				}
				if(htRenewForm.getChannelPrice() != null){
					htRenewForm.setChannelPrice(htRenewForm.getChannelPrice().multiply(num));
				}
				if(htRenewForm.getPurchasePrice() != null){
					htRenewForm.setPurchasePrice(htRenewForm.getPurchasePrice().multiply(num));
				}
				if(htRenewForm.getPhonePrice() != null){
					htRenewForm.setPhonePrice(htRenewForm.getPhonePrice().multiply(num));
				}
				if(htRenewForm.getMarketPrice() != null){
					htRenewForm.setMarketPrice(htRenewForm.getMarketPrice().multiply(num));
				}
				if(htRenewForm.getOtherPrice() != null){
					htRenewForm.setOtherPrice(htRenewForm.getOtherPrice().multiply(num));
				}
				if(htRenewForm.getOldNewPrice() != null){
					htRenewForm.setOldNewPrice(htRenewForm.getOldNewPrice().multiply(num));
				}
				if(htRenewForm.getSelfPrice() != null){
					htRenewForm.setSelfPrice(htRenewForm.getSelfPrice().multiply(num));
				}
				if(htRenewForm.getExpressPrice() != null){
					htRenewForm.setExpressPrice(htRenewForm.getExpressPrice().multiply(num));
				}
			}

			if ("renew_end".equals(htRenewForm.getBpm().getActivityId())) {
				if (htRenewForm.getExpressPrice() != null) {
					htRenewForm.setExpressPrice(htRenewForm.getExpressPrice().multiply(num));
				}
				if(htRenewForm.getPhonePrice() != null){
					htRenewForm.setPhonePrice(htRenewForm.getPhonePrice().multiply(num));
				}
			}

			super.save(htRenewForm);

			if ("renew_info".equals(htRenewForm.getBpm().getActivityId())) {
				FileUploadUtils.saveFileUpload(htRenewForm.getId(), "clientRenew_image");
			}
			if ("renew_end".equals(htRenewForm.getBpm().getActivityId())) {
				FileUploadUtils.saveFileUpload(htRenewForm.getId(), "renewEnd_image");
			}

		}

		// 如果为审核状态，则进行审批流操作
		if (DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())
				|| DataEntity.STATUS_AUDIT_BACK.equals(htFormInfo.getStatus())) {
			// 指定流程变量，作为流程条件，决定流转方向
			Map<String, Object> variables = MapUtils.newHashMap();


			//换新待分配
			if (htRenewForm.getBpm().getActivityId().equals("renew_wait")) {
				variables.put("checkStatus", '1');

			}


			//换新-待联系
			if (htRenewForm.getBpm().getActivityId().equals("renew_info")) {
				//1成功联系
				if ("1".equals(htRenewForm.getContactStatus())) {
					//理赔资料状态1已邮寄2未邮寄3已签收4未确认
					//未邮寄或者未确认
					if ("2".equals(htRenewForm.getClaimStatus()) || "4".equals(htRenewForm.getClaimStatus())) {
						variables.put("checkStatus", '2');
						htFormInfo.setManageStatus(ManageStatus.WX_DHSYJZT);
						htFormInfo.setFormStatus(FormStatus.WX_DQS);

					}
					//已邮寄
					if ("1".equals(htRenewForm.getClaimStatus())) {
						variables.put("checkStatus", '2');
						htFormInfo.setManageStatus(ManageStatus.WX_KHYJJ_DQS);
						htFormInfo.setFormStatus(FormStatus.WX_DQS);
					}
					//已签收
					if ("3".equals(htRenewForm.getClaimStatus())) {
						if ("1".equals(htRenewForm.getIsQualified())) {
							//合格发送短信
							SmsSendUtils.send(htRenewForm.getHtClaimSettlementForm().getPhone(),htRenewForm.getSmsModel());
							variables.put("checkStatus", '4');
							htFormInfo.setManageStatus(ManageStatus.WX_DBJ);
							htFormInfo.setFormStatus(FormStatus.WX_DBJ);
						} else {
							//不合格发送短信
							SmsSendUtils.send(htRenewForm.getHtClaimSettlementForm().getPhone(),htRenewForm.getSmsModel());

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
			//换新-待报价
			if (htRenewForm.getBpm().getActivityId().equals("renew_offer")) {
				variables.put("checkStatus", '1');
				htFormInfo.setManageStatus(ManageStatus.HX_FA_DSH);
				htFormInfo.setFormStatus(FormStatus.HX_FA_DSH);
			}
			//换新-换机方案售后经理待审核小于等于1000000
			if (htRenewForm.getBpm().getActivityId().equals("renew_manager")) {
				BigDecimal sum = htRenewForm.getSelfPrice().add(htRenewForm.getOtherPrice());

				//等于0
				if(sum.compareTo(new BigDecimal(0)) == 0){
					htFormInfo.setManageStatus(ManageStatus.HX_FA_DFK);
					htFormInfo.setFormStatus(FormStatus.HX_DFK);
					variables.put("checkStatus", '2');
				}
				//大于0并且小于等于1000000
				if(sum.compareTo(new BigDecimal(0)) == 1 && sum.compareTo(new BigDecimal(1000000)) < 1){
					variables.put("checkStatus", '2');
					//存储待付款信息
					HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
					htDeductibleInfo.setHtFormInfo(htRenewForm.getHtFormInfo());
					List<HtDeductibleInfo> list = htDeductibleInfoService.findList(htDeductibleInfo);
					if(list.size() > 0){
						htDeductibleInfo = list.get(0);
					}
					htDeductibleInfo.setPhone(htRenewForm.getHtClaimSettlementForm().getPhone());
					htDeductibleInfo.setUserName(htRenewForm.getHtClaimSettlementForm().getUserName());
					htDeductibleInfo.setHtFormInfo(htFormInfo);
					htDeductibleInfo.setBdId(htRenewForm.getId());
					htDeductibleInfo.setDeductiblesLimit(htRenewForm.getSelfPrice().doubleValue());
					htDeductibleInfo.setCreateDate(new Date());
					PolicyInfo policyInfo = policyInfoService.get(htFormInfo.getPolicyInfo());
					htDeductibleInfo.setProductId(policyInfo.getChannelProductId());
					htDeductibleInfo.setProductName(policyInfo.getChannelProductName());
					htDeductibleInfo.setFormType("2");
					htDeductibleInfo.setMaintainTypr("2");
					htDeductibleInfoService.save(htDeductibleInfo);

					//发送短信  用户需付款的金额=自付额+其他金额
					BigDecimal selfPrice = sum.divide(num);
					String content = NoteTemplateUtils.noteTemplateFiveRenew(htRenewForm.getHtBrandInfo().getName(),htRenewForm.getHtPhoneModelInfo().getModel(),selfPrice);
					SmsSendUtils.send(htRenewForm.getHtClaimSettlementForm().getPhone(),content);
					//发送微信通知
					String date = DateUtils.formatDateTime(new Date());
					HtUserInfo htUserInfo = htUserInfoService.get(htFormInfo.getUserId());

					wxTemplateUtils.sendWxTemplateStatusSix("",htFormInfo.getId(),selfPrice.toString(),htUserInfo.getOpenId());

					htFormInfo.setManageStatus(ManageStatus.HX_FA_DFK);
					htFormInfo.setFormStatus(FormStatus.HX_DFK);
				}
				//总监审核大于1000000
				if(sum.compareTo(new BigDecimal(1000000)) == 1){
					variables.put("checkStatus", '1');
					htFormInfo.setManageStatus(ManageStatus.HX_FA_DSH);
					htFormInfo.setFormStatus(FormStatus.HX_FA_DSH);

				}

			}
			//换新-维修方案售后总监待审核
			if (htRenewForm.getBpm().getActivityId().equals("renew_director")) {
				variables.put("checkStatus", '1');

				BigDecimal sum = htRenewForm.getSelfPrice().add(htRenewForm.getOtherPrice());


				//存储待付款信息
				HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
				htDeductibleInfo.setHtFormInfo(htRenewForm.getHtFormInfo());
				List<HtDeductibleInfo> list = htDeductibleInfoService.findList(htDeductibleInfo);
				if(list.size() > 0){
					htDeductibleInfo = list.get(0);
				}
				htDeductibleInfo.setPhone(htRenewForm.getHtClaimSettlementForm().getPhone());
				htDeductibleInfo.setUserName(htRenewForm.getHtClaimSettlementForm().getUserName());
				htDeductibleInfo.setHtFormInfo(htFormInfo);
				htDeductibleInfo.setBdId(htRenewForm.getId());
				htDeductibleInfo.setDeductiblesLimit(htRenewForm.getSelfPrice().doubleValue());
				htDeductibleInfo.setCreateDate(new Date());
				PolicyInfo policyInfo = policyInfoService.get(htFormInfo.getPolicyInfo());
				htDeductibleInfo.setProductId(policyInfo.getChannelProductId());
				htDeductibleInfo.setProductName(policyInfo.getChannelProductName());
				htDeductibleInfo.setFormType("2");
				htDeductibleInfo.setMaintainTypr("2");
				htDeductibleInfoService.save(htDeductibleInfo);

				//发送短信
				BigDecimal selfPrice = sum.divide(num);
				String content = NoteTemplateUtils.noteTemplateFiveRenew(htRenewForm.getHtBrandInfo().getName(),htRenewForm.getHtPhoneModelInfo().getModel(),selfPrice);
				SmsSendUtils.send(htRenewForm.getHtClaimSettlementForm().getPhone(),content);
				//发送微信通知
				String date = DateUtils.formatDateTime(new Date());
				HtUserInfo htUserInfo = htUserInfoService.get(htFormInfo.getUserId());
				wxTemplateUtils.sendWxTemplateStatusSix("",htFormInfo.getId(),date,htUserInfo.getOpenId());
				htFormInfo.setManageStatus(ManageStatus.HX_FA_DFK);
				htFormInfo.setFormStatus(FormStatus.HX_DFK);
			}
			//换新-待确认自付款财务
			if (htRenewForm.getBpm().getActivityId().equals("renew_pay")) {
				//发送短信
				String content = NoteTemplateUtils.noteTemplateSevenRenew();
				SmsSendUtils.send(htRenewForm.getHtClaimSettlementForm().getPhone(),content);
				HtDeductibleInfo htDeductibleInfo = new HtDeductibleInfo();
				htDeductibleInfo.setHtFormInfo(htRenewForm.getHtFormInfo());

				List<HtDeductibleInfo> list = htDeductibleInfoService.findList(htDeductibleInfo);
				if(list.size()>0){
					htDeductibleInfo = list.get(0);
					htDeductibleInfo.setAffirmStatus("0");
					htDeductibleInfoService.save(htDeductibleInfo);
				}

				htFormInfo.setManageStatus(ManageStatus.HX_YSZFK);
				htFormInfo.setFormStatus(FormStatus.HX_DCG);

			}
			//换新-换新完成待邮寄
			if (htRenewForm.getBpm().getActivityId().equals("renew_end")) {
				variables.put("checkStatus", '1');
				htFormInfo.setManageStatus(ManageStatus.HX_WC);
				htFormInfo.setFormStatus(FormStatus.WX_WXWC);

				String content = NoteTemplateUtils.noteTemplateNine(htRenewForm.getRenewExpressCompany(),htRenewForm.getRenewExpressNo(),htRenewForm.getNewImei());
				SmsSendUtils.send(htRenewForm.getHtClaimSettlementForm().getPhone(),content);

				//终止保单
				policyInfoService.stopPolicy(htFormInfo.getPolicyInfo().getId());

			}

			BpmParams bpmParams = htRenewForm.getBpm();
			bpmParams.setComment(htRenewForm.getRemarks());
			htFormInfo.setBpm(bpmParams);

			BpmUtils.complete(htFormInfo, variables, null);
			htFormInfoDao.update(htFormInfo);


		}
	}
	
	/**
	 * 更新状态
	 * @param htRenewForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtRenewForm htRenewForm) {
		super.updateStatus(htRenewForm);
	}
	
	/**
	 * 删除数据
	 * @param htRenewForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtRenewForm htRenewForm) {
		super.delete(htRenewForm);
	}


	/**
	 * 查询流程列表
	 * @return
	 */
	public Page<RenewListEntity> findRenewList(RenewListEntity renewListEntity, HttpServletRequest request, HttpServletResponse response) {

		Page<RenewListEntity> page = new Page<>(request, response);
		renewListEntity.setPage(page);
		return page.setList(dao.findRenewList(renewListEntity));
	}

	/**
	 * 查询换新工单列表
	 * @param htRenewEndForm
	 * @return
	 */
	public List<HtRenewEndForm> findEndList(HtRenewEndForm htRenewEndForm) {
		return dao.findEndList(htRenewEndForm);
	}

	/**
	 * 查询换新工单列表
	 * @param htRenewEndForm
	 * @return
	 */
	public Page<HtRenewEndForm> findAllList(HtRenewEndForm htRenewEndForm) {
		Page<HtRenewEndForm> page = (Page<HtRenewEndForm>) htRenewEndForm.getPage();
		page.setList(dao.findAllList(htRenewEndForm));
		return page;
	}


}