package com.jeesite.modules.template.utils;

import com.jeesite.modules.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteTemplateUtilsTest extends BaseTest {

    @Test
    public void noteTemplateOneRepair() {
        String s = NoteTemplateUtils.noteTemplateOneRepair(new String[]{"sss","aaa"});
        System.out.println(s);
    }
}