/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.web;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bpm.entity.BpmEntity;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.intermediary.entity.HtIntermediaryService;
import com.jeesite.modules.intermediary.service.HtIntermediaryServiceService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.entity.ProductInfo;
import com.jeesite.modules.product.service.HtGroupProductChildService;
import com.jeesite.modules.product.service.ProductInfoService;
import com.jeesite.modules.provider.entity.HtInsuranceProviderInfo;
import com.jeesite.modules.provider.service.HtInsuranceProviderInfoService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * 产品表（权益）Controller
 *
 * @author zhaohaifeng
 * @version 2020-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productInfo")
public class ProductInfoController extends BaseController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private HtPhoneModelInfoService phoneModelInfoService;
    @Autowired
    private HtAssemblyUnitService assemblyUnitService;
    @Autowired
    private HtIntermediaryServiceService intermediaryService;
    @Autowired
    private HtInsuranceProviderInfoService insuranceProviderService;
    @Autowired
    private HtGroupProductChildService htGroupProductChildService;


    /**
     * 获取数据
     */
    @ModelAttribute
    public ProductInfo get(String id, boolean isNewRecord) {
        return productInfoService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("product:productInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(ProductInfo productInfo, Model model) {
        productInfoService.updateUnit(productInfo, "0");
        model.addAttribute("productInfo", productInfo);
        return "modules/product/productInfoList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("product:productInfo:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<ProductInfo> listData(ProductInfo productInfo, HttpServletRequest request, HttpServletResponse response) {
        productInfo.setPage(new Page<>(request, response));
        Page<ProductInfo> page = productInfoService.findPage(productInfo);
        List<ProductInfo> list = page.getList();
        for (ProductInfo info : list) {
            productInfoService.updateUnit(info, "0");
            info.setUpdateBy(UserUtils.get(info.getUpdateBy()).getUserName());
            String assemblyName = assemblyUnitService.findAssemblyByProductId(info.getId());
            info.setAssemblyId(assemblyName);
        }
        page.setList(list);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("product:productInfo:view")
    @RequestMapping(value = "form")
    public String form(ProductInfo productInfo, Model model) {
        productInfoService.updateUnit(productInfo, "0");
        String productInfoId = productInfo.getId();
        if (!"".equals(productInfoId) && productInfoId != null) {
            //把维修部件信息放进 产品（权益）中
            String assemblyIds = assemblyUnitService.findIdsByProductId(productInfoId);
            productInfo.setAssemblyId(assemblyIds);
            //把配件信息放进 产品（权益）中
            String accessoriesIds = phoneModelInfoService.findIdsByProductId(productInfoId);
            productInfo.setPhoneModelId(accessoriesIds);
        }
        model.addAttribute("productInfo", productInfo);

        //部件数据
        HtAssemblyUnit htAssemblyUnit = new HtAssemblyUnit();
        htAssemblyUnit.setStatus("0");
        List<HtAssemblyUnit> assemblyUnitList = assemblyUnitService.findList(htAssemblyUnit);
        model.addAttribute("assemblyUnitList", assemblyUnitList);
        //保险供应商
        HtInsuranceProviderInfo insuranceProvider = new HtInsuranceProviderInfo();
        insuranceProvider.setStatus("0");
        List<HtInsuranceProviderInfo> insuranceProviderList = insuranceProviderService.findList(insuranceProvider);
        model.addAttribute("insuranceProviderList", insuranceProviderList);
        // 中介服务商
        HtIntermediaryService htIntermediaryService = new HtIntermediaryService();
        htIntermediaryService.setStatus("0");
        List<HtIntermediaryService> intermediaryServerList = intermediaryService.findList(htIntermediaryService);
        model.addAttribute("intermediaryServerList", intermediaryServerList);

        return "modules/product/productInfoForm";
    }

    /**
     * 保存产品表
     */
    @RequiresPermissions("product:productInfo:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated ProductInfo productInfo) {
        Date intoMarketDate = productInfo.getIntoMarketDate();
        Date exitMarketDate = productInfo.getExitMarketDate();
        if (exitMarketDate.getTime() < intoMarketDate.getTime()) {
            return renderResult(Global.FALSE, text("退市时间不得小于上市时间！"));
        }

        try {
            productInfoService.save(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
            String proCode = productInfo.getProCode();
            ProductInfo search = new ProductInfo();
            search.setProCode(proCode);
            long count = productInfoService.findCount(search);
            if (count==1){
                return renderResult(Global.FALSE, text("产品编码重复，请重新输入！"));
            }else{
                return renderResult(Global.FALSE, text("保存产品失败！"));
            }
        }
        return renderResult(Global.TRUE, text("保存产品成功！"));
    }

    /**
     * 停用产品表
     */
    @RequiresPermissions("product:productInfo:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(ProductInfo productInfo) {
        String id = productInfo.getId();
        HtGroupProductChild child = new HtGroupProductChild();
        child.setProductInfo(new ProductInfo(id));
        long count = htGroupProductChildService.findCount(child);
        if (count>0){return renderResult(Global.FALSE, text("此产品存在关联产品无法停用！"));}
        productInfo.setStatus(ProductInfo.STATUS_DISABLE);
        productInfoService.updateStatus(productInfo);
        return renderResult(Global.TRUE, text("停用产品成功"));
    }

    /**
     * 启用产品表
     */
    @RequiresPermissions("product:productInfo:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(ProductInfo productInfo) {
        productInfo.setStatus(ProductInfo.STATUS_NORMAL);
        productInfoService.updateStatus(productInfo);
        return renderResult(Global.TRUE, text("启用产品成功"));
    }

    /**
     * 删除产品表
     */
    @RequiresPermissions("product:productInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(ProductInfo productInfo) {
        String id = productInfo.getId();
        HtGroupProductChild child = new HtGroupProductChild();
        child.setProductInfo(new ProductInfo(id));
        long count = htGroupProductChildService.findCount(child);
        if (count>0){return renderResult(Global.FALSE, text("此产品存在关联产品无法删除！"));}
        productInfoService.delete(productInfo);
        return renderResult(Global.TRUE, text("删除产品成功！"));
    }

    /**
     *查询产品
     */
    @RequestMapping(value = "findList")
    @ResponseBody
    public  HashMap<String, Object> findList(ProductInfo productInfo) {
        HashMap<String, Object> result = MapUtils.newHashMap();
        try {
            List<ProductInfo> list = productInfoService.findList(productInfo);
            result.put("status",Global.TRUE);
            result.put("msg","成功");
            result.put("data",list);
        } catch (Exception e) {
            result.put("status",Global.FALSE);
            result.put("msg","失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询产品
     */
    @RequestMapping(value = "findByIds")
    @ResponseBody
    public  HashMap<String, Object> findByIds(String ids) {
        HashMap<String, Object> result = MapUtils.newHashMap();
        ArrayList<ProductInfo> list = new ArrayList<>();
        try {
            if (!"".equals(ids)&&ids!=null){
                String[] split = ids.split(",");
                for (String s : split) {
                    ProductInfo productInfo = productInfoService.get(s);
                    productInfoService.updateUnit(productInfo, "0");
                    list.add(productInfo);
                }
            }
            result.put("status",Global.TRUE);
            result.put("msg","成功");
            result.put("data",list);
        } catch (Exception e) {
            result.put("status",Global.FALSE);
            result.put("msg","失败");
            e.printStackTrace();
        }
        return result;
    }


}