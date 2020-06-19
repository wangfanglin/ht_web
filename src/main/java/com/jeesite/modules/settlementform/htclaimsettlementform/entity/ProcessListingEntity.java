package com.jeesite.modules.settlementform.htclaimsettlementform.entity;

import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.htwaitapplyform.entity.HtWaitApplyForm;

@Table
public class ProcessListingEntity extends HtProcessInstanceBase {

    private HtClaimSettlementForm htClaimSettlementForm;
    private HtWaitApplyForm htWaitApplyForm;
    private String ASSIGNEE;
    private String executionName;
    private String ProcessDefinitionName;
    private String taskId;
    private String executionId;
    private String isAssigned;
    private String level;
    private String description;
    private String taskName;

    public HtWaitApplyForm getHtWaitApplyForm() {
        return htWaitApplyForm;
    }

    public void setHtWaitApplyForm(HtWaitApplyForm htWaitApplyForm) {
        this.htWaitApplyForm = htWaitApplyForm;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(String isAssigned) {
        this.isAssigned = isAssigned;
    }

    public String getASSIGNEE() {
        return ASSIGNEE;
    }

    public void setASSIGNEE(String ASSIGNEE) {
        this.ASSIGNEE = ASSIGNEE;
    }

    public String getExecutionName() {
        return executionName;
    }
    public void setExecutionName(String executionName) {
        this.executionName = executionName;
    }
    public String getProcessDefinitionName() {
        return ProcessDefinitionName;
    }
    public void setProcessDefinitionName(String processDefinitionName) {
        ProcessDefinitionName = processDefinitionName;
    }
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getExecutionId() {
        return executionId;
    }
    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }
    public HtClaimSettlementForm getHtClaimSettlementForm() {
        return htClaimSettlementForm;
    }

    public void setHtClaimSettlementForm(HtClaimSettlementForm htClaimSettlementForm) {
        this.htClaimSettlementForm = htClaimSettlementForm;
    }
}
