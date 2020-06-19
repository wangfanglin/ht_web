package com.jeesite.modules.policy.service;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.policy.entity.JxPolicyInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class JxPolicyConverterTest extends BaseTest {

    @Autowired
    JxPolicyConverter jxPolicyConverter;
    @Autowired
    JxPolicyInfoService jxPolicyInfoService;


    @Test
    public void convert() {
        JxPolicyInfo jxPolicyInfo = jxPolicyInfoService.get("0002a11b539648df9b07153e7c5df857");
        jxPolicyConverter.convert(jxPolicyInfo, PolicyConverter.ConVertType.INSERT);

    }
}