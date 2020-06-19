package com.jeesite.modules.actrutask.service;

import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.service.internal.ActRuTaskBaseService;
import com.jeesite.modules.actrutask.service.internal.ActRuTaskRemoveDeleteService;
import com.jeesite.modules.actrutask.service.internal.ActRuTaskServiceContext;
import com.jeesite.modules.actrutask.utils.RuTaskBusinessException;
import com.jeesite.modules.actrutask.utils.RuTaskErrorCode;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工单颜色变化规则服务
 *
 * @author wangfanglin
 * @since 2020/04/22
 */
@Service
@Slf4j
public class ActRuTaskService {

    @Autowired
    private ActRuTaskRemoveDeleteService actRuTaskRemoveDeleteService;

    @Autowired
    private ActRuTaskServiceContext ruTaskServiceContext;

    @Autowired
    private ActRuTaskBaseService actRuTaskBaseService;

    /**
     * 定时器入口 ： 定时更新工单颜色
     */
    public void doUpdate(){
        try {
            List<ActRuTask> actRuTasks = actRuTaskBaseService.getActRuTask();
            updateFormColor(actRuTasks);
        }catch (Exception e){
            log.error("定时器更新失败",e);
        }

    }

    /**
     * 更新工单颜色
     *
     * @param sumUpdateDataList 任务对象集合
     */
    public void updateFormColor(List<ActRuTask> sumUpdateDataList){
        if(CollectionUtils.isEmpty(sumUpdateDataList)){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_COLLECTIONS_IS_NULL_ERROR,"查询集合为空");
        }
        Map<TaskFormEnum.TaskFormBeanEnum,List<ActRuTask>> keyIsTaskDefKeyMap = sumUpdateDataList.stream().collect(Collectors.groupingBy(ActRuTask :: getTaskFormTypeEnum));
        updateFormColor(keyIsTaskDefKeyMap);
    }

    /**
     * 更新工单颜色
     *
     * @param map
     */
    private void updateFormColor(Map<TaskFormEnum.TaskFormBeanEnum, List<ActRuTask>> map) {
        if (map.isEmpty()) {
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_DATA_IS_NULL_ERROR, "查询数据为空");
        }
        List<ActRuTask> actRuTaskList = new ArrayList<>();
        for (TaskFormEnum.TaskFormBeanEnum typeEnum : map.keySet()) {
            IActRuTaskService actRuTaskService = ruTaskServiceContext.getRuTaskService(typeEnum);
            if (!ObjectUtils.isEmpty(actRuTaskService)) {
                List<ActRuTask> actRuTasks = actRuTaskService.getFormListByColorRule(map.get(typeEnum));
                if (!CollectionUtils.isEmpty(actRuTasks)) {
                    actRuTaskList.addAll(actRuTasks);
                }
            }
        }
        if(!CollectionUtils.isEmpty(actRuTaskList)) {
            updateColorList(actRuTaskList);
        }
    }

    /**
     * 更新数据库数据
     *
     * @param updateAllTaskFormList
     */
    private void updateColorList(List<ActRuTask> updateAllTaskFormList) {
        if(!CollectionUtils.isEmpty(updateAllTaskFormList)){
            actRuTaskRemoveDeleteService.updateList(updateAllTaskFormList);
        }
    }
}
