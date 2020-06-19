/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.advisory.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.advisory.entity.HtAdvisoryInfo;
import com.jeesite.modules.advisory.service.HtAdvisoryHistoryService;
import com.jeesite.modules.advisory.service.HtAdvisoryInfoService;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.common.ActTaskUtils;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.policy.service.PolicyInfoService;
import com.jeesite.modules.repair.service.HtRepairClientFormService;
import com.jeesite.modules.settlementform.htcalllog.entity.HtCallLog;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import com.jeesite.modules.settlementform.htclaimsettlementform.service.HtClaimSettlementFormService;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.task.api.Task;
import org.hibernate.validator.constraints.Length;
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
import java.util.Date;
import java.util.List;

/**
 * ht_advisory_infoController
 *
 * @author zhaohaifeng
 * @version 2020-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/advisory/htAdvisoryInfo")
public class HtAdvisoryInfoController extends BaseController {

    @Autowired
    private HtAdvisoryInfoService htAdvisoryInfoService;
    @Autowired
    private HtFormInfoService formInfoService;
    @Autowired
    private HtBrandInfoService brandInfoService;
    @Autowired
    private HtPhoneModelInfoService phoneModelInfoService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private HtPhoneModelInfoService phoneModelService;
    @Autowired
    private ActTaskUtils actTaskUtils;
    @Autowired
    private HtCallLogService htCallLogService;
    @Autowired
    private HtRepairClientFormService htRepairClientFormService;


    /**
     * 获取数据
     */
    @ModelAttribute
    public HtAdvisoryInfo get(String id, boolean isNewRecord) {
        return htAdvisoryInfoService.get(id, isNewRecord);
    }

    /**
     * 查询保单列表
     */
    @RequiresPermissions("advisory:htAdvisoryInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(HtAdvisoryInfo htAdvisoryInfo, HtFormInfo htFormInfo, Model model) {
        //model.addAttribute("htAdvisoryInfo", htAdvisoryInfo);
        model.addAttribute("htFormInfo", htFormInfo);
        return "modules/advisory/htAdvisoryInfoList";
    }

    /**
     * 查询保单列表数据
     */
    @RequiresPermissions("advisory:htAdvisoryInfo:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<HtFormInfo> listData(HtFormInfo htFormInfo, HttpServletRequest request, HttpServletResponse response) {
        Page pageParam = new Page<HtFormInfo>();
        int pageNo = pageParam.getPageNo() - 1;
        int pageSize = pageParam.getPageSize();
        Page<HtFormInfo> page = htAdvisoryInfoService.findPageData(htFormInfo, pageNo, pageSize,pageParam);
        return page;
    }


    /**
     * 查询保单列表
     */
    @RequiresPermissions("advisory:htAdvisoryInfo:view")
    @RequestMapping(value = {"listAdv", ""})
    public String listAdv(HtAdvisoryInfo htAdvisoryInfo, Model model) {
        model.addAttribute("htAdvisoryInfo", htAdvisoryInfo);
        return "modules/advisory/htAdvisoryList";
    }

    /**
     * 查询保单列表数据
     */
    @RequiresPermissions("advisory:htAdvisoryInfo:view")
    @RequestMapping(value = "listDataAdv")
    @ResponseBody
    public Page<HtAdvisoryInfo> listDataAdv(HtAdvisoryInfo htAdvisoryInfo, HttpServletRequest request, HttpServletResponse response) {
        htAdvisoryInfo.setPage(new Page<>(request, response));
        Page<HtAdvisoryInfo> page = htAdvisoryInfoService.findPage(htAdvisoryInfo);
        List<HtAdvisoryInfo> list = page.getList();
        for (HtAdvisoryInfo advisoryInfo : list) {
            if (advisoryInfo.getDepartmentId() != null && !"".equals(advisoryInfo.getDepartmentId())) {
                advisoryInfo.setDepartmentId(UserUtils.get(advisoryInfo.getDepartmentId()).getUserName());
            }
            if (advisoryInfo.getOfficeId() != null && !"".equals(advisoryInfo.getOfficeId())) {
                advisoryInfo.setOfficeId(officeService.get(advisoryInfo.getOfficeId()).getOfficeName());
            }
            if (advisoryInfo.getPhoneBrand() != null && !"".equals(advisoryInfo.getPhoneBrand())) {
                advisoryInfo.setPhoneBrand(brandInfoService.get(advisoryInfo.getPhoneBrand()).getName());
            }
            if (advisoryInfo.getPhoneType() != null && !"".equals(advisoryInfo.getPhoneType())) {
                advisoryInfo.setPhoneType(phoneModelService.get(advisoryInfo.getPhoneType()).getModel());
            }
        }
        return page;
    }


    /**
     * 查看编辑表单
     */
    @RequiresPermissions("advisory:htAdvisoryInfo:view")
    @RequestMapping(value = "form")
    public String form(HtAdvisoryInfo htAdvisoryInfo, Model model, String flag, String formId) {
        if (StringUtils.isNotBlank(formId)) {
            HtFormInfo formInfo = formInfoService.get(formId);
            if (formInfo != null) {
                PolicyInfo policyInfo = formInfo.getPolicyInfo();
                if (policyInfo != null) {

                } else {
                    flag = "0";
                }
            } else {
                flag = "0";
            }
        }

        //查出通话表里最新一条记录
        String userCode = UserUtils.getUser().getUserCode();
        HtCallLog callLog = htCallLogService.getLastInboundInfo(userCode);
        if (callLog==null){
            //无用户呼入的情况下 关闭页面
         //   model.addAttribute("callPhone", "110");
        }else{
            model.addAttribute("callPhone", callLog.getUserPhone());
            htAdvisoryInfo.setCallPhone(callLog.getUserPhone());
            htAdvisoryInfo.setCallId(callLog.getId());
        }

        if ("1".equals(flag)) {
            //1是有保单进来的
            //保单的 姓名，联系电话，证件类型 ，证件号码，手机品牌，手机型号，
            HtFormInfo formInfo = formInfoService.get(formId);
            if (formInfo != null) {
                String strPhoneBrand = formInfo.getPolicyInfo().getStrPhoneBrand();
                String strPhoneModel = formInfo.getPolicyInfo().getStrPhoneModel();
                formInfo.getPolicyInfo().setStrPhoneBrand(brandInfoService.get(strPhoneBrand).getName());
                formInfo.getPolicyInfo().setStrPhoneModel(phoneModelInfoService.get(strPhoneModel).getModel());
                String policyId = formInfo.getPolicyInfo().getId();

                htAdvisoryInfo.setPolicyId(policyId);
                List<HtFormInfo> list = formInfoService.findListByPolicyId(policyId);
                model.addAttribute("list", list);
                model.addAttribute("form", formInfo);
                model.addAttribute("commonFromId", formId);
                model.addAttribute("commonApply", true);
                model.addAttribute("commonApplyId", formId);
                model.addAttribute("flag", "1");
            } else {
                model.addAttribute("flag", "0");
            }
        } else {
            model.addAttribute("flag", "0");
        }
        //手机品牌 型号需要带过去
        List<HtBrandInfo> brandList = brandInfoService.getBrandList();
        model.addAttribute("brandList", brandList);

        model.addAttribute("htAdvisoryInfo", htAdvisoryInfo);
        return "modules/advisory/htAdvisoryInfoForm";
    }


    /**
     * 投诉信息
     */
    @RequestMapping(value = "dutyForm")
    public String dutyForm(HtAdvisoryInfo htAdvisoryInfo, Model model) {
        String formId = htAdvisoryInfo.getHtFormInfo().getId();
        HtAdvisoryInfo htAdvisory = htAdvisoryInfoService.findByFormId(formId);
        htAdvisory.setBpm(htAdvisoryInfo.getBpm());
        HtFormInfo form = formInfoService.get(formId);
        htAdvisory.setHtFormInfo(form);
        model.addAttribute("htAdvisoryInfo", htAdvisory);
        if (!"".equals(form.getPolicyInfo()) && null != form.getPolicyInfo()) {
            model.addAttribute("commonForm", true);
            model.addAttribute("commonButton", false);
            model.addAttribute("commonFromId", formId);
            model.addAttribute("commonApplyId", formId);
            model.addAttribute("commonApply", true);
        } else {
            model.addAttribute("commonButton", true);
            model.addAttribute("commonForm", false);
            model.addAttribute("commonButtonId", formId);
            model.addAttribute("commonApplyId", formId);
            model.addAttribute("commonApply", false);

        }
        return "modules/advisory/htDutyForm";
    }

    /**
     * 保存投诉信息
     */
    @RequestMapping(value = "dutySave")
    @ResponseBody
    public String dutySave(HtAdvisoryInfo htAdvisoryInfo, Model model) {
        String contactStatus = htAdvisoryInfo.getContactStatus();
        if (!"1".equals(contactStatus)){
            Date againDate = htAdvisoryInfo.getAgainDate();
            if (againDate==null){
                return renderResult(Global.FALSE, text("如未联系成功，请选择下次沟通时间！"));
            }
        }
        htAdvisoryInfoService.dutySave(htAdvisoryInfo);
        return renderResult(Global.TRUE, text("保存成功！"));
    }


    /**
     * 保存ht_advisory_info
     */
    @RequiresPermissions("advisory:htAdvisoryInfo:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated HtAdvisoryInfo htAdvisoryInfo, String flag) {
        //判断参数
        String msgType = htAdvisoryInfo.getMsgType();
        String formId = htAdvisoryInfo.getOriginalFormId();
        String departmentId = htAdvisoryInfo.getDepartmentId();
        String officeId = htAdvisoryInfo.getOfficeId();

        if ("1".equals(flag)) {
            //有保单

            if ("1".equals(msgType) || "2".equals(msgType) || "3".equals(msgType)) {
                if ((officeId == null || "".equals(officeId))) {
                    return renderResult(Global.FALSE, text("部门信息不可为空！"));
                }

            }

            if ("4".equals(msgType)) {
                if ("".equals(formId) || formId == null) {
                    return renderResult(Global.FALSE, text("工单ID不可为空！"));
                }
                    Task task = actTaskUtils.getTask(formId);
                    if (task == null) {
                        return renderResult(Global.FALSE, text("此工单不可返修！"));
                    }else{
                        String taskDefinitionKey = task.getTaskDefinitionKey();
                        if (!"repair_end_restart".equals(taskDefinitionKey)) {
                            //工单需再维修完成节点 才可返修
                            return renderResult(Global.FALSE, text("此工单不可返修！"));
                        }
                    }
            }
        } else {
            //无保单
            if ("1".equals(msgType) || "2".equals(msgType) || "3".equals(msgType)) {
                if ((officeId == null || "".equals(officeId))) {
                    return renderResult(Global.FALSE, text("部门信息不可为空！"));
                }

            }
        }
        if ("1".equals(msgType) || "2".equals(msgType) || "3".equals(msgType)) {
            String userCodes = htRepairClientFormService.getUserCodes(officeId);
            if (StringUtils.isBlank(userCodes)){
                return renderResult(Global.FALSE, text("选择机构下暂无操作人员！"));
            }
        }


        htAdvisoryInfoService.save(htAdvisoryInfo, flag);
        return renderResult(Global.TRUE, text("保存成功！"));
    }

    /**
     * 删除ht_advisory_info
     */
    @RequiresPermissions("advisory:htAdvisoryInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(HtAdvisoryInfo htAdvisoryInfo) {
        htAdvisoryInfoService.delete(htAdvisoryInfo);
        return renderResult(Global.TRUE, text("删除ht_advisory_info成功！"));
    }


    /**
     * 查询机构下的人
     */
    @RequestMapping(value = "findPersonByOffice")
    @ResponseBody
    public List<Employee> findPersonByOffice(String officeId) {
        Employee employee = new Employee();
        employee.setOffice(new Office(officeId));
        List<Employee> employeeList = employeeService.findList(employee);
        return employeeList;
    }


    /**
     * 查看其他部门表单
     */

    @RequestMapping(value = "againForm")
    public String againForm(HtAdvisoryInfo htAdvisoryInfo, Model model, String id) {
        String formId = htAdvisoryInfo.getHtFormInfo().getId();
        HtAdvisoryInfo htAdvisory = htAdvisoryInfoService.findByFormId(formId);
        htAdvisory.setBpm(htAdvisoryInfo.getBpm());
        model.addAttribute("htAdvisoryInfo", htAdvisory);
        HtFormInfo form = formInfoService.get(formId);
        if (!"".equals(form.getPolicyInfo()) && null != form.getPolicyInfo()) {
            model.addAttribute("commonForm", true);
            model.addAttribute("commonButton", false);
            model.addAttribute("commonFromId", formId);
            model.addAttribute("commonApply", true);
            model.addAttribute("commonApplyId", formId);
        } else {
            model.addAttribute("commonButton", true);
            model.addAttribute("commonForm", false);
            model.addAttribute("commonButtonId", formId);
            model.addAttribute("commonApply", false);
            model.addAttribute("commonApplyId", formId);
        }
        return "modules/advisory/htAdvisoryForm";
    }

    /**
     * 保存其他表单
     */

    @RequestMapping(value = "againSave")
    @ResponseBody
    public String againSave(HtAdvisoryInfo htAdvisoryInfo) {
        String contactStatus = htAdvisoryInfo.getContactStatus();
        if (!"1".equals(contactStatus)){
            Date againDate = htAdvisoryInfo.getAgainDate();
            if (againDate==null){
                return renderResult(Global.FALSE, text("如未联系成功，请选择下次沟通时间！"));
            }
        }
        htAdvisoryInfoService.saveAgain(htAdvisoryInfo);
        return renderResult(Global.TRUE, text("保存成功！"));
    }


}