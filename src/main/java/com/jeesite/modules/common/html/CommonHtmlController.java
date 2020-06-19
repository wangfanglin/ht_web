/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.common.html;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.channel.entity.HtPurchasingChannels;
import com.jeesite.modules.channel.service.HtPurchasingChannelsService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.service.HtHistoryService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.RoleUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * 演示实例Controller
 *
 * @author ThinkGem
 * @version 2018-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/common")
public class CommonHtmlController extends BaseController {
    @Autowired
    private PolicyInfoService policyInfoService;
    @Autowired
    private HtFormInfoService formService;
    @Autowired
    private HtHistoryService htHistoryService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private HtUserApplyInfoService userApplyInfoService;
    @Autowired
    private ChannelProductInfoService channelProductInfoService;
    @Autowired
    private HtPurchasingChannelsService purchasingChannelsService;
    @Autowired
    private HtBrandInfoService htBrandInfoService;
    @Autowired
    private HtPhoneModelInfoService htPhoneModelInfoService;




    @RequestMapping(value = "listOpen")
    public String listOpen(HtHistory htHistory, Model model) {
        //历史 list 页面  根据 工单类型判断
        model.addAttribute("htHistory", htHistory);
        return "modules/common/htHistoryList";
    }

    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<HtHistory> listData(HtHistory htHistory, HttpServletRequest request, HttpServletResponse response) {
        String userCode = UserUtils.getUser().getUserCode();
        HashSet<String> setList = new HashSet<>();

        if("system".equals(userCode)||"admin".equals(userCode)){
            setList.add("0");
            setList.add("1");
            setList.add("2");
            setList.add("3");
            setList.add("4");
        }else{
            List<String> visibleList = htHistoryService.findVisible(userCode);
            setList.addAll(visibleList);
        }
        Page<HtHistory> page = new Page<>(request, response);
        List<HtHistory> list = htHistoryService.findListByType(htHistory.getWorkOrderId(),setList);

        page.setList(list);
        return page;
    }

    /**
     * @param model
     * @return
     */
    @RequestMapping(value = "form")
    public String form(String id, Model model) {
        HtHistory htHistory = htHistoryService.get(id);
        model.addAttribute("htHistory",htHistory);
        return "modules/common/htHistoryForm";
    }




    /**
     *
     * @param commonFromId 工单ID   工单保单信息
     * @param model
     * @return
     */
    @RequestMapping(value = "getPilicyDetail")
    public String getOrderDetail(String commonFromId,Boolean commonApply, Model model) {
        //添加工单信息
        HtFormInfo htForm = formService.get(commonFromId);
        model.addAttribute("htForm", htForm);
        String formType = htForm.getFormType();
        model.addAttribute("formType", formType);
        HtUserApplyInfo userApplyInfo = userApplyInfoService.findByFormId(commonFromId);
        if (userApplyInfo==null){
            model.addAttribute("apply", "2");
        }else{
            model.addAttribute("apply", "1");
        }
        //添加保单信息
        if (htForm != null) {
            String policyId = htForm.getPolicyInfo().getId();
            PolicyInfo policyInfo = policyInfoService.get(policyId);
            PolicyInfo query = new PolicyInfo();
            query.setStrContactNum(policyInfo.getStrContactNum());
            List<PolicyInfo> list = policyInfoService.findList(query);
            policyInfo.setPolAmountByPhone(list.size());
            //查出这个保单有几个工单
            int formAmount = formService.findFormAmount(policyInfo.getId());
            policyInfo.setFormAmount(formAmount);

            String channelId = policyInfo.getChannelId();
            Office office = officeService.get(channelId);
            policyInfo.setStrChannelName(office.getOfficeName());
            PolicyInfo policy = policyInfoService.updateUnit(policyInfo, "0");
            String strPhoneBrand = policy.getStrPhoneBrand();
            String strPhoneModel = policy.getStrPhoneModel();
            if (strPhoneBrand!=null&&!"".equals(strPhoneBrand)){policy.setStrPhoneBrand(htBrandInfoService.get(strPhoneBrand).getName()); }
            if (strPhoneModel!=null && !"".equals(strPhoneModel)){policy.setStrPhoneModel(htPhoneModelInfoService.get(strPhoneModel).getModel());   }

            model.addAttribute("policyInfo", policy);
            model.addAttribute("commonApplyId", commonFromId);
            model.addAttribute("commonApply", commonApply);
             } else {
            model.addAttribute("commonApplyId", commonFromId);
            model.addAttribute("commonApply", commonApply);
            model.addAttribute("policyInfo", new PolicyInfo());
        }
        model.addAttribute("workOrderId", commonFromId);
        return "modules/common/policyDetail#policyDetail";
    }



    /**
     *
     * @param commonButtonId 工单ID   查看工单历史的连接
     * @param model
     * @return
     */
    @RequestMapping(value = "historyButton")
    public String historyButton(String commonButtonId, Model model) {
        //公共参数  为了不重复 将工单ID 明明为commonButtonId
        model.addAttribute("commonButtonId",commonButtonId);
        return "modules/common/historyButton#button";
    }


    /**
     *
     * @param commonApplyId 工单ID   在线申请信息
     * @param model
     * @return
     */
    @RequestMapping(value = "applyCommon")
    public String applyCommon(String commonApplyId, Model model) {
        //公共参数  为了不重复 将工单ID commonApplyId
        //根据工单ID查出 在线申请信息
        //200326111004173  暂时固定传参  数据比较全    1243477955781148672
        HtFormInfo formInfo = formService.get(commonApplyId);
         model.addAttribute("type",formInfo.getFormType());
        HtUserApplyInfo userApply = userApplyInfoService.findByFormId(commonApplyId);
        Map<String,Object> area = userApplyInfoService.finfArea(userApply.getAreaId());
        PolicyInfo policyInfo = policyInfoService.get(userApply.getPolicyId());
        String channelProductId = policyInfo.getChannelProductId();
        String channelId = policyInfo.getChannelId();
        ChannelProductInfo channelProductInfo = channelProductInfoService.get(channelProductId);
        Office office = officeService.get(channelId);
        String name = channelProductInfo.getName();
        String strname = office.getOfficeName();
        policyInfo.setChannelProductName(name);
        policyInfo.setStrChannelName(strname);

        String province = (String)area.get("province_name");
        String phone = (String)area.get("user_phone");
        String address = (String)area.get("address");

        //所有图片
        Map<String,String> imgMap = userApplyInfoService.findPartImgs(userApply.getId());
        ArrayList<String> badPartImgs = new ArrayList<>();
        ArrayList<String> cardImgs = new ArrayList<>();
        List<String> voucherImgs = new ArrayList<>();
        badPartImgs.add(imgMap.get("bad_side_wimg"));
        badPartImgs.add(imgMap.get("bad_side_oimg"));
        badPartImgs.add(imgMap.get("bad_reverse_img"));
        badPartImgs.add(imgMap.get("bad_front_img"));
        cardImgs.add(imgMap.get("card_reverse_img"));
        cardImgs.add(imgMap.get("card_front_img"));
        cardImgs.add(imgMap.get("card_hand_img"));
        String voucherImg = imgMap.get("voucher_img");
        if (StringUtils.isNotBlank(voucherImg)){
            String[] split = voucherImg.split("\\|");
            voucherImgs = Arrays.asList(split);
        }
        //出险时间
        model.addAttribute("phone",phone);
        model.addAttribute("addressInfo",province+address);
        model.addAttribute("userApply",userApply);
        model.addAttribute("policyInfo",policyInfo);
        model.addAttribute("badPartImgs",badPartImgs);
        model.addAttribute("cardImgs",cardImgs);
        model.addAttribute("voucherImgs",voucherImgs);
        return "modules/common/applyCommon#applyCommon";
    }





}