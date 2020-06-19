package com.jeesite.modules.charts;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.number.dao.HtDateTableDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ChartsControllerTest extends BaseTest {

    @Autowired
    HtDateTableDao htDateTableDao;

   /* @Test
    public void get(){
        List<Map<String, Long>> daySales = htDateTableDao.pushDayData();
        System.out.println(daySales);
    }
*/
}