package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.common.lang.ObjectUtils;
import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.service.IActRuTaskService;
import com.jeesite.modules.actrutask.utils.FormUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单颜色规则（一）比较复杂详见需求文档
 *
 * @author wangfanglin
 * @since 2020/04/21
 */
@Service(value = "actRuTaskRuleOneService")
public class ActRuTaskRuleOneService extends ActRuTaskBaseService implements IActRuTaskService {
    /**
     * 颜色规则入口
     *
     * @param actRuTasks
     */
    @Override
    public List<ActRuTask> getFormListByColorRule(List<ActRuTask> actRuTasks){
        List<ActRuTask> updateAllTaskFormList = new ArrayList<>();
        baseTimeColorListRule(actRuTasks,updateAllTaskFormList);
        repairInfoOfferColorList(actRuTasks,updateAllTaskFormList);
        return updateAllTaskFormList;
    }

    /**
     * 维修工单颜色规则
     *
     * @param actRuTasks 任务工单数据集合
     * @param updateAllTaskFormList 需要更新得任务工单数据集合
     */
    public void repairInfoOfferColorList(List<ActRuTask> actRuTasks, List<ActRuTask> updateAllTaskFormList) {
        for (ActRuTask actRuTask : actRuTasks) {
            //"未邮寄、未确认、已签收、todo 审核通过、驳回"
            if (NUMBEI_TWO.equals(actRuTask.getClaimStatus()) || NUMBEI_THREE.equals(actRuTask.getClaimStatus()) || NUMBEI_FOUR.equals(actRuTask.getClaimStatus())) {
                FormUtils.legalParams(actRuTask);
                if (FormUtils.porintEighteenDate(actRuTask.getCreateTime()).getTime() >= actRuTask.getCreateTime().getTime()) {
                    baseRuleColorListRule(actRuTasks,updateAllTaskFormList);
                    continue;
                }
                //签收时间大于当天18点时规则和明天规则相同，所以将当天时间按照明天时间进行处理
                if (FormUtils.porintEighteenDate(actRuTask.getCreateTime()).getTime() < actRuTask.getCreateTime().getTime()) {
                    ActRuTask ruTask = ObjectUtils.clone(actRuTask);
                    ruTask.setCreateTime(FormUtils.porintSixDate());
                    baseRuleColorListRule(actRuTasks,updateAllTaskFormList);
                    continue;
                }
            }
        }
    }
    /**
     * 维修工单颜色规则
     *
     * @param actRuTasks 任务工单数据集合
     * @param updateAllTaskFormList 需要更新得任务工单数据集合
     */
    @Override
    public void baseTimeColorListRule(List<ActRuTask> actRuTasks, List<ActRuTask> updateAllTaskFormList) {
        for(ActRuTask actRuTask : actRuTasks) {
            //未邮寄、未确认、已签收、审核通过（非工单终止状态）、驳回的工单
            repairInfoOfferColorList(actRuTasks,updateAllTaskFormList);
            //未邮寄未确认
            if(NUMBEI_TWO.equals(actRuTask.getClaimStatus()) || NUMBEI_FOUR.equals(actRuTask.getClaimStatus())){
                //如果期间记录再次沟通的时间
                if(NUMBEI_ONE.equals("再次沟通时间")){
                    baseYellowColorListRule(actRuTasks,updateAllTaskFormList);
                    repairInfoOrangeColorListRule(actRuTasks,updateAllTaskFormList);
                    //整理维修-待联系客户红色工单规则和基础橙色规则相同
                    baseRedColorListRuleTwo(actRuTasks,updateAllTaskFormList);
                    continue;
                }
            }
        }
    }


}
