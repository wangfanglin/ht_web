package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.service.IActRuTaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单颜色规则（二）
 * 分配后即第1个自然天，工单颜色为黄色；当天产生操作记录后工单颜色变为绿色，如果未产生操作记录仍为黄色；该工单如果在≥2个自然天未产生操作记录则变为橙色。被驳回的工单颜色为红色。
 *
 * @author wangfanglin
 * @since 2020/04/21
 */
@Service(value = "actRuTaskRuleTwoService")
public class ActRuTaskRuleTwoService extends ActRuTaskBaseService implements IActRuTaskService {

    /**
     * 工单颜色规则入口
     * 被驳回 操作 是动作具有实时性 不在定时器中操作。
     * @param actRuTasks
     */
    @Override
    public List<ActRuTask> getFormListByColorRule(List<ActRuTask> actRuTasks) {
        List<ActRuTask> updateAllTaskFormList = new ArrayList<>();
        baseYellowColorListRule(actRuTasks,updateAllTaskFormList);
        baseOrangeColorListRule(actRuTasks,updateAllTaskFormList);
        return updateAllTaskFormList;
    }
}
