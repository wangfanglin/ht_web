package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 维修信息同步的入参
 * @author tangchao
 */

@Data
public class SynRepairInfoParameter implements Parameter {
    private String clmNo;                       //案件号
    private String orderId;                     //订单号   渤海理赔系统现存订单号
    private String deviceCode;                  //设备码,IMEI
    private String repairType;                  //维修类型  Y成功 N失败
    private String remark;                      //说明
    private String operationTime;               //操作时间  YYYY-MM-DD HH:mm:ss
    private String repairCompany;               //风控商/维修商名称             默认为 和德信通
    private String serviceTime;                 //服务时间  YYYY-MM-DD HH:mm:ss
    private String repairName;                  //工程师名称
    private String repairTel;                   //工程师电话
    private String repairMode;                  //维修方式  5100001上门 5100002到店 5100003邮寄
    private String expressSendNumber;           //客户邮寄快递单号
    private String expressSendName;             //客户邮寄快递名称
    private String expressReturnNumbe;          //返回客户快递单号
    private String expressReturnName;           //返回客户快递名称
    private String repairAddr;                  //维修地点

    /**
     *
     * @param clmNo         案件号
     * @param orderId       订单号   渤海理赔系统现存订单号
     * @param deviceCode    设备码，IMEI
     * @param repairType    维修类型  Y成功 N失败
     * @param operationTime 操作时间  YYYY-MM-DD HH:mm:ss
     * @param repairCompany 风控商/维修商名称               默认为 和德信通
     */
    public SynRepairInfoParameter(String clmNo, String orderId, String deviceCode, String repairType, String operationTime, String repairCompany) {
        this.clmNo = clmNo;
        this.orderId = orderId;
        this.deviceCode = deviceCode;
        this.repairType = repairType;
        this.operationTime = operationTime;
        this.repairCompany = repairCompany;
    }

    /**
     * 检查必填项
     * @return
     */
    @Override
    public boolean checkMandatory(){
        return StringUtils.isNotEmpty(clmNo)&&StringUtils.isNotEmpty(orderId)&&StringUtils.isNotEmpty(deviceCode)
                &&StringUtils.isNotEmpty(repairType)&&StringUtils.isNotEmpty(operationTime)&&StringUtils.isNotEmpty(repairCompany);
    }
}
