/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementformhistory.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.dao.HtClaimSettlementFormHistoryDao;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.entity.HtClaimSettlementFormHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 在线理赔表Service
 *
 * @author hongmengzhong
 * @version 2020-02-28
 */
@Service
@Transactional(readOnly = true)
public class HtClaimSettlementFormHistoryService extends CrudService<HtClaimSettlementFormHistoryDao, HtClaimSettlementFormHistory> {

    @Override
    public HtClaimSettlementFormHistory get(HtClaimSettlementFormHistory htClaimSettlementFormHistory){
        return super.get(htClaimSettlementFormHistory);
    }

    @Override
    @Transactional(readOnly=false)
    public void save(HtClaimSettlementFormHistory htClaimSettlementFormHistory){
        super.save(htClaimSettlementFormHistory);
    }

}