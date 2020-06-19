package com.jeesite.modules.convertor.entity;

/**
 * @author tangchao
 */

public enum ConverTask {
    CREATE("创建",""),
    UPDATE("更新","");


    private final String name;
    private final String content;


    ConverTask(String name,String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
