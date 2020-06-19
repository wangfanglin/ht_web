/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.number.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.number.dao.HtDateTableDao;
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
import com.jeesite.modules.number.entity.HtDateTable;
import com.jeesite.modules.number.service.HtDateTableService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.jeesite.common.lang.DateUtils.formatDate;

/**
 * 数量表Controller
 * @author baixue
 * @version 2020-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/number/htDateTable")
public class HtDateTableController extends BaseController {
	private static final String  TYPE_PHONE_NUM="3";
	private static final String  REFUSE_NUM="2";
	private static final String  PUSH_DATA_NUM="1";


	@Autowired
	private HtDateTableService htDateTableService;
	@Autowired
	private HtDateTableDao htDateTableDao;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtDateTable get(String id, boolean isNewRecord) {
		return htDateTableService.get(id, isNewRecord);
	}

	/**
	      * 获取当年的第一天
	      * @param year
	      * @return
	      */
	public static Date getCurrYearFirst(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	      * 获取某年第一天日期
	      * @param year 年份
	      * @return Date
	      */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst =  calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("number:htDateTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtDateTable htDateTable, Model model,String type) {
		if( TYPE_PHONE_NUM.equals(htDateTable.getType())){
			htDateTable.setType(type);
			if (htDateTable.getStartTime() == null && htDateTable.getEndTime() == null) {
				String time=formatDate(getCurrYearFirst());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				htDateTable.setStartTime(date);
				htDateTable.setEndTime(new Date());
			}
			model.addAttribute("htDateTable", htDateTable);
			return "modules/panel/htPhoneNumList";
		}
		if(REFUSE_NUM.equals(htDateTable.getType())){
			htDateTable.setType(type);
			if (htDateTable.getStartTime() == null && htDateTable.getEndTime() == null) {
				String time=formatDate(getCurrYearFirst());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				htDateTable.setStartTime(date);
				htDateTable.setEndTime(new Date());
			}
			model.addAttribute("htDateTable", htDateTable);
			return "modules/panel/htRefuseList";
		}
		if(PUSH_DATA_NUM.equals(htDateTable.getType())){
			htDateTable.setType(type);
			if (htDateTable.getStartTime() == null && htDateTable.getEndTime() == null) {
				String time=formatDate(getCurrYearFirst());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				htDateTable.setStartTime(date);
				htDateTable.setEndTime(new Date());
			}
			model.addAttribute("htDateTable", htDateTable);
			return "modules/panel/htOverList";
		}
        else {
            htDateTable.setType(type);
			if (htDateTable.getStartTime() == null && htDateTable.getEndTime() == null) {
				String time=formatDate(getCurrYearFirst());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				htDateTable.setStartTime(date);
				htDateTable.setEndTime(new Date());
			}
            model.addAttribute("htDateTable", htDateTable);
            return "modules/panel/htPushDataList";
        }
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("number:htDateTable:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtDateTable> listData(HtDateTable htDateTable, HttpServletRequest request, HttpServletResponse response) {
		if(PUSH_DATA_NUM.equals(htDateTable.getType())){
			Page<HtDateTable> page =  new Page<>(request, response);
			htDateTable.setPage(page);
			List<HtDateTable> list=htDateTableDao.overTime(htDateTable);
			page.setList(list);
			return page;
		}
		if(REFUSE_NUM.equals(htDateTable.getType())){
            Page<HtDateTable> page =  new Page<>(request, response);
            htDateTable.setPage(page);
            List<HtDateTable> list=htDateTableDao.refuseDate(htDateTable);
            page.setList(list);
            return page;
        }
		if(TYPE_PHONE_NUM.equals(htDateTable.getType())
		){
			Page<HtDateTable> page =  new Page<>(request, response);
			htDateTable.setPage(page);
			List<HtDateTable> list=htDateTableDao.phoneNum(htDateTable);
			page.setList(list);
			return page;
		}
		else {
            htDateTable.setPage(new Page<>(request, response));
            Page<HtDateTable> page = htDateTableService.findPage(htDateTable);
            return page;
        }
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("number:htDateTable:view")
	@RequestMapping(value = "form")
	public String form(HtDateTable htDateTable, Model model) {
		model.addAttribute("htDateTable", htDateTable);
		return "modules/number/htDateTableForm";
	}

	/**
	 * 保存数量表
	 */
	@RequiresPermissions("number:htDateTable:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtDateTable htDateTable) {
		htDateTableService.save(htDateTable);
		return renderResult(Global.TRUE, text("保存数量表成功！"));
	}
	
	/**
	 * 删除数量表
	 */
	@RequiresPermissions("number:htDateTable:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtDateTable htDateTable) {
		htDateTableService.delete(htDateTable);
		return renderResult(Global.TRUE, text("删除数量表成功！"));
	}
	
}