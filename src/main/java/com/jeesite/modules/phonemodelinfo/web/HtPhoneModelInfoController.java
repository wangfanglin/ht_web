/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.phonemodelinfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.htbreakdowninfo.service.HtBreakdownInfoService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;

/**
 * 机型库表Controller
 * @author hongmengzhong
 * @version 2020-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/phonemodelinfo/htPhoneModelInfo")
public class HtPhoneModelInfoController extends BaseController {

	@Autowired
	private HtPhoneModelInfoService htPhoneModelInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private HtBreakdownInfoService htBreakdownInfoService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtPhoneModelInfo get(String id, boolean isNewRecord) {
		return htPhoneModelInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtPhoneModelInfo htPhoneModelInfo, Model model) {
		List<HtBrandInfo> brandInfoList = htBrandInfoService.getBrandList();
		List<Map<String,Object>> officeList = htBreakdownInfoService.getOfficeList();
		model.addAttribute("brandInfoList", brandInfoList);
		model.addAttribute("officeList", officeList);
		model.addAttribute("htPhoneModelInfo", htPhoneModelInfo);
		return "modules/phonemodelinfo/htPhoneModelInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtPhoneModelInfo> listData(HtPhoneModelInfo htPhoneModelInfo, HttpServletRequest request, HttpServletResponse response) {
		htPhoneModelInfo.setPage(new Page<>(request, response));
		Page<HtPhoneModelInfo> page = htPhoneModelInfoService.findPage(htPhoneModelInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:view")
	@RequestMapping(value = "form")
	public String form(HtPhoneModelInfo htPhoneModelInfo, Model model) {
		if (htPhoneModelInfo.getPriceLow()!=null){
			htPhoneModelInfo.setPriceLow(htPhoneModelInfo.getPriceLow()/100);
			htPhoneModelInfo.setPriceHigh(htPhoneModelInfo.getPriceHigh()/100);
			htPhoneModelInfo.setPhonePrice(htPhoneModelInfo.getPhonePrice()/100);
			htPhoneModelInfo.setScreenPrice(htPhoneModelInfo.getScreenPrice()/100);
		}
		List<HtBrandInfo> brandList =htBrandInfoService.getBrandList();
		List<Map<String,Object>> officeList = htBreakdownInfoService.getOfficeList();
		model.addAttribute("officeList", officeList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("htPhoneModelInfo", htPhoneModelInfo);
		return "modules/phonemodelinfo/htPhoneModelInfoForm";
	}

	/**
	 * 保存机型库表
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtPhoneModelInfo htPhoneModelInfo) {
		try {
			htPhoneModelInfo.setPriceLow(htPhoneModelInfo.getPriceLow()*100);
			htPhoneModelInfo.setPriceHigh(htPhoneModelInfo.getPriceHigh()*100);
			htPhoneModelInfo.setPhonePrice(htPhoneModelInfo.getPhonePrice()*100);
			htPhoneModelInfo.setScreenPrice(htPhoneModelInfo.getScreenPrice()*100);
			htPhoneModelInfoService.save(htPhoneModelInfo);
		} catch (Exception e) {
			e.printStackTrace();

			HtPhoneModelInfo search = new HtPhoneModelInfo();
			search.setModel(htPhoneModelInfo.getModel());
			search.setBrandId(htPhoneModelInfo.getBrandId());
			long count = htPhoneModelInfoService.findCount(search);
			if (count==1){
				return renderResult(Global.FALSE, text("此品牌下型号输入重复，请重新输入！"));
			}else{
				return renderResult(Global.FALSE, text("保存机型库失败！"));
			}
		}
		return renderResult(Global.TRUE, text("保存机型库表成功！"));
	}
	
	/**
	 * 停用机型库表
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtPhoneModelInfo htPhoneModelInfo) {
		htPhoneModelInfo.setStatus(HtPhoneModelInfo.STATUS_DISABLE);
		htPhoneModelInfoService.updateStatus(htPhoneModelInfo);
		return renderResult(Global.TRUE, text("停用机型库表成功"));
	}
	
	/**
	 * 启用机型库表
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtPhoneModelInfo htPhoneModelInfo) {
		htPhoneModelInfo.setStatus(HtPhoneModelInfo.STATUS_NORMAL);
		htPhoneModelInfoService.updateStatus(htPhoneModelInfo);
		return renderResult(Global.TRUE, text("启用机型库表成功"));
	}
	
	/**
	 * 删除机型库表
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtPhoneModelInfo htPhoneModelInfo) {
		htPhoneModelInfoService.delete(htPhoneModelInfo);
		return renderResult(Global.TRUE, text("删除机型库表成功！"));
	}

	/**
	 * 机型库导出功能
	 * @return
	 */
	@RequestMapping("exportPhoneModel")
	public void exportPhoneModel(HtPhoneModelInfo htPhoneModelInfo, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        List<HtPhoneModelInfo> list = htPhoneModelInfoService.findList(htPhoneModelInfo);
		for (HtPhoneModelInfo phoneModelInfo : list) {
			phoneModelInfo.setPriceLow(phoneModelInfo.getPriceLow()/100);
			phoneModelInfo.setPriceHigh(phoneModelInfo.getPriceHigh()/100);
			phoneModelInfo.setPhonePrice(phoneModelInfo.getPhonePrice()/100);
			phoneModelInfo.setScreenPrice(phoneModelInfo.getScreenPrice()/100);
		}
        String fileName = "机型库列表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
        ExcelExport ee = new ExcelExport("机型库列表", HtPhoneModelInfo.class);
        Throwable var8 = null;
        try {
            ee.setDataList(list).write(response, fileName);
        } catch (Throwable var17) {
            var8 = var17;
            throw var17;
        } finally {
            if (ee != null) {
                if (var8 != null) {
                    try {
                        ee.close();
                    } catch (Throwable var16) {
                        var8.addSuppressed(var16);
                    }
                } else {
                    ee.close();
                }
            }
        }
    }


	/**
	 * 根据品牌查出手机型号
	 */
	@RequestMapping(value = "findByPhoneModel")
	@ResponseBody
	public List<HtPhoneModelInfo> findByPhoneModel(HtPhoneModelInfo htPhoneModelInfo) {
		List<HtPhoneModelInfo> list = htPhoneModelInfoService.findList(htPhoneModelInfo);
		return list;
	}
}