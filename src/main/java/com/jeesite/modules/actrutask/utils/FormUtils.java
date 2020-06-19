package com.jeesite.modules.actrutask.utils;

import com.jeesite.modules.actrutask.entity.ActRuTask;
import com.jeesite.modules.common.DateUtil;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 任务工具类
 *
 * @author wangfanglin
 * @date 2020/04/16
 */
public class FormUtils{

    /**
     * TODO 是否是第一个自然天（需求上没有明说 先按照当天24点之前就算第一个自然天）
     *
     * @param taskForm 单个任务工单数据
     * @return
     */
    public static Boolean boolIsCalendarDay(ActRuTask taskForm) {
        legalParams(taskForm);
        if(porintWeeHoursDate().getTime()> taskForm.getCreateTime().getTime()){
            return false;
        }
        return true;
    }

    /**
     * TODO 当天是否产生记录
     *
     * @param taskForm 单个任务工单数据
     * @return
     */
    /*public static Boolean boolIsAlreadyOperate(ActRuTask taskForm) {

        return false;
    }
    */

    /**
     * 校验
     * @param taskForm
     */
    public static void legalParams(ActRuTask taskForm){
        if(ObjectUtils.isEmpty(taskForm)){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_DATA_IS_NULL_ERROR,"任务工单为空");
        }
        Date createTime = taskForm.getCreateTime();
        if(createTime == null){
            throw new RuTaskBusinessException(RuTaskErrorCode.RU_TASK_DATA_IS_NULL_ERROR,"任务工单id:"+taskForm.getId()+",创建日期为空");
        }
    }
    /**
     *  大于两个自然天（当天日期和创建时间相差2天以上）
     *
     * @param taskForm
     * @return
     */
    public static Boolean boolGreaterThanSecondDay(ActRuTask taskForm) {
        legalParams(taskForm);
        if(DateUtil.parseDate(DateUtil.nowTime()).getTime() > getAddDate(taskForm.getCreateTime(),2).getTime()){
            return false;
        }
        return true;
    }
    /**
     *  大于等于两个自然天（当天日期和创建时间相差2天以上）
     *
     * @param taskForm
     * @return
     */
    public static Boolean boolGreaterEqualSecondDay(ActRuTask taskForm) {
        legalParams(taskForm);
        if(porintWeeHoursDate().getTime() <= getAddDate(taskForm.getCreateTime(),2).getTime()){
            return false;
        }
        return true;
    }

    /**
     * TODO 等于第二个自然天
     *
     * @param taskForm
     * @return
     */
    public static Boolean boolIsSecondDay(ActRuTask taskForm) {
        legalParams(taskForm);
        if(porintWeeHoursDate().getTime() <= getAddDate(taskForm.getCreateTime(),1).getTime()
                || porintWeeHoursDate().getTime() > getAddDate(taskForm.getCreateTime(),2).getTime()){
            return false;
        }
        return true;
    }

    /**
     * TODO 判断是否被驳回的工单 2020/04/20 与汤超沟通 不加驳回工单判断。加一个优先级只能递增向上。
     *
     * @param taskForm
     * @return
     */
    public static Boolean boolIsReject(ActRuTask taskForm) {

        return false;
    }



    /**
     *指定时间  当天 18:00
     *
     * @return
     */
    public static Date porintEighteenDate(Date now){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd ");
        Date startTime = null;
        try {
            startTime = df.parse(ds.format(now) +"18:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime;
    }

    /**
     *指定时间  凌晨
     *
     * @return
     */
    public static Date porintWeeHoursDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd ");
        Date startTime = null;
        try {
            startTime = df.parse(ds.format(now) +"23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime;
    }

    /**
     * 次日早上 06：00（当前日期）
     *
     * @return
     */
    public static Date porintSixDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd ");
        Date startTime = null;
        try {
            startTime = df.parse(ds.format(now) +"06:00:00");
            getAddDate(startTime,1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime;
    }

    /**
     * 日期增加天数
     * @param date
     * @param iCount
     * @return
     */
    public static Date getAddDate(Date date, int iCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, iCount);
        return cal.getTime();
    }

}
