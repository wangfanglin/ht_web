/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.forminfo.service;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.FormType;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.history.dao.HtHistoryDao;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.htwaitapplyform.service.HtWaitApplyFormService;
import com.jeesite.modules.policy.dao.PolicyInfoDao;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.template.utils.SmsSendUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工单主表Service
 * @author lichao
 * @version 2020-02-27
 */
@Service
@Transactional(readOnly=true)
public class HtFormInfoService extends CrudService<HtFormInfoDao, HtFormInfo> {
	@Autowired
	private ActTaskUtils actTaskUtils;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private PolicyInfoService policyInfoService;
	@Autowired
	private PolicyInfoDao policyInfoDao;
	@Autowired
	private HtUserApplyInfoService userApplyInfoService;
	@Autowired
	private HtHistoryDao htHistoryDao;
	@Autowired
	private HtWaitApplyFormService htWaitApplyFormService;

	/**
	 * 获取单条数据
	 * @param htFormInfo
	 * @return
	 */
	@Override
	public HtFormInfo get(HtFormInfo htFormInfo) {
		return super.get(htFormInfo);
	}
	
	/**
	 * &#x67e5;&#x8be2;&#x5206;&#x9875;&#x6570;&#x636e;
	 * @param htFormInfo &#x67e5;&#x8be2;&#x6761;&#x4ef6;
	 * @return
	 */
	@Override
	public Page<HtFormInfo> findPage(HtFormInfo htFormInfo) {
		return super.findPage(htFormInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htFormInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtFormInfo htFormInfo) {
		// 如果未设置状态，则指定状态为审核状态，以提交审核流程
		if (StringUtils.isBlank(htFormInfo.getStatus())){
			htFormInfo.setStatus(DataEntity.STATUS_AUDIT);
		}

		// 如果状态为正常，则代表不正常操作，可能前端进行了数据参数修改
		if (DataEntity.STATUS_NORMAL.equals(htFormInfo.getStatus())){
			throw new ServiceException(text("非法操作，前端数据被劫持！"));
		}

		// 如果状态为草稿或审核状态，才可以保存业务数据
		if (DataEntity.STATUS_DRAFT.equals(htFormInfo.getStatus())
				|| DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())){
			super.save(htFormInfo);
		}

		// 如果为审核状态，则进行审批流操作
		if (DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())){

			// 指定流程变量，作为流程条件，决定流转方向
			Map<String, Object> variables = MapUtils.newHashMap();
			variables.put("checkStatus", '4');

			// 如果流程实例为空，任务编号也为空，则：启动流程
			if (StringUtils.isBlank(htFormInfo.getBpm().getProcInsId())
					&& StringUtils.isBlank(htFormInfo.getBpm().getTaskId())){
				BpmUtils.start(htFormInfo, "hd_form_claim", variables, null);
			}
			// 如果有任务信息，则：提交任务
			else{
				BpmParams bpmParams = htFormInfo.getBpm();
				bpmParams.setPriority(TaskFormEnum.TaskFormColorEnum.ORANGE.getCode());
				BpmUtils.complete(htFormInfo, variables, null);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param htFormInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtFormInfo htFormInfo) {
		super.updateStatus(htFormInfo);
	}
	
	/**
	 * 删除数据
	 * @param htFormInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtFormInfo htFormInfo) {
		super.delete(htFormInfo);
	}

	/**
	 * 查询保单有多少工单
	 * @param policyId  保单ID
	 * @return
	 */
	public int findFormAmount(String policyId) {
		return dao.findFormAmount(policyId);
	}

	/**
	 * 查询推荐网点对比条件
	 * @param formId
	 * @return
	 */
    public Map<String, String> findMainPointCondition(String formId,String childId) {
    	return dao.findMainPointCondition(formId,childId);
    }

	public Integer findFormCount(String officeId) {
    	return dao.findFormCount(officeId);
	}

	/**
	 * 根据保单ID 查工单
	 * @param policyId
	 * @return
	 */
	public List<HtFormInfo> findListByPolicyId(String policyId) {
		return dao.findListByPolicyId(policyId);
	}

	public Integer findOrderSum(String officeId) {
		return dao.findOrderSum(officeId);
	}

	public List<HtFormInfo> findPageByPolicyId(HtFormInfo formInfo) {
		return dao.findPageByPolicyId(formInfo);
	}

	/**
	 * 根据保单查询最新的工单
	 * @param policyId
	 */
	public String getBypolicyId(String policyId) {
		return dao.getBypolicyId(policyId);
	}

	/**
	 * 获取机构下所有工单总金额
	 * @param organizationId
	 * @return
	 */
	public Double getPointSumPrice(String organizationId) {
		return dao.getPointSumPrice(organizationId);
	}

	/**
	 * 获取机构所有订单
	 * @param organizationId
	 * @return
	 */
	public List<HtFormInfo> findTotalFromInOffice(String organizationId) {
		return dao.findTotalFromInOffice(organizationId);
	}

	public List<Map<String, String>> findFormOffice() {
		return dao.findFormOffice();
	}

	public int findDateSales(String officeId, String dates) {
		return dao.findDateSales(officeId,dates);
	}


	//工单挂起
	@Transactional(readOnly = false)
	public boolean formStop(String formId) {
		Task task = actTaskUtils.getTask(formId);

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		//4.得到当前流程定义的实例是否都为暂停状态
		boolean suspended = processInstance.isSuspended();
		String processInstanceId = processInstance.getId();
		//5.判断
		if(suspended){

			return false;
		}else{
			//说明状态是已激活，可以暂停
			runtimeService.suspendProcessInstanceById(processInstanceId);
			HtFormInfo htFormInfo = new HtFormInfo();
			htFormInfo.setId(formId);
			htFormInfo.setIsRun("1");
			htFormInfo.setPutUp("1");
			dao.update(htFormInfo);
			return true;
		}
	}
	//工单激活
	@Transactional(readOnly = false)
	public boolean formStart(String formId) {
		Task task = actTaskUtils.getTask(formId);

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		//4.得到当前流程定义的实例是否都为暂停状态
		boolean suspended = processInstance.isSuspended();
		String processInstanceId = processInstance.getId();
		//5.判断
		if(suspended){
			//说明是暂停，就可以激活操作
			runtimeService.activateProcessInstanceById(processInstanceId);
			HtFormInfo htFormInfo = new HtFormInfo();
			htFormInfo.setId(formId);
			htFormInfo.setIsRun("0");
			htFormInfo.setPutUp("0");
			dao.update(htFormInfo);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 渤海保单开启工单的方法
	 */
	@Transactional(readOnly = false)
	public HtFormInfo openForm(String policyId) {
		PolicyInfo policyInfo = policyInfoService.get(policyId);

			HtFormInfo htFormInfo = new HtFormInfo();
			htFormInfo.setPolicyInfo(policyInfo);

               htFormInfo.setFormStatus(FormStatus.DSQ_CG_DFP);
               htFormInfo.setManageStatus(ManageStatus.DSQ_LXCG_DFP);
               htFormInfo.setFormType(FormType.DSQGD);
               htFormInfo.setBhFlag("1");

               policyInfoService.updatePolicyFlag(policyId); //保单标识

        // 如果未设置状态，则指定状态为审核状态，以提交审核流程
			if (StringUtils.isBlank(htFormInfo.getStatus())) {
				htFormInfo.setStatus(DataEntity.STATUS_AUDIT);
			}

			// 如果状态为正常，则代表不正常操作，可能前端进行了数据参数修改
			if (DataEntity.STATUS_NORMAL.equals(htFormInfo.getStatus())) {
				throw new RuntimeException("非法操作，前端数据被劫持！");
			}

			// 如果状态为草稿或审核状态，才可以保存业务数据
			if (DataEntity.STATUS_DRAFT.equals(htFormInfo.getStatus())
					|| DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {
				dao.insert(htFormInfo);
			}

			// 如果为审核状态，则进行审批流操作
			if (DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {

				// 指定流程变量，作为流程条件，决定流转到未申请节点
				Map<String, Object> variables = MapUtils.newHashMap();
                    variables.put("checkStatus", '1');

				// 如果流程实例为空，任务编号也为空，则：启动流程
				if (StringUtils.isBlank(htFormInfo.getBpm().getProcInsId())
						&& StringUtils.isBlank(htFormInfo.getBpm().getTaskId())) {
					BpmUtils.start(htFormInfo, "hd_form_claim", variables, null);
				}
			}
		String content = "尊敬的顾客，保修有我授权维修中心接收到您的维修请求，为了更好的为您提供服务，点击链接：http://hollardchina.com.cn/hollard/yindao.html 务必于48小时之内上传相应的资料，只有收到您的资料，维修申请才能获得批准，如有问题可以通过和德信通微信公众号咨询，我们会有专业的客服人员给您提供服务！";
			SmsSendUtils.send(policyInfo.getStrContactNum(),content);
			return htFormInfo;
	}


	/**
	 * 开启用户申请的工单
	 * @param userApplyInfo
	 */
	@Transactional(readOnly = false)
	public void applyForm(HtUserApplyInfo userApplyInfo) {
		//检查日志有没有处理过这个保单
		int count = userApplyInfoService.check(userApplyInfo.getId());
		if (count > 0) {
		} else {//已经处理过了
			String policyId = userApplyInfo.getPolicyId();
			PolicyInfo policyInfo = policyInfoService.get(policyId);
			policyInfo.setUserId(userApplyInfo.getUserId());
			policyInfoDao.update(policyInfo);

			//需要判断这个保单的来源
			String fromtype = policyInfo.getFromtype();
            String policyFlag = policyInfo.getPolicyFlag();
			if ("1".equals(fromtype)||"2".equals(fromtype)) {
			    if ("1".equals(policyFlag)){
                    System.out.println("================为渤海保单 关联工单信息================");
                    //根据保单 去待申请工单表 找出最近一条记录的工单ID
                    String formId = htWaitApplyFormService.getBypolicyId(policyId);
                    //把保单 和工单关联起来
                    //将最新的标识修改
                    HtUserApplyInfo htUserApply = new HtUserApplyInfo(userApplyInfo.getId());
                    htUserApply.setIsMain("0");
                    htUserApply.setFormId(formId);
                    htUserApply.setIsNewRecord(false);
                    userApplyInfoService.save(htUserApply);
                    /* 插入一条展示数据 给前台展示 */
                    HtHistory htHistory = new HtHistory();
                    htHistory.setWorkOrderId(formId);
                    htHistory.setFormType("100");//只作为 给前台展示
                    htHistory.setOperationStatus("1000"); //特殊处理状态
                    htHistory.setUserVisible("1");
                    htHistory.setCmsVisible("0");
                    htHistory.setCreateDate(new Date());
                    htHistory.setUpdateDate(new Date());
                    htHistoryDao.insert(htHistory);
                    /*把user ID关联*/
                    HtFormInfo formInfo = this.get(formId);
                    formInfo.setUserId(userApplyInfo.getUserId());
                    formInfo.setBhFlag("1");
                    dao.update(formInfo);
                }else{
                    System.out.println("================为渤海保单 关联工单信息================");
                    //根据保单 去待申请工单表 找出最近一条记录的工单ID
                    String formId = htWaitApplyFormService.getBypolicyId(policyId);
                    //把保单 和工单关联起来
                    //将最新的标识修改
                    HtUserApplyInfo htUserApply = new HtUserApplyInfo(userApplyInfo.getId());
                    htUserApply.setIsMain("0");
                    htUserApply.setFormId(formId);
                    htUserApply.setIsNewRecord(false);
                    userApplyInfoService.save(htUserApply);
                    //修改工单主表的处理状态
				HtFormInfo formInfo = this.get(formId);
				formInfo.setManageStatus(FormStatus.LP_DCL);
				formInfo.setManageStatus(ManageStatus.LP_ZLYGX_DLX);
                formInfo.setUserId(userApplyInfo.getUserId());
                formInfo.setBhFlag("1");
				dao.update(formInfo);
				//继续流转工单
				BpmParams bpmParams = new BpmParams();
				Task task = actTaskUtils.getTask(formId);
				bpmParams.setActivityId(task.getTaskDefinitionKey());
				bpmParams.setProcInsId(task.getProcessInstanceId());
				bpmParams.setTaskId(task.getId());
				formInfo.setBpm(bpmParams);
				Map<String, Object> variables = MapUtils.newHashMap();
				variables.put("checkStatus", '4');
				BpmUtils.complete(formInfo, variables, null);
                    /* 插入一条展示数据 给前台展示 */
                    HtHistory htHistory = new HtHistory();
                    htHistory.setWorkOrderId(formId);
                    htHistory.setFormType("100");//只作为 给前台展示
                    htHistory.setOperationStatus("1000"); //特殊处理状态
                    htHistory.setUserVisible("1");
                    htHistory.setCmsVisible("0");
                    htHistory.setCreateDate(new Date());
                    htHistory.setUpdateDate(new Date());
                    htHistoryDao.insert(htHistory);
                }
                //将已申请的工单 加入日志
                userApplyInfoService.insertApplyLog(userApplyInfo.getId());
			} else if ("0".equals(fromtype)) {
				System.out.println("================创建新工单 开启工作流================");
				//创建新工单 开启工作流

				HtFormInfo htFormInfo = new HtFormInfo();
                htFormInfo.setUserId(userApplyInfo.getUserId());
				htFormInfo.setPolicyInfo(policyInfo);
				htFormInfo.setFormStatus(FormStatus.LP_DCL);
				htFormInfo.setManageStatus(ManageStatus.LP_ZLYGX_DLX);
				// 如果未设置状态，则指定状态为审核状态，以提交审核流程
				if (StringUtils.isBlank(htFormInfo.getStatus())) {
					htFormInfo.setStatus(DataEntity.STATUS_AUDIT);
				}

				// 如果状态为正常，则代表不正常操作，可能前端进行了数据参数修改
				if (DataEntity.STATUS_NORMAL.equals(htFormInfo.getStatus())) {
					throw new RuntimeException("非法操作，前端数据被劫持！");
				}

				// 如果状态为草稿或审核状态，才可以保存业务数据
				if (DataEntity.STATUS_DRAFT.equals(htFormInfo.getStatus())
						|| DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {
					dao.insert(htFormInfo);
				}

//                 如果为审核状态，则进行审批流操作
				if (DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {

					// 指定流程变量，作为流程条件，决定流转方向
					Map<String, Object> variables = MapUtils.newHashMap();
					variables.put("checkStatus", '4');
					// 如果流程实例为空，任务编号也为空，则：启动流程
					if (StringUtils.isBlank(htFormInfo.getBpm().getProcInsId())
							&& StringUtils.isBlank(htFormInfo.getBpm().getTaskId())) {
						BpmUtils.start(htFormInfo, "hd_form_claim", variables, null);
					}
				}
				/* 插入一条展示数据 给前台展示 */
				HtHistory htHistory = new HtHistory();
				htHistory.setWorkOrderId(htFormInfo.getId());
				htHistory.setFormType("100");//只作为 给前台展示
				htHistory.setOperationStatus("1000"); //特殊处理状态
				htHistory.setUserVisible("1");
				htHistory.setCmsVisible("0");
                htHistory.setCreateDate(new Date());
                htHistory.setUpdateDate(new Date());
				htHistoryDao.insert(htHistory);

				//将最新的标识修改
				HtUserApplyInfo htUserApply = new HtUserApplyInfo(userApplyInfo.getId());
				htUserApply.setIsMain("0");
				htUserApply.setFormId(htFormInfo.getId());
				htUserApply.setIsNewRecord(false);
				userApplyInfoService.save(htUserApply);
                //将已申请的工单 加入日志
                userApplyInfoService.insertApplyLog(userApplyInfo.getId());
			}
		}
	}
	/**
	 * 查询需要挂起的工单
	 * @return
	 */
	public List<String> findPutUpForm() {
		return dao.findPutUpForm();
	}

	/**
	 * 查询挂起工单
	 * @param htFormInfo
	 * @return
	 */
	public List<HtFormInfo> findputUpPage(HtFormInfo htFormInfo,int pageNo,int pageSize) {
		String manageStatus = htFormInfo.getManageStatus();
		String formStatus = htFormInfo.getFormStatus();
		String id = htFormInfo.getId();
		List<HtFormInfo> formInfoList = dao.findputUpPage(id,pageNo,pageSize,manageStatus,formStatus);
		return formInfoList;
	}

	/**
	 * 根据保单查询1.0工单
	 * @param formInfo
	 * @return
	 */
	public List<HtFormInfo> findOldFormByPolicyId(HtFormInfo formInfo) {
		return dao.findOldFormByPolicyId(formInfo);
	}


	/**
	 * 开启捷信工单
	 *
	 * @param applyId
	 */
	@Transactional(readOnly = false)
	public HtFormInfo jxForm(String applyId) {
		System.out.println("================创建捷信工单 开启工作流================");
        String formId = IdGen.randomLong() + "";
		HtUserApplyInfo userApplyInfo = userApplyInfoService.get(applyId);
		String policyId = userApplyInfo.getPolicyId();
		PolicyInfo policyInfo = policyInfoService.get(policyId);
		policyInfo.setUserId(userApplyInfo.getUserId());
        userApplyInfo.setFormId(formId);
		policyInfoDao.update(policyInfo);

		userApplyInfo.setFormId(formId);
		userApplyInfoService.save(userApplyInfo);

		HtFormInfo htFormInfo = new HtFormInfo(formId);
		htFormInfo.setUserId(userApplyInfo.getUserId());
		htFormInfo.setPolicyInfo(policyInfo);
        htFormInfo.setBhFlag("1");
		htFormInfo.setFormStatus(FormStatus.LP_DCL);
		htFormInfo.setManageStatus(ManageStatus.LP_ZLYGX_DLX);
		// 如果未设置状态，则指定状态为审核状态，以提交审核流程
		if (StringUtils.isBlank(htFormInfo.getStatus())) {
			htFormInfo.setStatus(DataEntity.STATUS_AUDIT);
		}

		// 如果状态为正常，则代表不正常操作，可能前端进行了数据参数修改
		if (DataEntity.STATUS_NORMAL.equals(htFormInfo.getStatus())) {
			throw new RuntimeException("非法操作，前端数据被劫持！");
		}
		// 如果状态为草稿或审核状态，才可以保存业务数据
		if (DataEntity.STATUS_DRAFT.equals(htFormInfo.getStatus())
				|| DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {
			dao.insert(htFormInfo);
		}
		//如果为审核状态，则进行审批流操作
		if (DataEntity.STATUS_AUDIT.equals(htFormInfo.getStatus())) {

			// 指定流程变量，作为流程条件，决定流转方向
			Map<String, Object> variables = MapUtils.newHashMap();
			variables.put("checkStatus", '4');
			// 如果流程实例为空，任务编号也为空，则：启动流程
			if (StringUtils.isBlank(htFormInfo.getBpm().getProcInsId())
					&& StringUtils.isBlank(htFormInfo.getBpm().getTaskId())) {
				BpmUtils.start(htFormInfo, "hd_form_claim", variables, null);
			}
		}
		String content = "尊敬的顾客，已经接收到您的维修请求，您可以通过和德保修微信公众号查询，或者致电400-0999-855，我们会有专业的客服人员给您提供服务！";
		SmsSendUtils.send(policyInfo.getStrContactNum(),content);
//        HtHistory htHistory = new HtHistory();
//        htHistory.setWorkOrderId(htFormInfo.getId());
//        htHistory.setFormType("100");//只作为 给前台展示
//        htHistory.setOperationStatus("1000"); //特殊处理状态
//        htHistory.setUserVisible("1");
//        htHistory.setCmsVisible("0");
//        htHistory.setCreateDate(new Date());
//        htHistory.setUpdateDate(new Date());
//        htHistoryDao.insert(htHistory);
		return htFormInfo;
	}
}
