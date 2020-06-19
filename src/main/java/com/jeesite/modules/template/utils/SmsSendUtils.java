package com.jeesite.modules.template.utils;

import com.jeesite.common.service.ServiceException;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

/**
 *
 * 发送短信的 工具类
 * @author tangchao
 */
public class SmsSendUtils {
    /**
     * 账号
     */
    private static final String USER_ID = "J71053";
    /**
     * 密码
     */
    private static final String PASSWORD = "520521";
    /**
     * 默认手机号码个数
     */
    private static final String PHONE_SIZE = "1";
    /**
     * 默认手机号码个数
     */
    private static final String PORT = "*";


    /**
     *
     *  提供手机号和内容，进行短信发送
     * @param mobile        手机号
     * @param content       内容
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String send(String mobile,String content) {
        WmgwLocator wmgwLocator = new WmgwLocator();
        String strRetNum;
        try {
            strRetNum=wmgwLocator.getwmgwSoap().mongateCsSpSendSmsNew(USER_ID,
                    PASSWORD, mobile, new String(content.getBytes("UTF-8")), Integer.valueOf(PHONE_SIZE).intValue(),PORT);
            return strRetNum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
