/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.intermediary.entity.HtIntermediaryService;
import com.jeesite.modules.intermediary.service.HtIntermediaryServiceService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.entity.ProductInfo;
import com.jeesite.modules.product.service.HtGroupProductChildService;
import com.jeesite.modules.product.service.HtGroupProductInfoService;
import com.jeesite.modules.product.service.ProductInfoService;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.web.user.EmpUserController;
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

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道产品Controller
 *
 * @author zhaohaifeng
 * @version 2020-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/product/channelProductInfo")
public class ChannelProductInfoController extends BaseController {

    @Autowired
    private ChannelProductInfoService channelProductInfoService;
    @Autowired
    private HtGroupProductInfoService groupProductService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private HtAssemblyUnitService assemblyService;
    @Autowired
    private HtGroupProductChildService groupProductChildService;
    @Autowired
    private HtIntermediaryServiceService intermediaryService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private EmpUserController empUserController;
    @Autowired
    private HtGroupProductChildService childService;
    @Autowired
    private PolicyInfoService policyInfoService;
    @Autowired
    private HtMaintenancePointService maintenancePointService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public ChannelProductInfo get(String id, boolean isNewRecord) {
        return channelProductInfoService.get(id, isNewRecord);
    }

    /**
     * 查询列
     */
    @RequiresPermissions("product:channelProductInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(ChannelProductInfo channelProductInfo, Model model) {
        channelProductInfoService.updateUnit(channelProductInfo, "0");
        model.addAttribute("channelProductInfo", channelProductInfo);
        //将组合产品信息带到页面
        HtGroupProductInfo htGroupProductInfo = new HtGroupProductInfo();
        //查询正常使用的信息
        htGroupProductInfo.setStatus("0");
        List<HtGroupProductInfo> groupProductList = groupProductService.findList(htGroupProductInfo);
        model.addAttribute("groupProductList", groupProductList);
        return "modules/product/channelProductInfoList";
    }

    /**
     * 查询列数据
     */
    @RequiresPermissions("product:channelProductInfo:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<ChannelProductInfo> listData(ChannelProductInfo channelProductInfo, HttpServletRequest request, HttpServletResponse response) {
        channelProductInfo.setPage(new Page<>(request, response));
        Page<ChannelProductInfo> page = channelProductInfoService.findPage(channelProductInfo);
        List<ChannelProductInfo> list = page.getList();
        for (ChannelProductInfo info : list) {
            channelProductInfoService.updateUnit(info, "0");
            //需要在这里加上 组合产品的信息
            String groupProductId = info.getGroupProductId();
            if (groupProductId!=null&&!"".equals(groupProductId)){
            HtGroupProductInfo groupProduct = groupProductService.get(groupProductId);
            info.setGroupProductName(groupProduct.getProductName());
            List<HtGroupProductChild> childByGroupId = childService.findChildByGroupId(groupProductId);
            for (HtGroupProductChild child : childByGroupId) {
                String productId = child.getProductInfo().getId();
                ProductInfo productInfo = productInfoService.get(productId);
                String productType = productInfo.getProductType();
                String proName = productInfo.getProName();
                if ("0".equals(productType)) {
                    info.setMaintainProductName(proName);
                }
                if ("1".equals(productType)) {
                    info.setChangeProductName(proName);
                }
                if ("2".equals(productType)) {
                    info.setExtendProductName(proName);
                }
                if ("3".equals(productType)) {
                    info.setDataProductName(proName);
                }
            }}
        }
        return page;
    }




    /**
     * 查看编辑单
     */
    @RequiresPermissions("product:channelProductInfo:view")
    @RequestMapping(value = "form")
    public String form(ChannelProductInfo channelProductInfo, Model model) {
        channelProductInfoService.updateUnit(channelProductInfo, "0");
        List<Map<String, Object>> claimDatas = channelProductInfoService.findClaimDataByChannelProductId(channelProductInfo.getId());
        if (claimDatas!=null&&claimDatas.size()>0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map<String, Object> claimData : claimDatas) {
                String name = (String) claimData.get("id");
                stringBuilder.append(",").append(name);
            }
            channelProductInfo.setClaimData(stringBuilder.toString());
        }
        model.addAttribute("channelProductInfo", channelProductInfo);
        //将理赔清单信息带到页面
        List<Map<String, Object>> claimDataList = channelProductInfoService.findClaimData();
        model.addAttribute("claimDataList", claimDataList);
        //将组合产品信息带到页面
        HtGroupProductInfo htGroupProductInfo = new HtGroupProductInfo();
        //查询正常使用的信息
        htGroupProductInfo.setStatus("0");
        List<HtGroupProductInfo> groupProductList = groupProductService.findList(htGroupProductInfo);
        model.addAttribute("groupProductList", groupProductList);
      //渠道商
        // 中介服务商
        HtIntermediaryService htIntermediaryService = new HtIntermediaryService();
        htIntermediaryService.setStatus("0");
        List<HtIntermediaryService> intermediaryServerList = intermediaryService.findList(htIntermediaryService);
        model.addAttribute("intermediaryServerList", intermediaryServerList);


        return "modules/product/channelProductInfoForm";
    }

    /**
     * 保存渠道产品
     */
    @RequiresPermissions("product:channelProductInfo:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated ChannelProductInfo channelProductInfo) {
           try {
            channelProductInfoService.save(channelProductInfo);
        } catch (Exception e) {
            e.printStackTrace();
            String proCode = channelProductInfo.getCode();
            ChannelProductInfo search = new ChannelProductInfo();
            search.setCode(proCode);
            long count = channelProductInfoService.findCount(search);
            if (count==1){
                return renderResult(Global.FALSE, text("产品编码重复，请重新输入！"));
            }else{
                return renderResult(Global.FALSE, text("保存产品失败！"));
            }
        }
        return renderResult(Global.TRUE, text("保存渠道产品成功！"));
    }

    /**
     * 停用渠道产品
     */
    @RequiresPermissions("product:channelProductInfo:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(ChannelProductInfo channelProductInfo) {
        PolicyInfo policyInfo = new PolicyInfo();
        policyInfo.setPolicyStatus("1");
        policyInfo.setChannelProductId(channelProductInfo.getId());
        long count = policyInfoService.findCount(policyInfo);
        if (count>0){return renderResult(Global.TRUE, text("该产品下关联有效保单，无法停用！"));}
        channelProductInfo.setStatus(ChannelProductInfo.STATUS_DISABLE);
        channelProductInfoService.updateStatus(channelProductInfo);
        return renderResult(Global.TRUE, text("停用渠道产品成功"));
    }

    /**
     * 启用渠道产品
     */
    @RequiresPermissions("product:channelProductInfo:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(ChannelProductInfo channelProductInfo) {
        channelProductInfo.setStatus(ChannelProductInfo.STATUS_NORMAL);
        channelProductInfoService.updateStatus(channelProductInfo);
        return renderResult(Global.TRUE, text("启用渠道产品成功"));
    }

    /**
     * 删除渠道产品
     */
    @RequiresPermissions("product:channelProductInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(ChannelProductInfo channelProductInfo) {
        PolicyInfo policyInfo = new PolicyInfo();
        policyInfo.setPolicyStatus("1");
        policyInfo.setChannelProductId(channelProductInfo.getId());
        long count = policyInfoService.findCount(policyInfo);
        if (count>0){return renderResult(Global.TRUE, text("该产品下关联有效保单，无法删除！"));}
        channelProductInfoService.delete(channelProductInfo);
        return renderResult(Global.TRUE, text("删除渠道产品成功！"));
    }


    @RequestMapping({"treeData"})
    @ResponseBody
    public List<Map<String, Object>> treeData(String excludeCode, String parentCode, Boolean isAll, String officeTypes, String companyCode, String isShowCode, String isShowFullName, String isLoadUser, String postCode, String roleCode, String ctrlPermi,String corpCode,String flag) {
        List<Map<String, Object>> mapList = ListUtils.newArrayList();
         Office where = new Office();
        if (corpCode==null||"".equals(corpCode)){
            where.getSqlMap().getWhere().andBracket("office_code", QueryType.EQ, parentCode, 1).or("parent_code", QueryType.EQ, parentCode, 1)
                    .or("parent_codes", QueryType.LEFT_LIKE, "0,"+parentCode+",%", 2).or("corp_code", QueryType.LEFT_LIKE, corpCode,3).endBracket();

        }
        if (!"".equals(corpCode)&&corpCode!=null){
            where.getSqlMap().getWhere().andBracket("corp_code", QueryType.EQ, corpCode, 1).endBracket();
        }


        List<Office> list = officeService.findList(where);
        if ("1".equals(flag)){
            //查询维修机构时，将没有绑定网点的机构过滤掉
            if ("0_WXWD001".equals(parentCode)){
                for (int i = list.size()-1 ; i >= 0 ; i--) {
                    Office office = list.get(i);
                    if ("0".equals(office.getTreeLeaf())){
                        list.remove(i);
                    }
                }
                for (int j = list.size()-1 ; j >= 0 ; j--) {
                    Office office = list.get(j);
                    String officeCode = office.getOfficeCode();
                    HtMaintenancePoint infoByJG = maintenancePointService.findInfoByJG(officeCode);
                    if (infoByJG==null){
                        list.remove(j);
                    }
                }
                for (int k = list.size()-1 ; k >= 0 ; k--) {
                    Office office = list.get(k);
                    String officeCode = office.getOfficeCode();
                    HtMaintenancePoint infoByJG = maintenancePointService.findInfoByJG(officeCode);
                    if (infoByJG!=null){
                        if ("0".equals(infoByJG.getWhetherOrder())){
                            //1 是 0否
                            list.remove(k);
                        }
                    }
                }
            }
        }


        for(int i = 0; i < list.size(); ++i) {
            Office e = (Office)list.get(i);
            if ("0".equals(e.getStatus()) && (!StringUtils.isNotBlank(excludeCode) || !e.getId().equals(excludeCode) && !e.getParentCodes().contains("," + excludeCode + ","))) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentCode());
                String name = e.getOfficeName();
                if ("true".equals(isShowFullName) || "1".equals(isShowFullName)) {
                    name = e.getFullName();
                }

                map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getViewCode(), name));
                map.put("title", e.getFullName());
                if (StringUtils.inString(isLoadUser, new String[]{"true", "lazy"})) {
                    map.put("isParent", true);
                    if (StringUtils.equals(isLoadUser, "true")) {
                        List<Map<String, Object>> userList = empUserController.treeData("u_", e.getOfficeCode(), e.getOfficeCode(), companyCode, postCode, roleCode, isAll, isShowCode, ctrlPermi);
                        mapList.addAll(userList);
                    }
                }

                mapList.add(map);
            }
        }

        if (StringUtils.inString(isLoadUser, new String[]{"lazy"}) && StringUtils.isNotBlank(parentCode)) {
            List<Map<String, Object>> userList = this.empUserController.treeData("u_", parentCode, parentCode, companyCode, postCode, roleCode, isAll, isShowCode, ctrlPermi);
            mapList.addAll(userList);
        }

        return mapList;
    }


}