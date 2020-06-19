package com.jeesite.modules.policy.service;

import com.jeesite.modules.policy.dao.ConvertDao;
import com.jeesite.modules.policy.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.jeesite.modules.policy.service.PolicyConverter.ConVertType.INSERT;

/**
 * @author tangchao
 */
@Service
public class JxPolicyConverter implements PolicyConverter<JxPolicyInfo> {
    private final ConvertDao convertDao;
    private final PolicyConvertUtils policyConvertUtils;

    @Autowired
    public JxPolicyConverter(ConvertDao convertDao, PolicyConvertUtils policyConvertUtils) {
        this.convertDao = convertDao;
        this.policyConvertUtils = policyConvertUtils;
    }


    /**
     * 对捷信的订单进行转换
     * @param policy
     * @return
     */
    @Override
    public PolicyInfo convert(JxPolicyInfo policy,ConVertType type) {

        if (!checkUniqueness(policy, type)){return null;}


        PolicyInfo newPolicy = policyConvertUtils.savePolicyInfo(policy,"0");

        if (newPolicy == null) return null;


        return newPolicy;
    }




    /**
     * 检查唯一性
     * @param policy
     * @param type
     * @return
     */
    private boolean checkUniqueness(Policy policy, ConVertType type) {
        if(type==INSERT){
            try {
                convertDao.insertJxConvert(policy.getId());
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

}
