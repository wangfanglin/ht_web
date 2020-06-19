package com.jeesite.modules.task;

import com.jeesite.modules.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ActRuTaskTaskTest extends BaseTest {

    @Autowired
    ActRuTaskTask actRuTaskTask;

    @Test
    public void get(){
        actRuTaskTask.updateFormColor();
    }

}