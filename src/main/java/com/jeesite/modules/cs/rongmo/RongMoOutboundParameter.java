package com.jeesite.modules.cs.rongmo;

import com.jeesite.modules.cs.enetity.OutBoundParameter;

public class RongMoOutboundParameter {
    private String FromExten;
    private String Exten;
    private String ExtenType;
    private String ActionID;
    private String DialoutStrVar;
    private String OutShow;


    public RongMoOutboundParameter(OutBoundParameter parameter) {
        this.FromExten = parameter.getFromExten();
        this.Exten = parameter.getExten();
        this.ExtenType = parameter.getExtenType();
        this.ActionID = parameter.getBusinessId();
        this.DialoutStrVar = "{ \"businessId\": \""+parameter.getBusinessId()+"\" }";
        this.OutShow = parameter.getOutShow();
    }


    public RongMoOutboundParameter(String fromExten, String exten, String extenType) {
        FromExten = fromExten;
        Exten = exten;
        ExtenType = extenType;
    }

    public String getFromExten() {
        return FromExten;
    }

    public void setFromExten(String fromExten) {
        FromExten = fromExten;
    }

    public String getExten() {
        return Exten;
    }

    public void setExten(String exten) {
        Exten = exten;
    }

    public String getExtenType() {
        return ExtenType;
    }

    public void setExtenType(String extenType) {
        ExtenType = extenType;
    }

    public String getActionID() {
        return ActionID;
    }

    public void setActionID(String actionID) {
        ActionID = actionID;
    }

    public String getDialoutStrVar() {
        return DialoutStrVar;
    }

    public void setDialoutStrVar(String dialoutStrVar) {
        DialoutStrVar = dialoutStrVar;
    }

    public String getOutShow() {
        return OutShow;
    }

    public void setOutShow(String outShow) {
        OutShow = outShow;
    }

    @Override
    public String toString() {
        return "RongMoOutboundParameter{" +
                "FromExten='" + FromExten + '\'' +
                ", Exten='" + Exten + '\'' +
                ", ExtenType='" + ExtenType + '\'' +
                ", ActionID='" + ActionID + '\'' +
                ", DialoutStrVar='" + DialoutStrVar + '\'' +
                ", OutShow='" + OutShow + '\'' +
                '}';
    }
}
