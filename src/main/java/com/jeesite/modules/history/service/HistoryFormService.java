package com.jeesite.modules.history.service;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.history.entity.HistoryForm;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.form.HistoryFormConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryFormService {
    @Autowired
    HtHistoryService htHistoryService;
    @Autowired
    HistoryFormConstructor constructor;


    public  Page<HistoryForm> findList(HtHistory htHistory){
        Page<HtHistory> historyList = htHistoryService.findPage(htHistory);
        List<HistoryForm> historyFormList = new ArrayList<>();
        for (HtHistory history:
        historyList.getList()) {
            historyFormList.add(constructor.build(history));
        }

        Page<HistoryForm> historyFormPage =  new Page<>();
        historyFormPage.setPageNo(historyList.getPageNo());
        historyFormPage.setPageSize(historyList.getPageSize());
        historyFormPage.setCount(historyList.getCount());
        historyFormPage.setList(historyFormList);
        return historyFormPage;
    }
}
