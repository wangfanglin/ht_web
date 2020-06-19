package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.service.IActRuTaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单颜色规则（三）
 * 提交审核后即第1个自然天，工单颜色为黄色；当天产生操作记录后工单颜色变为绿色，如果未产生操作记录仍为黄色；该工单如果在2个自然天未产生操作记录则变为橙色，超过2个自然天为红色
 *
 * @author wangfanglin
 * @since 2020/04/21
 */
@Service(value = "actRuTaskRuleThreeService")
public class ActRuTaskRuleThreeService extends ActRuTaskBaseService implements IActRuTaskService {

    /**
     * 工单颜色规则入口
     * 操作 是动作 具有实时性 不在定时器中写
     * @param actRuTasks
     * @return
     */
    @Override
    public List<ActRuTask> getFormListByColorRule(List<ActRuTask> actRuTasks) {
        List<ActRuTask> updateAllTaskFormList = new ArrayList<>();
        baseRuleColorListRule(actRuTasks,updateAllTaskFormList);
        return updateAllTaskFormList;
    }
}
