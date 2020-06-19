/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.period.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jeesite.modules.period.dao.HtMonthTableDao;
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
import com.jeesite.modules.period.entity.HtMonthTable;
import com.jeesite.modules.period.service.HtMonthTableService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.jeesite.common.lang.DateUtils.formatDate;

/**
 * 月份表Controller
 *
 * @author baixue
 * @version 2020-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/period/htMonthTable")
public class HtMonthTableController extends BaseController {
    private static final String  PRICES_DATA_NUM="1";

    @Autowired
    private HtMonthTableService htMonthTableService;
    @Autowired
    private HtMonthTableDao htMonthTableDao;

    /**
     * 获取数据
     */
    @ModelAttribute
    public HtMonthTable get(String id, boolean isNewRecord) {
        return htMonthTableService.get(id, isNewRecord);
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
    @RequiresPermissions("period:htMonthTable:view")
    @RequestMapping(value = {"list", ""})
    public String list(HtMonthTable htMonthTable, Model model, String type) {
            if (PRICES_DATA_NUM.equals(htMonthTable.getType())) {
                htMonthTable.setType(type);
                if (htMonthTable.getStartTime() == null && htMonthTable.getEndTime() == null) {
                String time=formatDate(getCurrYearFirst());
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                htMonthTable.setStartTime(date);
                htMonthTable.setEndTime(new Date());

            }
            model.addAttribute("htMonthTable", htMonthTable);
        return "modules/panel/htPricesTableList";
            }else{
                htMonthTable.setType(type);
                if (htMonthTable.getStartTime() == null && htMonthTable.getEndTime() == null) {
                    String time=formatDate(getCurrYearFirst());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    htMonthTable.setStartTime(date);
                    htMonthTable.setEndTime(new Date());

                }
                model.addAttribute("htMonthTable", htMonthTable);
                return "modules/panel/htPeriodTableList";
            }
    }


    /**
     * 查询列表数据
     */
    @RequiresPermissions("period:htMonthTable:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<HtMonthTable> listData(HtMonthTable htMonthTable, HttpServletRequest request, HttpServletResponse response) {
        if (PRICES_DATA_NUM.equals(htMonthTable.getType())) {
            Page<HtMonthTable> page =  new Page<>(request, response);
            htMonthTable.setPage(page);
            List<HtMonthTable> list = htMonthTableDao.pricesList(htMonthTable);
            page.setList(list);
            return page;
        }else{
            Page<HtMonthTable> page = (new Page<>(request, response));
            htMonthTable.setPage(page);
            List<HtMonthTable> list= htMonthTableService.findList(htMonthTable);
            page.setList(list);
            return page;
        }

    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("period:htMonthTable:view")
    @RequestMapping(value = "form")
    public String form(HtMonthTable htMonthTable, Model model) {
        model.addAttribute("htMonthTable", htMonthTable);
        return "modules/period/htMonthTableForm";
    }

    /**
     * 保存月份表
     */
    @RequiresPermissions("period:htMonthTable:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated HtMonthTable htMonthTable) {
        htMonthTableService.save(htMonthTable);
        return renderResult(Global.TRUE, text("保存月份表成功！"));
    }

    /**
     * 删除月份表
     */
    @RequiresPermissions("period:htMonthTable:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(HtMonthTable htMonthTable) {
        htMonthTableService.delete(htMonthTable);
        return renderResult(Global.TRUE, text("删除月份表成功！"));
    }

}