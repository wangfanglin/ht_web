package com.jeesite.modules.actrutask.utils;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.common.DateUtil;
import org.junit.Test;

public class FormUtilsTest extends BaseTest {

    @Test
    public void boolIsCalendarDay() {
        ActRuTask actRuTask = new ActRuTask();
        actRuTask.setCreateTime(DateUtil.parseTime("2020-04-21 01:00:00"));
        FormUtils.boolIsCalendarDay(actRuTask);
        System.out.println(FormUtils.boolIsCalendarDay(actRuTask));
    }
}