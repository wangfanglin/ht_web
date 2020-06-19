/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.test.entity.TestForm;
import com.jeesite.modules.test.dao.TestFormDao;

/**
 * test_formService
 * @author tangchao
 * @version 2020-03-01
 */
@Service
@Transactional(readOnly=true)
public class TestFormService extends CrudService<TestFormDao, TestForm> {
	
	/**
	 * 获取单条数据
	 * @param testForm
	 * @return
	 */
	@Override
	public TestForm get(TestForm testForm) {
		return super.get(testForm);
	}
	
	/**
	 * 查询分页数据
	 * @param testForm 查询条件
	 * @param testForm.page 分页对象
	 * @return
	 */
	@Override
	public Page<TestForm> findPage(TestForm testForm) {
		return super.findPage(testForm);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param testForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TestForm testForm) {
		super.save(testForm);
	}
	
	/**
	 * 更新状态
	 * @param testForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TestForm testForm) {
		super.updateStatus(testForm);
	}
	
	/**
	 * 删除数据
	 * @param testForm
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TestForm testForm) {
		super.delete(testForm);
	}
	
}