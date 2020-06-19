/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbreakdowninfo.service;

import java.util.List;
import java.util.Map;

import com.jeesite.modules.sys.entity.Area;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htbreakdowninfo.entity.HtBreakdownInfo;
import com.jeesite.modules.htbreakdowninfo.dao.HtBreakdownInfoDao;

/**
 * 维修故障表Service
 * @author hongmengzhong
 * @version 2020-02-20
 */
@Service
@Transactional(readOnly=true)
public class HtBreakdownInfoService extends CrudService<HtBreakdownInfoDao, HtBreakdownInfo> {

	@Autowired
	private HtBreakdownInfoDao htBreakdownInfoDao;

	/**
	 * 获取单条数据
	 * @param htBreakdownInfo
	 * @return
	 */
	@Override
	public HtBreakdownInfo get(HtBreakdownInfo htBreakdownInfo) {
		return super.get(htBreakdownInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htBreakdownInfo 查询条件
	 * @param htBreakdownInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtBreakdownInfo> findPage(HtBreakdownInfo htBreakdownInfo) {
		return super.findPage(htBreakdownInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htBreakdownInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtBreakdownInfo htBreakdownInfo) {
		super.save(htBreakdownInfo);
	}
	
	/**
	 * 更新状态
	 * @param htBreakdownInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtBreakdownInfo htBreakdownInfo) {
		super.updateStatus(htBreakdownInfo);
	}
	
	/**
	 * 删除数据
	 * @param htBreakdownInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtBreakdownInfo htBreakdownInfo) {
		super.delete(htBreakdownInfo);
	}

    public void saveBreakDownMiddle(List<Map<String, Object>> middleInfo) {
		 htBreakdownInfoDao.saveBreakDownMiddle(middleInfo);
    }

	public List<HtBreakdownInfo> findAssemblyConcat(HtBreakdownInfo htBreakdownInfo) {
		return htBreakdownInfoDao.findAssemblyConcat(htBreakdownInfo);
	}

	public List<Map<String, Object>> getBreakDownAssemblyList(String breakdownId) {
		return htBreakdownInfoDao.getBreakDownAssemblyList(breakdownId);
	}

	public void delBreakDownMiddle(String breakDownInfoId) {
		htBreakdownInfoDao.delBreakDownMiddle(breakDownInfoId);
	}

    public List<Map<String, Object>> getAreaByLevel(String level) {
		return htBreakdownInfoDao.getAreaByLevel(level);
    }

	public List<Map<String, Object>> getOfficeList() {
		return htBreakdownInfoDao.getOfficeList();
	}

    public List<Map<String, Object>> getBreakDownInfo(int counts) {
		return htBreakdownInfoDao.getBreakDownInfo(counts);
    }

	public List<Area> getAreaLinkage(List<String> stringList) {
		return htBreakdownInfoDao.getAreaLinkage(stringList);
	}
}