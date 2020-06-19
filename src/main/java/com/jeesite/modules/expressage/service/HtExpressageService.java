/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.expressage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.expressage.entity.HtExpressage;
import com.jeesite.modules.expressage.dao.HtExpressageDao;

/**
 * 快递渠道Service
 * @author tangchao
 * @version 2020-02-22
 */
@Service
@Transactional(readOnly=true)
public class HtExpressageService extends CrudService<HtExpressageDao, HtExpressage> {
	
	/**
	 * 获取单条数据
	 * @param htExpressage
	 * @return
	 */
	@Override
	public HtExpressage get(HtExpressage htExpressage) {
		return super.get(htExpressage);
	}
	
	/**
	 * 查询分页数据
	 * @param htExpressage 查询条件
	 * @param htExpressage.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtExpressage> findPage(HtExpressage htExpressage) {
		return super.findPage(htExpressage);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htExpressage
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtExpressage htExpressage) {
		super.save(htExpressage);
	}
	
	/**
	 * 更新状态
	 * @param htExpressage
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtExpressage htExpressage) {
		super.updateStatus(htExpressage);
	}
	
	/**
	 * 删除数据
	 * @param htExpressage
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtExpressage htExpressage) {
		super.delete(htExpressage);
	}
	
}