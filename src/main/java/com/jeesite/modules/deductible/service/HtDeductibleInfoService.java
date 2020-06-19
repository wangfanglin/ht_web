/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.deductible.service;

import java.util.HashMap;
import java.util.List;

import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.bpm.utils.BpmUtils;
import com.jeesite.modules.common.FormStatus;
import com.jeesite.modules.common.FormType;
import com.jeesite.modules.common.ManageStatus;
import com.jeesite.modules.deductible.dao.HtDeductibleInfoHistoryDao;
import com.jeesite.modules.deductible.entity.HtDeductibleInfoHistory;
import com.jeesite.modules.forminfo.dao.HtFormInfoDao;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.receipt.entity.HtReceiptDataHistory;
import com.jeesite.modules.sys.utils.UserUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.deductible.entity.HtDeductibleInfo;
import com.jeesite.modules.deductible.dao.HtDeductibleInfoDao;

/**
 * 自付款信息表Service
 * @author zhaohaifeng
 * @version 2020-03-24
 */
@Service
@Transactional(readOnly=true)
public class HtDeductibleInfoService extends CrudService<HtDeductibleInfoDao, HtDeductibleInfo> {
	@Autowired
	private HtFormInfoDao formInfoDao;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HtDeductibleInfoHistoryDao deductibleInfoHistoryDao;
	@Autowired
	private HtHistoryService historyService;
	/**
	 * 获取单条数据
	 * @param htDeductibleInfo
	 * @return
	 */
	@Override
	public HtDeductibleInfo get(HtDeductibleInfo htDeductibleInfo) {
		return super.get(htDeductibleInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htDeductibleInfo 查询条件
	 * @return
	 */
	@Override
	public Page<HtDeductibleInfo> findPage(HtDeductibleInfo htDeductibleInfo) {
		return super.findPage(htDeductibleInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htDeductibleInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtDeductibleInfo htDeductibleInfo) {
			super.save(htDeductibleInfo);
	}
	
	/**
	 * 更新状态
	 * @param htDeductibleInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtDeductibleInfo htDeductibleInfo) {
		super.updateStatus(htDeductibleInfo);
	}
	
	/**
	 * 删除数据
	 * @param htDeductibleInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtDeductibleInfo htDeductibleInfo) {
		super.delete(htDeductibleInfo);
	}
	
}