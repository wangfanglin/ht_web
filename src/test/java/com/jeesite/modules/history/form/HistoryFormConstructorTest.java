package com.jeesite.modules.history.form;

import com.jeesite.modules.Application;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.form.constructor.FormConstructor;
import com.jeesite.modules.history.service.HtHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class HistoryFormConstructorTest {
    @Autowired
    HistoryFormConstructor historyFormConstructor;
    @Autowired
    HtHistoryService historyService;
    @Test
    public void getFormConstructor() {
        Map<String,FormConstructor>map =  historyFormConstructor.getFormConstructor();
        HtHistory history = new HtHistory();
        history.setFormId("1");
        System.out.println(map.get("test_test").build(history));
    }
    @Test
    public void build(){

        HtHistory history= historyService.get(new HtHistory("1"));
        System.out.println(historyFormConstructor.build(history));
    }
}