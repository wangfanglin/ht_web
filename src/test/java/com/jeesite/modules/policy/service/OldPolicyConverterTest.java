package com.jeesite.modules.policy.service;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.policy.entity.OldPolicyInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OldPolicyConverterTest extends BaseTest {
    @Autowired
    OldPolicyConverter oldPolicyConverter;
    @Autowired
    OldPolicyInfoService service;


    @Test
    public void convert() {
        OldPolicyInfo oldPolicyInfo = service.get("14446bad6a1c4384b26f1ac5ed68c874");
        oldPolicyConverter.convert(oldPolicyInfo, PolicyConverter.ConVertType.INSERT);

    }
}