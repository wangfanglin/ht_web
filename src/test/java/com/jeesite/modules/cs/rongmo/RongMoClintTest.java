package com.jeesite.modules.cs.rongmo;

import com.jeesite.modules.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class RongMoClintTest  {

    @Test
    public void outbound() {
        System.out.println(RongMoClint.outbound(new RongMoOutboundParameter("8000","13522305150","Local")));
    }
}