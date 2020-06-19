package com.jeesite.modules.task;

import com.jeesite.modules.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SynchronizationTaskTest extends BaseTest {

    @Autowired
    SynchronizationTask synchronizationTask;

    @Test
    public void convertJxPolicy() {
        long millis = System.currentTimeMillis();
        synchronizationTask.convertJxPolicy();
        logger.info("use time : {}",System.currentTimeMillis()-millis);

    }

    @Test
    public void convertOldPolicy() {
        long millis = System.currentTimeMillis();
        synchronizationTask.convertOldPolicy();
        logger.info("use time : {}",System.currentTimeMillis()-millis);
    }
}