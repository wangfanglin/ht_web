package com.jeesite.modules.test.entity;

public class HistoryConstructor {

    public static <t extends BaseHistoryData> t build(History history){
        switch (history.getType()){
            case "1":
                return (t) new TestUser("汤超",18);
            case "2":
                return (t) new TestOrder("1000",0);
            default:
                return null;
        }

    }
}
