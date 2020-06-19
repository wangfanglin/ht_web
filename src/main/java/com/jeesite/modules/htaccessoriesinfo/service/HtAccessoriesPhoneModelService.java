package com.jeesite.modules.htaccessoriesinfo.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htaccessoriesinfo.dao.HtAccessoriesPhoneModelDao;
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesPhoneModel;
import com.jeesite.modules.util.CommaJointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class HtAccessoriesPhoneModelService extends CrudService<HtAccessoriesPhoneModelDao, HtAccessoriesPhoneModel> {
    @Autowired
    private HtAccessoriesPhoneModelDao htAccessoriesPhoneModelDao;
    /**
     * 保存数据（插入或更新）
     * @param htAccessoriesPhoneModel
     */
    @Override
    @Transactional(readOnly=false)
    public void save(HtAccessoriesPhoneModel htAccessoriesPhoneModel) {
        super.save(htAccessoriesPhoneModel);
    }

    public void deleteBuAccessId(String accessoriesInfoId) {
        htAccessoriesPhoneModelDao.deleteBuAccessId(accessoriesInfoId);
    }

    public String getModelStr(String accessoriesInfoId) {
        List<String> brandList = htAccessoriesPhoneModelDao.getModelStr(accessoriesInfoId);
        if (brandList!=null && brandList.size()>0){
            return CommaJointUtil.commaJointString(brandList);
        }
        return "";
    }
}
