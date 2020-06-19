package com.jeesite.modules.cs.rongmo;

import lombok.Data;

/**
 * 容联 对外事件推送 的入参
 * @author tangchao
 */

@Data
public class RongMoEventPushParameter {
    private String CallNo;                      //主叫号码
    private String CalledNo;                    //被叫号码
    private String CallSheetID;                 //通话记录ID,CallSheetID 是这条通话记录再DB中的唯一id
    private String refCallSheetId;              //转接前通话的CallSheetID，可以用来定位转接电话的上一通通话
    private String CallID;                      //通话ID,通话连接的在系统中的唯一标识。CallID 是在通话进行中channel的id,可以用这个id来挂断通话之类的操作。一个channel有一个CallID,但一个call可能会出现在多个通话中,比如呼入转接。
    private String CallType;                    //通话类型：dialout外呼通话,normal普通来电,transfer呼入转接,dialTransfer外呼转接
    private String Ring;                        //通话振铃时间（话务进入呼叫中心系统的时间）
    private String RingingDate;                 //被叫振铃开始时间（呼入是按座席振铃的时间,外呼按客户振铃的时间）
    private String Begin;                       //通话接通时间（双方开始通话的时间,如果被叫没接听的话为空）
    private String End;                         //通话结束时间
    private String QueueTime;                   //来电进入技能组时间
    private String Agent;                       //处理坐席的工号
    private String Exten;                       //处理坐席的工号,历史原因该字段与Agent相同
    private String AgentName;                   //处理坐席的姓名
    private String Queue;                       //通话进入的技能组名称
    private String State;                       //接听状态：dealing（已接）,notDeal（振铃未接听）,leak（ivr放弃）,queueLeak（排队放弃）,blackList（黑名单）,voicemail（留言）,limit（并发限制）
    private String CallState;                   //事件状态：Ring, Ringing, Link, Hangup(Unlink也当成Hangup处理)
    private String ActionID;                    //通过调用外呼接口时,该字段会保存请求的actionID,其它情况下该字段为空
    private String WebcallActionID;             //通过调用webcall接口,该字段会保存请求的actionID,其它情况下该字段为空
    private String RecordFile;                  //通话录音文件名：用户要访问录音时,在该文件名前面加上服务器路径即可,如：FileServer/RecordFile
    private String FileServer;                  //通过FileServer中指定的地址加上RecordFile的值可以获取录音
    private String Province;                    //目标号码的省,例如北京市。呼入为来电号码,呼出为去电号码
    private String District;                    //目标号码的市,例如北京市。呼入为来电号码,呼出为去电号码
    private String IVRKEY;                      //通话在系统中选择的按键菜单,10004@0。格式为：按键菜单的节点编号@选择的菜单按键。如果为多级菜单则为：10004@0-10005@1。
    private String AccountId;                   //账户编号字段,默认不推送有需求的客户对接时联系七陌技术支持人员进行开通
    private String AccountName;                 //账户名称字段,默认不推送有需求的客户对接时联系七陌技术支持人员进行开通
    private String CdrVar;                      //软电话条中的自定义参数，只有在软电话条中用CdrVar自定义id后才会有该字段。
    private String DialoutStrVar;               //外呼接口和小号外呼接口中的自定义参数，只有在这两个接口中使用DialoutStrVar参数才会有该字段。
    private String userID;                      //在呼入是传的userId

    /**
     * 获取当前通话的录音URL
     * @return
     */
    public String getUrl(){
        return FileServer+"/"+RecordFile;
    }


}
