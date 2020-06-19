package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 残值对象
 * @author tangchao
 */
@Data
public class ResidualInfo implements Parameter {
    private String itemId;              //残值编号
    private String orderId;             //风控商订单号
    private String itemType;            //残值类型  1抵扣 2回收
    private String itemOutTime;         //残值寄出时间    YYYY-MM-DD HH:mm:ss
    private String itemAmt;             //残值价格
    private String itemNum;             //残值数量
    private String expSendNum;          //快递单号
    private String expSendName;         //快递公司
    private String itemCompany;         //残值服务商
    private String taskId;              //渤海流程号
    private String clmNo;               //案件号

    /**
     *
     * @param itemId        残值编号
     * @param orderId       风控商订单号
     * @param itemType      残值类型  1抵扣 2回收
     * @param itemOutTime   残值寄出时间    YYYY-MM-DD HH:mm:ss
     * @param itemAmt       残值价格
     * @param itemNum       残值数量
     * @param expSendNum    快递单号
     * @param expSendName   快递公司
     * @param itemCompany   残值服务商
     * @param taskId        渤海流程号
     * @param clmNo         案件号
     */
    public ResidualInfo(String itemId, String orderId, String itemType, String itemOutTime, String itemAmt, String itemNum, String expSendNum, String expSendName, String itemCompany, String taskId, String clmNo) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemOutTime = itemOutTime;
        this.itemAmt = itemAmt;
        this.itemNum = itemNum;
        this.expSendNum = expSendNum;
        this.expSendName = expSendName;
        this.itemCompany = itemCompany;
        this.taskId = taskId;
        this.clmNo = clmNo;
    }


    @Override
    public boolean checkMandatory() {
        return StringUtils.isNotEmpty(itemId)&& StringUtils.isNotEmpty(orderId)
                && StringUtils.isNotEmpty(itemType)&& StringUtils.isNotEmpty(itemOutTime)
                && StringUtils.isNotEmpty(itemAmt)&& StringUtils.isNotEmpty(itemNum)
                && StringUtils.isNotEmpty(expSendNum)&& StringUtils.isNotEmpty(expSendName)
                && StringUtils.isNotEmpty(itemCompany)&& StringUtils.isNotEmpty(taskId)&& StringUtils.isNotEmpty(clmNo);
    }
}
