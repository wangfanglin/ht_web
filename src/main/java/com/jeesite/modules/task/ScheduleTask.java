package com.jeesite.modules.task;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import com.jeesite.modules.advisory.entity.HtAdvisoryInfo;
import com.jeesite.modules.advisory.service.HtAdvisoryInfoService;
import com.jeesite.modules.bpm.entity.BpmTask;
import com.jeesite.modules.bpm.service.BpmTaskService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htdutyroster.service.HtDutyRosterService;
import com.jeesite.modules.renew.entity.HtRenewForm;
import com.jeesite.modules.renew.service.HtRenewFormService;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * 排班的 定时任务
 * @author tangchao
 *
 */
@Service
public class ScheduleTask {
    final
    HtDutyRosterService htDutyRosterService;
    final
    TaskService taskService;
    final
    RuntimeService runtimeService;
    final
    BpmTaskService bpmTaskService;
    final
    HtFormInfoService htFormInfoService;
    final
    HtRepairClientFormService htRepairClientFormService;
    final
    HtRenewFormService htRenewFormService;
    final
    HtAdvisoryInfoService htAdvisoryInfoService;



    public ScheduleTask(HtDutyRosterService htDutyRosterService, TaskService taskService, RuntimeService runtimeService, BpmTaskService bpmTaskService, HtFormInfoService htFormInfoService,HtAdvisoryInfoService htAdvisoryInfoService,HtRepairClientFormService htRepairClientFormService,HtRenewFormService htRenewFormService) {
        this.htDutyRosterService = htDutyRosterService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.bpmTaskService = bpmTaskService;
        this.htFormInfoService = htFormInfoService;
        this.htAdvisoryInfoService = htAdvisoryInfoService;
        this.htRepairClientFormService = htRepairClientFormService;
        this.htRenewFormService = htRenewFormService;
    }



    /**
     * 定期扫描待分配环节工单 并分配员工
     */
    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void schedule(){
        //查询所有待分配的任务
        List<Task> taskList  = taskService.createTaskQuery().taskDefinitionKey("claim_wait").list();
        for (Task task : taskList) {
            try {
                //从排班引擎获取一个用户
                Map<String,String> RosterInfo = htDutyRosterService.getDutyRosterStaffUserIdByRole("claim_info");

                String status = RosterInfo.get("status");
                String userId = RosterInfo.get("userId");

                //如果用户存在 完成任务并将任务分配给该员工
                if("success".equals(status)){
                    BpmTask bpmTask = new BpmTask(task);
                    bpmTask.setCurrentUser(new User("system"));
                    bpmTask.setAssignee(userId);
                    bpmTask.setNextUserCodes(userId);
                    bpmTask.setPriority(TaskFormEnum.TaskFormColorEnum.ORANGE.getCode());

                    updateFormAutomatic(task);
                    User user = UserUtils.get("system");
                    bpmTask.setCurrentUser(user);
                    bpmTaskService.completeTask(bpmTask);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }





    /**
     * 维修待联系 定期扫描待分配环节工单 并分配员工
     */
    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void clientSchedule(){
        //查询所有待分配的任务
        List<Task> taskList  = taskService.createTaskQuery().taskDefinitionKey("client_wait").list();
        for (Task task : taskList) {
            try {
                //从排班引擎获取一个用户
                Map<String,String> RosterInfo = htDutyRosterService.getDutyRosterStaffUserIdByRole("client_info");

                String status = RosterInfo.get("status");
                String userId = RosterInfo.get("userId");

                //如果用户存在 完成任务并将任务分配给该员工
                if("success".equals(status)){
                    BpmTask bpmTask = new BpmTask(task);
                    bpmTask.setCurrentUser(new User("system"));
                    bpmTask.setAssignee(userId);
                    bpmTask.setNextUserCodes(userId);
                    bpmTask.setPriority(TaskFormEnum.TaskFormColorEnum.ORANGE.getCode());


                    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                    String formId = StringUtils.remove(processInstance.getBusinessKey(),"hd_form_claim:");
                    HtFormInfo htFormInfo = htFormInfoService.get(formId);
                    htFormInfo.setIsAutomatic("1");
                    htFormInfoService.update(htFormInfo);
                    User user = UserUtils.get("system");
                    bpmTask.setCurrentUser(user);
                    bpmTaskService.completeTask(bpmTask);
//                    HtRepairClientForm htRepairClientForm = new HtRepairClientForm();
//                    htRepairClientForm.setHtFormInfo(htFormInfo);
//                    List<HtRepairClientForm> repairList = htRepairClientFormService.findList(htRepairClientForm);
//                    if(repairList.get(0).getAgainDate() != null){
//                        Date startDate = new Date();
//                        Date endDate = repairList.get(0).getAgainDate();
//                        endDate.setTime(endDate.getTime() + 15*60*1000);
//                        if(startDate.getTime() > endDate.getTime()){
//                            htFormInfo.setIsAutomatic("1");
//                            htFormInfoService.update(htFormInfo);
//                            bpmTaskService.completeTask(bpmTask);
//                        }
//                    }else{
//                        htFormInfo.setIsAutomatic("1");
//                        htFormInfoService.update(htFormInfo);
//                        bpmTaskService.completeTask(bpmTask);
//                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }




    /**
     * 修改工单信息
     * @param task
     */
    private void updateFormAutomatic(Task task) {
        try{
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String formId = StringUtils.remove(processInstance.getBusinessKey(),"hd_form_claim:");
            HtFormInfo htFormInfo = new HtFormInfo(formId);
            htFormInfo.setIsAutomatic("1");
            htFormInfoService.update(htFormInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 定期扫描咨询待分配环节工单 就行流转 根据状态判断待处理人
     */
    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void waitOfDistribution(){
        //查询所有待分配的任务
        List<Task> taskList  = taskService.createTaskQuery().taskDefinitionKey("advisory_wait").list();
        // List<Task> taskList = taskService.createTaskQuery().taskName("待分配环节").list();

        for (Task task : taskList) {
            try {
                //需要指派人
                String taskId = task.getId();

                HtAdvisoryInfo htAdvisoryInfo = htAdvisoryInfoService.findByTaskId(taskId);
                if (htAdvisoryInfo!=null){
                    Date againDate = htAdvisoryInfo.getAgainDate();
                    long l = System.currentTimeMillis();
                    if(againDate != null && l>againDate.getTime()){
                        String departmentId = htAdvisoryInfo.getUpdateBy();
                        BpmTask bpmTask = new BpmTask();
                        bpmTask.setId(task.getId());
                        bpmTask.setVariableList(new ArrayList<>());
                        bpmTask.setCurrentUser(new User("0_server_skus"));
                        bpmTask.setAssignee(departmentId);
                        bpmTask.setNextUserCodes(departmentId);
                        bpmTask.setPriority(TaskFormEnum.TaskFormColorEnum.ORANGE.getCode());
                        HashMap<String, Object> variables = new HashMap<>();
                        if ("0_tousu_1qiy".equals(departmentId)){
                            variables.put("checkStatus",'4');
                        }else{
                            variables.put("checkStatus",'3');
                        }
                        bpmTask.setTransientVariables(variables);
                        User user = UserUtils.get("system");
                        bpmTask.setCurrentUser(user);
                        bpmTaskService.completeTask(bpmTask);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 定期扫描换新待分配环节工单 并分配员工
     */
    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void scheduleRenew(){
        //查询所有待分配的任务
        List<Task> taskList  = taskService.createTaskQuery().taskDefinitionKey("renew_wait").list();
        for (Task task : taskList) {
            try {
                //从排班引擎获取一个用户
                Map<String,String> RosterInfo = htDutyRosterService.getDutyRosterStaffUserIdByRole("renew_info");

                String status = RosterInfo.get("status");
                String userId = RosterInfo.get("userId");

                //如果用户存在 完成任务并将任务分配给该员工
                if("success".equals(status)){
                    BpmTask bpmTask = new BpmTask(task);
                    bpmTask.setCurrentUser(new User("system"));
                    bpmTask.setAssignee(userId);
                    bpmTask.setNextUserCodes(userId);
                    bpmTask.setPriority(TaskFormEnum.TaskFormColorEnum.ORANGE.getCode());

                    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                    String formId = StringUtils.remove(processInstance.getBusinessKey(),"hd_form_claim:");
                    HtFormInfo htFormInfo = htFormInfoService.get(formId);
                    HtRenewForm htRenewForm = new HtRenewForm();
                    htRenewForm.setHtFormInfo(htFormInfo);
                    List<HtRenewForm> renewList = htRenewFormService.findList(htRenewForm);
                    if(renewList.size() > 0){
                        if(renewList.get(0).getAgainDate() != null){
                            Date startDate = new Date();
                            Date endDate = renewList.get(0).getAgainDate();
                            if(startDate.getTime() > endDate.getTime()){
                                htFormInfo.setIsAutomatic("1");
                                htFormInfoService.update(htFormInfo);
                                HashMap<String, Object> variables = new HashMap<>();
                                variables.put("checkStatus","1");
                                bpmTask.setVariables(variables);
                                User user = UserUtils.get("system");
                                bpmTask.setCurrentUser(user);
                                bpmTaskService.completeTask(bpmTask);
                            }
                        }else{
                            htFormInfo.setIsAutomatic("1");
                            htFormInfoService.update(htFormInfo);
                            HashMap<String, Object> variables = new HashMap<>();
                            variables.put("checkStatus","1");
                            bpmTask.setVariables(variables);
                            User user = UserUtils.get("system");
                            bpmTask.setCurrentUser(user);
                            bpmTaskService.completeTask(bpmTask);
                        }
                    }else{
                        htFormInfo.setIsAutomatic("1");
                        htFormInfoService.update(htFormInfo);
                        HashMap<String, Object> variables = new HashMap<>();
                        variables.put("checkStatus","1");
                        bpmTask.setVariables(variables);
                        User user = UserUtils.get("system");
                        bpmTask.setCurrentUser(user);
                        bpmTaskService.completeTask(bpmTask);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
