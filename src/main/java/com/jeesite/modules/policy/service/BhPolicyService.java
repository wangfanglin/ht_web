/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.policy.entity.BhPolicy;
import com.jeesite.modules.policy.dao.BhPolicyDao;

/**
 * bh_policyService
 * @author tangchao
 * @version 2020-05-07
 */
@Service
@Transactional(readOnly=true)
public class BhPolicyService extends CrudService<BhPolicyDao, BhPolicy> {
	
	/**
	 * 获取单条数据
	 * @param bhPolicy
	 * @return
	 */
	@Override
	public BhPolicy get(BhPolicy bhPolicy) {
		return super.get(bhPolicy);
	}
	
	/**
	 * 查询分页数据
	 * @param bhPolicy 查询条件
	 * @param bhPolicy.page 分页对象
	 * @return
	 */
	@Override
	public Page<BhPolicy> findPage(BhPolicy bhPolicy) {
		return super.findPage(bhPolicy);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bhPolicy
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BhPolicy bhPolicy) {
		super.save(bhPolicy);
	}
	
	/**
	 * 更新状态
	 * @param bhPolicy
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BhPolicy bhPolicy) {
		super.updateStatus(bhPolicy);
	}
	
	/**
	 * 删除数据
	 * @param bhPolicy
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BhPolicy bhPolicy) {
		super.delete(bhPolicy);
	}


	public BhPolicy getByPolicyinfoid(BhPolicy bhPolicy) {
		return dao.getByPolicyinfoid(bhPolicy);
	}
}