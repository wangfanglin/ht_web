package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 查勘结果
 * @author tangchao
 */

@Data
public class SurveyLoss implements Parameter{
    private String deviceSubfault;          //故障小类ID
    private String deviceSubfaultName;      //故障小类名称
    private String repairPlan;              //维修方案ID
    private String repairPlanName;          //维修方案名称
    private String deviceFault;             //故障大类ID
    private String deviceFaultName;         //故障大类名称
    private String planAmt;                 //维修价格
    private String realAmt;                 //实际价格  配件+工时+杂费
    private String fittingsAmt;             //配件价格
    private String repairTimeAmt;           //工时费
    private String otherAmt;                //杂费

    /**
     *
     * @param deviceSubfault            故障小类ID
     * @param deviceSubfaultName        故障小类名称
     * @param repairPlan                维修方案ID
     * @param repairPlanName            维修方案名称
     * @param deviceFault               故障大类ID
     * @param deviceFaultName           故障大类名称
     * @param planAmt                   维修价格
     * @param realAmt                   实际价格  配件+工时+杂费
     * @param fittingsAmt               配件价格
     * @param repairTimeAmt             工时费
     * @param otherAmt                  杂费
     */
    public SurveyLoss(String deviceSubfault, String deviceSubfaultName, String repairPlan, String repairPlanName, String deviceFault, String deviceFaultName, String planAmt, String realAmt, String fittingsAmt, String repairTimeAmt, String otherAmt) {
        this.deviceSubfault = deviceSubfault;
        this.deviceSubfaultName = deviceSubfaultName;
        this.repairPlan = repairPlan;
        this.repairPlanName = repairPlanName;
        this.deviceFault = deviceFault;
        this.deviceFaultName = deviceFaultName;
        this.planAmt = planAmt;
        this.realAmt = realAmt;
        this.fittingsAmt = fittingsAmt;
        this.repairTimeAmt = repairTimeAmt;
        this.otherAmt = otherAmt;
    }

    @Override
    public boolean checkMandatory() {
        return StringUtils.isNotEmpty(deviceSubfault)&& StringUtils.isNotEmpty(deviceSubfaultName)
                && StringUtils.isNotEmpty(repairPlan)&& StringUtils.isNotEmpty(repairPlanName)
                && StringUtils.isNotEmpty(deviceFault)&& StringUtils.isNotEmpty(deviceFaultName)
                && StringUtils.isNotEmpty(planAmt)&& StringUtils.isNotEmpty(realAmt)
                && StringUtils.isNotEmpty(fittingsAmt)&& StringUtils.isNotEmpty(repairTimeAmt)&& StringUtils.isNotEmpty(otherAmt);
    }
}
