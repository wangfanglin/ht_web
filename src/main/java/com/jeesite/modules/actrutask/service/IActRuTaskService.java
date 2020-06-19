package com.jeesite.modules.actrutask.service;


import com.jeesite.modules.actrutask.entity.ActRuTask;

import java.util.List;

/**
 * 任务服务接口
 *
 * @author wangfanglin
 * @since 2020/04/20
 */
public interface IActRuTaskService {

    /**
     * 统一颜色规则接口
     *
     * @param actRuTasks
     * @return
     */
    List<ActRuTask> getFormListByColorRule(List<ActRuTask> actRuTasks);
}
