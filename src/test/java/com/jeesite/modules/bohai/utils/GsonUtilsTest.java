package com.jeesite.modules.bohai.utils;

import com.jeesite.modules.BaseTest;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class GsonUtilsTest {

    @Test
    public void toJsonWtihNullField() {
    }

    @Test
    public void toJsonFilterNullField() {
    }

    @Test
    public void toJson() {
        String json = "{\"status\":\"SUCCESS\"}";
        System.out.println(json);
        System.out.println(GsonUtils.toJson(json));
    }

    @Test
    public void fromJson() {
        System.out.println(Stream.of(true,true,false).anyMatch(t -> !t));
    }

    @Test
    public void convert() {
    }
}