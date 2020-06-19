/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.receipt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import com.jeesite.modules.bohai.entity.PolicyDamageParameter;
import com.jeesite.modules.bohai.entity.Result;
import com.jeesite.modules.bohai.entity.ResultStatus;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.htuserapplyinfo.web.HtUserApplyInfoController;
import com.jeesite.modules.receipt.dao.HtReceiptDataDao;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.utils.AreaUtils;
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
import com.jeesite.modules.receipt.entity.HtReceiptData;
import com.jeesite.modules.receipt.service.HtReceiptDataService;

import java.util.Map;

/**
 * 收款人信息表Controller
 *
 * @author zhaohaifeng
 * @version 2020-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/receipt/htReceiptData")
public class HtReceiptDataController extends BaseController {

    @Autowired
    private HtReceiptDataService htReceiptDataService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private HtClaimSettlementFormService htClaimSettlementFormService;


    /**
     * 获取数据
     */
    @ModelAttribute
    public HtReceiptData get(String id, boolean isNewRecord) {
        return htReceiptDataService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("receipt:htReceiptData:view")
    @RequestMapping(value = {"list", ""})
    public String list(HtReceiptData htReceiptData, Model model) {
        model.addAttribute("htReceiptData", htReceiptData);
        return "modules/receipt/htReceiptDataList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("receipt:htReceiptData:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<HtReceiptData> listData(HtReceiptData htReceiptData, HttpServletRequest request, HttpServletResponse response) {
        htReceiptData.setPage(new Page<>(request, response));
        Page<HtReceiptData> page = htReceiptDataService.findPage(htReceiptData);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequestMapping(value = "form")
    public String form(HtReceiptData htReceiptData, Model model) {
        BpmParams bpm = htReceiptData.getBpm();
        HtFormInfo htFormInfo = htReceiptData.getHtFormInfo();
        String formId = htFormInfo.getId();
         htReceiptData = htReceiptDataService.findByFormId(formId);
        htReceiptData.setHtFormInfo(new HtFormInfo(formId));
        htReceiptData.setBpm(bpm);

        HtClaimSettlementForm claimSettlementForm = htClaimSettlementFormService.getByFormId(formId);
        htReceiptData.setPayeeIdNumber(claimSettlementForm.getIdNumber());
        String provinceCode = htReceiptData.getProvinceCode();
        Area area = areaService.get(provinceCode);
        String areaName = area.getAreaName();
        htReceiptData.setProvinceCode(areaName);
        htReceiptData.setCityCode(areaService.get(htReceiptData.getCityCode()).getAreaName());
        htReceiptData.setDistrictCode(areaService.get(htReceiptData.getDistrictCode()).getAreaName());


        model.addAttribute("htReceiptData", htReceiptData);
        model.addAttribute("commonFromId", formId);
        model.addAttribute("commonApply", true);
        model.addAttribute("commonApplyId", formId);
        return "modules/receipt/htReceiptDataForm";
    }

    /**
     * 保存收款人信息表
     */
    @RequiresPermissions("receipt:htReceiptData:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated HtReceiptData htReceiptData) {
        String status = htReceiptData.getStatus();
		if ("0".equals(status)){
			return renderResult(Global.FALSE, text("此用户还未填写信息！"));
		}
        try {
            htReceiptDataService.save(htReceiptData);
            return renderResult(Global.TRUE, text("保存收款人信息表成功！"));
        } catch (Exception e) {
            e.printStackTrace();
            if ("101".equals(e.getMessage())) {
                return renderResult(Global.FALSE, text("查勘接口调用失败！"));
            }
            if ("202".equals(e.getMessage())) {
                return renderResult(Global.FALSE, text("收款人信息增加接口调用失败！"));
            }
            return renderResult(Global.FALSE, text("保存收款人信息表失败！"));
        }
    }
}