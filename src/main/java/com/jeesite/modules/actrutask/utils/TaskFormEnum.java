package com.jeesite.modules.actrutask.utils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 工作流任务工单枚举类
 *
 * @author wangfanglin
 * @version 2020/04/17
 */
public interface  TaskFormEnum {

    /**
     * 工单类型枚举类  repair_info_offer_admin
     */
    enum TaskFormTypeEnum implements TaskFormEnum {
        //待申请
        ORDER_INFO("填写申请工单","order_info","前端用户",null),
        CLIENT_INFO("待申请-联系客户", "client_info","客服",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        CLIENT_CLOSE("待申请 - 申请关闭待审核", "client_close","客服",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        CLIENT_WAIT_RESTART("在线申请 - 已关闭待重启", "client_wait_restart","客服主管",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        CLIENT_CHECK_RESTART("在线申请 - 申请重启待审核", "client_check_restart","客服主管",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),

        //在线理赔
        CLAIM_WAIT("待分配", "claim_wait","系统",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        CLAIM_INFO("在线理赔-待处理", "claim_info","在线理赔操作员",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        CLAIM_CLOSE("在线理赔-申请关闭待审核", "claim_close","核赔主管",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        CLAIM_WAIT_RESTART("在线理赔-已关闭待重启", "claim_wait_restart","核赔主管",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        CLAIM_CHECK_RESTART("在线理赔-申请重启待审核", "claim_check_restart","核赔主管",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),

        //维修
        REPAIR_INFO("维修-待联系客户", "repair_info","维修网点",TaskFormBeanEnum.REPAIR_INFO),
        REPAIR_CHARGE_WAIT("维修改派待审核", "repair_charge_wait","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_OFFER("维修-待报价", "repair_offer","维修网点",TaskFormBeanEnum.REPAIR_OFFER),
        REPAIR_CHARGE("维修-报价待审核", "repair_charge","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_MANAGER("维修-维修方案售后经理待审核", "repair_manager","售后经理",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_WAIT("维修-待维修", "repair_wait","维修网点",TaskFormBeanEnum.REPAIR_WAIT),
        REPAIR_END("维修-维修完成待寄件", "repair_end","维修网点",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_FINANCE("财务待确认", "repair_finance","财务",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_ALL_CHECK("全损工单维修待审核","repair_all_check","财务",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_END_CHECK("维修完成待审核", "repair_end_check","核赔主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_CHANGE_OFFER("维修-报价待审核", "repair_change_offer","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_CHARGE_OVER("维修第三次修改待审核", "repair_charge_over","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_END_RESTART("维修完成", "repair_end_restart","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_CHARGE_CLOSE("维修-申请关闭关闭待审核", "repair_charge_close","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_CHANGE_TIME("维修-申请改派待审核", "repair_change_time","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_CLOSE_RESTART("维修-已关闭待重启", "repair_close_restart","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),
        REPAIR_WAIT_RESTART("维修-申请重启待审核", "repair_wait_restart","售后主管",TaskFormBeanEnum.REPAIR_INFO_OFFER_ADMIN),

        //换新
        RENEW_WAIT("换新-待分配", "renew_wait","系统",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_INFO("换新-待联系客户", "renew_info","换新售后",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_CONFIRM_TIME("换新-待联系客户", "renew_confirm_time","换新售后",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_OFFER("换新-待报价", "renew_offer","换新售后",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_MANAGER("换新-换机方案售后经理审核", "renew_manager","售后经理",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_CHENCK_RESTART("换新-申请重启待审核", "renew_chenck_restart","售后经理",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_DIRECTOR("换新-维修方案售后总监待审核", "renew_director","售后总监",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_WAIT_RESTART("换新-已关闭待重启", "renew_wait_restart","售后经理",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_CLOSE("换新-申请关闭待审核", "renew_close","售后经理",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_END("采购完成-待邮寄", "renew_end","换新售后",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),
        RENEW_PAY("自付款确认", "renew_pay","财务",TaskFormBeanEnum.CLAIM_RENEW_CLIENT),

        //其他
        OTHER_TYPE("其他","other_type","其他",TaskFormBeanEnum.OTHER_TYPE);


        private final String name;
        private final String code;
        private final String role;
        private final TaskFormBeanEnum rule;


        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getRole() {
            return role;
        }

        public TaskFormBeanEnum getRule() {
            return rule;
        }

        TaskFormTypeEnum(String name, String code,String role, TaskFormBeanEnum rule) {
            this.name = name;
            this.code = code;
            this.role = role;
            this.rule = rule;
        }

        public static TaskFormBeanEnum initByCode(final String code) {
            Optional<TaskFormTypeEnum> taskFormTypeEnum = Arrays.stream(TaskFormTypeEnum.values()).filter((bean) -> bean.getCode().equals(code)).findFirst();
            return taskFormTypeEnum.isPresent() ? taskFormTypeEnum.get().getRule():TaskFormBeanEnum.OTHER_TYPE;
        }

    }


    /**
     * 工单优先级枚举类
     */
    enum TaskFormColorEnum implements TaskFormEnum{
        YELLOW("工作流中各个节点工单优先级颜色：黄色", 50),
        RED("工作流中各个节点工单优先级颜色：红色", 200),
        ORANGE("工作流中各个节点工单优先级颜色：橙色", 100),
        GREEN("工作流中各个节点工单优先级颜色：绿色", 25),
        WRITE("工作流中各个节点工单优先级颜色：白色", 0);

        private String name;
        private Integer code;

        TaskFormColorEnum(String name, Integer code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 任务工单规则bean标志
     */
    enum TaskFormBeanEnum implements TaskFormEnum{
        REPAIR_INFO("维修-待联系客户", "repair_info","actRuTaskRuleOneService"),
        REPAIR_OFFER("维修-待报价", "repair_offer","actRuTaskRuleFourService"),
        REPAIR_WAIT("维修-待维修", "repair_wait","actRuTaskRuleFiveService"),
        CLAIM_RENEW_CLIENT("在线理赔、换新、待申请", "claim_renew_client","actRuTaskRuleTwoService"),
        REPAIR_INFO_OFFER_ADMIN("维修待联系客户和待报价审核页面", "repair_info_offer_admin","actRuTaskRuleThreeService"),
        OTHER_TYPE("其他","other_type",null);

        private String name;
        private String code;
        private String beanName;

        TaskFormBeanEnum(String name, String code,String beanName) {
            this.name = name;
            this.code = code;
            this.beanName = beanName;
        }

        public String getBeanName() {
            return beanName;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }
}
