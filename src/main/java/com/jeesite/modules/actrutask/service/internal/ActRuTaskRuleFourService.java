package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.service.IActRuTaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单颜色规则（四）
 * 当工单状态变更时间≤18：00时
 * 该自然天工单颜色为黄色，产生操作记录后工单颜色变为绿色，如果未产生操作记录第2个自然天变为橙色，当超过2个自然天变为红色；
 * 当工单状态变更时间＞18：00时
 * 则自产生工单的第1个及第2个自然天工单颜色为黄色，如果产生操作记录变为绿色，如果未产生操作记录第3个自然天变为橙色，当超过3个自然天变为红色；
 *
 * @author wangfanglin
 * @since 2020/04/21
 */
@Service(value = "actRuTaskRuleFourService")
public class ActRuTaskRuleFourService extends ActRuTaskBaseService implements IActRuTaskService {

    /**
     * 工单颜色规则入口
     * 操作具有实时性 不在定时器写
     * @param actRuTasks
     * @return
     */
    @Override
    public List<ActRuTask> getFormListByColorRule(List<ActRuTask> actRuTasks) {
        List<ActRuTask> updateAllTaskFormList = new ArrayList<>();
        baseTimeColorListRule(actRuTasks,updateAllTaskFormList);
        return updateAllTaskFormList;
    }
}
