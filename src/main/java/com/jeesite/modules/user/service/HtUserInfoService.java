/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.user.entity.HtUserInfo;
import com.jeesite.modules.user.dao.HtUserInfoDao;

/**
 * ht_user_infoService
 * @author lichao
 * @version 2020-04-20
 */
@Service
@Transactional(readOnly=true)
public class HtUserInfoService extends CrudService<HtUserInfoDao, HtUserInfo> {
	
	/**
	 * 获取单条数据
	 * @param htUserInfo
	 * @return
	 */
	@Override
	public HtUserInfo get(HtUserInfo htUserInfo) {
		return super.get(htUserInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htUserInfo 查询条件
	 * @param htUserInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtUserInfo> findPage(HtUserInfo htUserInfo) {
		return super.findPage(htUserInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtUserInfo htUserInfo) {
		super.save(htUserInfo);
	}
	
	/**
	 * 更新状态
	 * @param htUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtUserInfo htUserInfo) {
		super.updateStatus(htUserInfo);
	}
	
	/**
	 * 删除数据
	 * @param htUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtUserInfo htUserInfo) {
		super.delete(htUserInfo);
	}
	
}