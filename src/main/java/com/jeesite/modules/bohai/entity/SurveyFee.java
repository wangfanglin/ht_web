package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 查勘费用
 * @author tangchao
 */
@Data
public class SurveyFee implements Parameter{
    private String taskId;          //渤海流程号
    private String lossFee;         //费用金额
    private String feeTypeCode;     //费用类型编码
    private String feeTypeName;     //费用类型名称
    private String remark;          //备注
    private String policyNo;        //保单号
    private String caseNo;          //案件号
    private String updaterCode;     //操作人编码
    private String auditFee;        //费用最高金额

    /**
     *
     * @param taskId        渤海流程号
     * @param lossFee       费用金额
     * @param feeTypeCode   费用类型编码
     * @param feeTypeName   费用类型名称
     * @param policyNo      保单号
     * @param caseNo        案件号
     * @param updaterCode   操作人编码
     * @param auditFee      费用最高金额
     */
    public SurveyFee(String taskId, String lossFee, String feeTypeCode, String feeTypeName, String policyNo, String caseNo, String updaterCode, String auditFee) {
        this.taskId = taskId;
        this.lossFee = lossFee;
        this.feeTypeCode = feeTypeCode;
        this.feeTypeName = feeTypeName;
        this.policyNo = policyNo;
        this.caseNo = caseNo;
        this.updaterCode = updaterCode;
        this.auditFee = auditFee;
    }


    @Override
    public boolean checkMandatory() {
        return StringUtils.isNotEmpty(taskId)&& StringUtils.isNotEmpty(lossFee)
                && StringUtils.isNotEmpty(feeTypeCode)&& StringUtils.isNotEmpty(feeTypeName)
                && StringUtils.isNotEmpty(policyNo)&& StringUtils.isNotEmpty(caseNo)
                && StringUtils.isNotEmpty(updaterCode)&& StringUtils.isNotEmpty(auditFee);
    }
}
