package com.jeesite.modules.cs.enetity;

import com.jeesite.modules.cs.rongmo.RongMoOutboundResult;

/**
 * 外呼出参
 */
public class OutBoundResult {

    /**
     * 状态
     * 请求是否成功，当不成功时会在Message字段中给出原因
     */
    private String status;
    /**
     * 对应操作的唯一标记
     */
    private String actionId;

    /**
     * 结果说明
     *
     * 400 Error action：请求有误，请检查传递的参数是否合法
     * 401 PBX not found：账户配置问题
     * 403 Not allowed：用户账户或验证码无效或过期，请技术支持修改账户属性，允许这个账户使用外呼接口
     * 403 forbbiden：鉴权失败
     * 404 Agent not found：坐席未找到，请检查FromExten字段中的坐席分机号是否传递正确
     * 407 Agent can not take call 坐席无法接听电话（坐席没有登录），可通过传ExtenType解决该问题
     * 408 Agent Busy：坐席忙碌，无法接听（坐席已经有一通电话在进行中）。如果是话机方式（gateway）,可能是话机掉注册了，请检查一下话机状态
     * 409 Agent extenType not available：调用者指定的接听方式，不可用。可能原因：软电话未登陆，网关未绑定或未注册，未绑定手机号
     * 500 Server error：服务器错误
     */
    private String message;


    public OutBoundResult(String status, String actionId, String message) {
        this.status = status;
        this.actionId = actionId;
        this.message = message;
    }

    public OutBoundResult(RongMoOutboundResult result) {
        this.status = result.getSucceed();
        this.actionId = result.getActionID();
        this.message = result.getMessage();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OutBoundResult{" +
                "status='" + status + '\'' +
                ", actionId='" + actionId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
