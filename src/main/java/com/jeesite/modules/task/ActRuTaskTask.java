package com.jeesite.modules.task;

import com.jeesite.modules.actrutask.service.ActRuTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 任务工单工作流定时任务
 *
 * @author wangfanglin
 * @since 2020/04/21
 */
@Slf4j
@Configuration
@EnableScheduling
public class ActRuTaskTask {

    @Autowired
    private ActRuTaskService actRuTaskService;

    /**
     * 每天凌晨两点更新工作流中工单的颜色
     * @Scheduled(cron = "0 0 2 * * ?")
     * @author wangfanglin
     * @date 2020/04/16
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateFormColor(){
        try {
            actRuTaskService.doUpdate();
        }catch (Exception e){
            log.error("凌晨两点更新工作流中工单的颜色出现异常",e);
        }
    }
}
