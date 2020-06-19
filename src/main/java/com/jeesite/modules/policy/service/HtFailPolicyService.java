/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.service;

import java.util.List;

import com.jeesite.modules.policy.dao.PolicyInfoDao;
import com.jeesite.modules.policy.entity.JxPolicyInfo;
import com.jeesite.modules.policy.entity.OldPolicyInfo;
import com.jeesite.modules.policy.entity.PolicyInfo;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.policy.entity.HtFailPolicy;
import com.jeesite.modules.policy.dao.HtFailPolicyDao;

import javax.validation.constraints.NotBlank;

/**
 * 映射失败保单Service
 * @author zhaohaifeng
 * @version 2020-04-17
 */
@Service
@Transactional(readOnly=true)
public class HtFailPolicyService extends CrudService<HtFailPolicyDao, HtFailPolicy> {
	@Autowired
	private PolicyInfoDao policyInfoDao;
	@Autowired
	private JxPolicyConverter jxPolicyConverter;
	@Autowired
	private  JxPolicyInfoService jxPolicyInfoService;
	@Autowired
	private OldPolicyConverter oldPolicyConverter;
	@Autowired
	private OldPolicyInfoService oldPolicyInfoService;
	/**
	 * 获取单条数据
	 * @param htFailPolicy
	 * @return
	 */
	@Override
	public HtFailPolicy get(HtFailPolicy htFailPolicy) {
		return super.get(htFailPolicy);
	}
	
	/**
	 * 查询分页数据
	 * @param htFailPolicy 查询条件
	 * @return
	 */
	@Override
	public Page<HtFailPolicy> findPage(HtFailPolicy htFailPolicy) {
		return super.findPage(htFailPolicy);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htFailPolicy
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtFailPolicy htFailPolicy) {
		String policyId = htFailPolicy.getPolicyId();
		String type = htFailPolicy.getType();
		if ("0".equals(type)){
			JxPolicyInfo jxPolicyInfo = jxPolicyInfoService.get(policyId);
			jxPolicyInfo.setNewBrand(htFailPolicy.getNewestBrand());
			jxPolicyInfo.setNewModel(htFailPolicy.getNewestModel());
			jxPolicyInfo.setNewChannelId(htFailPolicy.getNewestChannel());
			jxPolicyInfo.setNewChannelProInfoId(htFailPolicy.getNewestChannelProduct());
			jxPolicyConverter.convert(jxPolicyInfo, PolicyConverter.ConVertType.UPDATE);
		}else{
			OldPolicyInfo oldPolicyInfo = oldPolicyInfoService.get(policyId);
			oldPolicyInfo.setNewBrand(htFailPolicy.getNewestBrand());
			oldPolicyInfo.setNewModel(htFailPolicy.getNewestModel());
			oldPolicyInfo.setNewChannelId(htFailPolicy.getNewestChannel());
			oldPolicyInfo.setNewChannelProInfoId(htFailPolicy.getNewestChannelProduct());
			oldPolicyConverter.convert(oldPolicyInfo,PolicyConverter.ConVertType.UPDATE);
		}
		htFailPolicy.setStatus("1");
		super.save(htFailPolicy);
	}




	
	/**
	 * 更新状态
	 * @param htFailPolicy
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtFailPolicy htFailPolicy) {
		super.updateStatus(htFailPolicy);
	}
	
	/**
	 * 删除数据
	 * @param htFailPolicy
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtFailPolicy htFailPolicy) {
		super.delete(htFailPolicy);
	}
	
}