package com.jeesite.modules.template.utils;

import com.jeesite.modules.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WxTemplateUtilsTest extends BaseTest {

    @Autowired
    WxTemplateUtils wxTemplateUtils;
    /**
     * status ：待付款通知
     * 通知名称：付款通知
     *
     * @param url 详情链接
     * @param demandOrder 需求单号
     * @param date 时间
     * @param userId 发送的用户
     * @return
     * @throws Exception
     */
    @Test
    public void sendWxTemplateStatusSix() {
        //String openId = "oJPBx5jTW2o6iPJ-oTnQzaN4yrTs";
        String openId = "oJPBx5sCmwAyv1IcqloiG4_sI1Ls";
        wxTemplateUtils.sendWxTemplateStatusEight("祝您生活愉快！期待与您再次合作！","小包",openId);
        wxTemplateUtils.sendWxTemplateStatusOne("2020-05-25 11:00:00","123465798","imei",openId);
        wxTemplateUtils.sendWxTemplateStatusTwo("41458797165215654821","小包",openId);
        wxTemplateUtils.sendWxTemplateStatusThree("wangfanglin","北京昌平","12345678912","修手机","2020-05-25 11:00:00",openId);
        wxTemplateUtils.sendWxTemplateStatusSix("www.baidu.com","O213det223","123456",openId);
        wxTemplateUtils.sendWxTemplateStatusSeven("小包","手机苹果15","2020-05-25 11:00:00",openId);
    }

    @Test
    public void get(){
        String openId = "oJPBx5sCmwAyv1IcqloiG4_sI1Ls";
        wxTemplateUtils.sendWxTemplateStatusFive("123456","2020-05-27",openId);
    }
}