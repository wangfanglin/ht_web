/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbrandmapinfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.htbreakdowninfo.service.HtBreakdownInfoService;
import com.jeesite.modules.htdutyroster.entity.HtDutyRoster;
import org.apache.commons.lang3.StringUtils;
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
import com.jeesite.modules.htbrandmapinfo.entity.HtBrandMapInfo;
import com.jeesite.modules.htbrandmapinfo.service.HtBrandMapInfoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * 品牌映射表Controller
 * @author hongmengzhong
 * @version 2020-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/htbrandmapinfo/htBrandMapInfo")
public class HtBrandMapInfoController extends BaseController {

	@Autowired
	private HtBrandMapInfoService htBrandMapInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private HtBreakdownInfoService htBreakdownInfoService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtBrandMapInfo get(String id, boolean isNewRecord) {
		return htBrandMapInfoService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("htbrandmapinfo:htBrandMapInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtBrandMapInfo htBrandMapInfo, Model model) {
		List<HtBrandInfo> brandList = htBrandInfoService.findList(new HtBrandInfo());
		List<Map<String, Object>> officeList = htBreakdownInfoService.getOfficeList();
		model.addAttribute("brandList", brandList);
		model.addAttribute("officeList", officeList);
		model.addAttribute("htBrandMapInfo", htBrandMapInfo);
		return "modules/htbrandmapinfo/htBrandMapInfoList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htbrandmapinfo:htBrandMapInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtBrandMapInfo> listData(HtBrandMapInfo htBrandMapInfo, HttpServletRequest request, HttpServletResponse response) {
		htBrandMapInfo.setPage(new Page<>(request, response));
		Page<HtBrandMapInfo> page = htBrandMapInfoService.findPage(htBrandMapInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htbrandmapinfo:htBrandMapInfo:view")
	@RequestMapping(value = "form")
	public String form(HtBrandMapInfo htBrandMapInfo, Model model) {
		List<HtBrandInfo> brandList = htBrandInfoService.findList(new HtBrandInfo());
		List<Map<String, Object>> officeList = htBreakdownInfoService.getOfficeList();
		model.addAttribute("officeList", officeList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("htBrandMapInfo", htBrandMapInfo);
		return "modules/htbrandmapinfo/htBrandMapInfoForm";
	}

	/**
	 * 保存品牌映射表
	 */
	@RequiresPermissions("htbrandmapinfo:htBrandMapInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtBrandMapInfo htBrandMapInfo) {
		if (brandMapInfoExist(htBrandMapInfo)) {
			htBrandMapInfoService.save(htBrandMapInfo);
			return renderResult(Global.TRUE, text("保存品牌映射表成功！"));
		}
		return renderResult(Global.FALSE, text("该映射品牌已存在！"));
	}

	/**
	 * 判断选择映射品牌是否存在
	 *
	 * @param htBrandMapInfo
	 * @return
	 */
	private boolean brandMapInfoExist(@Validated HtBrandMapInfo htBrandMapInfo) {
		boolean isTrues = true;
		if (StringUtils.isNotBlank(htBrandMapInfo.getMapBrandId())) {
			HtBrandMapInfo search = new HtBrandMapInfo();
			search.setMapBrandId(htBrandMapInfo.getMapBrandId());
			List<HtBrandMapInfo> list = htBrandMapInfoService.findList(search);
			if (list != null && list.size() > 0) {
				for (HtBrandMapInfo brandMapInfo : list) {
					if (brandMapInfo != null) {
						String distributionId = brandMapInfo.getDistributionId();
						String originalBrand = brandMapInfo.getOriginalBrand();
						String newDistributionId = htBrandMapInfo.getDistributionId();
						String newOriginalBrand = htBrandMapInfo.getOriginalBrand();
						if (distributionId.equals(newDistributionId) && originalBrand.equals(newOriginalBrand)) {
							isTrues = false;
						}
					}
				}
			}
		} else {
			isTrues = false;
		}
		return isTrues;
	}

	/**
	 * 停用品牌映射表
	 */
	@RequiresPermissions("htbrandmapinfo:htBrandMapInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtBrandMapInfo htBrandMapInfo) {
		htBrandMapInfo.setStatus(HtBrandMapInfo.STATUS_DISABLE);
		htBrandMapInfoService.updateStatus(htBrandMapInfo);
		return renderResult(Global.TRUE, text("停用品牌映射表成功"));
	}

	/**
	 * 启用品牌映射表
	 */
	@RequiresPermissions("htbrandmapinfo:htBrandMapInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtBrandMapInfo htBrandMapInfo) {
		htBrandMapInfo.setStatus(HtBrandMapInfo.STATUS_NORMAL);
		htBrandMapInfoService.updateStatus(htBrandMapInfo);
		return renderResult(Global.TRUE, text("启用品牌映射表成功"));
	}

	/**
	 * 删除品牌映射表
	 */
	@RequiresPermissions("htbrandmapinfo:htBrandMapInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtBrandMapInfo htBrandMapInfo) {
		htBrandMapInfoService.delete(htBrandMapInfo);
		return renderResult(Global.TRUE, text("删除品牌映射表成功！"));
	}

	/**
	 * 品牌映射导出功能
	 *
	 * @return
	 */
	@RequiresPermissions("phonemodelinfo:htPhoneModelInfo:view")
	@RequestMapping("exportBrandMap")
	public void exportBrandMap(HtBrandMapInfo htBrandMapInfo, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		List<HtBrandMapInfo> list = htBrandMapInfoService.findList(htBrandMapInfo);//wsUserInfoService.findAgentCouponExcelList(userCouponInfo);
		String fileName = "品牌映射列表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		ExcelExport ee = new ExcelExport("品牌映射列表", HtBrandMapInfo.class);
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
	 * 品牌映射导入功能
	 *
	 * @param file
	 * @return
	 */
	@ResponseBody
	@PostMapping({"importData"})
	public String importData(MultipartFile file) {
		try {
			String message = this.htBrandMapInfoService.importBrandMapData(file);
			return this.renderResult("true", "posfull:" + message);
		} catch (Exception var5) {
			return this.renderResult("false", "posfull:" + var5.getMessage());
		}
	}

	/**
	 * 品牌映射导入功能
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping({"importTemplate"})
	public void importTemplate(HttpServletResponse response) {
		HtBrandMapInfo htBrandMapInfo = new HtBrandMapInfo();
		htBrandMapInfo.setStatus("6");
		List<HtBrandMapInfo> list = htBrandMapInfoService.findList(htBrandMapInfo);
		String fileName = "品牌映射数据模板.xlsx";
		ExcelExport ee = new ExcelExport("品牌映射", HtBrandMapInfo.class, ExcelField.Type.IMPORT, new String[0]);
		Throwable var7 = null;
		try {
			ee.setDataList(list).write(response, fileName);
		} catch (Throwable var16) {
			var7 = var16;
			throw var16;
		} finally {
			if (ee != null) {
				if (var7 != null) {
					try {
						ee.close();
					} catch (Throwable var15) {
						var7.addSuppressed(var15);
					}
				} else {
					ee.close();
				}
			}

		}
	}
}