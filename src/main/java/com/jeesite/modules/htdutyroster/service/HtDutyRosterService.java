/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htdutyroster.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.utils.excel.ExcelImport;
import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.utils.UserUtils;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htdutyroster.entity.HtDutyRoster;
import com.jeesite.modules.htdutyroster.dao.HtDutyRosterDao;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 排班表Service
 * @author hongmengzhong
 * @version 2020-02-25
 */
@Service
@Transactional(readOnly=true)
public class HtDutyRosterService extends CrudService<HtDutyRosterDao, HtDutyRoster> {
	@Autowired
	private HtDutyRosterDao htDutyRosterDao;
	/**
	 * 获取单条数据
	 * @param htDutyRoster
	 * @return
	 */
	@Override
	public HtDutyRoster get(HtDutyRoster htDutyRoster) {
		return super.get(htDutyRoster);
	}
	
	/**
	 * 查询分页数据
	 * @param htDutyRoster 查询条件
	 * @return
	 */
	@Override
	public Page<HtDutyRoster> findPage(HtDutyRoster htDutyRoster) {
		return super.findPage(htDutyRoster);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htDutyRoster
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtDutyRoster htDutyRoster) {
		super.save(htDutyRoster);
	}
	
	/**
	 * 更新状态
	 * @param htDutyRoster
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtDutyRoster htDutyRoster) {
		super.updateStatus(htDutyRoster);
	}
	
	/**
	 * 删除数据
	 * @param htDutyRoster
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtDutyRoster htDutyRoster) {
		super.delete(htDutyRoster);
	}


	@Transactional(readOnly = false)
	public String importHtDutyRosterData(MultipartFile file) {
		if (file == null) {
			throw new ServiceException(this.text("请选择导入的数据文件！", new String[0]));
		} else {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder successMsg = new StringBuilder();
			StringBuilder failureMsg = new StringBuilder();

			try {
				ExcelImport ei = new ExcelImport(file, 2, 0);
				Throwable var8 = null;

				try {
					List<HtDutyRoster> list = ei.getDataList(HtDutyRoster.class, new String[0]);
					Iterator var10 = list.iterator();

					while(var10.hasNext()) {
						HtDutyRoster htDutyRoster = (HtDutyRoster)var10.next();
						try {
							if (htDutyRoster != null) {
								Map<String,String> officeInfo = htDutyRosterDao.getOfficeInfoByNo(htDutyRoster.getStaffNo());
								if (officeInfo!=null && StringUtils.isNotBlank(officeInfo.get("user_code"))){
									htDutyRoster.setStaffUserId(officeInfo.get("user_code"));
									htDutyRoster.setOrganizationId(officeInfo.get("office_code"));
									String booleans = htDutyRosterDao.getStaffWhether(htDutyRoster);
									if (StringUtils.isBlank(booleans)){
										this.save(htDutyRoster);
										++successNum;
										successMsg.append("<br/>" + htDutyRoster.getStaffNo() + "导入成功");
									}else{
										++failureNum;
										failureMsg.append("<br/>" + htDutyRoster.getStaffNo() + "导入失败：该员工已存在今天得排班");
									}
								}else{
									++failureNum;
									failureMsg.append("<br/>导入失败：未找到" + htDutyRoster.getStaffNo() + "该工号员工信息");

								}
							}
						} catch (Exception var26) {
							++failureNum;
							String msg = "<br/>" + failureNum + "导入失败：";
							if (var26 instanceof ConstraintViolationException) {
								ConstraintViolationException cve = (ConstraintViolationException)var26;
								ConstraintViolation violation;
								for(Iterator var15 = cve.getConstraintViolations().iterator(); var15.hasNext(); msg = msg + Global.getText(violation.getMessage(), new String[0]) + " (" + violation.getPropertyPath() + ")") {
									violation = (ConstraintViolation)var15.next();
								}
							} else {
								msg = msg + var26.getMessage();
							}

							failureMsg.append(msg);
							this.logger.error(msg, var26);
						}
					}
				} catch (Throwable var27) {
					var8 = var27;
					throw var27;
				} finally {
					if (ei != null) {
						if (var8 != null) {
							try {
								ei.close();
							} catch (Throwable var25) {
								var8.addSuppressed(var25);
							}
						} else {
							ei.close();
						}
					}

				}
			} catch (Exception var29) {
				failureMsg.append(var29.getMessage());
				this.logger.error(var29.getMessage(), var29);
			}

			if (failureNum > 0) {
				failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
				return failureMsg.toString();
				//throw new ServiceException(failureMsg.toString());
			} else {
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
				return successMsg.toString();
			}
		}
	}
	/**
	 *	根据角色id 获取当前应值班的人员
	 * @return
	 */
	public Map<String,String> getDutyRosterStaffUserId(String officeId) {
		String userId = htDutyRosterDao.getDutyRosterStaffUserId(officeId);
		Map<String,String> returnMsg = new HashMap<>();
		returnMsg.put("status","error");
		if (StringUtils.isNotBlank(userId)){
			htDutyRosterDao.saveRosterLog(userId);
			returnMsg.put("status","success");
			returnMsg.put("userId",userId);
		}
		return returnMsg;
	}

	/**
	 *	根据角色id 获取当前应值班的人员
	 * @param roleId 角色id
	 * @return
	 */
	public Map<String,String> getDutyRosterStaffUserIdByRole(String roleId) {
		String userId = htDutyRosterDao.getDutyRosterStaffUserIdByRole(roleId);
		Map<String,String> returnMsg = new HashMap<>();
		returnMsg.put("status","error");
		if (StringUtils.isNotBlank(userId)){
			htDutyRosterDao.saveRosterLog(userId);
			returnMsg.put("status","success");
			returnMsg.put("userId",userId);
		}
		return returnMsg;
	}

	/**
	 * 根据userCode获取当前人员是否上班
	 * @param userCode
	 * @return
	 */
	public boolean findWhetherBeOnDuty(String userCode){
		int index = htDutyRosterDao.findWhetherBeOnDuty(userCode);
		if (index>0){
			return true;
		}
		return false;
	}
}