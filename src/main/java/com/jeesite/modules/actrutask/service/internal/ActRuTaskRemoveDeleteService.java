package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.dao.ActRuTaskMapper;
import com.jeesite.modules.actrutask.entity.ActRuTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 内部调用的增删改查方法，方便事务控制
 *
 * @author wangfanglin
 * @version  2020/04/16
 */
@Service
@Slf4j
public class ActRuTaskRemoveDeleteService {

    @Autowired
    private ActRuTaskMapper actRuTaskMapper;

    /***
     *
     * 更新对象集合
     *
     * @param actRuTasks 任务对象集合
     */
    @Transactional(rollbackFor = Exception.class,timeout = 60)
    public void updateList(List<ActRuTask> actRuTasks){
        try {
            actRuTaskMapper.updateByList(actRuTasks);
        }catch (Exception e){
            log.error("批量更新任务工单数据异常",e);
        }
    }
}
