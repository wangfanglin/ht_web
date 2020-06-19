package com.jeesite.modules.htdutyroster.service;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.task.OpenFormTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class HtDutyRosterServiceTest extends BaseTest {
    @Autowired
    HtDutyRosterService service;
    @Autowired
    OpenFormTask openFormTask;
    @Test
    public void getDutyRosterStaffUserId() {
        ArrayList<Map<String,String>> list = new ArrayList<>();
        int i = 0;
        while (i<100){
            Map<String, String> map = service.getDutyRosterStaffUserId("0_TEST001");
            list.add(map);
            i++;
        }

        for (Map<String, String> map : list) {
            System.out.println("============="+i+"=================");
            System.out.println(map.get("status"));
            System.out.println(map.get("userId"));
            System.out.println("==============================");
        }
    }


    @Test
    public void getDuty() {
        openFormTask.openApplyForm();
    }

}