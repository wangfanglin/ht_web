/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htmaintenancepoint.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.utils.excel.ExcelImport;
import com.jeesite.modules.htbrandmapinfo.entity.HtBrandMapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.dao.HtMaintenancePointDao;
import com.jeesite.modules.file.utils.FileUploadUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 维修网点表Service
 * @author hongmengzhong
 * @version 2020-02-22
 */
@Service
@Transactional(readOnly=true)
public class HtMaintenancePointService extends CrudService<HtMaintenancePointDao, HtMaintenancePoint> {
	@Autowired
	private HtMaintenancePointDao htMaintenancePointDao;
	
	/**
	 * 获取单条数据
	 * @param htMaintenancePoint
	 * @return
	 */
	@Override
	public HtMaintenancePoint get(HtMaintenancePoint htMaintenancePoint) {
		return super.get(htMaintenancePoint);
	}
	
	/**
	 * 查询分页数据
	 * @param htMaintenancePoint 查询条件
	 * @param htMaintenancePoint.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtMaintenancePoint> findPage(HtMaintenancePoint htMaintenancePoint) {
		return super.findPage(htMaintenancePoint);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htMaintenancePoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtMaintenancePoint htMaintenancePoint) {
		super.save(htMaintenancePoint);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(htMaintenancePoint.getId(), "htMaintenancePoint_image");
	}
	
	/**
	 * 更新状态
	 * @param htMaintenancePoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtMaintenancePoint htMaintenancePoint) {
		super.updateStatus(htMaintenancePoint);
	}
	
	/**
	 * 删除数据
	 * @param htMaintenancePoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtMaintenancePoint htMaintenancePoint) {
		super.delete(htMaintenancePoint);
	}

    public void deleteByTableName(String tableName, String pointId) {
		htMaintenancePointDao.deleteByTableName(tableName,pointId);
    }

	@Transactional(readOnly=false)
	public void saveTableInfo(Map<String, Object> maintainBrandMap) {
		htMaintenancePointDao.saveTableInfo(maintainBrandMap);
	}

	public List<String> getStrListByTableName(String tableName,String columnName, String maintenancePointId) {
		return htMaintenancePointDao.getStrListByTableName(tableName,columnName,maintenancePointId);
	}

	public String getProvinceCityCode(String areaCode) {
		return htMaintenancePointDao.getProvinceCityCode(areaCode);
	}

	public List<HtMaintenancePoint> findListRewrite(HtMaintenancePoint htMaintenancePoint) {
		return htMaintenancePointDao.findListRewrite(htMaintenancePoint);
	}

	/**
	 * 获取维修网点条件结果
	 * @return
	 */
	public List<Map<String,String>> findPointConditionResult(){
		return htMaintenancePointDao.findPointConditionResult();
	}

	public HtMaintenancePoint findInfoByJG(String organizationId) {
		return htMaintenancePointDao.findInfoByJG(organizationId);
	}

    public List<HtMaintenancePoint> findPointOfficeList() {
		return htMaintenancePointDao.findPointOfficeList();
	}

	/**
	 *导入数据
	 */
	@Transactional(readOnly = false)
	public String importMaintenancePointData (MultipartFile file) {
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
					List<HtMaintenancePoint> list = ei.getDataList(HtMaintenancePoint.class,new String[0]);
					Iterator var10 = list.iterator();
					while(var10.hasNext()) {
						HtMaintenancePoint htMaintenancePoint = (HtMaintenancePoint)var10.next();
						try {
							if (htMaintenancePoint != null) {
								if (StringUtils.isNotBlank(htMaintenancePoint.getMaintenancePointName())){
								    this.save(htMaintenancePoint);
											++successNum;
											successMsg.append("<br/>"+ "导入成功");
									}
								else{
									++failureNum;
									failureMsg.append("<br/>导入失败：没有找到维修网点名称");
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
				throw new ServiceException(failureMsg.toString());
			} else {
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
				return successMsg.toString();
			}
		}
	}
}