/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htuserapplyinfo.service;

import java.util.List;
import java.util.Map;

import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.dao.HtUserApplyInfoDao;

/**
 * 用户在线申请表Service
 * @author tangchao
 * @version 2020-03-10
 */
@Service
@Transactional(readOnly=true)
public class HtUserApplyInfoService extends CrudService<HtUserApplyInfoDao, HtUserApplyInfo> {
	
	/**
	 * 获取单条数据
	 * @param htUserApplyInfo
	 * @return
	 */
	@Override
	public HtUserApplyInfo get(HtUserApplyInfo htUserApplyInfo) {
		return super.get(htUserApplyInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htUserApplyInfo 查询条件
	 * @param htUserApplyInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtUserApplyInfo> findPage(HtUserApplyInfo htUserApplyInfo) {
		return super.findPage(htUserApplyInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htUserApplyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtUserApplyInfo htUserApplyInfo) {
		super.save(htUserApplyInfo);
	}
	
	/**
	 * 更新状态
	 * @param htUserApplyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtUserApplyInfo htUserApplyInfo) {
		super.updateStatus(htUserApplyInfo);
	}
	
	/**
	 * 删除数据
	 * @param htUserApplyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtUserApplyInfo htUserApplyInfo) {
		super.delete(htUserApplyInfo);
	}

	/**
	 * 根据工单ID 查新在线申请信息
	 * @param formId
	 */
	public HtUserApplyInfo findByFormId(String formId) {
		return dao.findByFormId(formId);
	}

	public Map<String, Object> finfArea(String areaId) {
		return dao.finfArea(areaId);
	}

	/**
	 * 查询所有损坏部位图片
	 * @param id
	 * @return
	 */
	public Map<String,String> findPartImgs(String id) {
		return dao.findPartImgs(id);
	}

	/**
	 * 加入开启已申请工单的日志
	 * @param applyId
	 */
	public void insertApplyLog(String applyId) {
		dao.insertApplyLog(applyId);
	}

	/**
	 * 查询有无已申请工单的日志
	 * @param applyId
	 * @return
	 */
	public int check(String applyId) {
		return dao.check(applyId);
	}

	/**
	 * 查询申请信息的userID
	 * @param policyId
	 * @return
	 */
	public String findUserByPolicy(String policyId) {
		return dao.findUserByPolicy(policyId);
	}


	public HtUserApplyInfo getByClmNo(HtUserApplyInfo htUserApplyInfo) {
		return dao.getByClmNo(htUserApplyInfo);
	}
}