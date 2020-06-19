package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.service.IActRuTaskService;
import com.jeesite.modules.actrutask.utils.RuTaskBusinessException;
import com.jeesite.modules.actrutask.utils.RuTaskErrorCode;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import com.jeesite.modules.common.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单颜色规则（五）
 * 生成待维修工单后，如果在预计维修时间内，工单颜色为绿色；当在维修工期指定日期时，工单颜色为橙色，超出维修工期为红色。
 *
 * @author wangfanglin
 * @since 2020/04/21
 */
@Service(value = "actRuTaskRuleFiveService")
public class ActRuTaskRuleFiveService extends ActRuTaskBaseService implements IActRuTaskService {
    @Override
    public List<ActRuTask> getFormListByColorRule(List<ActRuTask> actRuTasks) {
        if(CollectionUtils.isEmpty(actRuTasks)){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_COLLECTIONS_IS_NULL_ERROR,"集合为空");
        }
        List<ActRuTask> updateAllTaskFormList = new ArrayList<>();
        for(ActRuTask actRuTask : actRuTasks){
            if(StringUtils.isEmpty(actRuTask.getRepairEndDate())){
                throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_DATA_IS_NULL_ERROR,"任务工单id:"+actRuTask.getId()+",没有维修完成时间，无法判断");
            }
            if(DateUtil.parseDate(DateUtil.nowTime()).getTime() > actRuTask.getRepairEndDate().getTime()){
                if (TaskFormEnum.TaskFormColorEnum.GREEN.getCode().compareTo(actRuTask.getPriority()) == 1) {
                    actRuTask.setPriority(TaskFormEnum.TaskFormColorEnum.GREEN.getCode());
                    updateAllTaskFormList.add(actRuTask);
                }
            }
            if(DateUtil.parseDate(DateUtil.nowTime()).getTime() < actRuTask.getRepairEndDate().getTime()){
                if (TaskFormEnum.TaskFormColorEnum.RED.getCode().compareTo(actRuTask.getPriority()) == 1) {
                    actRuTask.setPriority(TaskFormEnum.TaskFormColorEnum.RED.getCode());
                    updateAllTaskFormList.add(actRuTask);
                }
            }
        }
        return updateAllTaskFormList;
    }
}
