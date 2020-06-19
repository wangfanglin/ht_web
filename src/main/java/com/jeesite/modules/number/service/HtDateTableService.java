/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.number.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.number.entity.HtDateTable;
import com.jeesite.modules.number.dao.HtDateTableDao;

/**
 * 数量表Service
 * @author baixue
 * @version 2020-04-20
 */
@Service
@Transactional(readOnly=true)
public class HtDateTableService extends CrudService<HtDateTableDao, HtDateTable> {

	/**
	 * 获取单条数据
	 * @param htDateTable
	 * @return
	 */
	@Override
	public HtDateTable get(HtDateTable htDateTable) {
		return super.get(htDateTable);
	}

	/**
	 * 查询分页数据
	 * @param htDateTable 查询条件
	 * @param htDateTable.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtDateTable> findPage(HtDateTable htDateTable) {
		return super.findPage(htDateTable);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htDateTable
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtDateTable htDateTable) {
		super.save(htDateTable);
	}
	
	/**
	 * 更新状态
	 * @param htDateTable
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtDateTable htDateTable) {
		super.updateStatus(htDateTable);
	}
	
	/**
	 * 删除数据
	 * @param htDateTable
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtDateTable htDateTable) {
		super.delete(htDateTable);
	}
	
}