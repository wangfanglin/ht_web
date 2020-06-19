/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.htdutyroster.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.htdutyroster.entity.HtDutyRoster;
import com.jeesite.modules.htdutyroster.service.HtDutyRosterService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 排班表Controller
 * @author hongmengzhong
 * @version 2020-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/htdutyroster/htDutyRoster")
public class HtDutyRosterController extends BaseController {

	@Autowired
	private HtDutyRosterService htDutyRosterService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtDutyRoster get(String id, boolean isNewRecord) {
		return htDutyRosterService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtDutyRoster htDutyRoster, Model model) {
		model.addAttribute("htDutyRoster", htDutyRoster);
		return "modules/htdutyroster/htDutyRosterList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtDutyRoster> listData(HtDutyRoster htDutyRoster, HttpServletRequest request, HttpServletResponse response) {
		htDutyRoster.setPage(new Page<>(request, response));
		Page<HtDutyRoster> page = htDutyRosterService.findPage(htDutyRoster);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:view")
	@RequestMapping(value = "form")
	public String form(HtDutyRoster htDutyRoster, Model model) {
		model.addAttribute("htDutyRoster", htDutyRoster);
		return "modules/htdutyroster/htDutyRosterForm";
	}

	/**
	 * 保存排班表
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtDutyRoster htDutyRoster) {
		Date startTime = htDutyRoster.getStartTime();
		Date endTime = htDutyRoster.getEndTime();
		long time = startTime.getTime();
		long time1 = endTime.getTime();
		if (time>=time1){
			return renderResult(Global.FALSE, text("开始时间不可大于结束时间！"));
		}
		htDutyRosterService.save(htDutyRoster);
		return renderResult(Global.TRUE, text("保存排班表成功！"));
	}
	
	/**
	 * 停用排班表
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtDutyRoster htDutyRoster) {
		htDutyRoster.setStatus(HtDutyRoster.STATUS_DISABLE);
		htDutyRosterService.updateStatus(htDutyRoster);
		return renderResult(Global.TRUE, text("停用排班表成功"));
	}
	
	/**
	 * 启用排班表
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtDutyRoster htDutyRoster) {
		htDutyRoster.setStatus(HtDutyRoster.STATUS_NORMAL);
		htDutyRosterService.updateStatus(htDutyRoster);
		return renderResult(Global.TRUE, text("启用排班表成功"));
	}
	
	/**
	 * 删除排班表
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtDutyRoster htDutyRoster) {
		htDutyRosterService.delete(htDutyRoster);
		return renderResult(Global.TRUE, text("删除排班表成功！"));
	}

	/**
	 * 导入
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions({"sys:empUser:edit"})
	@PostMapping({"importData"})
	public String importData(MultipartFile file) {
		try {
			String message = this.htDutyRosterService.importHtDutyRosterData(file);
			return this.renderResult("true", "posfull:" + message);
		} catch (Exception var5) {
			return this.renderResult("false", "posfull:" + var5.getMessage());
		}
	}

	/**
	 * 导出
	 * @param file
	 * @return
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:view")
	@RequestMapping({"exportBrandMap"})
	public void importTemplate(HtDutyRoster htDutyRoster,HttpServletResponse response) {
		List<HtDutyRoster> list = htDutyRosterService.findList(htDutyRoster);
		String fileName = "排班数据表.xlsx";
		ExcelExport ee = new ExcelExport("排班数据表", HtDutyRoster.class);
		Throwable var7 = null;
		try {
			ee.setDataList(list).write(response, fileName);
		} catch (Throwable var16) {
			var7 = var16;
			throw var16;
		} finally {
			if (ee != null) {
				if (var7 != null) {
					try {
						ee.close();
					} catch (Throwable var15) {
						var7.addSuppressed(var15);
					}
				} else {
					ee.close();
				}
			}

		}

	}




	/**
	 * 导出
	 * @param file
	 * @return
	 */
	@RequiresPermissions("htdutyroster:htDutyRoster:view")
	@RequestMapping({"importTemplate"})
	public void importTemplate(HttpServletResponse response) {
		HtDutyRoster htDutyRoster = new HtDutyRoster();
		htDutyRoster.setStatus("2");
		List<HtDutyRoster> list = htDutyRosterService.findList(htDutyRoster);
		String fileName = "排班数据模板.xlsx";
		ExcelExport ee = new ExcelExport("用户数据", HtDutyRoster.class, ExcelField.Type.IMPORT, new String[0]);
		Throwable var7 = null;
		try {
			ee.setDataList(list).write(response, fileName);
		} catch (Throwable var16) {
			var7 = var16;
			throw var16;
		} finally {
			if (ee != null) {
				if (var7 != null) {
					try {
						ee.close();
					} catch (Throwable var15) {
						var7.addSuppressed(var15);
					}
				} else {
					ee.close();
				}
			}

		}

	}


	@RequestMapping(value = "getDutyRosterStaff")
	@ResponseBody
	public Map<String, String> getDutyRosterStaff(String officeId) {
		return htDutyRosterService.getDutyRosterStaffUserId(officeId);
	}

}