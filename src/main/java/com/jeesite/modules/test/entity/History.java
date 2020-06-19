package com.jeesite.modules.test.entity;

public class History<t extends BaseHistoryData> {
    private String name ;
    private String type;
    private t data;

    public History(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public t getData() {
        return data;
    }

    public void setData(t data) {
        this.data = data;
    }
}

