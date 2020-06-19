package com.jeesite.modules.template.utils;

import com.jeesite.modules.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class SmsSendUtilsTest {

    @Test
    public void send() {
        SmsSendUtils.send("15010788869","test");
    }
}