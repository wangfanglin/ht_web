/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bh.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bh.entity.BhFormInfo;
import com.jeesite.modules.bh.dao.BhFormInfoDao;

/**
 * bh_form_infoService
 * @author lichao
 * @version 2020-04-20
 */
@Service
@Transactional(readOnly=true)
public class BhFormInfoService extends CrudService<BhFormInfoDao, BhFormInfo> {
	
	/**
	 * 获取单条数据
	 * @param bhFormInfo
	 * @return
	 */
	@Override
	public BhFormInfo get(BhFormInfo bhFormInfo) {
		return super.get(bhFormInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param bhFormInfo 查询条件
	 * @param bhFormInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<BhFormInfo> findPage(BhFormInfo bhFormInfo) {
		return super.findPage(bhFormInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bhFormInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BhFormInfo bhFormInfo) {
		super.save(bhFormInfo);
	}
	
	/**
	 * 更新状态
	 * @param bhFormInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BhFormInfo bhFormInfo) {
		super.updateStatus(bhFormInfo);
	}
	
	/**
	 * 删除数据
	 * @param bhFormInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BhFormInfo bhFormInfo) {
		super.delete(bhFormInfo);
	}
	
}