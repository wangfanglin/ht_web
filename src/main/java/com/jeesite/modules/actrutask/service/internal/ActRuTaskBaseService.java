package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.dao.ActRuTaskMapper;
import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.actrutask.utils.FormUtils;
import com.jeesite.modules.actrutask.utils.RuTaskBusinessException;
import com.jeesite.modules.actrutask.utils.RuTaskErrorCode;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 任务服务
 *
 * @author wangfanglin
 * @version 2020/04/16
 */
@Service
@Slf4j
public class ActRuTaskBaseService extends ActRuTaskRuleService {

    @Autowired
    private ActRuTaskRemoveDeleteService actRuTaskRemoveDeleteService;

    @Autowired
    private ActRuTaskMapper actRuTaskMapper;

    @Autowired
    private ActRuTaskServiceContext ruTaskServiceContext;

    /**状态 0*/
    public static String NUMBEI_ZERO = "0";

    /**状态 1*/
    public static String NUMBEI_ONE = "1";

    /**状态 2*/
    public static String NUMBEI_TWO = "2";

    /**状态 3*/
    public static String NUMBEI_THREE = "3";

    /**状态 4*/
    public static String NUMBEI_FOUR = "4";


    /**
     * 获得任务集合
     *
     * @return List<ActRuTask> 任务对象集合
     */
    public List<ActRuTask> getActRuTask() {
        List<ActRuTask> actRuTasks = actRuTaskMapper.selectAll();
        List<ActRuTask> formStatus = actRuTaskMapper.selectAllIn();
        formatTaskFormList(actRuTasks,formStatus);
        if(CollectionUtils.isEmpty(actRuTasks)){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_COLLECTIONS_IS_NULL_ERROR,"查询集合为空");
        }
        return actRuTasks;
    }

    /**
     * 工单颜色规则
     *
     * @param actRuTasks
     * @param updateAllTaskFormList
     */
    public void baseRuleColorListRule(List<ActRuTask> actRuTasks, List<ActRuTask> updateAllTaskFormList) {
        baseYellowColorListRule(actRuTasks, updateAllTaskFormList);
        repairInfoOrangeColorListRule(actRuTasks, updateAllTaskFormList);
        baseRedColorListRuleTwo(actRuTasks, updateAllTaskFormList);
    }

    /**
     * 整理维修-待联系客户橙色工单规则
     *
     * @param actRuTasks 任务工单数据集合
     * @param updateOrangeTaskFormList 需要更新得任务工单数据集合
     */
    public void repairInfoOrangeColorListRule(List<ActRuTask> actRuTasks, List<ActRuTask> updateOrangeTaskFormList) {
        for(ActRuTask taskForm : actRuTasks){
            Boolean boolIsSecondDay = FormUtils.boolIsSecondDay(taskForm);
            if (boolIsSecondDay ) {
                if (TaskFormEnum.TaskFormColorEnum.ORANGE.getCode().compareTo(taskForm.getPriority()) == 1) {
                    taskForm.setPriority(TaskFormEnum.TaskFormColorEnum.ORANGE.getCode());
                    updateOrangeTaskFormList.add(taskForm);
                }
            }
        }
    }

    /**
     * 将工单邮寄状态拼装到任务表中
     *
     * @param actRuTasks
     * @param formStatus
     */
    private void formatTaskFormList(List<ActRuTask> actRuTasks, List<ActRuTask> formStatus) {
        if(CollectionUtils.isEmpty(actRuTasks) || CollectionUtils.isEmpty(formStatus)){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_COLLECTIONS_IS_NULL_ERROR,"集合数据为空");
        }
        Map<String,ActRuTask> map = formStatus.stream().collect(Collectors.toMap(ActRuTask :: getProcInstId, Function.identity(),(actRuTask1,actRuTask2)->actRuTask2));
        actRuTasks.stream().forEach(e->{
            if(map.containsKey(e.getProcInstId())){
                e.setProcInstId(map.get(e.getProcInstId()).getProcInstId());
                e.setFormId(map.get(e.getProcInstId()).getFormId());
                e.setContactStatus(map.get(e.getProcInstId()).getContactStatus());
                e.setRepairEndDate(map.get(e.getProcInstId()).getRepairEndDate());
            }
        });
    }

}



