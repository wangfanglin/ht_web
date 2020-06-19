/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.policy.service;

import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.policy.dao.PolicyDetailDao;
import com.jeesite.modules.policy.dao.PolicyInfoDao;
import com.jeesite.modules.policy.entity.PolicyDetail;
import com.jeesite.modules.policy.entity.PolicyInfo;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.entity.HtGroupProductChild;
import com.jeesite.modules.product.entity.HtGroupProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.product.service.HtGroupProductChildService;
import com.jeesite.modules.product.service.HtGroupProductInfoService;
import com.jeesite.modules.sys.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

/**
 * 保单Service
 *
 * @author zhaohaifeng
 * @version 2020-02-24
 */
@Service
@Transactional(readOnly = true)
public class PolicyInfoService extends CrudService<PolicyInfoDao, PolicyInfo> {
    @Autowired
    private ChannelProductInfoService channelProductService;
    @Autowired
    private HtGroupProductChildService groupProductChildService;
    @Autowired
    private HtGroupProductInfoService groupProductInfoService;
    @Autowired
    private PolicyDetailDao policyDetailDao;
    @Autowired
    private PolicyDetailService policyDetailService;
    @Autowired
    private HtFormInfoService formInfoService;
    @Autowired
    private OfficeService officeService;

    /**
     * 获取单条数据
     *
     * @param policyInfo
     * @return
     */
    @Override
    public PolicyInfo get(PolicyInfo policyInfo) {
        return super.get(policyInfo);
    }

    /**
     * 查询分页数据
     *
     * @param policyInfo      查询条件
     * @return
     */
    @Override
    public Page<PolicyInfo> findPage(PolicyInfo policyInfo) {
        return super.findPage(policyInfo);
    }

    /**
     * 保存数据（插入或更新）
     *
     */
    @Override
    @Transactional(readOnly = false)
    public void save(PolicyInfo policy) {
        policy.setIsNewRecord(true);
        String policyId = IdGen.randomLong()+"";
        policy.setId(policyId);
        policy.setUniqueMark(IdGen.uuid());
        String channelId = policy.getChannelId();
        if ("42".equals(channelId)){policy.setFromtype("1");}//渤海}
        if ("81".equals(channelId)){policy.setFromtype("2");}//捷信}


        PolicyInfo policyInfo = this.updateUnit(policy, "1");
        String channelProductId = policyInfo.getChannelProductId();
        ChannelProductInfo channelProductInfo = channelProductService.get(channelProductId);
        policyInfo.setChannelProductName(channelProductInfo.getName());
        /*存入保险的详情信息*/
        String groupProductId = channelProductInfo.getGroupProductId();
        HtGroupProductInfo htGroupProductInfo = groupProductInfoService.get(groupProductId);
        List<HtGroupProductChild> childList = groupProductChildService.findChildByGroupId(groupProductId);

        //先算 保单的保额
        String priceFlag = htGroupProductInfo.getPriceFlag();
        if ("1".equals(priceFlag)){
            //手机金额作为商品保额
            BigDecimal intSellPrice = policy.getIntSellPrice();
            policy.setSurplusCoverage(intSellPrice);
        }else if ("0".equals(priceFlag)){
            //标的金额 不作为商品保额
            BigDecimal coverage = htGroupProductInfo.getCoverage();
            policy.setSurplusCoverage(coverage);
        }
        //根据保单的起止时间  改变状态
        long startEffective = policyInfo.getDateEffectiveDate().getTime();
        long endEffective = policyInfo.getDateEndDate().getTime();
        long timeMillis = System.currentTimeMillis();
        String policyStatus="";
        if (timeMillis>startEffective&&timeMillis<endEffective) { policyStatus = "1"; }else{
            if (timeMillis<startEffective) { policyStatus = "0"; }
            if (timeMillis>endEffective) { policyStatus = "3"; }
        }
        policyInfo.setPolicyStatus(policyStatus);



        //保单的起止时间是手动选的

        ArrayList<PolicyDetail> policyDetails = new ArrayList<>();
        //保单的详情的权益信息  状态和起止时间
        if (childList!=null&&childList.size()>0){
        for (HtGroupProductChild child : childList) {
            PolicyDetail policyDetail = new PolicyDetail();
            policyDetail.setPolicyInfo(new PolicyInfo(policyId));
            policyDetail.setHtGroupProductChild(child);
            String isImmediately = child.getIsImmediately();
            Long takeDay = child.getTakeDay();
            Date effectTime = child.getEffectTime();
            Long validity = child.getValidity();
            if ("1".equals(isImmediately)) {
                //1立即生效
                Date date = policyInfo.getDateEffectiveDate();
                policyDetail.setEquityStartTime(date);
                if (validity != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.MONTH, validity.intValue());
                    policyDetail.setEquityEndTime(calendar.getTime());
                }
            } else if ("0".equals(isImmediately)) {
                //0否
                if (effectTime != null) {
                    policyDetail.setEquityStartTime(effectTime);
                    if (validity != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(effectTime);
                        calendar.add(Calendar.MONTH, validity.intValue());
                        policyDetail.setEquityEndTime(calendar.getTime());
                    }
                } else if (takeDay != null) {
                    Date date = policyInfo.getDateEffectiveDate();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, takeDay.intValue());
                    //这是生效时间
                    Date time = calendar.getTime();
                    policyDetail.setEquityStartTime(time);
                    if (validity != null) {
                        calendar.setTime(time);
                        calendar.add(Calendar.MONTH, validity.intValue());
                        policyDetail.setEquityEndTime(calendar.getTime());
                    }

                }
            }
            long startTime = policyDetail.getEquityStartTime().getTime();
            long endTime = policyDetail.getEquityEndTime().getTime();
            long now = System.currentTimeMillis();
            String status="";
            if (startTime<now&&endTime>now) { status = "1"; }
            if (startTime>now) { status = "0"; }
            if (endTime<now) { status = "3"; }

            policyDetail.setEquityStatus(status);
            policyDetails.add(policyDetail);
        }
        policyDetailDao.insertBatch(policyDetails);
        }
        super.save(policyInfo);
    }

    /**
     * 更新状态
     *
     * @param policyInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(PolicyInfo policyInfo) {
        super.updateStatus(policyInfo);
    }

    /**
     * 删除数据
     *
     * @param policyInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(PolicyInfo policyInfo) {
        super.delete(policyInfo);
    }

    /**
     * 更改单位
     *
     * @param policyInfo
     * @return
     */
    public PolicyInfo updateUnit(PolicyInfo policyInfo, String flag) {
        //1 是元转分  0 是分转元
        if ("0".equals(flag)) {
            if (policyInfo.getIntSellPrice() != null) {
                policyInfo.setIntSellPrice(policyInfo.getIntSellPrice().divide(new BigDecimal(100)));
            }
            if (policyInfo.getIntProductPrice() != null) {
                policyInfo.setIntProductPrice(policyInfo.getIntProductPrice().divide(new BigDecimal(100)));
            }
            if (policyInfo.getSurplusCoverage() != null) {
                policyInfo.setSurplusCoverage(policyInfo.getSurplusCoverage().divide(new BigDecimal(100)));
            }
        } else if ("1".equals(flag)) {
            if (policyInfo.getIntSellPrice() != null) {
                policyInfo.setIntSellPrice(policyInfo.getIntSellPrice() .multiply(new BigDecimal(100)));
            }
            if (policyInfo.getIntProductPrice() != null) {
                policyInfo.setIntProductPrice(policyInfo.getIntProductPrice() .multiply(new BigDecimal(100)));
            }
            if (policyInfo.getSurplusCoverage() != null) {
                policyInfo.setSurplusCoverage(policyInfo.getSurplusCoverage()  .multiply(new BigDecimal(100)));
            }
        }
        return policyInfo;
    }

    /**
     * 扣减保额
     * @param policyId
     * @param money
     * @return
     */
    @Transactional(readOnly = false)
    public Double minusCoverage(String policyId, double money,String formId) {
        PolicyInfo policyInfo = this.get(policyId);
        BigDecimal surplusCoverage = policyInfo.getSurplusCoverage();
        double overTop=0;
        if (surplusCoverage.doubleValue()>=money){
            //正常扣除
          BigDecimal remain = surplusCoverage.subtract(new BigDecimal(money));
            dao.updateSurplusCoverage(remain.doubleValue(),policyId);
            //修改保单限额//加入日志
            dao.insertCoverageLog(money,policyId,formId);

        }else{
            //保额不足 算出差价 超出的作为自付额
            overTop = money-surplusCoverage.doubleValue();
            dao.updateSurplusCoverage(0,policyId);
            //保单限额归零//加入日志
             dao.insertCoverageLog(surplusCoverage.doubleValue(),policyId,formId);
        }
        //返回自付额
        return overTop;
    }

    /**
     * 终止保单的
     * @param policyId
     */
    @Transactional(readOnly = false)
    public void  stopPolicy(String policyId) {
        //将保单的  状态改为已失效
        PolicyInfo policyInfo = new PolicyInfo(policyId);
        policyInfo.setPolicyStatus("2");
        dao.update(policyInfo);
        //将所有的子权益改为已失效
        PolicyDetail policyDetail = new PolicyDetail();
        policyDetail.setPolicyInfo(new PolicyInfo(policyId));
        List<PolicyDetail> policyDetailList = policyDetailService.findList(policyDetail);
        if (policyDetailList!=null&&policyDetailList.size()>0){
        for (PolicyDetail detail : policyDetailList) {
            detail.setEquityStatus("2");
            policyDetailService.save(detail);
        }
        }
    }


    public HtGroupProductInfo findGpBypolicyId(String policyId) {
        PolicyInfo policyInfo = this.get(policyId);
        ChannelProductInfo channelProductInfo = channelProductService.get(policyInfo.getChannelProductId());
        return groupProductInfoService.get(channelProductInfo.getGroupProductId());
    }




    /**
     * 加入渤海的保单日志
     * @param uniqueMark
     */
    public void insertBhLog(String uniqueMark) {
        dao.insertBhLog(uniqueMark);
    }
    /**
     * 查询渤海的保单日志数量
     * @param uniqueMark
     */
    public int check(String uniqueMark) {
        return dao.check(uniqueMark);
    }

    /**
     * 单一规则时 是否终止保单
     */
    public void terminate(String formId){
        //判断剩余保额
        HtFormInfo formInfo = formInfoService.get(formId);
        String policyId = formInfo.getPolicyInfo().getId();
        PolicyInfo policyInfo = this.get(policyId);
        //取出最低的次数
        int min = Integer.valueOf(dao.findCountLast(policyId));

        Boolean flag = false;
        /*查询保单的出险次数*/
        int countWx = Integer.valueOf(dao.findCountWx(policyId));
       int countHx = Integer.valueOf(dao.findCountHx(policyId));
      // String countYb = dao.findCountYb(policyId);
      // String countSjb =dao.findCountSjb(policyId);
        /*延保产品和 数据保产品 走的都是维修流程*/
        BigDecimal surplusCoverage = policyInfo.getSurplusCoverage();

        HtGroupProductInfo htGroupProductInfo = this.findGpBypolicyId(policyId);
        String terminationRulesItem = htGroupProductInfo.getTerminationRulesItem();
        if (terminationRulesItem!=null&&!"".equals(terminationRulesItem)){
            String[] split = terminationRulesItem.split(",");
            List<String> list = Arrays.asList(split);
            for (String item : list) {
                //维修次数  次数按照最低的次数
                if ("0".equals(item)){if (countWx>=min){flag = true;}}
                if ("1".equals(item)){if (countHx>=min){flag = true; }}
                if ("2".equals(item)){if (surplusCoverage.doubleValue()<=0){flag = true;}}
                if ("3".equals(item)){if (countWx>=min){flag = true;}}
            }
        }
            if (flag){this.stopPolicy(policyId);}
    }

    public void updatePolicyFlag(String policyId) {
        dao.updatePolicyFlag(policyId);
    }
}