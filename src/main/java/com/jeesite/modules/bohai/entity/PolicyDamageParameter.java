package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 3.3.估损提交 入参
 * @author tangchao
 */
@Data
public class PolicyDamageParameter implements Parameter{
    private String clmNo;       //渤海案件号
    private String amt;         //估损金额

    /**
     *
     * @param clmNo 渤海案件号
     * @param amt   估损金额
     */
    public PolicyDamageParameter(String clmNo, String amt) {
        this.clmNo = clmNo;
        this.amt = amt;
    }

    @Override
    public boolean checkMandatory() {
        return StringUtils.isNotEmpty(clmNo)&& StringUtils.isNotEmpty(amt);
    }
}
