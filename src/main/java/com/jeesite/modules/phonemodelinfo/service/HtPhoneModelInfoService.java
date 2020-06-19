/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.phonemodelinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.dao.HtPhoneModelInfoDao;

/**
 * 机型库表Service
 * @author hongmengzhong
 * @version 2020-02-17
 */
@Service
@Transactional(readOnly=true)
public class HtPhoneModelInfoService extends CrudService<HtPhoneModelInfoDao, HtPhoneModelInfo> {

	@Autowired
	private HtPhoneModelInfoDao htPhoneModelInfoDao;
	/**
	 * 获取单条数据
	 * @param htPhoneModelInfo
	 * @return
	 */
	@Override
	public HtPhoneModelInfo get(HtPhoneModelInfo htPhoneModelInfo) {
		return super.get(htPhoneModelInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htPhoneModelInfo 查询条件
	 * @param htPhoneModelInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtPhoneModelInfo> findPage(HtPhoneModelInfo htPhoneModelInfo) {
		return super.findPage(htPhoneModelInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htPhoneModelInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtPhoneModelInfo htPhoneModelInfo) {
		super.save(htPhoneModelInfo);
	}
	
	/**
	 * 更新状态
	 * @param htPhoneModelInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtPhoneModelInfo htPhoneModelInfo) {
		super.updateStatus(htPhoneModelInfo);
	}
	
	/**
	 * 删除数据
	 * @param htPhoneModelInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtPhoneModelInfo htPhoneModelInfo) {
		super.delete(htPhoneModelInfo);
	}

	/**
	 * 查出产品关联的机型ID
	 * @param productInfoId
	 * @return
	 */
	public String findIdsByProductId(String productInfoId) {
		List<String> ids = dao.findIdsByProductId(productInfoId);
		StringBuffer stringBuffer = new StringBuffer();
		for (String id : ids) {
			stringBuffer.append(",").append(id);
		}
		return stringBuffer.toString();
	}


}