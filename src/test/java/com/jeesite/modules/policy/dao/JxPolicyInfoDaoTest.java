package com.jeesite.modules.policy.dao;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.policy.entity.JxPolicyInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class JxPolicyInfoDaoTest extends BaseTest {
    @Autowired
    JxPolicyInfoDao jxPolicyInfoDao;


    @Test
    public void findNewList() {
        List<JxPolicyInfo> newList = jxPolicyInfoDao.findNewList(new JxPolicyInfo());
        for (JxPolicyInfo jxPolicyInfo:
        newList) {
            System.out.println(jxPolicyInfo.toString());
        }
    }
}