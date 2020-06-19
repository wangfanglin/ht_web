package com.jeesite.modules.policy.service;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.bh.entity.BhFormInfo;
import com.jeesite.modules.bh.service.BhFormInfoService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htuserapplyinfo.entity.HtUserApplyInfo;
import com.jeesite.modules.htuserapplyinfo.service.HtUserApplyInfoService;
import com.jeesite.modules.policy.dao.ConvertDao;
import com.jeesite.modules.policy.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.jeesite.modules.policy.service.PolicyConverter.ConVertType.INSERT;

/**
 *
 * @author tangchao
 */
@Service
public class OldPolicyConverter implements PolicyConverter<OldPolicyInfo> {
    private final ConvertDao convertDao;
    private final BhFormInfoService bhFormInfoService;
    private final HtFormInfoService htFormInfoService;
    private final BhPolicyService bhPolicyService;
    private final HtUserApplyInfoService htUserApplyInfoService;
    private final PolicyConvertUtils policyConvertUtils;

    /**
     * 渤海渠道ID
     */
    private static final String OLD_CHANNEL_BOHAI = "42";

    /**
     * 捷信渠道ID
     */
    private static final String OLD_CHANNEL_JIEXIN = "81";

    @Autowired
    public OldPolicyConverter(ConvertDao convertDao, BhFormInfoService bhFormInfoService, HtFormInfoService htFormInfoService, BhPolicyService bhPolicyService, HtUserApplyInfoService htUserApplyInfoService, PolicyConvertUtils policyConvertUtils) {
        this.convertDao = convertDao;
        this.bhFormInfoService = bhFormInfoService;
        this.htFormInfoService = htFormInfoService;
        this.bhPolicyService = bhPolicyService;
        this.htUserApplyInfoService = htUserApplyInfoService;
        this.policyConvertUtils = policyConvertUtils;
    }


    /**
     * 对捷信的订单进行转换
     * @param policy
     * @return
     */
    @Override
    public PolicyInfo convert(OldPolicyInfo policy, ConVertType type) {

        BhPolicy boHaiInformation = null;
        HtFormInfo htFormInfo = null;

        //检查是否是渤海或者是捷信
        if(isBoHai(policy)){
            //检查hb_policy_info表是否已经有值,如果没有值,直接返回
            boHaiInformation = getBoHaiInformation(policy);
            if(boHaiInformation==null){
                return null;
            }
        }

        //检查唯一性，避免出现重复转换
        if (!checkUniqueness(policy, type)){return null;}

        HtUserApplyInfo apply = getApply(boHaiInformation);

        //判断应该重新生成还是绑定保单,如果拥有已经理赔的案件号，则只需update
        if(checkClmNo(apply,boHaiInformation)){

            htFormInfo = htFormInfoService.jxForm(apply.getId());

        }else{

            //添加保单
            PolicyInfo newPolicy = policyConvertUtils.savePolicyInfo(policy,"1");


            if(newPolicy==null) {
                return null;
            }

            //如果是渤海的订单，开启相应的待申请工单
            if(isBoHai(policy)){
                htFormInfo = htFormInfoService.openForm(newPolicy.getId());
            }

        }


        if(isBoHai(policy)&&htFormInfo!=null){
            BhFormInfo bhFormInfo = BhFormInfo.convertByOld(policy,boHaiInformation,htFormInfo);
            bhFormInfoService.save(bhFormInfo);
        }

        return null;
    }


    /**
     * 判断是否具有相同的报案号
     * @param apply             在线申请信息
     * @param boHaiInformation  渤海回传信息
     * @return  true：相同  fasle：不同
     */
    private boolean checkClmNo(HtUserApplyInfo apply, BhPolicy boHaiInformation) {
        if(apply==null) {
            return false;
        }
        if(boHaiInformation==null) {
            return false;
        }
        if(StringUtils.isBlank(apply.getClmNo())) {
            return false;
        }
        if(apply.equals(boHaiInformation.getClmno())) {
            return false;
        }
        return true;
    }

    /**
     * 通过渤海保单号，获取在线申请信息
     * @param boHaiInformation          渤海信息
     * @return
     */
    private HtUserApplyInfo getApply(BhPolicy boHaiInformation) {
        if(boHaiInformation==null) {
            return null;
        }
        HtUserApplyInfo htUserApplyInfo = new HtUserApplyInfo();
        htUserApplyInfo.setClmNo(boHaiInformation.getClmno());
        return htUserApplyInfoService.getByClmNo(htUserApplyInfo);
    }

    /**
     * 获取渤海的信息
     * @param policy
     * @return
     */
    private BhPolicy getBoHaiInformation(OldPolicyInfo policy) {
        BhPolicy bhPolicy = new BhPolicy();
        bhPolicy.setPolicyinfoid(policy.getId());
        return bhPolicyService.getByPolicyinfoid(bhPolicy);
    }


    /**
     * 通过保单信息，判断是否是渤海推送的保单
     * @param policy
     * @return
     */
    private boolean isBoHai(OldPolicyInfo policy) {
        String channelId = policy.getStrchannelname();
        if(OLD_CHANNEL_BOHAI.equals(channelId)) {
            return true;
        }
        if(OLD_CHANNEL_JIEXIN.equals(channelId)) {
            return true;
        }
        return false;
    }

    /**
     * 检查唯一性
     * @param policy
     * @param type
     * @return
     */
    private boolean checkUniqueness(Policy policy, ConVertType type) {
        if(type==INSERT){
            try {
                convertDao.insertOldConvert(policy.getId());
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
