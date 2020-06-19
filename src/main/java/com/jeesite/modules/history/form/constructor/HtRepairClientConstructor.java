package com.jeesite.modules.history.form.constructor;

import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.expressage.service.HtExpressageService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.form.FormConfig;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.repair.dao.HtRepairClientHistoryDao;
import com.jeesite.modules.repair.entity.HtRepairClientHistory;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:ZHF
 * @Time: 2020/3/4 15:46
 */
@FormConfig("repair")
@Service
public class HtRepairClientConstructor implements FormConstructor<HtRepairClientHistory> {
    @Autowired
    private HtRepairClientHistoryDao dao;
    @Autowired
    private HtBrandInfoService brandService;
    @Autowired
    private HtPhoneModelInfoService phoneModelService;
    @Autowired
    private HtExpressageService expressageService;

    @Override
    public HtRepairClientHistory build(HtHistory htHistory) {
        HtRepairClientHistory htRepairClientHistory = new HtRepairClientHistory();
        htRepairClientHistory.setId(htHistory.getFormId());
        HtRepairClientHistory history = dao.getByEntity(htRepairClientHistory);
        //存的是ID 先把名字查出来再返回
        /*手机品牌 机型*/
        String phoneBrand = history.getPhoneBrand();
        String phoneType = history.getPhoneType();
        if (phoneBrand!=null&&!"".equals(phoneBrand)){history.setPhoneBrand(brandService .get(phoneBrand).getName());}
        if (phoneType!=null&&!"".equals(phoneType)){history.setPhoneType(phoneModelService .get(phoneType).getModel());}
        /*快递公司*/
        String expressCompany = history.getExpressCompany();
        if (!"".equals(expressCompany)&&expressCompany!=null){history.setExpressCompany(expressageService.get(expressCompany).getExprname());}
        return history;

    }
}
