/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bh.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bh.entity.BhFaultplandict;
import com.jeesite.modules.bh.dao.BhFaultplandictDao;

/**
 * 渤海方案库Service
 * @author lichao
 * @version 2020-04-21
 */
@Service
@Transactional(readOnly=true)
public class BhFaultplandictService extends CrudService<BhFaultplandictDao, BhFaultplandict> {
	
	/**
	 * 获取单条数据
	 * @param bhFaultplandict
	 * @return
	 */
	@Override
	public BhFaultplandict get(BhFaultplandict bhFaultplandict) {
		return super.get(bhFaultplandict);
	}
	
	/**
	 * 查询分页数据
	 * @param bhFaultplandict 查询条件
	 * @param bhFaultplandict.page 分页对象
	 * @return
	 */
	@Override
	public Page<BhFaultplandict> findPage(BhFaultplandict bhFaultplandict) {
		return super.findPage(bhFaultplandict);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bhFaultplandict
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BhFaultplandict bhFaultplandict) {
		super.save(bhFaultplandict);
	}
	
	/**
	 * 更新状态
	 * @param bhFaultplandict
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BhFaultplandict bhFaultplandict) {
		super.updateStatus(bhFaultplandict);
	}
	
	/**
	 * 删除数据
	 * @param bhFaultplandict
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BhFaultplandict bhFaultplandict) {
		super.delete(bhFaultplandict);
	}
	
}