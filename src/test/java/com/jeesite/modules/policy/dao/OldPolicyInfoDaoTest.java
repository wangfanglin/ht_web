package com.jeesite.modules.policy.dao;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.policy.entity.JxPolicyInfo;
import com.jeesite.modules.policy.entity.OldPolicyInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class OldPolicyInfoDaoTest extends BaseTest {
    @Autowired
    OldPolicyInfoDao dao;


    @Test
    public void findNewList() {
        List<OldPolicyInfo> newList = dao.findNewList(new OldPolicyInfo());
        for (OldPolicyInfo oldPolicyInfo:
                newList) {
            System.out.println(oldPolicyInfo.toString());
        }
    }
}