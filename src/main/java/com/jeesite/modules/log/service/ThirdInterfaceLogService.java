/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.log.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.log.entity.ThirdInterfaceLog;
import com.jeesite.modules.log.dao.ThirdInterfaceLogDao;

/**
 * 第三方接口调用日志Service
 * @author tangchao
 * @version 2020-04-02
 */
@Service
@Transactional(readOnly=true)
public class ThirdInterfaceLogService extends CrudService<ThirdInterfaceLogDao, ThirdInterfaceLog> {
	
	/**
	 * 获取单条数据
	 * @param thirdInterfaceLog
	 * @return
	 */
	@Override
	public ThirdInterfaceLog get(ThirdInterfaceLog thirdInterfaceLog) {
		return super.get(thirdInterfaceLog);
	}
	
	/**
	 * 查询分页数据
	 * @param thirdInterfaceLog 查询条件
	 * @param thirdInterfaceLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<ThirdInterfaceLog> findPage(ThirdInterfaceLog thirdInterfaceLog) {
		return super.findPage(thirdInterfaceLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * 该方法应保证永远不会抛出异常
	 * 以免影响到正常业务逻辑流转
	 * @param thirdInterfaceLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ThirdInterfaceLog thirdInterfaceLog) {

		try {
			logger.info("接口URL:{},类型:{},方法名:{},入参:{},出参:{}"
					,thirdInterfaceLog.getUrl(),thirdInterfaceLog.getType(),
					thirdInterfaceLog.getMethod(),thirdInterfaceLog.getParameter(),thirdInterfaceLog.getResult());
			super.save(thirdInterfaceLog);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新状态
	 * @param thirdInterfaceLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ThirdInterfaceLog thirdInterfaceLog) {
		super.updateStatus(thirdInterfaceLog);
	}
	
	/**
	 * 删除数据
	 * @param thirdInterfaceLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ThirdInterfaceLog thirdInterfaceLog) {
		super.delete(thirdInterfaceLog);
	}
	
}