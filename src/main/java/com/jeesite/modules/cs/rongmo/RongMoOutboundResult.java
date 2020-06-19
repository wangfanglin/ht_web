package com.jeesite.modules.cs.rongmo;

public class RongMoOutboundResult {
    private String Response;
    private String ActionID;
    private String Succeed;
    private String Message;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getActionID() {
        return ActionID;
    }

    public void setActionID(String actionID) {
        ActionID = actionID;
    }

    public String getSucceed() {
        return Succeed;
    }

    public void setSucceed(String succeed) {
        Succeed = succeed;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    @Override
    public String toString() {
        return "RongMoOutboundResult{" +
                "Response='" + Response + '\'' +
                ", ActionID='" + ActionID + '\'' +
                ", Succeed='" + Succeed + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
