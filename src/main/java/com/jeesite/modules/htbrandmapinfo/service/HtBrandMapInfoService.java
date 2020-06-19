/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htbrandmapinfo.service;

import java.util.Iterator;
import java.util.List;
import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.utils.excel.ExcelImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htbrandmapinfo.entity.HtBrandMapInfo;
import com.jeesite.modules.htbrandmapinfo.dao.HtBrandMapInfoDao;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 品牌映射表Service
 * @author hongmengzhong
 * @version 2020-02-19
 */
@Service
@Transactional(readOnly=true)
public class HtBrandMapInfoService extends CrudService<HtBrandMapInfoDao, HtBrandMapInfo> {
	@Autowired
	private  HtBrandMapInfoDao htBrandMapInfoDao;

	/**
	 * 获取单条数据
	 * @param htBrandMapInfo
	 * @return
	 */
	@Override
	public HtBrandMapInfo get(HtBrandMapInfo htBrandMapInfo) {
		return super.get(htBrandMapInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param htBrandMapInfo 查询条件
	 * @param htBrandMapInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<HtBrandMapInfo> findPage(HtBrandMapInfo htBrandMapInfo) {
		return super.findPage(htBrandMapInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param htBrandMapInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HtBrandMapInfo htBrandMapInfo) {
		super.save(htBrandMapInfo);
	}
	
	/**
	 * 更新状态
	 * @param htBrandMapInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HtBrandMapInfo htBrandMapInfo) {
		super.updateStatus(htBrandMapInfo);
	}
	
	/**
	 * 删除数据
	 * @param htBrandMapInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HtBrandMapInfo htBrandMapInfo) {
		super.delete(htBrandMapInfo);
	}

	public HtBrandMapInfo findInfoBrandId(String mapBrandId) {
		return dao.findInfoBrandId(mapBrandId);
	}





	public HtBrandMapInfo getByDistributionIdAndOriginalBrand(HtBrandMapInfo htBrandMapInfo) {
		return dao.getByDistributionIdAndOriginalBrand(htBrandMapInfo);
	}


	/**
	 *导入数据
	 */
	@Transactional(readOnly = false)
	public String importBrandMapData (MultipartFile file) {
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
					List<HtBrandMapInfo> list = ei.getDataList(HtBrandMapInfo.class,new String[0]);
					Iterator var10 = list.iterator();
					while(var10.hasNext()) {
						HtBrandMapInfo htBrandMapInfo = (HtBrandMapInfo)var10.next();
						try {
							if (htBrandMapInfo != null) {
								if (StringUtils.isNotBlank(htBrandMapInfo.getDistributionId()) && StringUtils.isNotBlank(htBrandMapInfo.getMapBrandId()) && StringUtils.isNotBlank(htBrandMapInfo.getOriginalBrand())) {
									String officeDistribution = htBrandMapInfoDao.getDistributionId(htBrandMapInfo.getDistributionId());
									String brandInfo = htBrandMapInfoDao.getMapBrandId(htBrandMapInfo.getMapBrandId());
									if (StringUtils.isBlank(officeDistribution) ||  StringUtils.isBlank(brandInfo)) {
										++failureNum;
										String str ;
											if (officeDistribution == null){
												str = "渠道商";
												failureMsg.append("<br/>导入失败："+str+"查询不到");
											}
											if (brandInfo == null){
												str = "映射品牌";
												failureMsg.append("<br/>导入失败："+str+"查询不到");
											}
									} else {
										htBrandMapInfo.setMapBrandId(brandInfo);
										htBrandMapInfo.setDistributionId(officeDistribution);
										String mapId = htBrandMapInfoDao.getMap(htBrandMapInfo);
										if (StringUtils.isBlank(mapId)) {
											this.save(htBrandMapInfo);
											++successNum;
											successMsg.append("<br/>"+ "导入成功");
										} else {
											htBrandMapInfo.setId(mapId);
											htBrandMapInfoDao.update(htBrandMapInfo);
											++successNum;
											successMsg.append("<br/>" + htBrandMapInfo.getId() + "修改成功");
										}
									}
								}
							else{
								++failureNum;
								failureMsg.append("<br/>导入失败：渠道商，原品牌，映射品牌都不能为空");
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