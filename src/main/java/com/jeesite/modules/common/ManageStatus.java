package com.jeesite.modules.common;/**
 * @Auther: lichao
 * @Date: 2020/2/29 14:47
 * @Description:
 */

/**
 * @Auther: lichao
 * @Date: 2020/2/29 14:47
 * @Description:
 */
public class ManageStatus {
    //理赔-资料已更新，待联系
    public static final String LP_ZLYGX_DLX = "1001";

    //理赔-未成功联系
    public static final String LP_WCGLX = "1002";

    //理赔-申请关闭，待审核
    public static final String LP_SQGB_DSH = "1003";

    //理赔-申请关闭，审核驳回
    public static final String LP_SQGB_SHBH = "1004";

    //理赔-申请关闭，审核通过   文案：服务中止   由于资料或设备的原因，您的服务中止，如有疑问，请联系客服：4000999855
    public static final String LP_SQGB_SHTG = "1005";

    //理赔-申请重启，审核驳回
    public static final String LP_SQCQ_SHBH= "1006";

    //理赔-申请重启，审核通过
    public static final String LP_SQCQ_SHTG = "1007";//

    //理赔-申请重启，待审核
    public static final String LP_SQCQ_DSH = "1008";

    //理赔-资料不合格-待联系
    public static final String LP_ZLBHG_DLX = "1009";

    //理赔-资料不合格-重新填写完毕
    public static final String LP_CXTX_DLX = "1010";  //针对前台展示文案  加的一条状态



    //维修-待核实邮寄状态
    public static final String WX_DHSYJZT = "2005";

    //维修-未邮寄，待核实邮寄时间
    public static final String WX_WYJ_DHS = "2006";

    //维修-客户已邮寄-待签收
    public static final String WX_KHYJJ_DQS = "2007";

    //维修-已签收，待录入资料
    public static final String WX_YQS_DLR = "2008";

    //维修-资料不合格核实邮寄状态
    public static final String WX_ZLBHG_HSYJZT = "2009";

    //维修-资料合格，待报价
    public static final String WX_DBJ = "2010";

    //维修-报价及维修方案待审核
    public static final String WX_BJ_DSH = "2011";

    //维修-资料不合格，核实邮寄状态
    public static final String WX_BHG_DHSYJ = "2012";

    //维修-待联系
    public static final String WX_DLX = "2013";

    //维修-报价及维修方案审核通过，待付款
    public static final String WX_BJ_SHTG_DFK = "2014";

    //维修-报价及维修方案审核通过
    public static final String WX_BJ_SHTG = "2015";

    //维修-已收自付款，待维修
    public static final String WX_YSZFK_DWX = "2016";

    //维修-维修已超期
    public static final String WX_YCQ = "2017";

    //维修-已修改维修时间
    public static final String WX_YXG = "2018";

    //维修-修改维修时间待审核
    public static final String WX_XGWXSJ_DSH = "2019";

    //修改维修时间审核通过
    public static final String WX_XGWXSJ_SHTG = "2020";

    //维修完成待审核
    public static final String WX_WXWC_DSH = "2021";

    //维修完成审核通过
    public static final String WX_SHTG = "2022";

    //申请改派，待审核
    public static final String WX_SQGP_DSH = "2023";
    //维修-申请关闭工单，待审核
    public static final String WX_SQGB_DSH = "2024";
    //维修-审核通过，改派完成  文案： 改派维修，由于XX原因，改派其他维修站为您维修
        public static final String WX_GPWC = "2025";

    //维修-审核通过，工单关闭  文案：服务中止   由于资料或设备的原因，您的服务中止，如有疑问，请联系客服：4000999855
    public static final String WX_SQGB_SHTG = "2026";

    //维修-申请重启，待审核
    public static final String WX_SQCQ_DSH = "2027";

    //维修-审核通过，工单重启
    public static final String WX_SQCQ_SHTG = "2028";

    //维修-申请改派，驳回
    public static final String WX_SQGP_SHBH = "2029";
    //维修-申请关闭，审核驳回
    public static final String WX_SQGB_SHBH = "2030";
    //维修-申请重启，审核驳回
    public static final String WX_SQCQ_SHBH = "2031";

    //维修完成
    public static final String WX_WXWC = "2032";
    //维修完成-待寄件
    public static final String WX_WXWC_DJJ = "2033";
    //申请修改维修方案
    public static final String WX_SQ_WXFA = "2034";
    //申请修改维修方案，审核通过
    public static final String WX_SQ_WXFA_SHTG = "2035";
    //维修申请返修
    public static final String WX_SQFX = "2036";




    //全损工单-创建全损工单 等待用户完善 收款信息
    public static final String QS_DLR = "3001";

    //全损工单-录入用户信息待审核
    public static final String QS_DSH = "3002";

    //全损工单-财务审核通过，回传成功
    public static final String QS_CW_SHTG = "3003";

    //全损工单-审核驳回
    public static final String QS_SHBH = "3004";



    //自付款工单-待付款
    public static final String ZFK_DFK = "4001";

    //自付款工-支付成功 待审核
    public static final String ZFK_ZFCG_DSH = "4002";

    //自付款工-审核通过
    public static final String ZFK_SHTG = "4003";

    //自付款工-审核驳回
    public static final String ZFK_SHBH = "4004";

    //换新-待邮寄
    public static final String HX_DYJ = "5000";

    //换新-审核通过，待提交换新方案
    public static final String HX_SHTG = "5001";

    //换新-换新方案已提交，待审核
    public static final String HX_FA_DSH = "5002";

    //换新-换新方案驳回
    public static final String HX_FA_BH = "5003";

    //换新-换新方案通过，待付款
    public static final String HX_FA_DFK = "5004";

    //换新-已收自付款
    public static final String HX_YSZFK = "5005";

    //换新-已邮寄，换新完成
    public static final String HX_WC = "5006";

    //换新-申请关闭-待审核
    public static final String HX_SQGB = "5007";

    //换新-申请重启-待审核
    public static final String HX_SQCQ = "5008";

    //换新-已关闭 待重启  文案：服务中止   由于资料或设备的原因，您的服务中止，如有疑问，请联系客服：4000999855
    public static final String HX_YGB = "5009";

    //换新-已关闭 待重启
    public static final String HX_CXKQ = "5010";





    //咨询工单-已受理
    public static final String  ZX_YSL = "6001";

    //咨询工单-已完成
    public static final String  ZX_YWC = "6002";

    //咨询工单-待处理
    public static final String  ZX_DCL = "6003";

    //咨询工单-待联系
    public static final String  ZX_DLX = "6004";

    //待申请-联系成功-待分配
    public static final String DSQ_LXCG_DFP = "7001";
    //待申请-未联系成功-待联系
    public static final String DSQ_WLXCG_DFP = "7002";
    //待申请-关闭待重启 文案：服务中止   由于资料或设备的原因，您的服务中止，如有疑问，请联系客服：4000999855
    public static final String DSQ_GB_DCQ = "7003";

    //待申请-申请关闭
    public static final String DSQ_SQGB = "7004";

    //待申请-申请重启
    public static final String DSQ_SQCQ = "7005";


}
