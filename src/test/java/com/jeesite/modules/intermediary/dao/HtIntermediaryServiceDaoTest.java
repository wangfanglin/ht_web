package com.jeesite.modules.intermediary.dao;

import com.jeesite.modules.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class HtIntermediaryServiceDaoTest {
    @Autowired
    HtIntermediaryServiceDao dao;

    @Test
    public void sum() {
        System.out.println(dao.sum());
    }
}