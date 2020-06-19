package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 收款人信息添加的入参
 * @author tangchao
 */
@Data
public class PayeeInformationParameter implements Parameter {

    private String clmNo;           //渤海案件号
    private String plyNo;           //保单号
    private String province;        //省
    private String city;            //市
    private String area;            //区
    private String userName;        //姓名
    private String idCard;          //身份证号
    private String bankName;        //银行名称
    private String bankType;        //银行类别
    private String bankAccount;     //账号


    /**
     *
     * @param clmNo         渤海案件号
     * @param plyNo         保单号
     * @param province      省
     * @param city          市
     * @param area          区
     * @param userName      姓名
     * @param idCard        身份证号
     * @param bankName      银行名称
     * @param bankType      银行类别
     * @param bankAccount   账号
     */
    public PayeeInformationParameter(String clmNo, String plyNo, String province, String city, String area, String userName, String idCard, String bankName, String bankType, String bankAccount) {
        this.clmNo = clmNo;
        this.plyNo = plyNo;
        this.province = province;
        this.city = city;
        this.area = area;
        this.userName = userName;
        this.idCard = idCard;
        this.bankName = bankName;
        this.bankType = bankType;
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean checkMandatory() {
        return StringUtils.isNotEmpty(clmNo)&& StringUtils.isNotEmpty(plyNo)
                && StringUtils.isNotEmpty(province)&& StringUtils.isNotEmpty(city)
                && StringUtils.isNotEmpty(area)&& StringUtils.isNotEmpty(userName)
                && StringUtils.isNotEmpty(idCard)&& StringUtils.isNotEmpty(bankName)
                && StringUtils.isNotEmpty(bankType)&& StringUtils.isNotEmpty(bankAccount);
    }
}
