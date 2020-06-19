package com.jeesite.modules.ds;

import com.jeesite.common.entity.Extend;
import com.jeesite.modules.brandinfo.dao.HtBrandInfoDao;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.channel.dao.HtPurchasingChannelsDao;
import com.jeesite.modules.channel.entity.HtPurchasingChannels;
import com.jeesite.modules.expressage.dao.HtExpressageDao;
import com.jeesite.modules.expressage.entity.HtExpressage;
import com.jeesite.modules.htmaintenancepoint.dao.HtMaintenancePointDao;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.intermediary.dao.HtIntermediaryServiceDao;
import com.jeesite.modules.intermediary.entity.HtIntermediaryService;
import com.jeesite.modules.phonemodelinfo.dao.HtPhoneModelInfoDao;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.product.dao.ChannelProductInfoDao;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.provider.dao.HtInsuranceProviderInfoDao;
import com.jeesite.modules.provider.entity.HtInsuranceProviderInfo;
import com.jeesite.modules.sys.dao.OfficeDao;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用来同步数据
 *
 * @author:ZHF
 * @Time: 2020/4/21 16:01
 */
//status  0正常 1删除 2停用
    @Service
    @Transactional(readOnly=true)
public class DataSync {
    @Autowired
    private DataSyncDao dataSyncDao;
    @Autowired
    private HtBrandInfoDao brandInfoDao;
    @Autowired
    private HtPhoneModelInfoDao phoneModelInfoDao;
    @Autowired
    private HtPurchasingChannelsDao purchasingChannelsDao;
    @Autowired
    private HtExpressageDao expressageDao;
    @Autowired
    private OfficeDao officeDao;
    @Autowired
    private HtIntermediaryServiceDao intermediaryServiceDao;
    @Autowired
    private HtInsuranceProviderInfoDao insuranceProviderInfoDao;
    @Autowired
    private ChannelProductInfoDao channelProductInfoDao;
    @Autowired
    private HtMaintenancePointDao maintenancePointDao;

    private static final String userCode = UserUtils.getUser().getUserCode();

    /**
     * 同步品牌
     */
    @Transactional(readOnly=false)
    public void brand() {
        List<String> oldBrand = dataSyncDao.findOldbrand();

        ArrayList<HtBrandInfo> htBrandInfos = new ArrayList<>();
        for (String brand : oldBrand) {
            HtBrandInfo htBrandInfo = new HtBrandInfo();
            htBrandInfo.setName(brand);
            htBrandInfo.setCreateBy(userCode);
            htBrandInfo.setCreateDate(new Date());
            htBrandInfo.setUpdateBy(userCode);
            htBrandInfo.setUpdateDate(new Date());
            htBrandInfo.setStatus("0");
            htBrandInfos.add(htBrandInfo);
        }
        brandInfoDao.insertBatch(htBrandInfos);
    }

    /**
     * 同步机型数据  重新跑
     */
    @Transactional(readOnly=false)
    public void phoneModel() {
        List<Map<String, Object>> oldPhoneModel = dataSyncDao.findOldPhoneModel();
        ArrayList<HtPhoneModelInfo> htPhoneModelInfos = new ArrayList<>();
        for (Map<String, Object> map : oldPhoneModel) {
            HtPhoneModelInfo htPhoneModelInfo = new HtPhoneModelInfo((String) map.get("id"));
            htPhoneModelInfo.setBrandId((String) map.get("brandID"));
            htPhoneModelInfo.setDistributionId("0_XS001001001");//暂时没有渠道  需要给一个默认渠道
            htPhoneModelInfo.setModel((String) map.get("model"));
            if (map.get("price_low") != null && !"".equals(map.get("price_low"))) {
                htPhoneModelInfo.setPriceLow(Double.valueOf((String) map.get("price_low")));
            }else{
                htPhoneModelInfo.setPriceLow(0.00);
            }
            if (map.get("price_high") != null && !"".equals(map.get("price_high"))) {
                htPhoneModelInfo.setPriceHigh(Double.valueOf((String) map.get("price_high")));
            }else{
                htPhoneModelInfo.setPriceHigh(0.00);
            }
            if (map.get("phone_price") != null && !"".equals(map.get("phone_price"))) {
                htPhoneModelInfo.setPhonePrice(((BigDecimal) map.get("phone_price")).doubleValue());
            }else{
                htPhoneModelInfo.setPhonePrice(0.00);
            }
            if (map.get("screen_price") != null && !"".equals(map.get("screen_price"))) {
                htPhoneModelInfo.setScreenPrice(((BigDecimal) map.get("screen_price")).doubleValue());
            }else{
                htPhoneModelInfo.setScreenPrice(0.00);
            }
            htPhoneModelInfo.setSort(50);
            htPhoneModelInfo.setCreateBy(userCode);
            htPhoneModelInfo.setCreateDate(new Date());
            htPhoneModelInfo.setUpdateBy(userCode);
            htPhoneModelInfo.setUpdateDate(new Date());
            htPhoneModelInfo.setStatus("0");
            htPhoneModelInfos.add(htPhoneModelInfo);
        }
        phoneModelInfoDao.insertBatch(htPhoneModelInfos);
    }

    /**
     * 同步采购渠道数据
     */
    @Transactional(readOnly=false)
    public void purchasingChannels() {
        List<Map<String, Object>> purchasing = dataSyncDao.findOldPurchasing();
        ArrayList<HtPurchasingChannels> list = new ArrayList<>();
        for (Map<String, Object> map : purchasing) {
            HtPurchasingChannels htPurchasingChannels = new HtPurchasingChannels(String.valueOf(map.get("id")));
            htPurchasingChannels.setStrname((String) map.get("strName"));
            htPurchasingChannels.setIntcommission(new BigDecimal((Double) map.get("intCommission")));
            String clearingForm = (String) map.get("strAccount");
            if ("日结".equals(clearingForm)) {
                htPurchasingChannels.setClearingForm("1");
            } else if ("月结".equals(clearingForm)) {
                htPurchasingChannels.setClearingForm("0");
            }
            htPurchasingChannels.setCode((String) map.get("strBusinessID"));
            htPurchasingChannels.setCreateBy(userCode);
            htPurchasingChannels.setCreateDate(new Date());
            htPurchasingChannels.setUpdateBy(userCode);
            htPurchasingChannels.setUpdateDate(new Date());
            String delFlag = (String) map.get("del_flag");
            if ("0".equals(delFlag)) {
                htPurchasingChannels.setStatus("0");
            } else {
                htPurchasingChannels.setStatus("1");
            }
            list.add(htPurchasingChannels);
        }
        purchasingChannelsDao.insertBatch(list);
    }
    /**
     * 同步快递渠道数据
     */
    @Transactional(readOnly=false)
    public void expressageChannels() {
        List<Map<String, Object>> expressageList = dataSyncDao.findOldExpressage();
        ArrayList<HtExpressage> list = new ArrayList<>();
        for (Map<String, Object> map : expressageList) {
            HtExpressage expressage = new HtExpressage((String) map.get("id"));
            expressage.setExprid((String) map.get("exprId"));
            expressage.setExprname((String) map.get("exprName"));
            String exprPay = (String)map.get("exprPay");
            if ("日结".equals(exprPay)) {
                expressage.setExprpay("1");
            } else if ("月结".equals(exprPay)) {
                expressage.setExprpay("0");
            }
            expressage.setRemark((String) map.get("remarks"));
            expressage.setCreateBy(userCode);
            expressage.setCreateDate(new Date());
            expressage.setUpdateBy(userCode);
            expressage.setUpdateDate(new Date());
            String delFlag = (String) map.get("del_flag");
            if ("0".equals(delFlag)) {
                expressage.setStatus("0");
            } else {
                expressage.setStatus("1");
            }

            list.add(expressage);
        }
        expressageDao.insertBatch(list);
    }

    /**
     * 同步销售渠道数据
     */
    @Transactional(readOnly=false)
    public void saleChannels() {
        List<Map<String, Object>> saleList = dataSyncDao.findOldSale();
        ArrayList<Office> list = new ArrayList<>();
        for (int i = 0; i < saleList.size(); i++) {
            Map<String, Object> map = saleList.get(i);
            Office office = new Office(map.get("id")+"");
            office.setParentCode("0_XS001");
            office.setParentCodes("0_XS001,");
            office.setTreeSort(count(i));  //基数60 每次30递增
            office.setTreeSorts("0000000070,00000000"+StringCount(i));
            office.setTreeLeaf("0");
            office.setTreeLevel(1);
            office.setTreeNames("销售渠道/"+(String)map.get("salechannelname"));
            office.setViewCode((String)map.get("salechannelid"));
            office.setOfficeName((String)map.get("salechannelname"));
            office.setFullName((String)map.get("salechannelname"));
            office.setOfficeType("2");//设置为市级公司
            office.setLeader((String)map.get("stake_holder"));
            office.setPhone((String)map.get("telephone"));
            Extend extend = new Extend();
            extend.setExtendS1((String)map.get("position"));//扩展字段1 保存职务信息
            extend.setExtendS2((String)map.get("linkman"));//扩展字段2 保存联系人信息
            extend.setExtendS3((String)map.get("account"));//扩展字段3 保存账号信息
            extend.setExtendS4((String)map.get("bank"));//扩展字段4 保存开户行信息
            //还有一个人员渠道
            office.setExtend(extend);
            office.setRemarks((String) map.get("remarks"));
            office.setCreateBy(userCode);
            office.setCreateDate(new Date());
            office.setUpdateBy(userCode);
            office.setUpdateDate(new Date());
            String delFlag = (String) map.get("del_flag");
            if ("0".equals(delFlag)) {
                office.setStatus("0");
            } else {
                office.setStatus("1");
            }

            list.add(office);
        }
        officeDao.insertBatch(list);
    }
    public int count (int i) {
       int  num  = 60 ; int fold = 30;
       if (i==0){return num;}
           return i*fold+num;
    }
    public String StringCount (int i) {
        StringBuilder builder = new StringBuilder();
        int  num = 60 ; int fold = 30;
        if (i==0){ return builder.append(num).append(",").toString(); }
        return builder.append(i*fold+num).append(",").toString();
    }


    /**
     * 同步中介服务商数据  重新跑
     */
    @Transactional(readOnly=false)
    public void agentChannels() {
        List<Map<String, Object>> saleList = dataSyncDao.findOldAgent();
        ArrayList<HtIntermediaryService> list = new ArrayList<>();
        for (Map<String, Object> map : saleList) {
            HtIntermediaryService intermediaryService = new HtIntermediaryService((String)map.get("id"));
            intermediaryService.setAgentid((String)map.get("agentid"));
            intermediaryService.setName((String)map.get("name"));
            intermediaryService.setRemark((String)map.get("remarks"));
            intermediaryService.setServicecharge((String)map.get("servicecharge"));
            intermediaryService.setCreateBy(userCode);
            intermediaryService.setCreateDate(new Date());
            intermediaryService.setUpdateBy(userCode);
            intermediaryService.setUpdateDate(new Date());
            String delFlag = (String) map.get("delflag");
            if ("0".equals(delFlag)) {
                intermediaryService.setStatus("0");
            } else {
                intermediaryService.setStatus("1");
            }

            list.add(intermediaryService);
        }
        intermediaryServiceDao.insertBatch(list);
    }

    /**
     * 同步保险服务商数据
     */
    @Transactional(readOnly=false)
    public void supplierChannels() {
        List<Map<String, Object>> saleList = dataSyncDao.findOldSupplier();
        ArrayList<HtInsuranceProviderInfo> list = new ArrayList<>();
        for (Map<String, Object> map : saleList) {
            HtInsuranceProviderInfo insuranceProviderInfo = new HtInsuranceProviderInfo(String.valueOf(map.get("id")));
            insuranceProviderInfo.setSerialnumber((String)map.get("serialnumber"));
            insuranceProviderInfo.setSuppliername((String)map.get("suppliername"));
            insuranceProviderInfo.setCreateBy(userCode);
            insuranceProviderInfo.setCreateDate(new Date());
            insuranceProviderInfo.setUpdateBy(userCode);
            insuranceProviderInfo.setUpdateDate(new Date());
            insuranceProviderInfo.setStatus("0");
            list.add(insuranceProviderInfo);
        }
        insuranceProviderInfoDao.insertBatch(list);
    }

    /**
     * 同步渠道商品数据
     */
    @Transactional(readOnly=false)
    public void productChannels() {
        List<Map<String, Object>> saleList = dataSyncDao.findOldProduct();
        ArrayList<ChannelProductInfo> list = new ArrayList<>();
        for (Map<String, Object> map : saleList) {
            ChannelProductInfo channelProductInfo = new ChannelProductInfo(String.valueOf(map.get("id")));
            channelProductInfo.setDistributionId(String.valueOf(map.get("channelSaleID")));
            channelProductInfo.setDistributionName((String)map.get("channelSaleName"));
            channelProductInfo.setIntermediaryServiceId((String)map.get("agentID"));
            channelProductInfo.setName((String)map.get("productName"));
            channelProductInfo.setCreateBy(userCode);
            channelProductInfo.setCreateDate(new Date());
            channelProductInfo.setUpdateBy(userCode);
            channelProductInfo.setUpdateDate(new Date());
            String delFlag = (String) map.get("del_flag");
            if ("0".equals(delFlag)) {
                channelProductInfo.setStatus("0");
                channelProductInfo.setIsStart("1");
            } else {
                channelProductInfo.setStatus("1");
                channelProductInfo.setIsStart("0");
            }
            list.add(channelProductInfo);
        }
        channelProductInfoDao.insertBatch(list);
    }

    /**
     * 同步维修站点数据
     */
    @Transactional(readOnly=false)
    public void maintenancePoint() {
        List<Map<String, Object>> saleList = dataSyncDao.findOldMaintenancePoint();
        ArrayList<HtMaintenancePoint> list = new ArrayList<>();
        for (Map<String, Object> map : saleList) {
            HtMaintenancePoint maintenancePoint = new HtMaintenancePoint((String)map.get("id"));
            maintenancePoint.setMaintenancePointName((String)map.get("name"));
            String master = (String) map.get("master");
            String phone = (String) map.get("phone");
            maintenancePoint.setContactOne(master);
            maintenancePoint.setContactNumberOne(phone);
            maintenancePoint.setContactTwo(master);
            maintenancePoint.setContactNumberTwo(phone);
            //maintenancePoint.setProvince();
            //maintenancePoint.setCity();
            //maintenancePoint.setArea();
            maintenancePoint.setAddress((String)map.get("address"));
            //maintenancePoint.setLongitudeLatitude();暂无经纬度信息
            //maintenancePoint.setCoverageProvince();暂无覆盖省信息
            //maintenancePoint.setCoverageCity();暂无覆盖市信息
            //maintenancePoint.setContractLifeStart();//暂无合同开始时间
            //maintenancePoint.setContractLifeEnd();//暂无合同结束时间
            //maintenancePoint.setDoorPicture(); 暂无门头照片
            maintenancePoint.setWhetherOrder("0");//默认为不接单
            //maintenancePoint.setOrganizationId("0_WXWD001001002");  默认不挂载机构
            maintenancePoint.setRemarks((String)map.get("remarks"));
            maintenancePoint.setCreateBy(userCode);
            maintenancePoint.setCreateDate(new Date());
            maintenancePoint.setUpdateBy(userCode);
            maintenancePoint.setUpdateDate(new Date());
            String delFlag = (String) map.get("del_flag");
            if ("0".equals(delFlag)) {
                maintenancePoint.setStatus("0");
            } else {
                maintenancePoint.setStatus("1");
            }
            list.add(maintenancePoint);
        }
        maintenancePointDao.insertBatch(list);
    }
}
