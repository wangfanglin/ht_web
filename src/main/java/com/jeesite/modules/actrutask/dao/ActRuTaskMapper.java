package com.jeesite.modules.actrutask.dao;


import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.actrutask.entity.ActRuTask;

import java.util.List;
import java.util.Map;

/**
 * 任务表查询mapper
 *
 * @author wangfanglin
 * @version 2020/04/17
 */
@MyBatisDao
public interface ActRuTaskMapper {


    /**
     * 根据主键查询
     *
     * @param id  主键id
     * @return ActRuTask 任务对象
     */
    ActRuTask selectById(String id);

    /**
     * 根据map 查询
     *
     * @param params
     * @return List 对象集合
     */
    List<ActRuTask> select(Map params);

    /**
     * 根据工作流集合
     *
     * @param list 节点类型集合
     * @return List 任务对象集合
     */
    List<ActRuTask> selectByTaskDefKeys(List list);

    /**
     * 更新任务工单对象集合
     *
     * @param actRuTasks 工单对象集合
     */
    int updateByList(List<ActRuTask> actRuTasks);

    /**
     * 查询所有
     *
     * @return List 任务对象集合
     */
    List<ActRuTask> selectAll();

    /**
     * 查询
     *
     * @return List 任务对象集合
     */
    List<ActRuTask> selectAllIn();

}