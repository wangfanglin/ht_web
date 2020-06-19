package com.jeesite.modules.cs.web;

import com.jeesite.modules.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CsExternalControllerTest {
    @Autowired
    CsExternalController csExternalController;
    @Test
    public void incomingCall() {
        Calendar calendar =Calendar.getInstance();
        System.out.println(calendar.getTime());
    }
}