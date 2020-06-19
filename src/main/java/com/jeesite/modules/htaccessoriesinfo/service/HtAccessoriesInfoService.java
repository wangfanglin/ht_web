/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htaccessoriesinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesInfo;
import com.jeesite.modules.htaccessoriesinfo.dao.HtAccessoriesInfoDao;

/**
 * 配件表Service
 * @author hongmengzhong
 * @version 2020-02-19
 */
@Service
@Transactional(readOnly=true)
public class HtAccessoriesInfoService extends CrudService<HtAccessoriesInfoDao, HtAccessoriesInfo> {

	@Autowired
	private HtAccessoriesInfoDao htAccessoriesInfoDao;

	/**
	 * 获取单条数据
	 * @param htAccessoriesInfo
	 * @return
	 */
	@Override
	public HtAccessoriesInfo get(HtAccessoriesInfo htAccessoriesInfo) {
		return super.get(htAccessoriesInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htAccessoriesInfo 查询条件
	 * @param htAccessoriesInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtAccessoriesInfo> findPage(HtAccessoriesInfo htAccessoriesInfo) {
		return super.findPage(htAccessoriesInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htAccessoriesInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtAccessoriesInfo htAccessoriesInfo) {
		super.save(htAccessoriesInfo);
	}
	
	/**
	 * 更新状态
	 * @param htAccessoriesInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtAccessoriesInfo htAccessoriesInfo) {
		super.updateStatus(htAccessoriesInfo);
	}
	
	/**
	 * 删除数据
	 * @param htAccessoriesInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtAccessoriesInfo htAccessoriesInfo) {
		super.delete(htAccessoriesInfo);
	}

    public List<HtAccessoriesInfo> getAccessoriesInfoList(HtAccessoriesInfo htAccessoriesInfo) {
		return htAccessoriesInfoDao.getAccessoriesInfoList(htAccessoriesInfo);
    }
}