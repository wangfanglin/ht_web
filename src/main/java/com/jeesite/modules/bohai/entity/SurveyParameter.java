package com.jeesite.modules.bohai.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 查勘接口入参
 * @author tangchao
 */
@Data
public class SurveyParameter implements Parameter {
    private String taskId;                          //渤海流程号 保顿订单接口推送给风控商的ID
    private String clmNo;                           //渤海案件号
    private String oldDeviceCode;                   //原有设备码
    private String newDeviceCode;                   //新设备码  如果没有更换主板，则没有此节点
    private String surveyResult;                    //维修说明
    private String srvyTime;                        //维修时间  YYYY-MM-DD HH:mm:ss
    private String sugAllAmt;                       //维修价格合计
    private String allAmt;                          //实际价格合计
    private String deviceType;                      //设备分类ID
    private String deviceBrand;                     //品牌ID
    private String deviceModel;                     //型号ID
    private String deviceAttr;                      //属性值ID
    private List<SurveyLoss> surveyLoss;            //查勘结果集合
    private List<ResidualInfo> residualInfo;        //残值列表
    private List<SurveyFee> surveyFee;              //费用列表


    /**
     *
     * @param taskId            渤海流程号 保顿订单接口推送给风控商的ID
     * @param clmNo             渤海案件号
     * @param oldDeviceCode     原有设备码
     * @param srvyTime          维修时间  YYYY-MM-DD HH:mm:ss
     * @param sugAllAmt         维修价格合计
     * @param allAmt            实际价格合计
     * @param deviceType        设备分类ID
     * @param deviceBrand       品牌ID
     * @param deviceModel       型号ID
     * @param deviceAttr        属性值ID
     * @param surveyLoss        查勘结果集合
     */
    public SurveyParameter(String taskId, String clmNo, String oldDeviceCode, String srvyTime, String sugAllAmt, String allAmt, String deviceType, String deviceBrand, String deviceModel, String deviceAttr, List<SurveyLoss> surveyLoss) {
        this.taskId = taskId;
        this.clmNo = clmNo;
        this.oldDeviceCode = oldDeviceCode;
        this.srvyTime = srvyTime;
        this.sugAllAmt = sugAllAmt;
        this.allAmt = allAmt;
        this.deviceType = deviceType;
        this.deviceBrand = deviceBrand;
        this.deviceModel = deviceModel;
        this.deviceAttr = deviceAttr;
        this.surveyLoss = surveyLoss;
    }

    @Override
    public boolean checkMandatory() {
        if(surveyLoss==null) return false;
        if(!Parameter.checkListCheckMandatory(surveyLoss)) return false;
//        if(residualInfo!=null && !Parameter.checkListCheckMandatory(residualInfo)) {
//            return false;
//        }
//
//        if(surveyFee!=null && !Parameter.checkListCheckMandatory(surveyFee)) {
//           return false;
//        }

        return StringUtils.isNotEmpty(taskId)&& StringUtils.isNotEmpty(clmNo)
                && StringUtils.isNotEmpty(oldDeviceCode)&& StringUtils.isNotEmpty(srvyTime)
                && StringUtils.isNotEmpty(sugAllAmt)&& StringUtils.isNotEmpty(allAmt)
                && StringUtils.isNotEmpty(deviceType)&& StringUtils.isNotEmpty(deviceBrand)
                && StringUtils.isNotEmpty(deviceModel)&& StringUtils.isNotEmpty(deviceAttr);
    }
}
