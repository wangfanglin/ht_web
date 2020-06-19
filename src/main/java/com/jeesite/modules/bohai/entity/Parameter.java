package com.jeesite.modules.bohai.entity;

import java.util.List;

/**
 * 渤海入参
 * @author tangchao
 */
public interface Parameter {
    /**
     * 检查必填项是否填写完整
     * @return  true 完整 false 不完整
     */
    boolean checkMandatory();


    /**
     * 检查 实现了 Parameter 的集合 必填项是否填写完整
     * @param list
     * @param <T>
     * @return
     */
    static <T extends Parameter> boolean checkListCheckMandatory(List<T> list){
        return !list.stream()
                .map(Parameter::checkMandatory)
                .anyMatch(t -> !t);
    }

}
