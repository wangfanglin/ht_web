/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.web;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.test.entity.History;
import com.jeesite.modules.test.entity.HistoryConstructor;
import com.jeesite.modules.policy.entity.PolicyInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.test.entity.TestData;
import com.jeesite.modules.test.service.TestDataService;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 演示实例Controller
 * @author ThinkGem
 * @version 2018-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/demo")
public class DemoController extends BaseController {

	@Autowired
	private TestDataService testDataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TestData get(String id, boolean isNewRecord) {
		return testDataService.get(id, isNewRecord);
	}
	
	/**
	 * DataGrid
	 */
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "dataGrid/{viewName}")
	public String dataGrid(@PathVariable String viewName, TestData testData, Model model) {
		return "modules/demo/demoDataGrid" + StringUtils.cap(viewName);
	}
	
	/**
	 * Form
	 */
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "form/{viewName}")
	public String form(@PathVariable String viewName, TestData testData, Model model) {
		return "modules/demo/demoForm" + StringUtils.cap(viewName);
	}




	/**
	 * 模拟表单
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "testFrom")
	public String testFrom(String policyId, Model model){
		model.addAttribute("policyId",policyId);
		return "modules/test/testFrom";
	}


	/**
	 * 模拟获取保单信息 工单信息 历史记录的controller
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getOrderDetail")
	public String getOrderDetail(String id, Model model){
		//添加保单信息
		model.addAttribute("policyInfo","这是保单的信息，保单id为："+id);
		//添加工单信息
		model.addAttribute("orderInfo","这是工单的信息");
		//添加流转历史信息
		List<String> orderList = new ArrayList<>();
		orderList.add("流转历史1");
		orderList.add("流转历史2");
		model.addAttribute("h1",HistoryConstructor.build(new History("1")));
		model.addAttribute("h2",HistoryConstructor.build(new History("2")));
		HistoryConstructor.build(new History("2"));
		model.addAttribute("orderLogs",orderList);
		return "modules/test/orderDetail#orderDetail";
	}


	/**
	 * 模拟表单
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "reqSvc")
	public String reqSvc(String policyId, Model model){
		model.addAttribute("policyId",policyId);


		return "modules/test/testFrom";
	}



	@Test
	public void req() {
		String secret = "123456";

		Map<String, String> dataMap = new HashMap<>();
		//作业帮充值套餐类型，单独申请
		dataMap.put("productId","1");
		//商户标识，单独申请
//		dataMap.put("merchant","");
		//商户标识，与merchant参数意义相同，当两个参数同时存在时优先使用partnerId，后续将全部使用partnerId参数
		dataMap.put("partnerId","test");
		//客户侧订单ID，保证唯一性，例如订单号
		dataMap.put("partnerOrderId","123");
		//用户标识
		dataMap.put("user","13100000020");
		//用户标识类型，可选值为：phone或uid。当值为phone时，如果用户不存在将会自动注册
		dataMap.put("userType","phone");
		dataMap.put("method","md5");
		//请求时间,时间戳格式: 1556161218
		Long timestamp = System.currentTimeMillis();
		dataMap.put("ts",timestamp.toString());

		//对键名按ASCII升序排列
		String[] sortedKeys = dataMap.keySet().toArray(new String[]{});
		Arrays.sort(sortedKeys);
		StringBuilder s2 = new StringBuilder();
		for (String key : sortedKeys) {
			s2.append(key).append("=").append(dataMap.get(key)).append("&");
		}
		s2.deleteCharAt(s2.length() - 1);

		String signMd5 = Md5Utils.md5(s2.toString());

		String sign = Md5Utils.md5(signMd5+secret);
		dataMap.put("sign",sign);

		String result = HttpClientUtils.post("https://test27.suanshubang.com",dataMap);

		System.out.println("返回参数："+result);
	}


}