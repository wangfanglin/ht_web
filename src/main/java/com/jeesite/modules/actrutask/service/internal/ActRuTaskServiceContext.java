package com.jeesite.modules.actrutask.service.internal;

import com.jeesite.modules.actrutask.service.IActRuTaskService;
import com.jeesite.modules.actrutask.utils.RuTaskBusinessException;
import com.jeesite.modules.actrutask.utils.RuTaskErrorCode;
import com.jeesite.modules.actrutask.utils.TaskFormEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 任务工单提供实现类的策略CONTEXT
 * 负责提供实现类，不做任何业务动作
 *
 * @author wangfanglin
 * @since 2020/04/21
 */
@Service
@Slf4j
public class ActRuTaskServiceContext implements ApplicationContextAware {

    /**Spring应用上下文环境*/
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ActRuTaskServiceContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 实例化方法
     *
     * @param name 实例化名字
     * @param <T> 实例化类型
     * @return
     */
    private static <T> T getBean(String name){
        T bean = null;
        try{
            if(null != getApplicationContext()){
                bean= (T)getApplicationContext().getBean(name);
            }
        }catch (Exception e){
            log.error("实例化任务工单服务错误",name,e.getMessage());
        }
        return bean;
    }

    /**
     * 获取实例化Bean
     *
     * @param taskFormBeanEnum
     * @return
     */
    public final IActRuTaskService getRuTaskService(TaskFormEnum.TaskFormBeanEnum taskFormBeanEnum){
        if(taskFormBeanEnum == null){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_DATA_IS_NULL_ERROR,"任务工单类型为空");
        }
        if(TaskFormEnum.TaskFormBeanEnum.OTHER_TYPE.equals(taskFormBeanEnum)){
            return null;
        }
        IActRuTaskService ruTaskService = getBean(taskFormBeanEnum.getBeanName());
        if(ruTaskService == null){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_DATA_IS_NULL_ERROR,"spring容器中无Bean类型为"+taskFormBeanEnum.getBeanName()+"的service,请检查spring配置");
        }
        return ruTaskService;
    }
}
