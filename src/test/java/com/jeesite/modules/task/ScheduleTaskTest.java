package com.jeesite.modules.task;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.BaseTest;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.TaskInfoQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ScheduleTaskTest extends BaseTest {
    @Autowired
    ScheduleTask scheduleTask;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    HtFormInfoService htFormInfoService;


    @Test
    public void schedule() {
        scheduleTask.schedule();

//        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
//        ProcessInstance result = query.processInstanceId("1236127504274808833").singleResult();
//        System.out.println(result.getActivityId());
//        System.out.println(result);

//        taskService.createTaskQuery().processInstanceId("1235027709795192832").singleResult().get;


    }
}