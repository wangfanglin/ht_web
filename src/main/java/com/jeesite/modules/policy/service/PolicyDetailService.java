/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.dao.PolicyDetailDao;

/**
 * 保单详情表Service
 * @author zhaohaifeng
 * @version 2020-04-13
 */
@Service
@Transactional(readOnly=true)
public class PolicyDetailService extends CrudService<PolicyDetailDao, PolicyDetail> {
	
	/**
	 * 获取单条数据
	 * @param policyDetail
	 * @return
	 */
	@Override
	public PolicyDetail get(PolicyDetail policyDetail) {
		return super.get(policyDetail);
	}
	
	/**
	 * 查询分页数据
	 * @param policyDetail 查询条件
	 * @param policyDetail.page 分页对象
	 * @return
	 */
	@Override
	public Page<PolicyDetail> findPage(PolicyDetail policyDetail) {
		return super.findPage(policyDetail);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param policyDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PolicyDetail policyDetail) {
		super.save(policyDetail);
	}
	
	/**
	 * 更新状态
	 * @param policyDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PolicyDetail policyDetail) {
		super.updateStatus(policyDetail);
	}
	
	/**
	 * 删除数据
	 * @param policyDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PolicyDetail policyDetail) {
		super.delete(policyDetail);
	}

	/**
	 * 结束保单的子权益
	 * @param policyId
	 * @param child
	 */
	@Transactional(readOnly=false)
	public void overEquity(String policyId,String child ) {
	dao.updateEquity(policyId,child);
	}

	/**
	 * 查询1.0 的工单
	 * @param commonFromId
	 * @return
	 */
	public Map<String, Object> findOrder(String commonFromId) {
		return dao.findOrder(commonFromId);
	}
}