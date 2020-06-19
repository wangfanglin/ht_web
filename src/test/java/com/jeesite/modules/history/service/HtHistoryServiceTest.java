package com.jeesite.modules.history.service;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.history.entity.HtHistory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class HtHistoryServiceTest extends BaseTest {
@Autowired
HtHistoryService service;
    @Test
    public void save() {
        HtHistory htHistory = new HtHistory();
        htHistory.setFormType("1");
        htHistory.setWorkOrderId("1234");
        htHistory.setFormId("123");
       // htHistory.setActHiId("1234");
        htHistory.setActivityId("123");
        htHistory.setAfterActivityId("234r");
       // htHistory.setBeforeActivityId("234");
        htHistory.setCmsVisible("");
        htHistory.setDisposeUserId("324r");
        service.save(htHistory);
    }
}