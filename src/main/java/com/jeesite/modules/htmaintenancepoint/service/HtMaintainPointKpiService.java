package com.jeesite.modules.htmaintenancepoint.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.htmaintenancepoint.dao.HtMaintainPointKpiDao;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintainPointKpi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class HtMaintainPointKpiService extends CrudService<HtMaintainPointKpiDao, HtMaintainPointKpi> {

    @Override
    @Transactional(readOnly=false)
    public void save(HtMaintainPointKpi htMaintainPointKpi) {

        super.save(htMaintainPointKpi);
    }


    @Transactional(readOnly=false)
    public void  truncateInfo(){
        dao.truncateInfo();
    }

    public HtMaintainPointKpi getTableInfo(String pointId) {
        return dao.getTableInfo(pointId);
    }
}
