package com.jeesite.modules.task;

import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.flowable.service.FlowableTaskServiceImpl;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htdutyroster.entity.HtDutyRoster;
import com.jeesite.modules.htdutyroster.service.HtDutyRosterService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.policy.dao.PolicyDetailDao;
import com.jeesite.modules.policy.dao.PolicyInfoDao;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyDetailService;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.sys.entity.User;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author:ZHF
 * @Time: 2020/4/9 11:06
 */
@Service
public class OpenFormTask {
    @Autowired
    private HtUserApplyInfoService userApplyInfoService;
    @Autowired
    private PolicyInfoService policyInfoService;
    @Autowired
    private HtFormInfoService formInfoService;
    @Autowired
    private PolicyDetailService policyDetailService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HtDutyRosterService dutyRosterService;
    @Autowired
    private BpmTaskService bpmTaskService;
    @Autowired
    private PolicyInfoDao policyInfoDao;
    @Autowired
    private PolicyDetailDao policyDetailDao;



    /**
     * 用户在线申请后 为其开启工单流程
     */
    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
   // @Scheduled(cron = "0 0 */1 * * ?") //每小时执行一次
    public void openApplyForm() {
        System.out.println("=====================开始执行已申请保单 开启流程===================");
        HtUserApplyInfo htUserApplyInfo = new HtUserApplyInfo();
        htUserApplyInfo.setIsMain("1");
        List<HtUserApplyInfo> list = userApplyInfoService.findList(htUserApplyInfo);
        if (list != null && list.size() > 0) {
            for (HtUserApplyInfo userApplyInfo : list) {
                formInfoService.applyForm(userApplyInfo);
            }
        }
    }



//    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
//    public void openBhForm() {
//        //查询出渤海的保单信息
//        System.out.println("=====================开始执行渤海保单 开启流程===================");
//        PolicyInfo policyInfo = new PolicyInfo();
//        policyInfo.setFromtype("1");
//        policyInfo.setIsCreateForm("0");
//        List<PolicyInfo> policyInfoList = policyInfoService.findList(policyInfo);
//        if (policyInfoList != null && policyInfoList.size() > 0) {
//            for (PolicyInfo info : policyInfoList) {
//                formInfoService.openForm(info,"1");
//            }
//        }
//    }

//    //@Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
//    public void openJxForm() {
//        //查询出渤海的保单信息
//        System.out.println("=====================开始执行捷信保单 开启流程===================");
//        PolicyInfo policyInfo = new PolicyInfo();
//        policyInfo.setFromtype("2");
//        policyInfo.setIsCreateForm("0");
//        List<PolicyInfo> policyInfoList = policyInfoService.findList(policyInfo);
//        if (policyInfoList != null && policyInfoList.size() > 0) {
//            for (PolicyInfo info : policyInfoList) {
//                formInfoService.openForm(info,"2");
//            }
//        }
//    }


    /**
     * 定期更新保单的状态
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天一点 执行
    public void updatePolicy() {
        List<PolicyInfo> list = policyInfoService.findList(new PolicyInfo());
        for (PolicyInfo policyInfo : list) {

            String policyId = policyInfo.getId();
            PolicyDetail policyDetail = new PolicyDetail();
            policyDetail.setPolicyInfo(new PolicyInfo(policyId));
            List<PolicyDetail> policyDetailList = policyDetailService.findList(policyDetail);


            long start = policyInfo.getDateEffectiveDate().getTime();
            long end = policyInfo.getDateEndDate().getTime();
            long now = System.currentTimeMillis();
            if (now>=start&&now<=end) {
                //保障中的保单
                policyInfo.setPolicyStatus("1");
                for (PolicyDetail detail : policyDetailList) {
                    if ("2".equals(detail.getEquityStatus())){
                        //不用处理已经失效的权益，可能它已经被出险过了
                        continue;
                    }
                    long startTime = detail.getEquityStartTime().getTime();
                    long endTime = detail.getEquityEndTime().getTime();
                    long currentTime = System.currentTimeMillis();
                    String status = "";
                    if (startTime > currentTime) {
                        status = "0";
                    }
                    if (endTime < currentTime) {
                        status = "2";
                    }
                    if (startTime < currentTime && endTime > currentTime) {
                        status = "1";
                    }
                    detail.setEquityStatus(status);
                    policyDetailDao.update(detail);
                }
            } else {
                if (now<start) {
                    //未生效
                    policyInfo.setPolicyStatus("0");
                    for (PolicyDetail detail : policyDetailList) {
                        detail.setEquityStatus("0");
                        policyDetailDao.update(detail);
                    }
                }
                if (now>end) {
                    //已失效
                    policyInfo.setPolicyStatus("3");
                    //将详情中所有子权益设置为 已失效
                    for (PolicyDetail detail : policyDetailList) {
                        detail.setEquityStatus("3");
                        policyDetailDao.update(detail);
                    }
                }
            }
            policyInfoDao.update(policyInfo);
        }
    }


    /**
     * 工单回收重新分配
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天一点 执行
    //@Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void recyclingForm() {
        System.out.println("=======================开始执行工单重新分配=============================");
        List<Task> clientInfo = taskService.createTaskQuery().taskDefinitionKey("client_info").list();
        List<Task> claimInfo = taskService.createTaskQuery().taskDefinitionKey("claim_info").list();
        List<Task> renewInfo = taskService.createTaskQuery().taskDefinitionKey("renew_info").list();
        //这个是所有明天  处理人 不上班的 工单
        List<Task> clientList = this.screening(clientInfo);
        List<Task> claimList = this.screening(claimInfo);
        List<Task> renewList = this.screening(renewInfo);
        //查出 明天上班的人  转办 过去
        if (clientList != null && clientList.size() > 0) {
            for (Task task : clientList) {
                //待申请
                Map<String, String> RosterInfo = dutyRosterService.getDutyRosterStaffUserIdByRole("client_info");
                String status = RosterInfo.get("status");
                String userId = RosterInfo.get("userId");
                if ("success".equals(status)) {
                    this.transfer(task, userId);
                }
            }
        }
        if (claimList != null && claimList.size() > 0) {
            for (Task task : claimList) {
                //核赔
                Map<String, String> RosterInfo = dutyRosterService.getDutyRosterStaffUserIdByRole("claim_info");
                String status = RosterInfo.get("status");
                String userId = RosterInfo.get("userId");
                if ("success".equals(status)) {
                    this.transfer(task, userId);
                }
            }
        }
        if (renewList != null && renewList.size() > 0) {
            for (Task task : renewList) {
                //换新
                Map<String, String> RosterInfo = dutyRosterService.getDutyRosterStaffUserIdByRole("renew_info");
                String status = RosterInfo.get("status");
                String userId = RosterInfo.get("userId");
                if ("success".equals(status)) {
                    this.transfer(task, userId);
                }
            }
        }
    }

    /**
     * 筛选没人上班的任务
     *
     * @param taskList
     * @return
     */
    public List<Task> screening(List<Task> taskList) {
        if (taskList != null && taskList.size() > 0) {
        HtDutyRoster htDutyRoster = new HtDutyRoster();
        htDutyRoster.setStartTime(new Date());
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.HOUR, 23);
        htDutyRoster.setEndTime(instance.getTime());
        htDutyRoster.setStatus("0");
        List<HtDutyRoster> personList = dutyRosterService.findList(htDutyRoster);
        for (HtDutyRoster dutyRoster : personList) {
            String staffUserId = dutyRoster.getStaffUserId();
                for (int i = 0; i < taskList.size(); i++) {
                    String assignee = taskList.get(i).getAssignee();
                    if (staffUserId.equals(assignee)) {
                        taskList.remove(i);
                    }
                }
            }
        }
        return taskList;
    }

    /**
     * 转办方法
     *
     * @param task
     * @param userId
     */
    public void transfer(Task task, String userId) {
        //进行转办
        BpmTask claim = new BpmTask();
        claim.setId(task.getId());
        claim.setCurrentUser(new User("system"));
        bpmTaskService.claimTask(claim);//在这里用 当前系统用户把工签收手掉

        BpmTask bpmTask = new BpmTask();
        bpmTask.setDelegateState("");
        bpmTask.setId(task.getId());
        bpmTask.setComment("工单重新分配");
        bpmTask.setVariableList(new ArrayList<>());
        bpmTask.setUserCode(userId);//转办到的usercode
        bpmTask.setCurrentUser(new User("system"));
        bpmTaskService.turnTask(bpmTask);//转办流转
    }

    /** 工单挂起
     */
   // @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void putUp() {
        System.out.println("=============================开始执行工单挂起==========================");
        List<String> putFormId = formInfoService.findPutUpForm();
        for (String formId : putFormId) {
            formInfoService.formStop(formId);
        }
    }
}
