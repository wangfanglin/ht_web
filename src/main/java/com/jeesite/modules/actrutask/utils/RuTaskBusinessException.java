package com.jeesite.modules.actrutask.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义任务业务异常
 *
 * @author wangfanglin
 * @version 2020/04/16
 */
public class RuTaskBusinessException extends RuntimeException{

    protected AtomicLong errorCode;

    protected Object data;

    public RuTaskBusinessException(Long errorCode, String message) {
        this(errorCode, message, null, null);
    }

    public RuTaskBusinessException(Long errorCode,String message,Object data,Throwable e){
        super(message,e);
        this.errorCode = errorCode==null ? new AtomicLong(RuTaskErrorCode.RU_TASK_COMMENT_ERROR) : new AtomicLong(errorCode);
        this.data = data;
    }

}
