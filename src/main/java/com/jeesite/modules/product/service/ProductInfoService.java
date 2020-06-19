/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.service;

import java.util.Arrays;
import java.util.List;

import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.htassemblyunit.dao.HtAssemblyUnitDao;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyDetailService;
import com.jeesite.modules.product.dao.HtGroupProductChildDao;
import com.jeesite.modules.product.dao.HtGroupProductInfoDao;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import org.aspectj.lang.annotation.AfterReturning;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.product.entity.ProductInfo;
import com.jeesite.modules.product.dao.ProductInfoDao;

import javax.validation.constraints.NotBlank;

/**
 * 产品表（权益）Service
 *
 * @author zhaohaifeng
 * @version 2020-02-17
 */
@Service
@Transactional(readOnly = true)
public class ProductInfoService extends CrudService<ProductInfoDao, ProductInfo> {
    @Autowired
    private HtAssemblyUnitService assemblyUnitService;
    @Autowired
    private PolicyDetailService policyDetailService;

    /**
     * 获取单条数据
     *
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo get(ProductInfo productInfo) {
        return super.get(productInfo);
    }

    /**
     * 查询分页数据
     *
     * @param productInfo      查询条件
     * @return
     */
    @Override
    public Page<ProductInfo> findPage(ProductInfo productInfo) {
        return super.findPage(productInfo);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param productInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void save(ProductInfo productInfo) {
        //保存产品（权益）
        String id = productInfo.getId();
        String productInfoId = IdGen.uuid();

        if (id == null || "".equals(id)) {
            productInfo.setId(productInfoId);
            productInfo.setIsNewRecord(true);
        } else {
            productInfoId = id;
        }
        //停用状态 1 立即启用 0 否
        String isStart = productInfo.getIsStart();
        if ("1".equals(isStart)){productInfo.setStatus("0");}
        if ("0".equals(isStart)){productInfo.setStatus("2");}
        this.updateUnit(productInfo, "1");
        //将折旧率 /100
        String productType = productInfo.getProductType();
        if ("1".equals(productType)){
            productInfo.setBasisDepreciation(productInfo.getBasisDepreciation()/100);
            productInfo.setMonthlyDepreciation(productInfo.getMonthlyDepreciation()/100);
        }
        super.save(productInfo);


        // 产品跟部件是有一张中间表的
        String assembly = productInfo.getAssemblyId();
        if (!"".equals(assembly)&&assembly!=null){
        String[] assemblyId = productInfo.getAssemblyId().split(",");
        List<String> assemblyIds = Arrays.asList(assemblyId);
        assemblyUnitService.deleteProductAssemblyMiddle(productInfoId);
        assemblyUnitService.insertProductAssemblyMiddle(productInfoId, assemblyIds);
        }
    }

    /**
     * 更新状态
     *
     * @param productInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(ProductInfo productInfo) {
        super.updateStatus(productInfo);
    }

    /**
     * 删除数据
     *
     * @param productInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(ProductInfo productInfo) {
        super.delete(productInfo);
    }

    /**
     * 处理金额
     *
     * @param productInfo
     * @param flag
     * @return
     */
    public ProductInfo updateUnit(ProductInfo productInfo, String flag) {
        //1 是元转分  0 是分转元
        if ("0".equals(flag)) {
            if (productInfo.getInsuranceRestrictPrice() != null) {
                productInfo.setInsuranceRestrictPrice(productInfo.getInsuranceRestrictPrice() / 100);
            }
            if (productInfo.getChangePayment() != null) {
                productInfo.setChangePayment(productInfo.getChangePayment() / 100);
            }
            if (productInfo.getServeCostPrice() != null) {
                productInfo.setServeCostPrice(productInfo.getServeCostPrice() / 100);
            }
            if (productInfo.getPremium() != null) {
                productInfo.setPremium(productInfo.getPremium() / 100);
            }
            if (productInfo.getSuggestedRetailPrice() != null) {
                productInfo.setSuggestedRetailPrice(productInfo.getSuggestedRetailPrice() / 100);
            }
            if (productInfo.getCoverage() != null) {
                productInfo.setCoverage(productInfo.getCoverage() / 100);
            }
            if (productInfo.getRestrictPrice() != null) {
                productInfo.setRestrictPrice(productInfo.getRestrictPrice() / 100);
            }

        } else if ("1".equals(flag)) {
            if (productInfo.getRestrictPrice() != null) {
                productInfo.setRestrictPrice(productInfo.getRestrictPrice() * 100);
            }
            if (productInfo.getInsuranceRestrictPrice() != null) {
                productInfo.setInsuranceRestrictPrice(productInfo.getInsuranceRestrictPrice() * 100);
            }
            if (productInfo.getChangePayment() != null) {
                productInfo.setChangePayment(productInfo.getChangePayment() * 100);
            }
            if (productInfo.getServeCostPrice() != null) {
                productInfo.setServeCostPrice(productInfo.getServeCostPrice() * 100);
            }
            if (productInfo.getPremium() != null) {
                productInfo.setPremium(productInfo.getPremium() * 100);
            }
            if (productInfo.getSuggestedRetailPrice() != null) {
                productInfo.setSuggestedRetailPrice(productInfo.getSuggestedRetailPrice() * 100);
            }
            if (productInfo.getCoverage() != null) {
                productInfo.setCoverage(productInfo.getCoverage() * 100);
            }

        }
        return productInfo;
    }


    /**
     * 根据保单查询换新产品
     * @param policyId
     */
    public ProductInfo findRenew(String policyId) {
        PolicyDetail policyDetail = new PolicyDetail();
        policyDetail.setPolicyInfo(new PolicyInfo(policyId));
        List<PolicyDetail> list = policyDetailService.findList(policyDetail);
        for (PolicyDetail detail : list) {
            String productType = detail.getProductInfo().getProductType();
            if ("1".equals(productType)){
                return detail.getProductInfo();
            }
        }
        return null;
    }

}