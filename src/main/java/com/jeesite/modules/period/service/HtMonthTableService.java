/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.period.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.period.entity.HtMonthTable;
import com.jeesite.modules.period.dao.HtMonthTableDao;
import org.springframework.util.CollectionUtils;

/**
 * 月份表Service
 * @author baixue
 * @version 2020-04-16
 */
@Service
@Transactional(readOnly=true)
public class HtMonthTableService extends CrudService<HtMonthTableDao, HtMonthTable> {
	private HtMonthTable htMonthTable;
	private HtMonthTableDao htMonthTableDao;
	/**
	 * 获取单条数据
	 * @param htMonthTable
	 * @return
	 */
	@Override
	public HtMonthTable get(HtMonthTable htMonthTable) {
		return super.get(htMonthTable);
	}

	/**
	 * 查询分页数据
	 * @param htMonthTable 查询条件
	 * @param htMonthTable.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtMonthTable> findPage(HtMonthTable htMonthTable) {
		return super.findPage(htMonthTable);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htMonthTable
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtMonthTable htMonthTable) {
		super.save(htMonthTable);
	}
	
	/**
	 * 更新状态
	 * @param htMonthTable
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtMonthTable htMonthTable) {
		super.updateStatus(htMonthTable);
	}
	
	/**
	 * 删除数据
	 * @param htMonthTable
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtMonthTable htMonthTable) {
		super.delete(htMonthTable);
	}
	
}