package com.jeesite.modules.policy.service;

import com.jeesite.modules.policy.entity.HtFailPolicy;
import com.jeesite.modules.policy.entity.OldPolicyInfo;
import com.jeesite.modules.policy.entity.Policy;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.sys.entity.Office;

/**
 *
 * @author tangchao
 */

public interface PolicyConverter<T extends Policy> {
    /**
     * 将老数据转换成新数据
     * @param policy
     * @return
     */
    PolicyInfo convert(T policy,ConVertType type);








    enum ConVertType{
        INSERT,UPDATE;
    }



}
