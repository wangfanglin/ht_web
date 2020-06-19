package com.jeesite.modules.bohai.entity;

import com.jeesite.common.lang.StringUtils;

/**
 * 故障信息子节点
 *
 * @author wangfanglin
 * @since 2020/04/22
 */
public class AssessList implements Parameter{
    /**故障大类ID*/
    private String deviceFault;
    /**故障小类ID*/
    private String deviceSubfault;
    /**维修方案ID*/
    private String repairPlan;
    /**建议维修价格*/
    private String planAmt;
    /**实际金额 默认0*/
    private String realAmt;

    public AssessList (String deviceFault,String deviceSubfault,String repairPlan,String planAmt){
        this.deviceFault = deviceFault;
        this.deviceSubfault = deviceSubfault;
        this.repairPlan = repairPlan;
        this.planAmt = planAmt;
    }

    @Override
    public boolean checkMandatory() {
        return !StringUtils.isEmpty(deviceFault) && !StringUtils.isEmpty(deviceSubfault)
                && StringUtils.isEmpty(repairPlan) && StringUtils.isEmpty(planAmt);
    }
}
