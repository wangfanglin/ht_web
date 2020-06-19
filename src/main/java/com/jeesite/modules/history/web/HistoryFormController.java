package com.jeesite.modules.history.web;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.history.entity.HistoryForm;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HistoryFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/history/historyForm")
public class HistoryFormController {
    @Autowired
    HistoryFormService service;
    @Autowired
    HtFormInfoService formInfoService;

    @RequestMapping("findList")
    public String findList(String commonFromId, String commonLinkId, int pageNo, int pageSize, Model model) {
        HtHistory history = new HtHistory();
        history.setWorkOrderId(commonFromId);
        Page<HtHistory> page = new Page<>(pageNo, pageSize);
        history.setPage(page);
        Page<HistoryForm> formPage = service.findList(history);
        model.addAttribute("commonHistoryFormList", formPage.getList());

        if (formPage.getCount() <= formPage.getPageNo() * formPage.getPageSize()) {
            model.addAttribute("status", 2);
        }
        return "modules/common/historyFormList#historyFormList";
    }
}
