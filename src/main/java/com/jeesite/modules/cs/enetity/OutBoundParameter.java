package com.jeesite.modules.cs.enetity;

/**
 * 外呼入参
 * @author tangchao
 */
public class OutBoundParameter {
    private String fromExten;               //坐席工号
    private String exten;                   //被叫号码
    private String extenType;               //Local/sip/gateway   外呼时强制坐席使用该接听方式进行外呼。Local为“手机”,”gateway为“语音网关”
    private String businessId;              //业务id
    private String outShow;                 //指定外呼外显号码 此号码必须为账号线路配置里的真实号码。最新的通话服务器才支持该功能，如果需使用此功能可提前找群内对接技术确认。


    public OutBoundParameter(String fromExten, String exten, String extenType, String businessId) {
        this.fromExten = fromExten;
        this.exten = exten;
        this.extenType = extenType;
        this.businessId = businessId;
    }

    public String getFromExten() {
        return fromExten;
    }

    public void setFromExten(String fromExten) {
        this.fromExten = fromExten;
    }

    public String getExten() {
        return exten;
    }

    public void setExten(String exten) {
        this.exten = exten;
    }

    public String getExtenType() {
        return extenType;
    }

    public void setExtenType(String extenType) {
        this.extenType = extenType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getOutShow() {
        return outShow;
    }

    public void setOutShow(String outShow) {
        this.outShow = outShow;
    }


    @Override
    public String toString() {
        return "OutBoundParameter{" +
                "fromExten='" + fromExten + '\'' +
                ", exten='" + exten + '\'' +
                ", extenType='" + extenType + '\'' +
                ", businessId='" + businessId + '\'' +
                ", outShow='" + outShow + '\'' +
                '}';
    }
}
