package com.jeesite.modules.common;/**
 * @Auther: lichao
 * @Date: 2020/2/29 14:23
 * @Description:
 */

/**
 * @Auther: lichao
 * @Date: 2020/2/29 14:23
 * @Description:
 */
public class FormStatus {

    //工单关闭
    public static final String GD_KQ = "0";

    //工单开启
    public static final String GD_GB = "1";




    //待处理
    public static final String LP_DCL = "1001";
    //处理中
    public static final String LP_CLZ = "1002";
    //待审核
    public static final String LP_DSH = "1003";
    //驳回
    public static final String LP_BH = "1004";



    //维修-待签收
    public static final String WX_DQS = "1005";
    //维修-待录入资料
    public static final String WX_DLR = "1006";
    //维修-待报价
    public static final String WX_DBJ = "1007";
    //维修-报价审核未通过
    public static final String WX_BJSH_WTG = "1008";
    //维修-待付款
    public static final String WX_DFK = "1009";
    //维修-修改报价，待审核
    public static final String WX_XGBJ_DSH = "1010";
    //维修-维修中
    public static final String WX_WXZ = "1011";
    //维修-维修时间驳回
    public static final String WX_WXSJ_BH = "1012";
    //维修-维修完成驳回
    public static final String WX_WXWC_BH = "1013";
    //工单完成
    public static final String WX_WXWC = "1014";
    //维修-申请改派，待审核
    public static final String WX_SQGP_DSH = "1015";
    //维修-改派中
    public static final String WX_GPZ = "1016";
    //维修-报价待审核
    public static final String WX_BJ_DSH = "1017";
    //维修-维修完成待审核
    public static final String WX_WXWC_DSH = "1018";

    //维修-待处理
    public static final String WX_DCL = "1019";

    //修改维修时间待审核
    public static final String WX_XGWXSJ_DSH = "1020";


    //维修-改派完成
    public static final String WX_GPWC = "1021";

    //维修-申请关闭工单，待审核
    public static final String WX_SQGB_DSH = "1022";

    //维修-审核通过，工单关闭
    public static final String WX_SQGB_SHTG = "1023";

    //维修-申请重启，待审核
    public static final String WX_SQCQ_DSH = "1024";

    //维修-审核通过，工单重启
    public static final String WX_SQCQ_SHTG = "1025";


    //维修-申请改派，驳回
    public static final String WX_SQGP_SHBH = "1029";

    //维修-申请关闭，审核驳回
    public static final String WX_SQGB_SHBH = "1030";

    //维修-申请重启，审核驳回
    public static final String WX_SQCQ_SHBH = "1031";



    //理赔-申请关闭，审核驳回
    public static final String LP_SQGB_SHBH = "1032";

    //理赔-申请关闭，审核通过
    public static final String LP_SQGB_SHTG = "1033";

    //理赔-申请重启，审核驳回
    public static final String LP_SQCQ_SHBH= "1034";

    //理赔-申请重启，审核通过
    public static final String LP_SQCQ_SHTG = "1035";

    //理赔-申请重启，待审核
    public static final String LP_SQCQ_DSH = "1036";


    //维修申请返修
    public static final String WX_SQFX = "1037";



    //理赔-申请关闭，待审核
    public static final String LP_SQGB_DSH = "2037";


    //全损-待录入信息
    public static final String  QS_DLR = "3001";

    //全损-待审核
    public static final String  QS_DSH = "3002";

    //全损-工单完成
    public static final String  QS_GDWC = "3003";



    //自付款-待付款
    public static final String  ZFK_DFK = "4001";

    //自付款-待审核
    public static final String  ZFK_DSH = "4002";

    //自付款-财务-审核通过
    public static final String  ZFK_SHTG = "4003";






    //换新-待提交换新方案
    public static final String  HX_FA_DTJ = "5001";

    //换新-换新方案待审核
    public static final String  HX_FA_DSH = "5002";

    //换新-换新方案驳回
    public static final String  HX_FA_BH = "5003";

    //换新-换新待付款
    public static final String  HX_DFK = "5004";

    //换新-换新待采购
    public static final String  HX_DCG = "5005";

    //换新-申请关闭
    public static final String  HX_SQGB = "5006";

    //换新-申请重启
    public static final String  HX_SQCQ = "5007";

    //换新-工单已关闭
    public static final String  HX_YGB = "5008";



    //咨询工单-已受理
    public static final String  ZX_YSL = "6001";

    //咨询工单-已完成
    public static final String  ZX_YWC = "6002";

    //咨询工单-待处理
    public static final String  ZX_DCL = "6003";

    //待申请 联系成功 待分配
    public static final String DSQ_CG_DFP = "7001";
    //待申请 未联系成功 待分配
    public static final String DSQ_SB_DFP = "7002";
    //待申请 工单关闭
    public static final String DSQ_GB = "7003";
    //待申请 工单  申请关闭
    public static final String DSQ_SQGB = "7004";
    //待申请 工单  申请重启
    public static final String DSQ_SQCQ = "7005";

}
