/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbreakdowninfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.util.CommaJointUtil;
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
import com.jeesite.modules.htbreakdowninfo.entity.HtBreakdownInfo;
import com.jeesite.modules.htbreakdowninfo.service.HtBreakdownInfoService;

import java.util.*;

/**
 * 维修故障表Controller
 * @author hongmengzhong
 * @version 2020-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/htbreakdowninfo/htBreakdownInfo")
public class HtBreakdownInfoController extends BaseController {

	@Autowired
	private HtBreakdownInfoService htBreakdownInfoService;
	@Autowired
	private HtAssemblyUnitService htAssemblyUnitService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtBreakdownInfo get(String id, boolean isNewRecord) {
		return htBreakdownInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htbreakdowninfo:htBreakdownInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtBreakdownInfo htBreakdownInfo, Model model) {
		model.addAttribute("htBreakdownInfo", htBreakdownInfo);
		return "modules/htbreakdowninfo/htBreakdownInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htbreakdowninfo:htBreakdownInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtBreakdownInfo> listData(HtBreakdownInfo htBreakdownInfo, HttpServletRequest request, HttpServletResponse response) {
		Page<HtBreakdownInfo> page = new Page<>(request, response);
		htBreakdownInfo.setPage(page);
		List<HtBreakdownInfo> htBreakdownInfoList = htBreakdownInfoService.findAssemblyConcat(htBreakdownInfo);
		page.setList(htBreakdownInfoList);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htbreakdowninfo:htBreakdownInfo:view")
	@RequestMapping(value = "form")
	public String form(HtBreakdownInfo htBreakdownInfo, Model model) {
		if(StringUtils.isNotBlank(htBreakdownInfo.getId())){
			List<Map<String,Object>> breakDownAssemblyList = htBreakdownInfoService.getBreakDownAssemblyList(htBreakdownInfo.getId());
			List<String> zhuList = new ArrayList<String>();
			List<String> fuList = new ArrayList<String>();
			if (breakDownAssemblyList!=null && breakDownAssemblyList.size()>0){
				for (Map<String, Object> map : breakDownAssemblyList) {
					if ("1".equals(map.get("is_zf"))){
						zhuList.add(map.get("assembly_id").toString());
					}else {
						fuList.add(map.get("assembly_id").toString());
					}
				}
			}
			htBreakdownInfo.setUnitZhuStr(CommaJointUtil.commaJointString(zhuList));
			htBreakdownInfo.setUnitFuStr(CommaJointUtil.commaJointString(fuList));
		}
		List<HtAssemblyUnit> unitZhuList = htAssemblyUnitService.findListByStart("1");
		List<HtAssemblyUnit> unitFuList = htAssemblyUnitService.findListByStart("0");
		model.addAttribute("unitZhuList", unitZhuList);
		model.addAttribute("unitFuList", unitFuList);
		model.addAttribute("htBreakdownInfo", htBreakdownInfo);
		return "modules/htbreakdowninfo/htBreakdownInfoForm";
	}

	/**
	 * 保存维修故障表
	 */
	@RequiresPermissions("htbreakdowninfo:htBreakdownInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtBreakdownInfo htBreakdownInfo) {
		htBreakdownInfoService.save(htBreakdownInfo);
		List<String> unitZhuList = Arrays.asList(htBreakdownInfo.getUnitZhuStr().split(","));
		List<String> unitFuList = Arrays.asList(htBreakdownInfo.getUnitFuStr().split(","));
		String breakDownInfoId = htBreakdownInfo.getId();
		List<Map<String, Object>> middleInfo = new ArrayList<Map<String, Object>>();
		//先删除在 插入中间表
		htBreakdownInfoService.delBreakDownMiddle(breakDownInfoId);
		if (StringUtils.isNotBlank(htBreakdownInfo.getUnitZhuStr())){
			for (String unitZhuId : unitZhuList) {
				Map<String, Object> zhuMap = new HashMap<>();
				zhuMap.put("breakdown_id", breakDownInfoId);
				zhuMap.put("is_zf", "1");
				zhuMap.put("assembly_id", unitZhuId);
				middleInfo.add(zhuMap);
			}
		}
		if (StringUtils.isNotBlank(htBreakdownInfo.getUnitFuStr())){
			for (String unitFuId : unitFuList) {
				Map<String,Object> fuMap = new HashMap<>();
				fuMap.put("breakdown_id",breakDownInfoId);
				fuMap.put("is_zf","0");
				fuMap.put("assembly_id",unitFuId);
				middleInfo.add(fuMap);
			}
		}
		htBreakdownInfoService.saveBreakDownMiddle(middleInfo);
		return renderResult(Global.TRUE, text("保存维修故障表成功！"));
	}
	
	/**
	 * 停用维修故障表
	 */
	@RequiresPermissions("htbreakdowninfo:htBreakdownInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtBreakdownInfo htBreakdownInfo) {
		htBreakdownInfo.setStatus(HtBreakdownInfo.STATUS_DISABLE);
		htBreakdownInfoService.updateStatus(htBreakdownInfo);
		return renderResult(Global.TRUE, text("停用维修故障表成功"));
	}
	
	/**
	 * 启用维修故障表
	 */
	@RequiresPermissions("htbreakdowninfo:htBreakdownInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtBreakdownInfo htBreakdownInfo) {
		htBreakdownInfo.setStatus(HtBreakdownInfo.STATUS_NORMAL);
		htBreakdownInfoService.updateStatus(htBreakdownInfo);
		return renderResult(Global.TRUE, text("启用维修故障表成功"));
	}
	
	/**
	 * 删除维修故障表
	 */
	@RequiresPermissions("htbreakdowninfo:htBreakdownInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtBreakdownInfo htBreakdownInfo) {
		htBreakdownInfoService.delete(htBreakdownInfo);
		return renderResult(Global.TRUE, text("删除维修故障表成功！"));
	}


	@RequestMapping(value = "getmaintainId")
	@ResponseBody
	public Map<String,String> getmaintainId(String zhuidStr,String fuidStr) {
		Map<String,String> returnMap = new HashMap<>();
		if (StringUtils.isNotBlank(zhuidStr)&&StringUtils.isNotBlank(fuidStr)){
			List<String> listzhu = Arrays.asList(zhuidStr.split(","));
			List<String> listfu = Arrays.asList(fuidStr.split(","));
			List<String> allInfo = Lists.newArrayList();
			allInfo.addAll(listzhu);allInfo.addAll(listfu);
			List<Map<String, Object>> mapList = htBreakdownInfoService.getBreakDownInfo(allInfo.size());
			for (Map<String, Object> objectMap : mapList) {
				String dbStr = (String)objectMap.get("str");
				String breakDownid = (String) objectMap.get("breakdown_id");
				if (StringUtils.isNotBlank(dbStr) && StringUtils.isNotBlank(breakDownid)){
					int index = 0;
					boolean isTrue = true;
					for (String zhufuStr : allInfo) {
						if (isTrue){
							index++;
							isTrue = dbStr.indexOf(zhufuStr)!=-1?true:false;
						}else {
							break;
						}
					}
					if (isTrue){
						returnMap.put("status","success");
						returnMap.put("data",breakDownid);
					}
				}
				System.out.println(breakDownid+"  "+dbStr);
			}
		}else{
			returnMap.put("status","error");
			returnMap.put("data","1");
		}
		return returnMap;
	}
}