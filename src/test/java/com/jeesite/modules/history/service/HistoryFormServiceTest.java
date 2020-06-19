package com.jeesite.modules.history.service;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.BaseTest;
import com.jeesite.modules.history.entity.HistoryForm;
import com.jeesite.modules.history.entity.HtHistory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class HistoryFormServiceTest extends BaseTest {
    @Autowired
    HistoryFormService service;
    @Test
    public void findList() {
        Page<HtHistory> page = new Page<>(1,5);
        HtHistory htHistory = new HtHistory();
        htHistory.setWorkOrderId("1234113631904256000");
        htHistory.setPage(page);
        System.out.println(page);
    }
}