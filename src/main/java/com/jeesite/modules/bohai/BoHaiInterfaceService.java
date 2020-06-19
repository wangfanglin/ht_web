package com.jeesite.modules.bohai;

import com.jeesite.modules.bohai.entity.*;
import com.jeesite.modules.bohai.utils.GsonUtils;
import com.jeesite.modules.bohai.utils.HttpReqUtils;
import com.jeesite.modules.bohai.utils.MD5;
import com.jeesite.modules.log.entity.ThirdInterfaceLog;
import com.jeesite.modules.log.entity.ThirdInterfaceType;
import com.jeesite.modules.log.service.ThirdInterfaceLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;



/**
 *
 * 渤海接口服务
 * @author tangchao
 */
@Service
public class BoHaiInterfaceService {
    @Autowired
    ThirdInterfaceLogService logService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SYN_REPAIR_URL = "http://117.8.230.48:9080/esi/data.do?method=esData&esType=jkxCallBack";
    private static final String POLICY_DAMAGE_URL= "http://117.8.230.48:9080/esi/data.do?method=esData&esType=assessmentOfLoss";
    private static final String UPLOAD_IMAGE_URL = "http://117.8.230.48:9080/esi/data.do?method=esData&esType=upLoadImage";
    private static final String SURVEY_URL = "http://117.8.230.48:9080/esi/data.do?method=esData&esType=addSurvey_intfa";
    private static final String SAVE_PAYEE_INFORMATION_URL = "http://117.8.230.48:9080/esi/data.do?method=esData&esType=addPayeeInfo";
    private static final String REPROT_SUBMIT = "http://117.8.230.48:9080/esi/data.do?method=esData&esType=hfReportSubmit ";
    /**
     * 3.2. 维修信息同步
     * @param parameter 维修信息同步入参
     * @return Result.data  订单号
     */
    public Result<String> synRepairInfo(SynRepairInfoParameter parameter)  {
        try {
            if(!checkParameter(parameter)) {
                return new Result(ResultStatus.PARAMETER_ERROR);
            }
            String resultStr = call(SYN_REPAIR_URL,"jkxCallBack",parameter);
            if(!StringUtils.isNotBlank(resultStr))  return new Result<>(ResultStatus.INTERFACE_ERROR);
            Map<String, Object> jsonStr = GsonUtils.fromJson(resultStr, Map.class);
            String code = (String) jsonStr.get("code");
            if(!isSuccess(code)) return new Result<>(ResultStatus.INTERFACE_ERROR);
            Map<String, String> objMap =  (Map<String, String>) jsonStr.get("obj");
            String orderIdStr = objMap.get("orderId");
            return new Result<>(ResultStatus.SUCCESS,orderIdStr);
        }catch (Exception e){
            logger.error("调用维修信息同步接口失败");
            e.printStackTrace();
            return new Result<>(ResultStatus.ERROR);
        }
    }


    /**
     *  3.3.估损提交
     * @param parameter 估损提交入参
     * @return
     */
    public Result policyDamage(PolicyDamageParameter parameter)  {
        try {
            if(!checkParameter(parameter)) {
                return new Result(ResultStatus.PARAMETER_ERROR);
            }
            String resultStr = call(POLICY_DAMAGE_URL,"assessmentOfLoss",parameter);
            if(!StringUtils.isNotBlank(resultStr))  return new Result<>(ResultStatus.INTERFACE_ERROR);

            Map<String, Object> jsonStr = GsonUtils.fromJson(resultStr, Map.class);
            String code = (String) jsonStr.get("code");
            if(!isSuccess(code)) return new Result<>(ResultStatus.INTERFACE_ERROR);
            return new Result<>(ResultStatus.SUCCESS);
        }catch (Exception e){
            logger.error("调用估损提交接口失败");
            e.printStackTrace();
            return new Result<>(ResultStatus.ERROR);
        }
    }

    /**
     *  报案接口
     * @param parameter 估损提交入参
     * @return
     */
    public Result reprotSubmit(ReportCaseParameter parameter)  {
        try {
            if(!checkParameter(parameter)) {
                return new Result(ResultStatus.PARAMETER_ERROR);
            }
            String resultStr = call(REPROT_SUBMIT,"hfReportSubmit",parameter);
            if(!StringUtils.isNotBlank(resultStr))  return new Result<>(ResultStatus.INTERFACE_ERROR);
            Map<String, Object> jsonStr = GsonUtils.fromJson(resultStr, Map.class);
            String code = (String) jsonStr.get("code");
            if(!isSuccess(code)) return new Result<>(ResultStatus.INTERFACE_ERROR);
            return new Result<>(ResultStatus.SUCCESS);
        }catch (Exception e){
            logger.error("调用报案接口失败");
            e.printStackTrace();
            return new Result<>(ResultStatus.ERROR);
        }
    }


    /**
     *  3.6.影像上传
     * @param parameter 估损提交入参
     * @return  Result.data:图片ID
     */
    public Result upLoadImage(UpLoadImageParameter parameter)  {
        try {
            if(!checkParameter(parameter)) {
                return new Result(ResultStatus.PARAMETER_ERROR);
            }
            String resultStr = call(UPLOAD_IMAGE_URL,"upLoadImage",parameter);
            if(!StringUtils.isNotBlank(resultStr))  return new Result<>(ResultStatus.INTERFACE_ERROR);
            Map<String, Object> jsonStr =GsonUtils.fromJson(resultStr, Map.class);
            String code = (String) jsonStr.get("code");
            if(!isSuccess(code)) return new Result<>(ResultStatus.INTERFACE_ERROR);
            return new Result<>(ResultStatus.SUCCESS);
        }catch (Exception e){
            logger.error("调用估损提交接口失败");
            e.printStackTrace();
            return new Result<>(ResultStatus.ERROR);
        }
    }




    /**
     *  3.5.查勘提交
     * @param parameter 估损提交入参
     * @return  Result.data:图片ID
     */
    public Result survey(SurveyParameter parameter)  {
        try {
            if(!checkParameter(parameter)) {
                return new Result(ResultStatus.PARAMETER_ERROR);
            }
            String resultStr = call(SURVEY_URL,"survey",parameter);
            if(!StringUtils.isNotBlank(resultStr))  return new Result<>(ResultStatus.INTERFACE_ERROR);
            Map<String, Object> jsonStr =GsonUtils.fromJson(resultStr, Map.class);
            String code = (String) jsonStr.get("code");
            if(!isSuccess(code)) return new Result<>(ResultStatus.INTERFACE_ERROR);
            return new Result<>(ResultStatus.SUCCESS);
        }catch (Exception e){
            logger.error("调用估损提交接口失败");
            e.printStackTrace();
            return new Result<>(ResultStatus.ERROR);
        }
    }



    /**
     * 3.4.收款人信息增加
     * @param parameter 维修信息同步入参
     * @return Result.data  订单号
     */
    public Result savePayeeInformation(PayeeInformationParameter parameter)  {
        try {
            if(!checkParameter(parameter)) {
                return new Result(ResultStatus.PARAMETER_ERROR);
            }
            String resultStr = call(SAVE_PAYEE_INFORMATION_URL,"savePayeeInformation",parameter);
            if(!StringUtils.isNotBlank(resultStr))  return new Result<>(ResultStatus.INTERFACE_ERROR);
            Map<String, Object> jsonStr = GsonUtils.fromJson(resultStr, Map.class);
            String code = (String) jsonStr.get("code");
            if(!isSuccess(code)) return new Result<>(ResultStatus.INTERFACE_ERROR);
            return new Result<>(ResultStatus.SUCCESS);
        }catch (Exception e){
            logger.error("调用维修信息同步接口失败");
            e.printStackTrace();
            return new Result<>(ResultStatus.ERROR);
        }
    }







    /**
     * 添加log日志
     * @param url           接口地址
     * @param method        接口名
     * @param parameter     入参
     * @param resultStr     出参
     */
    public void saveLog(String url,String method,String parameter, String resultStr) {
        logService.save(new ThirdInterfaceLog(url, ThirdInterfaceType.BOHAI,method,parameter,resultStr));
    }


    /**
     * 接口调用是否成功
     * @param code  渤海的 响应代码    000000成功
     * @return
     */
    private boolean isSuccess(String code) {
        return "000000".equals(code);
    }

    /**
     * 调用接口
     * @param parameter 入参
     * @param method    方法名
     * @return          返回值
     * @throws UnsupportedEncodingException
     */
    private String call(String url,String method,Object parameter) throws UnsupportedEncodingException {
        String parameterJson = GsonUtils.toJson(parameter);
        String encoderParam = java.net.URLEncoder.encode(GsonUtils.toJson(parameterJson),"UTF-8");
        String resultStr = HttpReqUtils.sendPostJson(url, encoderParam, getToken(parameterJson));
        saveLog(UPLOAD_IMAGE_URL,method,parameterJson, resultStr);
        return resultStr;
    }

    /**
     * 获取 token
     * @param json
     * @return
     */
    private String getToken(String json) {
        //appid=1，randomNumber=随机数，timestamp=时间戳
        //目前先生成6位随机数
        return MD5.toMD5("1"+"65537"+System.currentTimeMillis()+Math.random()*1000000+json);
    }

    /**
     * 检查入参
     * @param parameter
     * @return
     */
    private boolean checkParameter(Parameter parameter){
        return parameter!=null&&parameter.checkMandatory();
    }
}
