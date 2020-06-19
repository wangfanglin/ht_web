/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.history.service;

import java.util.HashSet;
import java.util.List;

import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.dao.HtHistoryDao;

/**
 * 操作历史总表Service
 * @author tangchao
 * @version 2020-03-02
 */
@Service
@Transactional(readOnly=true)
public class HtHistoryService extends CrudService<HtHistoryDao, HtHistory> {
	
	/**
	 * 获取单条数据
	 * @param htHistory
	 * @return
	 */
	@Override
	public HtHistory get(HtHistory htHistory) {
		return super.get(htHistory);
	}
	
	/**
	 * 查询分页数据
	 * @param htHistory 查询条件
	 * @param htHistory.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtHistory> findPage(HtHistory htHistory) {
		return super.findPage(htHistory);
	}

	public Page<HtHistory> findList(HtHistory htHistory,int pageNo,int pageNum) {
			return super.findPage(htHistory);
	}

	
	/**
	 * 保存数据（插入或更新）
	 * @param htHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtHistory htHistory) {
		super.save(htHistory);
	}
	
	/**
	 * 更新状态
	 * @param htHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtHistory htHistory) {
		super.updateStatus(htHistory);
	}
	
	/**
	 * 删除数据
	 * @param htHistory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtHistory htHistory) {
		super.delete(htHistory);
	}

	public String findUserByOffer(String intermediaryServiceId) {
			return dao.findUserByOffer(intermediaryServiceId);
	}

	/**
	 * 根据用户查询历史的可见度
	 * @param userCode
	 * @return
	 */
	public List<String> findVisible(String userCode) {
		return dao.findVisible(userCode);
	}

	/**
	 * 分页查询历史
	 * @param workOrderId
	 * @param setList
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<HtHistory> findListByType(String workOrderId, HashSet<String> setList) {
		List<HtHistory> list = dao.findListByType(workOrderId, setList);
		for (HtHistory htHistory : list) {
			htHistory.setDisposeUserId(UserUtils.get(htHistory.getDisposeUserId()).getUserName());
		}
		return list;
	}
}