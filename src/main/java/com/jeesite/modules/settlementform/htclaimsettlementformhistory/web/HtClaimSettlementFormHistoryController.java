/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.settlementform.htclaimsettlementformhistory.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bpm.entity.BpmParams;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.htassemblyunit.entity.HtAssemblyUnit;
import com.jeesite.modules.htassemblyunit.service.HtAssemblyUnitService;
import com.jeesite.modules.htbreakdowninfo.entity.HtBreakdownInfo;
import com.jeesite.modules.htbreakdowninfo.service.HtBreakdownInfoService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.settlementform.htcalllog.entity.HtCallLog;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.HtClaimSettlementForm;
import com.jeesite.modules.settlementform.htclaimsettlementform.entity.ProcessListingEntity;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.entity.HtClaimSettlementFormHistory;
import com.jeesite.modules.settlementform.htclaimsettlementformhistory.service.HtClaimSettlementFormHistoryService;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.util.CommaJointUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 在线理赔表Controller
 * @author hongmengzhong
 * @version 2020-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/htclaimsettlementform/htClaimSettlementForm")
public class HtClaimSettlementFormHistoryController extends BaseController {

	@Autowired
	private HtClaimSettlementFormHistoryService htClaimSettlementFormHistoryService;
	@Autowired
	private HtClaimSettlementFormService htClaimSettlementFormService;
	@Autowired
	private HtAssemblyUnitService htAssemblyUnitService;
	@Autowired
	private HtBreakdownInfoService htBreakdownInfoService;
	@Autowired
	private ChannelProductInfoService channelProductInfoService;
	@Autowired
	private HtBrandInfoService htBrandInfoService;
	@Autowired
	private HtPhoneModelInfoService htPhoneModelInfoService;
	@Autowired
	private HtCallLogService htCallLogService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtClaimSettlementFormHistory get(String id, boolean isNewRecord) {
		return htClaimSettlementFormHistoryService.get(id, isNewRecord);
	}
	

	/**
	 * 查看编辑表单
	 */
	//@RequiresPermissions("htclaimsettlementform:htClaimSettlementForm:view")
	@RequestMapping(value = "formClaHistory")
	public String formClaHistory(HtClaimSettlementFormHistory htClaimSettlementFormHistory, Model model) {
		HtCallLog htCallLog = new HtCallLog();
		if (StringUtils.isNotBlank(htClaimSettlementFormHistory.getCallInfoId())){
			htCallLog = htCallLogService.get(htClaimSettlementFormHistory.getCallInfoId());

		}
		List<HtBrandInfo> brandInfoList = htBrandInfoService.getBrandList();
		List<HtBreakdownInfo> breakDownList = htBreakdownInfoService.findList(new HtBreakdownInfo());
		List<HtAssemblyUnit> unitZhuList = htAssemblyUnitService.findListByStart("1");
		List<HtAssemblyUnit> unitFuList = htAssemblyUnitService.findListByStart("0");
		List<Map<String, Object>> claimDataList = channelProductInfoService.findClaimData();
		//if (StringUtils.isNotBlank(htClaimSettlementFormHistory.getSettlementDataId())){
			//List<String> settlist = htClaimSettlementFormService.getSettlementDataList(htClaimSettlementFormHistory.getSettlementDataId());
		//	if (settlist!=null&&settlist.size()>0) {
				htClaimSettlementFormHistory.setClaimDataStr(htClaimSettlementFormHistory.getSettlementDataId());
		//	}
		//}


		htClaimSettlementFormHistory.setReturnAddress(htClaimSettlementFormHistory.getReturnAddress());
		List<HtPhoneModelInfo> htPhoneModelInfoList = htPhoneModelInfoService.findList(new HtPhoneModelInfo());
		if (StringUtils.isBlank(htCallLog.getData())){
			htCallLog.setData("null");
		}
		if (StringUtils.isNotBlank(htClaimSettlementFormHistory.getDamageImgs())){
			List<String> damageImgsList = Arrays.asList(htClaimSettlementFormHistory.getDamageImgs().split("\\|"));
			model.addAttribute("damageImgsList", damageImgsList);
		}
		if (StringUtils.isNotBlank(htClaimSettlementFormHistory.getIdentityCardImgs())){
			List<String> identityCardImgsList = Arrays.asList(htClaimSettlementFormHistory.getIdentityCardImgs().split("\\|"));
			model.addAttribute("identityCardImgsList", identityCardImgsList);
		}
		if (StringUtils.isNotBlank(htClaimSettlementFormHistory.getPurchaseImgs())){
			List<String> purchaseImgsList = Arrays.asList(htClaimSettlementFormHistory.getPurchaseImgs().split("\\|"));
			model.addAttribute("purchaseImgsList", purchaseImgsList);
		}
		model.addAttribute("htCallLog", htCallLog);
		model.addAttribute("htPhoneModelInfoList", htPhoneModelInfoList);
		model.addAttribute("brandInfoList", brandInfoList);
		model.addAttribute("claimDataList", claimDataList);
		model.addAttribute("breakDownList", breakDownList);
		model.addAttribute("unitZhuList", unitZhuList);
		model.addAttribute("unitFuList", unitFuList);
		model.addAttribute("htClaimSettlementFormHistory", htClaimSettlementFormHistory);
		return "modules/settlementform/htclaimsettlementform/htClaimSettlementFormFormHistory";
	}
}