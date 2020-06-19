package com.jeesite.modules.policy.dao;

import com.jeesite.modules.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ConvertDaoTest extends BaseTest {
    @Autowired
    ConvertDao convertDao;


    @Test
    public void insertJxConvert() {
        convertDao.insertJxConvert("1");
    }

    @Test
    public void insertOldConvert() {
        convertDao.insertJxConvert("1");
    }
}