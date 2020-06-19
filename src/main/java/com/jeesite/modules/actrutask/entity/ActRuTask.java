package com.jeesite.modules.actrutask.entity;

import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import lombok.Data;

import java.util.Date;

/**
 * 任务表对象
 *
 * @author wangfanglin
 * @version  2020/04/16
 */
@Data
public class ActRuTask {
    private String id;

    private Integer rev;

    private String executionId;

    private String procInstId;

    private String procDefId;

    private String taskDefId;

    private String scopeId;

    private String subScopeId;

    private String scopeType;

    private String scopeDefinitionId;

    private String propagatedStageInstId;

    private String name;

    private String parentTaskId;

    private String description;

    private String taskDefKey;

    private String owner;

    private String assignee;

    private String delegation;

    private Integer priority;

    private Date createTime;

    private Date dueDate;

    private String category;

    private Integer suspensionState;

    private String tenantId;

    private String formKey;

    private Date claimTime;

    private Byte isCountEnabled;

    private Integer varCount;

    private Integer idLinkCount;

    private Integer subTaskCount;

    private String businessKey;

    private String contactStatus;

    private String formId;

    private Date repairEndDate;

    private String claimStatus;

    public TaskFormEnum.TaskFormBeanEnum getTaskFormTypeEnum() {

        return TaskFormEnum.TaskFormTypeEnum.initByCode(taskDefKey);
    }


}