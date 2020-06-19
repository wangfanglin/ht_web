package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.utils.FormUtils;
import com.jeesite.modules.actrutask.utils.RuTaskBusinessException;
import com.jeesite.modules.actrutask.utils.RuTaskErrorCode;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 基础规则服务抽象一层
 *
 * @author wangfanglin
 * @since 2020/04/20
 */
@Service
public abstract class ActRuTaskRuleService{

    /**
     * 基础橙色工单规则 大于等于两个自然天 变为橙色
     *
     * @param actRuTasks
     * @param updateOrangeTaskFormList
     */
    public void baseOrangeColorListRule(List<ActRuTask> actRuTasks, List<ActRuTask> updateOrangeTaskFormList) {
        actRuTasks.stream().forEach(taskForm->{
            Boolean boolGreaterThanSecondDay = FormUtils.boolGreaterEqualSecondDay(taskForm);
            if (boolGreaterThanSecondDay ) {
                //优先级只能向上递增
                if (!TaskFormEnum.TaskFormColorEnum.ORANGE.getCode().equals(taskForm.getPriority())) {
                    taskForm.setPriority(TaskFormEnum.TaskFormColorEnum.ORANGE.getCode());
                    updateOrangeTaskFormList.add(taskForm);
                }
            }
        });
    }

    /**
     * 基础红色工单规则（二）大于两个自然天 变为红色
     *
     * @param actRuTasks
     * @param updateOrangeTaskFormList
     */
    public void baseRedColorListRuleTwo(List<ActRuTask> actRuTasks, List<ActRuTask> updateOrangeTaskFormList) {
        actRuTasks.stream().forEach(taskForm->{
            Boolean boolGreaterThanSecondDay = FormUtils.boolGreaterThanSecondDay(taskForm);
            if (boolGreaterThanSecondDay ) {
                //优先级只能向上递增
                if (TaskFormEnum.TaskFormColorEnum.RED.getCode().compareTo(taskForm.getPriority()) == 1) {
                    taskForm.setPriority(TaskFormEnum.TaskFormColorEnum.RED.getCode());
                    updateOrangeTaskFormList.add(taskForm);
                }
            }
        });
    }

    /**
     * 基础黄色工单规则 分配后即第一个自然天，变为黄色
     *
     * @param actRuTasks
     * @param updateYellowTaskFormList
     */
    public void baseYellowColorListRule(List<ActRuTask> actRuTasks, List<ActRuTask> updateYellowTaskFormList) {
        actRuTasks.stream().forEach(taskForm->{
            Boolean boolIsCalendarDay = FormUtils.boolIsCalendarDay(taskForm);
            if (boolIsCalendarDay) {
                //优先级只能向上递增
                if (TaskFormEnum.TaskFormColorEnum.YELLOW.getCode().compareTo(taskForm.getPriority()) == 1) {
                    taskForm.setPriority(TaskFormEnum.TaskFormColorEnum.YELLOW.getCode());
                    updateYellowTaskFormList.add(taskForm);
                }
            }
        });
    }

    /**
     * 基础根据时间来划分规则方法
     *
     * @param actRuTasks
     * @param updateAlltaskFormList 所有任务数据集合
     */
    public void baseTimeColorListRule(List<ActRuTask> actRuTasks, List<ActRuTask> updateAlltaskFormList) {
        for (ActRuTask actRuTask : actRuTasks) {
            if(StringUtils.isEmpty(actRuTask.getCreateTime())) {
                throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_DATA_IS_NULL_ERROR, "任务工单id:" + actRuTask.getId() + ",创建时间createTime:null");
            }
            if (FormUtils.porintEighteenDate(actRuTask.getCreateTime()).getTime() >= (actRuTask.getCreateTime().getTime())) {
                baseYellowColorListRule(actRuTasks, updateAlltaskFormList);
                baseOrangeColorListRule(actRuTasks, updateAlltaskFormList);
                baseRedColorListRuleTwo(actRuTasks, updateAlltaskFormList);
                continue;
            }
            //签收时间大于当天18点时规则和明天规则相同，所以将当天时间按照明天时间进行处理
            if (FormUtils.porintEighteenDate(actRuTask.getCreateTime()).getTime() < (actRuTask.getCreateTime().getTime())) {
                actRuTask.setCreateTime(FormUtils.porintSixDate());
                baseYellowColorListRule(actRuTasks, updateAlltaskFormList);
                baseOrangeColorListRule(actRuTasks, updateAlltaskFormList);
                baseRedColorListRuleTwo(actRuTasks, updateAlltaskFormList);
                continue;
            }
        }
    }

}
