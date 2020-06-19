package com.jeesite.modules.ds;

import com.jeesite.modules.BaseTest;
import org.aspectj.lang.annotation.AfterReturning;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DataSyncTest extends BaseTest {
    @Autowired
    private DataSync dataSync;

    @Test
    public void brand() {
        dataSync.brand();
    }

    @Test
    public void phoneModel() {
        dataSync.phoneModel();
    }

    @Test
    public void purchasingChannels() {
        dataSync.purchasingChannels();
    }

    @Test
    public void expressageChannels() {
        dataSync.expressageChannels();
    }

    @Test
    public void saleChannels() {
        dataSync.saleChannels();
    }


    @Test
    public void agentChannels() {
        dataSync.agentChannels();
    }

    @Test
    public void supplierChannels() {
        dataSync.supplierChannels();
    }

    @Test
    public void productChannels() {
        dataSync.productChannels();
    }

    @Test
    public void maintenancePoint() {
        dataSync.maintenancePoint();
    }
}