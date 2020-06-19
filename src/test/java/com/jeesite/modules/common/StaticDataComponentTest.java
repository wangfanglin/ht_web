package com.jeesite.modules.common;

import com.jeesite.modules.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class StaticDataComponentTest extends BaseTest {
    @Autowired
    StaticDataComponent staticDataComponent;
    @Test
    public void getOldChannelBohai() {
        Assert.assertEquals("42",staticDataComponent.getOldChannelBohai());
    }

    @Test
    public void getOldChannelJiexin() {
        Assert.assertEquals("81",staticDataComponent.getOldChannelJiexin());
    }
}