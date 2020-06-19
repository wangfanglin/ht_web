package com.jeesite.modules.bohai.entity;

/**
 * 返回信息
 * @author tangchao
 */

public enum ResultStatus {
    SUCCESS("SUCCESS","成功"),
    ERROR("ERROR","出现错误"),
    PARAMETER_ERROR("PARAMETER_ERROR","参数错误"),
    INTERFACE_ERROR("INTERFACE_ERROR","接口返回失败");

    private String name;
    private String content;

    ResultStatus(String name, String content) {
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
