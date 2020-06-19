/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.advisory.dao.HtAdvisoryHistoryDao;
import com.jeesite.modules.advisory.entity.HtAdvisoryHistory;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.FormType;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.history.dao.HtHistoryDao;
import com.jeesite.modules.history.entity.HistoryForm;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.task.ScheduleTask;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.advisory.entity.HtAdvisoryInfo;
import com.jeesite.modules.advisory.dao.HtAdvisoryInfoDao;

/**
 * ht_advisory_infoService
 * @author zhaohaifeng
 * @version 2020-03-30
 */
@Service
@Transactional(readOnly=true)
public class HtAdvisoryInfoService extends CrudService<HtAdvisoryInfoDao, HtAdvisoryInfo> {

	@Autowired
	private HtAdvisoryHistoryDao advisoryHistoryDao;
	@Autowired
	private HtHistoryService historyService;
	@Autowired
	private HtFormInfoDao formInfoDao;
	@Autowired
	private TaskService taskService;
	@Autowired
	private BpmTaskService bpmTaskService;
    @Autowired
    private HtRepairClientFormService repairClientFormService;
    @Autowired
	private HtFormInfoService formInfoService;
    @Autowired
	private HtCallLogService htCallLogService;
	/**
	 * 获取单条数据
	 * @param htAdvisoryInfo
	 * @return
	 */
	@Override
	public HtAdvisoryInfo get(HtAdvisoryInfo htAdvisoryInfo) {
		return super.get(htAdvisoryInfo);
	}

	/**
	 * 保单查询列表
	 * @param htFormInfo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<HtFormInfo> findPageData(HtFormInfo htFormInfo,int pageNo,int pageSize, Page pageParam) {
		List<HtFormInfo> formInfoList = dao.findFormLsit(htFormInfo,pageSize,pageNo);
		pageParam.setList(formInfoList);
		return pageParam;
	}

	@Override
	public Page<HtAdvisoryInfo> findPage(HtAdvisoryInfo htAdvisoryInfo) {return super.findPage(htAdvisoryInfo); }

	/**
	 * 保存数据（插入或更新）
	 * @param htAdvisoryInfo
	 */
	@Transactional(readOnly=false, rollbackFor = Exception.class, timeout = 60)
	public void save(HtAdvisoryInfo htAdvisoryInfo,String flag) {
		//销毁
		htCallLogService.verification(htAdvisoryInfo.getCallId());

		String workOrderId = IdGen.randomLong() + "";//工单 ID
		String formId = IdGen.randomLong() + "";//表单ID
		String historyId = IdGen.randomLong() + "";//历史ID


		String policyId = htAdvisoryInfo.getPolicyId();
		String officeId = htAdvisoryInfo.getOfficeId();
		String departmentId = htAdvisoryInfo.getDepartmentId();
		String userCode = UserUtils.getUser().getUserCode();
		String msgType = htAdvisoryInfo.getMsgType();
		htAdvisoryInfo.setFormType(FormType.ZXGD);
		HtFormInfo htFormInfo = new HtFormInfo();

		//存入工单主表
		htFormInfo.setId(workOrderId);
		htFormInfo.setFormStatus(FormStatus.ZX_YSL);
		if ("1".equals(flag)){htFormInfo.setPolicyInfo(new PolicyInfo(policyId));}
		htFormInfo.setManageStatus(ManageStatus.ZX_YSL);
		htFormInfo.setFormType(FormType.ZXGD);
		htFormInfo.setIsAutomatic("1");//启动工单时 还未分配
		htFormInfo.setOffice(new Office(officeId));
		htFormInfo.setIsRun("1");

		//存入表单
		htAdvisoryInfo.setId(formId);
		htAdvisoryInfo.setHtFormInfo(new HtFormInfo(workOrderId));


		HtAdvisoryHistory htAdvisoryHistory = new HtAdvisoryHistory();
		BeanUtils.copyProperties(htAdvisoryInfo,htAdvisoryHistory);
		htAdvisoryHistory.setFormId(workOrderId);
		htAdvisoryHistory.setId(historyId);
		htAdvisoryHistory.setActivityId("advisory_info");
		htAdvisoryHistory.setTaskBy(userCode);
		htAdvisoryHistory.setType("0");




		HtHistory history = new HtHistory();
		history.setWorkOrderId(workOrderId);
		history.setFormId(formId);
		history.setHistoryFormId(historyId);
		history.setFormType(FormType.ZXGD);
		history.setUserVisible("1");
		history.setCmsVisible("1");
		history.setActivityName("用户咨询-已受理");
		history.setActivityId("advisory_info");
		history.setOperationStatus(ManageStatus.ZX_YSL);
		history.setDisposeUserId(userCode);
		history.setIsBack("0");


		//需要判断 工单类型  和 信息状态
		if ("4".equals(msgType)){
			//如果是返修流转条件比较特殊
			//调用维修流程的方法继续流转
            //将表单的维修网点修改了
			String originalFormId = htAdvisoryInfo.getOriginalFormId();
				HtRepairClientForm repairClientForm = new HtRepairClientForm();
				HtFormInfo info = new HtFormInfo(originalFormId);
				repairClientForm.setHtFormInfo(info);
				repairClientFormService.repairBack(repairClientForm);
				HtFormInfo formInfo = formInfoService.get(originalFormId);
				String originalOfficeCode = formInfo.getOffice().getOfficeCode();
				htAdvisoryInfo.setOfficeId(originalOfficeCode);
				htAdvisoryHistory.setOfficeId(originalOfficeCode);

		}
		//formInfoDao.insert(htFormInfo);
        dao.insert(htAdvisoryInfo);
        advisoryHistoryDao.insert(htAdvisoryHistory);
		//如果不是返修，不是 咨询 不是保单咨询  就开启流程
		if (!"4".equals(msgType)&&!"0".equals(msgType) && !"5".equals(msgType)){
			HashMap<String, Object> variables = new HashMap<>();
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
			formInfoDao.insert(htFormInfo);
		}
		if ("3".equals(msgType)){
			//流转至投诉环节
			variables.put("checkStatus",'4');
			//投诉
			history.setAfterActivityId("advisory_complain");
		}else{
			//流转至别的部门
			variables.put("checkStatus",'3');
			BpmParams bpmParams = new BpmParams();
			String userCodes = repairClientFormService.getUserCodes(officeId);
			bpmParams.setNextUserCodes(userCodes);
			htFormInfo.setBpm(bpmParams);
			//待处理
			history.setAfterActivityId("advisory_dispose");
		}
		BpmUtils.start(htFormInfo, "hd_advisory_info", variables, null);
            historyService.save(history);
        }
	}

	/**
	 * 更新状态
	 * @param htAdvisoryInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtAdvisoryInfo htAdvisoryInfo) {
		super.updateStatus(htAdvisoryInfo);
	}
	
	/**
	 * 删除数据
	 * @param htAdvisoryInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtAdvisoryInfo htAdvisoryInfo) {
		super.delete(htAdvisoryInfo);
	}

	/**
	 * 更新咨询工单信息
	 * @param htAdvisoryInfo
	 */
	@Transactional(readOnly=false)
	public void saveAgain(HtAdvisoryInfo htAdvisoryInfo) {


		String userCode = UserUtils.getUser().getUserCode();
		HtFormInfo htFormInfo = htAdvisoryInfo.getHtFormInfo();
		BpmParams bpm = htAdvisoryInfo.getBpm();
		Task task= taskService.createTaskQuery().taskId(bpm.getTaskId()).singleResult();

		String contactStatus = htAdvisoryInfo.getContactStatus();

		String workOrderId = htFormInfo.getId();
		String formId = htAdvisoryInfo.getId();
		String historyId = IdGen.randomLong() + "";
		//存入表单历史信息
		HtAdvisoryHistory htAdvisoryHistory = new HtAdvisoryHistory();
		BeanUtils.copyProperties(htAdvisoryInfo,htAdvisoryHistory);
		htAdvisoryHistory.setId(historyId);
		htAdvisoryHistory.setFormId(htFormInfo.getId());
		htAdvisoryHistory.setType("1");
		htAdvisoryHistory.setActivityId(bpm.getActivityId());
		htAdvisoryHistory.setTaskBy(userCode);
		htAdvisoryHistory.setTaskId(bpm.getTaskId());
		advisoryHistoryDao.insert(htAdvisoryHistory);
		//存总历史信息
		HtHistory history = new HtHistory();
		history.setWorkOrderId(workOrderId);
		history.setFormId(formId);
		history.setHistoryFormId(historyId);
		history.setFormType(FormType.ZXGD);
		history.setUserVisible("1");
		history.setCmsVisible("1");
		history.setActivityName(task.getName());
		history.setActivityId(bpm.getActivityId());
		history.setDisposeUserId(userCode);
		history.setIsBack("0");


		//根据联系情况进行流转 1联系成功 2无人接听 3 无法接通 4 拒接 5其他

		HashMap<String, Object> variables = new HashMap<>();
		//根据联系情况 流转 方向
		if ("1".equals(contactStatus)){
			history.setOperationStatus(ManageStatus.ZX_YWC);
			htFormInfo.setManageStatus(ManageStatus.ZX_YWC);
			htFormInfo.setFormStatus(FormStatus.ZX_YWC);
			variables.put("checkStatus",'1');//工单结束
		}else{
			history.setOperationStatus(ManageStatus.ZX_DLX);
			htFormInfo.setManageStatus(ManageStatus.ZX_DLX);
			htFormInfo.setFormStatus(FormStatus.ZX_YSL);
			variables.put("checkStatus",'2');//流转至待分配状态

		}

		BpmUtils.complete(htAdvisoryInfo,variables,null);
		if ("1".equals(contactStatus)){
			history.setAfterActivityId("advisory_over");
		}else{
			Task taskNew = taskService.createTaskQuery().processInstanceId(htAdvisoryInfo.getBpm().getProcInsId()).singleResult();
			history.setAfterActivityId(taskNew.getTaskDefinitionKey());
			htAdvisoryInfo.setTaskId(taskNew.getId());
		}
		dao.update(htAdvisoryInfo);

		historyService.save(history);

		formInfoDao.update(htFormInfo);
	}
    @Transactional(readOnly=false)
    public void dutySave(HtAdvisoryInfo htAdvisoryInfo) {
		BpmParams bpm = htAdvisoryInfo.getBpm();
        BpmTask task = bpmTaskService.getTask(bpm.getTaskId());
       // Task task= taskService.createTaskQuery().taskId(bpm.getTaskId()).singleResult();
		String contactStatus = htAdvisoryInfo.getContactStatus();
		//投诉流程
        //更新表单状态  工单状态  状态  然后流转  记录历史
        HtFormInfo htFormInfo = htAdvisoryInfo.getHtFormInfo();
        //存表单历史
		String historyFormId = IdGen.randomLong() + "";
		HtAdvisoryHistory htAdvisoryHistory = new HtAdvisoryHistory();
		BeanUtils.copyProperties(htAdvisoryInfo,htAdvisoryHistory);
		//设置历史表单ID
		htAdvisoryHistory.setId(historyFormId);
		htAdvisoryHistory.setTaskBy(htAdvisoryInfo.getCurrentUser().getUserCode());
		htAdvisoryHistory.setActivityId(bpm.getActivityId());
		htAdvisoryHistory.setTaskId(bpm.getTaskId());
		htAdvisoryHistory.setFormId(htFormInfo.getId());
		/*type值 用来展示历史的时候 进入不同的form页面 */
		htAdvisoryHistory.setType("2");
		advisoryHistoryDao.insert(htAdvisoryHistory);
        //存总历史
		HtHistory history = new HtHistory();
		history.setHistoryFormId(historyFormId);
		history.setFormId(htAdvisoryInfo.getId());
		history.setWorkOrderId(htFormInfo.getId());
		history.setActivityId(bpm.getActivityId());
		history.setActivityName(task.getName());
		history.setFormType(FormType.ZXGD);
		history.setDisposeUserId(htAdvisoryInfo.getCurrentUser().getUserCode());
		history.setCmsVisible("1");
		history.setUserVisible("1");
		//根据联系状态流转
		HashMap<String, Object> variables = new HashMap<>();
		if ("1".equals(contactStatus)){
			//联系成功 工单结束
			variables.put("checkStatus",'1');
		history.setOperationStatus(ManageStatus.ZX_DCL);
			htFormInfo.setFormStatus(FormStatus.ZX_DCL);
			htFormInfo.setManageStatus(ManageStatus.ZX_DCL);
		}else{
			variables.put("checkStatus",'2');//流转条件
		    history.setOperationStatus(ManageStatus.ZX_YWC);
			htFormInfo.setFormStatus(FormStatus.ZX_YWC);
			htFormInfo.setManageStatus(ManageStatus.ZX_YWC);
		}
		BpmUtils.complete(htAdvisoryInfo,variables,null);
		if ("1".equals(contactStatus)){
			history.setAfterActivityId("advisory_over");
		}else{
			Task taskNew = taskService.createTaskQuery().processInstanceId(htAdvisoryInfo.getBpm().getProcInsId()).singleResult();
			history.setAfterActivityId(taskNew.getTaskDefinitionKey());
			htAdvisoryInfo.setTaskId(taskNew.getId());
		}
		dao.update(htAdvisoryInfo);
		historyService.save(history);

		formInfoDao.update(htFormInfo);
    }


    public HtAdvisoryInfo findByFormId(String id) {
        return dao.findByFormId(id);
    }

	public HtAdvisoryInfo findByTaskId(String taskId) {
		return dao.findByTaskId(taskId);
	}

    public Integer findCountInOffice(String type, String organizationId) {
		return dao.findCountInOffice(type,organizationId);
    }
}