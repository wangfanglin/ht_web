package com.jeesite.modules.repair.service;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.repair.entity.HtRepairClientForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class HtRepairClientFormServiceTest extends BaseTest {
@Autowired
HtRepairClientFormService repairClientFormService;
    @Test
    public void get() {
        HtRepairClientForm htRepairClientForm = new HtRepairClientForm();
        HtFormInfo htFormInfo = new HtFormInfo();
        htFormInfo.setId("2134");
        htRepairClientForm.setHtFormInfo(htFormInfo);
        repairClientFormService.get(htRepairClientForm);
    }
}