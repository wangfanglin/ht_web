package com.jeesite.modules.common;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActTaskUtils {

    @Autowired
    TaskService taskService;
    /**
     * 通过formId 查询当前的task
     * @param formId
     */
    public Task getTask(String key , String formId){
        System.out.println(key+":"+formId);
        return taskService.createTaskQuery().processInstanceBusinessKey(key+":"+formId).singleResult();
    }

    public Task getTask(String formId){
        return getTask("hd_form_claim",formId);
    }

}
