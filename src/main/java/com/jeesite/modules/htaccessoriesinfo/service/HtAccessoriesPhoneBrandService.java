package com.jeesite.modules.htaccessoriesinfo.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htaccessoriesinfo.dao.HtAccessoriesPhoneBrandDao;
import com.jeesite.modules.htaccessoriesinfo.entity.HtAccessoriesPhoneBrand;
import com.jeesite.modules.util.CommaJointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class HtAccessoriesPhoneBrandService extends CrudService<HtAccessoriesPhoneBrandDao, HtAccessoriesPhoneBrand> {

    @Autowired
    private HtAccessoriesPhoneBrandDao htAccessoriesPhoneBrandDao;

    /**
     * 保存数据（插入或更新）
     * @param htAccessoriesPhoneBrand
     */
    @Override
    @Transactional(readOnly=false)
    public void save(HtAccessoriesPhoneBrand htAccessoriesPhoneBrand) {
        super.save(htAccessoriesPhoneBrand);
    }

    public void deleteBuAccessId(String accessoriesInfoId) {
        htAccessoriesPhoneBrandDao.deleteBuAccessId(accessoriesInfoId);
    }

    public String getBrandStr(String accessoriesInfoId) {
        List<String> brandList = htAccessoriesPhoneBrandDao.getBrandList(accessoriesInfoId);
        if (brandList!=null && brandList.size()>0){
            return CommaJointUtil.commaJointString(brandList);
        }
        return "";
    }
}
