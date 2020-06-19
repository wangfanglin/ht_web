/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.common.DateUtil;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.entity.ProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.product.service.HtGroupProductChildService;
import com.jeesite.modules.product.service.ProductInfoService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tools.ant.util.DateUtils;
import org.hibernate.validator.constraints.Length;
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
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.service.HtGroupProductInfoService;

import java.math.BigDecimal;
import java.util.*;

/**
 * 组合产品表Controller
 * @author zhaoheifeng
 * @version 2020-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/product/htGroupProductInfo")
public class HtGroupProductInfoController extends BaseController {

	@Autowired
	private HtGroupProductInfoService htGroupProductInfoService;
	@Autowired
	private HtGroupProductChildService groupProductChildService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private HtPhoneModelInfoService phoneModelInfoService;
	@Autowired
	private ChannelProductInfoService channelProductInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public HtGroupProductInfo get(String id, boolean isNewRecord) {
		return htGroupProductInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:htGroupProductInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HtGroupProductInfo htGroupProductInfo, Model model) {
		model.addAttribute("htGroupProductInfo", htGroupProductInfo);
		return "modules/product/htGroupProductInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:htGroupProductInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<HtGroupProductInfo> listData(HtGroupProductInfo htGroupProductInfo, HttpServletRequest request, HttpServletResponse response) {
		htGroupProductInfo.setPage(new Page<>(request, response));
		Page<HtGroupProductInfo> page = htGroupProductInfoService.findPage(htGroupProductInfo);
		List<HtGroupProductInfo> list = page.getList();
		for (HtGroupProductInfo groupProductInfo : list) {
			String id = groupProductInfo.getId();
			//查出对应的产品名称
			List<Map<String,String>> result = htGroupProductInfoService.findProductName(id);
			for (Map<String, String> map : result) {
				String name = map.get("name");
				String type = map.get("type");
				if ("0".equals(type)){groupProductInfo.setWeiXiuName(name);}
				if ("1".equals(type)){groupProductInfo.setHuanJiName(name);}
				if ("2".equals(type)){groupProductInfo.setYanBaoName(name);}
				if ("3".equals(type)){groupProductInfo.setShuJuBaoName(name);}
			}
			groupProductInfo.setUpdateBy(UserUtils.get(groupProductInfo.getUpdateBy()).getUserName());

		}
		page.setList(list);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:htGroupProductInfo:view")
	@RequestMapping(value = "form")
	public String form(HtGroupProductInfo htGroupProductInfo, Model model) {
		if (htGroupProductInfo.getIsNewRecord()){htGroupProductInfo.setId(IdGen.randomLong()+"");}

		model.addAttribute("htGroupProductInfo", htGroupProductInfo);
		return "modules/product/htGroupProductInfoForm";
	}
    /**
     * 查看编辑表单
     */
    //@RequiresPermissions("product:htGroupProductInfo:edit")
    @RequestMapping(value = "next")
    public String next(HtGroupProductInfo htGroupProductInfo, Model model) {
    	//需要把 子产品、终止规则和 有效期查出来
		String isSingleValidity = htGroupProductInfo.getIsSingleValidity();//是否单独设置有效期
		String terminationRules = htGroupProductInfo.getTerminationRules();//终止规则
		String groupProductInfoId = htGroupProductInfo.getId();
		List<HtGroupProductChild> childByGroupId = groupProductChildService.findChildByGroupId(groupProductInfoId);
		//有效期 和 终止规则 去都在 子表里面
		StringBuilder weiXiuProductIds = new StringBuilder();
		StringBuilder huanJiProductIds = new StringBuilder();
		StringBuilder shuJuBaoProductIds = new StringBuilder();
		StringBuilder yanBaoProductIds = new StringBuilder();
		if (childByGroupId!=null&&childByGroupId.size()>0){
		for (HtGroupProductChild child : childByGroupId) {
			String productType = child.getProductType();
			if ("0".equals(productType)){
				weiXiuProductIds.append(child.getProductInfo().getId()).append(",");
				htGroupProductInfo.setWeiXiuOverResult(child);
			}
			if ("1".equals(productType)){
				huanJiProductIds.append(child.getProductInfo().getId());
				htGroupProductInfo.setHuanJiOverResult(child);
			}
			if ("2".equals(productType)){
				yanBaoProductIds.append(child.getProductInfo().getId());
				htGroupProductInfo.setYanBaoOverResult(child);
			}
			if ("3".equals(productType)){
				shuJuBaoProductIds.append(child.getProductInfo().getId());
				htGroupProductInfo.setShuJuBaoOverResult(child);
			}

		}
		}

		htGroupProductInfo.setWeiXiuProductIds(weiXiuProductIds.toString());
		htGroupProductInfo.setHuanJiProductIds(huanJiProductIds.toString());
		htGroupProductInfo.setShuJuBaoProductIds(shuJuBaoProductIds.toString());
		htGroupProductInfo.setYanBaoProductIds(yanBaoProductIds.toString());
		//关联的机型信息查出来

		htGroupProductInfo.setPhoneModelId(groupProductChildService.findPhoneMod(groupProductInfoId));
		//如果是单一规则需要将柜子从子产品中取出 放入主产品里
		if ("0".equals(isSingleValidity)){
			if (childByGroupId!=null&&childByGroupId.size()>0){
				HtGroupProductChild child = childByGroupId.get(0);
				htGroupProductInfo.setIsImmediately(child.getIsImmediately());
				htGroupProductInfo.setTakeDay(child.getTakeDay());
				htGroupProductInfo.setValidity(child.getValidity());
			}
		}
		model.addAttribute("htGroupProductInfo", htGroupProductInfo);
        String combinationType = htGroupProductInfo.getCombinationType();

		// 机型数据
		HtPhoneModelInfo htPhoneModelInfo = new HtPhoneModelInfo();
		htPhoneModelInfo.setStatus("0");
		List<HtPhoneModelInfo> phoneModels = phoneModelInfoService.findList(htPhoneModelInfo);
		model.addAttribute("phoneModels", phoneModels);

		/*销售上下限 和 限额 转为元*/

		if (htGroupProductInfo.getMaxPrice()!=null&&htGroupProductInfo.getMaxPrice()!=0){
			htGroupProductInfo.setMaxPrice(htGroupProductInfo.getMaxPrice() / 100); }
		if (htGroupProductInfo.getMinPrice()!=null && htGroupProductInfo.getMinPrice()!=0){
			htGroupProductInfo.setMinPrice(htGroupProductInfo.getMinPrice() / 100); }
		if (htGroupProductInfo.getCoverage()!=null){
			htGroupProductInfo.setCoverage(htGroupProductInfo.getCoverage().divide(new BigDecimal(100))); }

		//下面时控制页面的
        List<String> combinationList = Arrays.asList(combinationType.split(","));
        Boolean weixiu=false;
        Boolean huanji=false;
        Boolean yanbao=false;
        Boolean shujubao=false;
        for (String s : combinationList) {
            if ("0".equals(s)){weixiu=true;}
            if ("1".equals(s)){huanji=true;}
            if ("2".equals(s)){yanbao=true;}
            if ("3".equals(s)){shujubao=true;}
        }

		model.addAttribute("weixiu", weixiu);
        model.addAttribute("huanji", huanji);
        model.addAttribute("yanbao", yanbao);
		model.addAttribute("shujubao", shujubao);
		ProductInfo wProductInfo = new ProductInfo();
		wProductInfo.setProductType("0");
		List<ProductInfo> wList = productInfoService.findList(wProductInfo);

		ProductInfo hProductInfo = new ProductInfo();
		hProductInfo.setProductType("1");
		List<ProductInfo> hList = productInfoService.findList(hProductInfo);

		ProductInfo yProductInfo = new ProductInfo();
		yProductInfo.setProductType("2");
		List<ProductInfo> yList = productInfoService.findList(yProductInfo);

		ProductInfo sProductInfo = new ProductInfo();
		sProductInfo.setProductType("3");
		List<ProductInfo> sList = productInfoService.findList(sProductInfo);
		model.addAttribute("wList", wList);
		model.addAttribute("hList", hList);
		model.addAttribute("yList", yList);
		model.addAttribute("sList", sList);





		model.addAttribute("isSingleValidity", isSingleValidity);//是否单一设置
        model.addAttribute("terminationRules", terminationRules);//终止规则
        return "modules/product/htGroupProductInfoFormDetail";
    }




	/**
	 * 保存组合产品表
	 */
	@RequiresPermissions("product:htGroupProductInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated HtGroupProductInfo htGroupProductInfo) {
		htGroupProductInfoService.save(htGroupProductInfo);
		return renderResult(Global.TRUE, text("保存组合产品成功！"));
	}
	
	/**
	 * 停用组合产品表
	 */
	@RequiresPermissions("product:htGroupProductInfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(HtGroupProductInfo htGroupProductInfo) {
		ChannelProductInfo channelProductInfo = new ChannelProductInfo();
		channelProductInfo.setGroupProductId(htGroupProductInfo.getId());
		long count = channelProductInfoService.findCount(channelProductInfo);
		if (count>0){return renderResult(Global.FALSE, text("该产品下关联其他产品，无法停用！"));}
		htGroupProductInfo.setStatus(HtGroupProductInfo.STATUS_DISABLE);
		htGroupProductInfoService.updateStatus(htGroupProductInfo);
		return renderResult(Global.TRUE, text("停用组合产品成功"));
	}
	
	/**
	 * 启用组合产品表
	 */
	@RequiresPermissions("product:htGroupProductInfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(HtGroupProductInfo htGroupProductInfo) {
		htGroupProductInfo.setStatus(HtGroupProductInfo.STATUS_NORMAL);
		htGroupProductInfoService.updateStatus(htGroupProductInfo);
		return renderResult(Global.TRUE, text("启用组合产品成功"));
	}
	
	/**
	 * 删除组合产品表
	 */
	@RequiresPermissions("product:htGroupProductInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HtGroupProductInfo htGroupProductInfo) {
		ChannelProductInfo channelProductInfo = new ChannelProductInfo();
		channelProductInfo.setGroupProductId(htGroupProductInfo.getId());
		long count = channelProductInfoService.findCount(channelProductInfo);
		if (count>0){return renderResult(Global.FALSE, text("该产品下关联其他产品，无法删除！"));}
		htGroupProductInfoService.delete(htGroupProductInfo);
		return renderResult(Global.TRUE, text("删除组合产品成功！"));
	}


	/**
	 *
	 * @param groupProductId
	 * @return
	 */
	@RequestMapping(value = "findDetail")
	@ResponseBody
	public Map<String, Object> findDetail(String groupProductId) {
		HashMap<String, Object> result = MapUtils.newHashMap();
		HashMap<String, Object> map = new HashMap<>();
		try {
			HtGroupProductInfo htGroupProductInfo = htGroupProductInfoService.get(groupProductId);
			String terminationRules = htGroupProductInfo.getTerminationRules();//终止规则
			String isSingleValidity = htGroupProductInfo.getIsSingleValidity();//生效规则
			List<HtGroupProductChild> childByGroupId = groupProductChildService.findChildByGroupId(groupProductId);
			HtGroupProductChild child = childByGroupId.get(0);
			//是否为单一设置
			if ("1".equals(isSingleValidity)) {
				//单独
				map.put("isSingleValidity", true);
			}
			if ("0".equals(isSingleValidity)) {
				//统一
				String isImmediately = child.getIsImmediately();
				Long takeDay = child.getTakeDay();
				Long validity = child.getValidity();
				if ("1".equals(isImmediately)){map.put("isImmediately", "自购买之日即时生效");}
				if ("0".equals(isImmediately)){map.put("isImmediately", "购买之日第"+takeDay+"日生效");}
				map.put("validity", validity+"个月");
				map.put("isSingleValidity", false);
			}

			//终止规则
			if ("1".equals(terminationRules)) {
				//复合
				map.put("fuhe", htGroupProductInfo.getTerminationRulesSketch());
				map.put("terminationRules", true);
			}
			if ("0".equals(terminationRules)) {

				//单一规则 所有的子产品都是统一的终止规则
				// 终止规则
				String terminationRulesItem = htGroupProductInfo.getTerminationRulesItem();
				String rulesItem = DictUtils.getDictLabels("termination_rules_item", terminationRulesItem, terminationRulesItem);

				map.put("terminationRules", false);
				map.put("danyiguize",rulesItem);
			}


			//都有哪些 产品类型
			Boolean weixiu=false;
			Boolean huanxin=false;
			Boolean yanbao=false;
			Boolean shujubao=false;

			for (HtGroupProductChild groupgProductChild : childByGroupId) {
				String productType = groupgProductChild.getProductType();
				if ("0".equals(productType)){ weixiu = true;}
				if ("1".equals(productType)){huanxin = true;}
				if ("2".equals(productType)){yanbao = true;}//yanbao
				if ("3".equals(productType)){shujubao = true;}//shujubao
			}
			map.put("weixiu",weixiu);
			map.put("huanxin",huanxin);
			map.put("yanbao",yanbao);
			map.put("shujubao",shujubao);



			result.put("msg", "调用成功");
			result.put("status", "success");
			result.put("data",map);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "接口异常");
			result.put("status", "error");
			return result;
		}
	}

	/**
	 * 根据组合产品查询固定类型的产品（权益）
	 * @param productType
	 * @param groupProductId
	 * @return
	 */
	@RequestMapping(value = "listProduct")
	@ResponseBody
	public HashMap<Object, Object> listProduct(String productType,String groupProductId) {
		HashMap<Object, Object> result = MapUtils.newHashMap();
		HashMap<Object, Object> map = MapUtils.newHashMap();
		try {
			List<ProductInfo> productInfoList =	groupProductChildService.listProduct(productType,groupProductId);
			List<HtGroupProductChild> childByGroupId = groupProductChildService.findChildByGroupId(groupProductId);
			for (HtGroupProductChild child : childByGroupId) {
				String productType1 = child.getProductType();
				if (productType.equals(productType1)){
					String isImmediately = child.getIsImmediately();

					Long validity = child.getValidity();
					Long takeDay = child.getTakeDay();
					if ("1".equals(isImmediately)){map.put("isImmediately","自购买之日起立即生效");}
					if ("0".equals(isImmediately)){
						if (takeDay!=null){
							map.put("isImmediately","自购买第"+takeDay+"日生效");
						}else{
							Date effectTime = child.getEffectTime();
							String format = DateUtils.format(effectTime, "yyyy-MM-dd");
							map.put("isImmediately",format+"日生效");
						}
						}
					map.put("validity",validity+"个月");
					break;
				}
			}
			HtGroupProductInfo htGroupProductInfo = htGroupProductInfoService.get(groupProductId);
			Double minPrice = htGroupProductInfo.getMinPrice();
			Double maxPrice = htGroupProductInfo.getMaxPrice();
			for (ProductInfo productInfo : productInfoList) {
				productInfo.setMinPrice(minPrice);
				productInfo.setMaxPrice(maxPrice);
			}

			map.put("productInfoList",productInfoList);


			result.put("msg", "调用成功");
			result.put("status", "success");
			result.put("data",map);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "接口异常");
			result.put("status", "error");
		}
		return result;
	}
	
}