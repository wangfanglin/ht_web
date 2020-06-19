package com.jeesite.modules.util;

import java.util.List;

public class CommaJointUtil {

    /**
     * 逗号拼接String方法
     * @param stringList
     * @return
     */
    public static String commaJointString(List<String> stringList){
        StringBuffer sb = new StringBuffer();
        for (String str: stringList) {
            sb.append(str).append(",");
        }
        String commaJointStr = sb.deleteCharAt(sb.length() - 1).toString();
        return commaJointStr;
    }
}
