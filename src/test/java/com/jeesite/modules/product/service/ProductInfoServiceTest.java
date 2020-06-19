package com.jeesite.modules.product.service;

import com.jeesite.modules.BaseTest;
import com.jeesite.modules.product.entity.ProductInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ProductInfoServiceTest extends BaseTest {
@Autowired
ProductInfoService productInfoService;
    @Test
    public void findRenew() {
        ProductInfo renew = productInfoService.findRenew("5660265651846783420");
        System.out.println("renew = " + renew);
    }
}