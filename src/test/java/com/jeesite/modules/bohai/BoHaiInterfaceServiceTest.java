package com.jeesite.modules.bohai;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.bohai.entity.ReportCaseParameter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class BoHaiInterfaceServiceTest extends BaseTest {

    @Autowired
    BoHaiInterfaceService boHaiInterfaceService;

    @Test
    public void reprotSubmit() {
        ReportCaseParameter reportCaseParameter = new ReportCaseParameter(null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        boHaiInterfaceService.reprotSubmit(reportCaseParameter);
    }
}