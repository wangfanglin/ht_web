/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.policy.entity.JxPolicyInfo;
import com.jeesite.modules.policy.dao.JxPolicyInfoDao;

/**
 * jx_policy_infoService
 * @author tangchao
 * @version 2020-04-27
 */
@Service
@Transactional(readOnly=true)
public class JxPolicyInfoService extends CrudService<JxPolicyInfoDao, JxPolicyInfo> {
	
	/**
	 * 获取单条数据
	 * @param jxPolicyInfo
	 * @return
	 */
	@Override
	public JxPolicyInfo get(JxPolicyInfo jxPolicyInfo) {
		return super.get(jxPolicyInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param jxPolicyInfo 查询条件
	 * @param jxPolicyInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<JxPolicyInfo> findPage(JxPolicyInfo jxPolicyInfo) {
		return super.findPage(jxPolicyInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param jxPolicyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(JxPolicyInfo jxPolicyInfo) {
		super.save(jxPolicyInfo);
	}
	
	/**
	 * 更新状态
	 * @param jxPolicyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(JxPolicyInfo jxPolicyInfo) {
		super.updateStatus(jxPolicyInfo);
	}
	
	/**
	 * 删除数据
	 * @param jxPolicyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(JxPolicyInfo jxPolicyInfo) {
		super.delete(jxPolicyInfo);
	}

    public List<JxPolicyInfo> findNewList(JxPolicyInfo jxPolicyInfo) {
		return dao.findNewList(jxPolicyInfo);
    }
}