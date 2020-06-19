package com.jeesite.modules.log.entity;

/**
 *
 *  第三方接口类型
 *  该enum应与字典表 third_interface_type 保持一致
 * @author tangchao
 */

public enum ThirdInterfaceType {
    RONGLIAN("容联","1"),
    BOHAI("渤海","0");
    private String name;
    private String code;


    ThirdInterfaceType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
