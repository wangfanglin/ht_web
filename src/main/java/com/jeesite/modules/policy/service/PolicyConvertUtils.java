package com.jeesite.modules.policy.service;


import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.brandinfo.entity.HtBrandInfo;
import com.jeesite.modules.brandinfo.service.HtBrandInfoService;
import com.jeesite.modules.htbrandmapinfo.entity.HtBrandMapInfo;
import com.jeesite.modules.htbrandmapinfo.service.HtBrandMapInfoService;
import com.jeesite.modules.phonemodelinfo.entity.HtPhoneModelInfo;
import com.jeesite.modules.phonemodelinfo.service.HtPhoneModelInfoService;
import com.jeesite.modules.policy.entity.*;
import com.jeesite.modules.product.entity.ChannelProductInfo;
import com.jeesite.modules.product.service.ChannelProductInfoService;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 该类为保单信息转换提供一些共用的方法
 * @author tangchao
 */
@Service
public class PolicyConvertUtils {
    private final OfficeService officeService;
    private final HtBrandMapInfoService brandMapInfoService;
    private final HtBrandInfoService htBrandInfoService;
    private final ChannelProductInfoService channelProductInfoService;
    private final HtFailPolicyService failPolicyService;
    private final PolicyInfoService policyInfoService;
    private final HtPhoneModelInfoService htPhoneModelInfoService;

    @Autowired
    public PolicyConvertUtils(OfficeService officeService, HtBrandMapInfoService brandMapInfoService, HtBrandInfoService htBrandInfoService, ChannelProductInfoService channelProductInfoService, HtFailPolicyService failPolicyService, PolicyInfoService policyInfoService,HtPhoneModelInfoService htPhoneModelInfoService) {
        this.officeService = officeService;
        this.brandMapInfoService = brandMapInfoService;
        this.htBrandInfoService = htBrandInfoService;
        this.channelProductInfoService = channelProductInfoService;
        this.failPolicyService = failPolicyService;
        this.policyInfoService = policyInfoService;
        this.htPhoneModelInfoService = htPhoneModelInfoService;
    }


    /**
     * 根据老Policy 创建新的Policy
     * 如果转换失败，放置到保单映射失败表中
     * @param policy
     * @return
     */
    public PolicyInfo savePolicyInfo(Policy policy,String type) {
        PolicyInfo newPolicy = policy.convertNew();

        StringBuffer errorContent = new StringBuffer();

        //转换渠道
        convertChannel(policy, newPolicy, errorContent);

        //转换品牌
        convertBrand(policy, newPolicy, errorContent);

        //转换型号
        convertModel(policy,newPolicy,errorContent);

        //转换产品
        convertProduct(policy, newPolicy, errorContent);


        //查看是否转换成功
        if(StringUtils.isNotBlank(errorContent)){
            //如果转换失败，将保单放置到转换失败的保单表中
            insertFailPolicy(policy, newPolicy, errorContent,type);
            return null;
        }

        //保存保单
        policyInfoService.save(newPolicy);
        return newPolicy;
    }




    /**
     * 转换失败的信息添加
     * @param policy
     * @param newPolicy
     * @param errorContent
     */
    public void insertFailPolicy(Policy policy, PolicyInfo newPolicy, StringBuffer errorContent,String type) {
        newPolicy.setIsError("1");
        HtFailPolicy failPolicy = new HtFailPolicy(policy,newPolicy,errorContent,type);
        failPolicyService.insert(failPolicy);
    }

    /**
     * 转换渠道
     * @param policy
     * @param newPolicy
     * @param errorContent
     */
    public void convertChannel(Policy policy, PolicyInfo newPolicy, StringBuffer errorContent) {
        String id = policy.getNewChannelId()==null?policy.getStrchannelname():policy.getNewChannelId();
        Office office = officeService.get(new Office(id));
        if(office!=null){
            newPolicy.setStrChannelName(office.getOfficeName());
            newPolicy.setChannelId(office.getId());
        }else{
            newPolicy.setStrChannelName(null);
            newPolicy.setChannelId(null);
            errorContent.append("渠道转换异常,");
        }
    }


    /**
     * 转换机型
     * @param policy
     * @param newPolicy
     * @param errorContent
     */
    public void convertModel(Policy policy, PolicyInfo newPolicy, StringBuffer errorContent) {

        if(policy.getNewModel()!=null){

            String id = policy.getNewModel()==null?policy.getStrphonemodel():policy.getNewModel();
            HtPhoneModelInfo htPhoneModelInfo = htPhoneModelInfoService.get(id);
            if(htPhoneModelInfo!=null){
                newPolicy.setStrPhoneModel(id);
            }else{
                newPolicy.setStrPhoneModel(null);
                errorContent.append("机型转换异常,");
            }
        }else{
            newPolicy.setStrPhoneModel(null);
            errorContent.append("机型转换异常,");
        }
    }

    /**
     * 转换品牌
     * @param policy
     * @param newPolicy
     * @param errorContent
     */
    public void convertBrand(Policy policy, PolicyInfo newPolicy, StringBuffer errorContent) {

        if(policy.getNewBrand()==null){
            HtBrandMapInfo brandMap = new HtBrandMapInfo();
            brandMap.setDistributionId(policy.getStrchannelname());
            brandMap.setOriginalBrand(policy.getStrphonebrand());
            brandMap = brandMapInfoService.getByDistributionIdAndOriginalBrand(brandMap);
            if(brandMap!=null){
                newPolicy.setStrPhoneBrand(brandMap.getMapBrandId());
            }else{
                newPolicy.setStrPhoneBrand(null);
                errorContent.append("品牌转换异常,");
            }
        }else{
            HtBrandInfo htBrandInfo = htBrandInfoService.get(new HtBrandInfo(policy.getNewBrand()));
            if(htBrandInfo!=null){
                newPolicy.setStrPhoneBrand(htBrandInfo.getId());
            }else{
                newPolicy.setStrPhoneBrand(null);
                errorContent.append("品牌转换异常,");
            }
        }




    }

    /**
     * 转换产品
     * @param policy
     * @param newPolicy
     * @param errorContent
     */
    public void convertProduct(Policy policy, PolicyInfo newPolicy, StringBuffer errorContent) {
        String id = policy.getNewChannelProInfoId()==null?policy.getChannelProInfoId():policy.getNewChannelProInfoId();
        ChannelProductInfo channelProductInfo = new ChannelProductInfo(id);
        channelProductInfo = channelProductInfoService.get(channelProductInfo);
        if(channelProductInfo!=null){
            newPolicy.setChannelProductId(channelProductInfo.getId());
        }else{
            newPolicy.setChannelProductId(null);
            errorContent.append("渠道产品转换异常,");
        }
    }

}
