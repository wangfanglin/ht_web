package com.jeesite.modules.template.utils;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/***
 * 短息模板工具类
 *
 * @author wangfanglin
 * @date 2020/04/07
 */
@Slf4j
public class NoteTemplateUtils {



    public static String formatInformation(String[] information) {
        try {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < information.length; i++) {
                sb.append(information[i] + ",");
            }
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信1：核赔-选择维修网点后，显示短信内容，提交后发送 （换新）
     *
     * @param  information（勾选的资料）
     * @return String
     */
    public static String noteTemplateOneRepair(String[] information){
        try {
            StringBuffer sb = new StringBuffer();
            String s = formatInformation(information);
            sb.append("您好，您的理赔已申请，请只将").append(s)
                    .append("快递至：北京市朝阳区霄云路36号国航大厦1303室，焦金辉，电话：18511019009（此电话不接受咨询），请用空白纸写上手机开机密码，原手机串号、回邮地址和收件人(须为客户本人)及联系电话。苹果手机还需关闭\\\"查找我的Iphone\\\"功能，如未关闭将无法受理服务。其他配件如充电器、数据线、耳机、保修卡、包装盒、会员卡等请一律不要邮寄，否则恕不保管，如果您损坏手机串号无法辨识且需要您提供有效证明资料佐证（机器串码背贴或外包装盒子上的盒贴），否则将无法享受本服务，提供的身份证复印件必须与购买时登记的姓名一致。 如有问题请关注公众号\\\"和德信通\\\"联系在线客服。另邮寄需选择顺丰，（到付，不包含保价费用）。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信1：核赔-选择维修网点后，显示短信内容，提交后发送 （维修）
     *
     * @param information 勾选的资料
     * @param address 维修站地址
     * @param master 维修站联系人
     * @param phone 维修站联系电话
     * @return
     */
    public static String noteTemplateOneRenew(String[] information,String address,String master,String phone){
        try {
            StringBuffer sb = new StringBuffer();
            String s = formatInformation(information);
            sb.append("您好，您的理赔已申请，请将").append(s)
                    .append("送至或快递至：").append(address)
                    .append("收件人：").append(master)
                    .append("，联系电话：").append(phone)
                    .append("，请用空白纸写上手机开机密码，原手机串号、回邮地址和收件人(须为客户本人)及联系电话。除指定邮寄物品外，其它邮寄物品一律不予保管及回寄。苹果手机还需关闭\\\"查找我的Iphone\\\"功能，提供的身份证复印件必须与系统申请时登记的姓名一致。 如有问题请关注公众号\\\"和德信通\\\"联系在线客服。另邮寄需选择顺丰，（到付，不包含保价费用）。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     *
     *  TODO 短信2：如果需要叫邮寄，调用微信模板（）
     */


    /**
     * 短信3：维修站审核通过，确认后发送 (维修|换新)
     *
     * @param
     * @return String
     */
    public static String noteTemplateThreeRenew(){
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您邮寄的资料已经审核通过，我们会有工作人员主动联系您，确认置换机相关事宜，请您保持手机畅通，换机处理需要一定时间，并耐心等待。如有问题请联系和德信通全国客服热线：4008-900-001或登录www.hollardchina.com.cn点击业务查询页面后输入您手机的串码查询进度。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信3：维修站审核通过，确认后发送 (维修|换新)
     *
     * @param
     * @return String
     */
    public static String noteTemplateThreeRepair() {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您邮寄的损坏手机和资料已经审核通过，维修处理需要一定时间，修复后会主动与您联系，请您保持手机畅通，并耐心等待。如需查看维修进度，请联系和德信通全国客服热线：4008-900-001或登录www.hollardchina.com.cn点击业务查询页面后输入您手机的串码查询进度，感谢您的配合。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信4：审核不合格，需要调出资料勾选 (维修)
     *
     * @param information  资料
     * @param address 邮寄地址
     * @return String
     */
    public static String noteTemplateFourRepair(String information,String address){
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您邮寄的资料缺少：").append(information)
                    .append("请尽快补充，并快递至：").append(address)
                    .append("，此电话不提供咨询服务，如有问题请联系和德信通全国客服热线：4008-900-001，感谢您的配合。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信4：审核不合格，需要调出资料勾选 (换新)
     *
     * @param information 资料
     * @return String
     */
    public static String noteTemplateFourRenew(String information){
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您邮寄的资料缺少：").append(information)
                    .append("，请尽快补充，并快递至：北京市朝阳区霄云路36号国航大厦1303室，收件人：焦金辉，电话：18511019009（此电话不接受咨询），如有问题请联系和德信通全国客服热线：4008-900-001或登录www.hollardchina.com.cn点击业务查询页面后输入您手机的串码查询进度，感谢您的配合。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }


    /**
     * 短信5：换新工单-确认完置换手机品牌型号价格、自付额后，采购新机匹配页面
     *
     * @param brand 手机品牌
     * @param model 型号
     * @param payprice 应支付的自付额
     * @return String
     */
    public static String noteTemplateFiveRenew(String brand, String model, BigDecimal payprice) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，请在7日内付款，逾期未付款的，视为您放弃换机，本服务终止。您确认置换的手机品牌为：\"").append(brand)
                    .append("，型号为：").append(model)
                    .append("，应支付的自付额为：").append(payprice)
                    .append("。我们将在收到自付额后，短信通知您。自付额支付方式：关注微信公众号：和德信通，通过微信支付。（提示：微信汇款延迟一天到账，请耐心等待）。如非本人汇款，请您在备注中务必填写申请理赔人姓名，以便我们及时为您提供服务。如有问题请联系和德信通全国客服热线：4008-900-001。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信6：维修部审核报价后，发送
     *
     * @param totalSumms 维修总费用
     * @param ziFus 自付额
     * @return String
     */
    public static String noteTemplateSixRepair(BigDecimal totalSumms,BigDecimal ziFus) {

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您的手机维修总费用为：").append(totalSumms)
                    .append("，您应支付的自付额为：").append(ziFus)
                    .append("，我们将在收到自付额后，短信通知您。自付额支付方式：关注微信公众号“和德信通”，通过微信支付。（提示：微信汇款延迟一天到账，请耐心等待）。如非本人汇款，请您在备注中务必填写申请理赔人姓名，以便我们及时为您提供服务。如有问题请联系和德信通全国客服热线：4008-900-001。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信7：财务部收到客户自付款后，维修自付款页面
     *
     * @param phone 联系电话
     * @return String
     */
    public static String noteTemplateSevenRepair(String phone) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您汇至我司账户上的自付额已收到，我们会在3个工作日内尽快安排为您发货。请您保持手机畅通，并耐心等待。如有问题请联系电话：")
                    .append(phone)
                    .append("。或登录www.hollardchina.com.cn点击业务查询页面后输入您手机的串码查询进度，感谢您的配合。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信7：财务部收到客户自付款后，换机自付款页面
     *
     * @param
     * @return Stirng
     */
    public static String noteTemplateSevenRenew() {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您汇至我司账户上的自付额已收到，我们会在3个工作日内尽快安排为您发货。请您保持手机畅通，并耐心等待。如有问题请联系和德信通全国客服热线：4008-900-001或登录www.hollardchina.com.cn点击业务查询页面后输入您手机的串码查询进度，感谢您的配合。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信8：维修完成确认页面
     *
     * @param
     * @return String
     */
    public static String noteTemplateEight() {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您送修的手机已修好。请您前来取件。感谢您对我们工作的大力支持和配合！");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信9：购部完成采购并发货后，采购发货页面
     *
     * @param exprCompany 快递公司 、快递单号 exprId、新机IMEI号 newImei
     * @return String
     */
    public static String noteTemplateNine(String exprCompany,String exprId,String newImei) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您的置换手机已经发货，快递公司为：").append(exprCompany)
                    .append("，快递单号为：").append(exprId)
                    .append("，新机IMEI号为：").append(newImei)
                    .append("。请您注意查收。感谢您对我们工作的大力支持和配合！如有问题请联系和德信通全国客服热线：4008-900-001或登录www.hollardchina.com.cn点击业务查询页面后输入您手机的串码查询进度，感谢您的配合。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

    /**
     * 短信10：维修部完成维修并发货后，维修发货页面
     *
     * @param exprCompany 快递公司
     * @param exprId 快递单号
     * @return String
     */
    public static String noteTemplateTen(String exprCompany,String exprId) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，您的维修手机已经发货，快递公司为：").append(exprCompany)
                    .append("，快递单号为：").append(exprId)
                    .append("。请您注意查收。感谢您对我们工作的大力支持和配合！如有问题请联系和德信通全国客服热线：4008-900-001或登录www.hollardchina.com.cn点击业务查询页面后输入您手机的串码查询进度，感谢您的配合。");
            return sb.toString();
        }catch (Exception e){
            log.error("调用发送短息模板异常",e);
        }
        return null;
    }

}
