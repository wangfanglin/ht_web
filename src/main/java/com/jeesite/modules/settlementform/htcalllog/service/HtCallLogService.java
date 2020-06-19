/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htcalllog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.settlementform.htcalllog.entity.HtCallLog;
import com.jeesite.modules.settlementform.htcalllog.dao.HtCallLogDao;

/**
 * 通话记录表Service
 * @author hongmengzhong
 * @version 2020-03-12
 */
@Service
@Transactional(readOnly=true)
public class HtCallLogService extends CrudService<HtCallLogDao, HtCallLog> {
	@Autowired
	private HtCallLogDao htCallLogDao;
	/**
	 * 获取单条数据
	 * @param htCallLog
	 * @return
	 */
	@Override
	public HtCallLog get(HtCallLog htCallLog) {
		return super.get(htCallLog);
	}
	
	/**
	 * 查询分页数据
	 * @param htCallLog 查询条件
	 * @return
	 */
	@Override
	public Page<HtCallLog> findPage(HtCallLog htCallLog) {
		return super.findPage(htCallLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htCallLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtCallLog htCallLog) {
		super.save(htCallLog);
	}
	
	/**
	 * 更新状态
	 * @param htCallLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtCallLog htCallLog) {
		super.updateStatus(htCallLog);
	}
	
	/**
	 * 删除数据
	 * @param htCallLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtCallLog htCallLog) {
		super.delete(htCallLog);
	}

    public String getCallLogId(String userCode) {
		String callId = htCallLogDao.getByUserId(userCode,"0").getId();
		HtCallLog callLog = new HtCallLog(callId);
		callLog.setStatus("1");
		htCallLogDao.updateStatus(callLog);
		return callId;
    }

	/**
	 * 获取最新的呼入信息
	 * @param userCode	用户ID
	 * @return
	 */
	public HtCallLog getLastInboundInfo(String userCode) {
		return htCallLogDao.getByUserId(userCode, "1");
	}


	/**
	 * 对通话记录进行核销
	 * @param id		通话记录ID
	 * @return			true：核销成功  false：核销失败
	 */
	public boolean verification(String id) {
		HtCallLog callLog = new HtCallLog(id);
		callLog.setStatus("1");
		long result = htCallLogDao.updateStatus(callLog);
		return result>0;
	}




	/**
	 * 根据呼入呼出查询总数
	 * @param type
	 * @return
	 */
	public int findListBuStatus(String type) {
		return dao.findListBuStatus(type);
    }


    public String getUserIdByEmpNoFirst (String empId){return dao.getUserIdByEmpNoFirst(empId);}
}