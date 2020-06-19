package com.jeesite.modules.task;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.actrutask.dao.ActRuTaskMapper;
import com.jeesite.modules.actrutask.entity.ActRuTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ActRuTaskTest extends BaseTest {

    @Autowired
    ActRuTaskTask actRuTask;

    @Autowired
    private ActRuTaskMapper actRuTaskMapper;

    @Test
    public void get(){
        actRuTask.updateFormColor();
    }
    @Test
    public void get1(){
        List<ActRuTask> actRuTasks = new ArrayList<>();
        ActRuTask actRuTask = new ActRuTask();
        actRuTask.setId("1250324211754373121");
        actRuTask.setPriority(100);
        actRuTasks.add(actRuTask);
        ActRuTask actRuTask1 = new ActRuTask();
        actRuTask1.setId("1250714242452389888");
        actRuTask1.setPriority(200);
        actRuTasks.add(actRuTask1);
        int i = actRuTaskMapper.updateByList(actRuTasks);
        if(i>0){
            System.out.println("————————————————————————————————————————————————————————————————————————插入成功");
        }else {
            System.out.println("————————————————————————————————————————————————————————————————————————————插入失败");
        }
    }

}