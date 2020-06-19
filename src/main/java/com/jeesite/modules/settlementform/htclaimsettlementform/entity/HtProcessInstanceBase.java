package com.jeesite.modules.settlementform.htclaimsettlementform.entity;


import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;


@Table
public class HtProcessInstanceBase extends DataEntity<HtProcessInstanceBase> {


    private HtFormInfo htFormInfo;
    private PolicyInfo policyInfo;
    private User user;
    private Role role;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public PolicyInfo getPolicyInfo() {
        return policyInfo;
    }

    public void setPolicyInfo(PolicyInfo policyInfo) {
        this.policyInfo = policyInfo;
    }


    public HtFormInfo getHtFormInfo() {
        return htFormInfo;
    }
    public void setHtFormInfo(HtFormInfo htFormInfo) {
        this.htFormInfo = htFormInfo;
    }
}
