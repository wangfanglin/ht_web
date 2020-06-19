/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.service;

import java.util.List;

import com.jeesite.modules.policy.entity.JxPolicyInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.policy.entity.OldPolicyInfo;
import com.jeesite.modules.policy.dao.OldPolicyInfoDao;

/**
 * policy_infoService
 * @author tangchao
 * @version 2020-04-27
 */
@Service
@Transactional(readOnly=true)
public class OldPolicyInfoService extends CrudService<OldPolicyInfoDao, OldPolicyInfo> {
	
	/**
	 * 获取单条数据
	 * @param oldPolicyInfo
	 * @return
	 */
	@Override
	public OldPolicyInfo get(OldPolicyInfo oldPolicyInfo) {
		return super.get(oldPolicyInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param oldPolicyInfo 查询条件
	 * @param oldPolicyInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<OldPolicyInfo> findPage(OldPolicyInfo oldPolicyInfo) {
		return super.findPage(oldPolicyInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param oldPolicyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(OldPolicyInfo oldPolicyInfo) {
		super.save(oldPolicyInfo);
	}
	
	/**
	 * 更新状态
	 * @param oldPolicyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(OldPolicyInfo oldPolicyInfo) {
		super.updateStatus(oldPolicyInfo);
	}
	
	/**
	 * 删除数据
	 * @param oldPolicyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(OldPolicyInfo oldPolicyInfo) {
		super.delete(oldPolicyInfo);
	}

    public List<OldPolicyInfo> findNewList(OldPolicyInfo oldPolicyInfo) {
		return dao.findNewList(oldPolicyInfo);
    }
}