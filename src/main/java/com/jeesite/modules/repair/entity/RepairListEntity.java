package com.jeesite.modules.repair.entity;

import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtProcessInstanceBase;
import com.jeesite.modules.sys.entity.Office;

@Table
public class RepairListEntity extends HtProcessInstanceBase {

    private HtRepairClientForm htRepairClientForm;
    private HtClaimSettlementForm htClaimSettlementForm;
    private HtBrandInfo htBrandInfo;
    private HtPhoneModelInfo htPhoneModelInfo;
    private Office office;
    private String ASSIGNEE;
    private String executionName;
    private String ProcessDefinitionName;
    private String taskId;
    private String executionId;
    private String isAssigned;
    private String level;
    private String description;
    private String taskName;
    private String formStatus;
    private String manageStatus;

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getManageStatus() {
        return manageStatus;
    }

    public void setManageStatus(String manageStatus) {
        this.manageStatus = manageStatus;
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

    public HtRepairClientForm getHtRepairClientForm() {
        return htRepairClientForm;
    }

    public void setHtRepairClientForm(HtRepairClientForm htRepairClientForm) {
        this.htRepairClientForm = htRepairClientForm;
    }

    public HtClaimSettlementForm getHtClaimSettlementForm() {
        return htClaimSettlementForm;
    }

    public void setHtClaimSettlementForm(HtClaimSettlementForm htClaimSettlementForm) {
        this.htClaimSettlementForm = htClaimSettlementForm;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public HtBrandInfo getHtBrandInfo() {
        return htBrandInfo;
    }

    public void setHtBrandInfo(HtBrandInfo htBrandInfo) {
        this.htBrandInfo = htBrandInfo;
    }

    public HtPhoneModelInfo getHtPhoneModelInfo() {
        return htPhoneModelInfo;
    }

    public void setHtPhoneModelInfo(HtPhoneModelInfo htPhoneModelInfo) {
        this.htPhoneModelInfo = htPhoneModelInfo;
    }
}
