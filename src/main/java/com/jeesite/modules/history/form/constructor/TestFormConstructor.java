package com.jeesite.modules.history.form.constructor;

import com.jeesite.modules.history.entity.HtHistory;

import com.jeesite.modules.history.form.FormConfig;
import com.jeesite.modules.test.entity.TestForm;
import com.jeesite.modules.test.service.TestFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@FormConfig("test_test")
@Service
public class TestFormConstructor implements FormConstructor<TestForm> {
    @Autowired
    TestFormService testFormService;

    @Override
    public TestForm build(HtHistory htHistory) {
        return testFormService.get(htHistory.getFormId());
    }
}
