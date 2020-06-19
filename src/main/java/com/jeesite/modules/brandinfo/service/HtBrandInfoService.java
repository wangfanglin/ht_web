/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.brandinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.dao.HtBrandInfoDao;

/**
 * 设备品牌表Service
 * @author hongmengzhong
 * @version 2020-02-17
 */
@Service
@Transactional(readOnly=true)
public class HtBrandInfoService extends CrudService<HtBrandInfoDao, HtBrandInfo> {

	@Autowired
	private HtBrandInfoDao htBrandInfoDao;
	/**
	 * 获取单条数据
	 * @param htBrandInfo
	 * @return
	 */
	@Override
	public HtBrandInfo get(HtBrandInfo htBrandInfo) {
		return super.get(htBrandInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htBrandInfo 查询条件
	 * @param htBrandInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtBrandInfo> findPage(HtBrandInfo htBrandInfo) {
		return super.findPage(htBrandInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htBrandInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtBrandInfo htBrandInfo) {
		super.save(htBrandInfo);
	}
	
	/**
	 * 更新状态
	 * @param htBrandInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtBrandInfo htBrandInfo) {
		super.updateStatus(htBrandInfo);
	}
	
	/**
	 * 删除数据
	 * @param htBrandInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtBrandInfo htBrandInfo) {
		super.delete(htBrandInfo);
	}

	public List<HtBrandInfo> getBrandList() {
		return htBrandInfoDao.getBrandList();
	}
}