package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 渤海报案
 *
 * @author wangfanglin
 * @since 2020/04/22
 */
@Data
public class ReportCaseParameter implements Parameter {

    /**
     * 报案图片流水号
     */
    private String replmgSerNo;
    /**
     * IMEI/SN
     */
    private String deviceCode;
    /**
     * 报案人姓名
     */
    private String linkName;
    /**
     * 报案时间
     */
    private String rptDate;
    /**
     * 省代码
     */
    private String province;
    /**
     * 市代码
     */
    private String city;
    /**
     * 区代码
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 报案人身份证号
     */
    private String idCard;
    /**
     * 设备分类ID
     */
    private String deviceType;
    /**
     * 设备品牌ID
     */
    private String deviceBrand;
    /**
     * 设备型号ID
     */
    private String deviceModel;
    /**
     * 颜色ID
     */
    private String deviceAttr;
    /**
     * 出险小类
     */
    private String accdntSubcaus;
    /**
     * 备注
     */
    private String remark;
    /**
     * 出险大类
     */
    private String accdntCaus;
    /**
     * 垫付标识
     */
    private String isJKXAdvancedFlag;
    /**
     * 维修方式
     */
    private String repair;
    /**
     * 报案方式
     */
    private String rptWay;
    /**
     * 出险时间
     */
    private String visit;
    /**
     * 出险经过
     */
    private String accdntDtl;
    /**
     * 理赔类型
     */
    private String kindCode;
    /**
     * 报案操作员（处理员）
     */
    private String rptCde;
    /**
     * 报案渠道
     */
    private String channelCode;
    /**
     * 故障信息
     */
    private List<AssessList> assessList;
    /**
     * 故障金额之和
     */
    private String sugAllAmt;
    /**
     * 维修总金额
     */
    private String allAmt;
    /**
     * 和德HD劲螭JC
     */
    private String systemId;

    public ReportCaseParameter(String deviceCode, String linkName, String rptDate, String city, String district, String tel, String idCard, String deviceType, String deviceModel, String deviceAttr, String accdntSubcaus, List<AssessList> assessList, String sugAllAmt, String systemId) {
        this.deviceCode = deviceCode;
        this.linkName = linkName;
        this.rptDate = rptDate;
        this.city = city;
        this.district = district;
        this.tel = tel;
        this.idCard = idCard;
        this.deviceType = deviceType;
        this.deviceModel = deviceModel;
        this.accdntSubcaus = accdntSubcaus;
        this.deviceAttr = deviceAttr;
        this.assessList = assessList;
        this.sugAllAmt = sugAllAmt;
        this.systemId = systemId;
    }


    @Override
    public boolean checkMandatory() {

        if(assessList!=null && !Parameter.checkListCheckMandatory(assessList)) {
            return false;
        }

        return StringUtils.isNotEmpty(deviceCode)&& StringUtils.isNotEmpty(linkName)
                && StringUtils.isNotEmpty(rptDate)&& StringUtils.isNotEmpty(city)
                && StringUtils.isNotEmpty(district)&& StringUtils.isNotEmpty(tel)
                && StringUtils.isNotEmpty(idCard)&& StringUtils.isNotEmpty(deviceType)
                && StringUtils.isNotEmpty(deviceModel)&& StringUtils.isNotEmpty(accdntSubcaus)
                && StringUtils.isNotEmpty(deviceAttr)&& StringUtils.isNotEmpty(sugAllAmt)
                && StringUtils.isNotEmpty(systemId);
    }
}
