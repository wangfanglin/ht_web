package com.jeesite.modules.charts.service;

import com.jeesite.modules.charts.Dao.ChartsDao;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.httimeefficiency.service.HtTimeEfficiencyService;
import com.jeesite.modules.number.dao.HtDateTableDao;
import com.jeesite.modules.number.entity.HtDateTable;
import com.jeesite.modules.period.dao.HtMonthTableDao;
import com.jeesite.modules.period.entity.HtMonthTable;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
@RequestMapping(value = "${adminPath}/charts")
public class ChartsController {

    @Autowired
    HtFormInfoService htFormInfoService;
    @Autowired
    HtTimeEfficiencyService htTimeEfficiencyService;
    @Autowired
    HtCallLogService htCallLogService;
    @Autowired
    HtDateTableDao htDateTableDao;
    @Autowired
    HtMonthTableDao htMonthTableDao;
    @Autowired
    ChartsDao chartsDao;

    @RequestMapping(value = "default")
    public String list(Model model) {
        int day=chartsDao.dayAll();
        int channel=chartsDao.channelAll();
        int staff=chartsDao.staffAll();
        int user=chartsDao.userAll();
        model.addAttribute("day",day);
        model.addAttribute("channel",channel);
        model.addAttribute("staff",staff);
        model.addAttribute("user",user);
        return "modules/charts/echartsData";
    }
    /**
     * 仪表盘——报案量
     * @return 报案量
     */
    @RequestMapping(value = "getReportAmount")
    @ResponseBody
    public Map<String, Object> getReportAmount() {
        Map<String, Object> result = new HashMap<>();
        result.put("dayMaxNumber", 20 );
        result.put("dayLevel", 4 );
        result.put("monthMaxNumber", 200 );
        result.put("monthLevel", 40 );
        result.put("officeName", "暂无");
        List<HtDateTable> pushData=htDateTableDao.pushData();
        List<String> officeName = new ArrayList<>();
        List<Long> daySalesList = new ArrayList<>();
        List<Long> monthSalesList = new ArrayList<>();
        if(pushData.size() != 0){
            for(int i=0;i<pushData.size();i++){
                officeName.add(pushData.get(i).getOfficeName());
                daySalesList.add(pushData.get(i).getDayNum());
                monthSalesList.add(pushData.get(i).getMonthNum());
            }
            result.put("officeName", officeName);
            long dayMaxNumber=getNumber(Collections.max(daySalesList));
            result.put("dayMaxNumber", dayMaxNumber );
            long dayLevel=0;
            dayLevel=dayMaxNumber/5;
            result.put("dayLevel", dayLevel );
            result.put("daySalesList", daySalesList);
            long monthMaxNumber=getNumber(monthSalesList.get(0));
            result.put("monthMaxNumber", monthMaxNumber );
            long monthLevel=monthMaxNumber/5;
            result.put("monthLevel", monthLevel );
            result.put("monthSalesList", monthSalesList);
        }
        return result;
    }
   /**
     * 仪表盘——网点超期件数
     * @return 网点超期件数
     */
    @RequestMapping(value = "getPastdueAmount")
    @ResponseBody
    public Map<String, Object> getPastdueAmount() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> officeList = htDateTableDao.getOverOffice();
        List<String> maintenancePointName = new ArrayList<>();
        List<String> pastdueList = new ArrayList<>();
        result.put("MaxNumber", 10 );
        result.put("Level", 2 );
        result.put("officeName", "暂无");
        if(officeList.size() != 0){
            for (Map<String, Object> map : officeList) {
                maintenancePointName.add(map.get("maintenance_point_name").toString());
                pastdueList.add(map.get("result_num").toString());
            }
            result.put("officeName",maintenancePointName);
            result.put("pastdueList",pastdueList);
            long MaxNumber=getNumber(Long.parseLong(pastdueList.get(0)));
            result.put("MaxNumber", MaxNumber );
            long Level=MaxNumber/5;
            result.put("Level", Level );
        }
        return result;
    }
    /**
     * 仪表盘——通话量
     * @return 通话量
     */
    @RequestMapping(value = "getCallVolumeAmount")
    @ResponseBody
    public Map<String, Object> getCallVolumeAmount() {
        Map<String, Object> result = new HashMap<>();
        int incomingCall = htCallLogService.findListBuStatus("1");
        int callOut = htCallLogService.findListBuStatus("0");
        result.put("incomingCall",incomingCall);
        result.put("callOut",callOut);
        return result;
    }
    /**
     * 仪表盘——拒单量
     * @return 拒单量
     */
    @RequestMapping(value = "getRefuseNumber")
    @ResponseBody
    public Map<String, Object> getRefuseNumber() {
        Map<String, Object> result = new HashMap<>();
        List<Integer> number = htDateTableDao.refuseNumber();
        result.put("MaxNumber", 10);
        result.put("Level", 2);
        if (number.size() != 0){
            long MaxNumber = 0;
            MaxNumber = getNumber(Long.parseLong(Collections.max(number).toString()));
            result.put("MaxNumber", MaxNumber);
            long Level = 0;
            Level = MaxNumber / 5;
            result.put("Level", Level);
            result.put("Num", number);
     }
        return result;

}
    /**
     * 仪表盘——网点平均维修周期
     * @return 网点平均维修周期
     */
    @RequestMapping(value = "getPeriodList")
    @ResponseBody
    public Map<String, Object> getPeriod() {
        Map<String, Object> result = new HashMap<>();
        result.put("officeName", "暂无");
        result.put("Max", 200);
        result.put("Level", 40);
        List<HtMonthTable> period = htMonthTableDao.getPeriodList();
        if (period.size() != 0) {
            List<String> officeName = new ArrayList<>();
            List<Long> maxData=new ArrayList<>();
                for (int i = 0; i < period.size(); i++) {
                    officeName.add(i, period.get(i).getMaintenancePointName());
                }
            result.put("officeName", officeName);
            result.put("officeNameOne", officeName.get(0));
            result.put("officeNameTwo", officeName.get(1));
            result.put("officeNameThr", officeName.get(2));
            List<Integer> officeDataOne = new ArrayList<>();
            officeDataOne.add(0, Integer.parseInt(period.get(0).getJanuary()));
            officeDataOne.add(1, Integer.parseInt(period.get(0).getFebruary()));
            officeDataOne.add(2, Integer.parseInt(period.get(0).getMarch()));
            officeDataOne.add(3, Integer.parseInt(period.get(0).getApril()));
            officeDataOne.add(4, Integer.parseInt(period.get(0).getMay()));
            officeDataOne.add(5, Integer.parseInt(period.get(0).getJune()));
            officeDataOne.add(6, Integer.parseInt(period.get(0).getJuly()));
            officeDataOne.add(7, Integer.parseInt(period.get(0).getAugust()));
            officeDataOne.add(8, Integer.parseInt(period.get(0).getSeptember()));
            officeDataOne.add(9, Integer.parseInt(period.get(0).getOctober()));
            officeDataOne.add(10, Integer.parseInt(period.get(0).getNovember()));
            officeDataOne.add(11, Integer.parseInt(period.get(0).getDecember()));
            maxData.add(0,Long.parseLong(Collections.max(officeDataOne).toString()));
            result.put("officeDataOne", officeDataOne);
            if(period.size() > 1){
                List<Integer> officeDataTwo = new ArrayList<>();
                officeDataTwo.add(0, Integer.parseInt(period.get(1).getJanuary()));
                officeDataTwo.add(1, Integer.parseInt(period.get(1).getFebruary()));
                officeDataTwo.add(2, Integer.parseInt(period.get(1).getMarch()));
                officeDataTwo.add(3, Integer.parseInt(period.get(1).getApril()));
                officeDataTwo.add(4, Integer.parseInt(period.get(1).getMay()));
                officeDataTwo.add(5, Integer.parseInt(period.get(1).getJune()));
                officeDataTwo.add(6, Integer.parseInt(period.get(1).getJuly()));
                officeDataTwo.add(7, Integer.parseInt(period.get(1).getAugust()));
                officeDataTwo.add(8, Integer.parseInt(period.get(1).getSeptember()));
                officeDataTwo.add(9, Integer.parseInt(period.get(1).getOctober()));
                officeDataTwo.add(10, Integer.parseInt(period.get(1).getNovember()));
                officeDataTwo.add(11, Integer.parseInt(period.get(1).getDecember()));
                maxData.add(1,Long.parseLong(Collections.max(officeDataTwo).toString()));
                result.put("officeDataTwo", officeDataTwo);
                }
            if(period.size() > 2){
                List<Integer> officeDataThr = new ArrayList<>();
                officeDataThr.add(0, Integer.parseInt(period.get(2).getJanuary()));
                officeDataThr.add(1, Integer.parseInt(period.get(2).getFebruary()));
                officeDataThr.add(2, Integer.parseInt(period.get(2).getMarch()));
                officeDataThr.add(3, Integer.parseInt(period.get(2).getApril()));
                officeDataThr.add(4, Integer.parseInt(period.get(2).getMay()));
                officeDataThr.add(5, Integer.parseInt(period.get(2).getJune()));
                officeDataThr.add(6, Integer.parseInt(period.get(2).getJuly()));
                officeDataThr.add(7, Integer.parseInt(period.get(2).getAugust()));
                officeDataThr.add(8, Integer.parseInt(period.get(2).getSeptember()));
                officeDataThr.add(9, Integer.parseInt(period.get(2).getOctober()));
                officeDataThr.add(10, Integer.parseInt(period.get(2).getNovember()));
                officeDataThr.add(11, Integer.parseInt(period.get(2).getDecember()));
                maxData.add(1,Long.parseLong(Collections.max(officeDataThr).toString()));
                result.put("officeDataThr", officeDataThr);
            }
            if(maxData.size() != 0 ) {
                    long sum=0;
                    for(int i = 0; i < maxData.size();i++){
                        sum+=maxData.get(i);
                    }
                    long MaxNumber=getNumber(sum);
                    result.put("Max", MaxNumber );
                    long Level=MaxNumber/5;
                    result.put("Level", Level );
                }
        }
        return result;
    }
 /**
     * 仪表盘——网点平均维修金额
     * @return 网点平均维修金额
     */
    @RequestMapping(value = "getPricesList")
    @ResponseBody
    public Map<String, Object> getPricesList() {
        Map<String, Object> result = new HashMap<>();
        result.put("officeName", "暂无");
        result.put("maxData", 200);
        result.put("Level", 40);
        List<HtMonthTable> prices = htMonthTableDao.getPricesList();
        if (prices.size() != 0) {
            List<String> officeName = new ArrayList<>();
            List<Long> maxData = new ArrayList<>();
            for (int i = 0; i < prices.size(); i++) {
                officeName.add(i, prices.get(i).getMaintenancePointName());
            }
            result.put("officeName", officeName);
            result.put("officeNameOne", officeName.get(0));
            result.put("officeNameTwo", officeName.get(1));
            result.put("officeNameThr", officeName.get(2));
            List<Integer> officeDataOne = new ArrayList<>();
            officeDataOne.add(0, Integer.parseInt(prices.get(0).getJanuary()));
            officeDataOne.add(1, Integer.parseInt(prices.get(0).getFebruary()));
            officeDataOne.add(2, Integer.parseInt(prices.get(0).getMarch()));
            officeDataOne.add(3, Integer.parseInt(prices.get(0).getApril()));
            officeDataOne.add(4, Integer.parseInt(prices.get(0).getMay()));
            officeDataOne.add(5, Integer.parseInt(prices.get(0).getJune()));
            officeDataOne.add(6, Integer.parseInt(prices.get(0).getJuly()));
            officeDataOne.add(7, Integer.parseInt(prices.get(0).getAugust()));
            officeDataOne.add(8, Integer.parseInt(prices.get(0).getSeptember()));
            officeDataOne.add(9, Integer.parseInt(prices.get(0).getOctober()));
            officeDataOne.add(10, Integer.parseInt(prices.get(0).getNovember()));
            officeDataOne.add(11, Integer.parseInt(prices.get(0).getDecember()));
            maxData.add(0, Long.parseLong(Collections.max(officeDataOne).toString()));
            result.put("officeDataOne", officeDataOne);
            if (prices.size() > 1) {
                List<Integer> officeDataTwo = new ArrayList<>();
                officeDataTwo.add(0, Integer.parseInt(prices.get(1).getJanuary()));
                officeDataTwo.add(1, Integer.parseInt(prices.get(1).getFebruary()));
                officeDataTwo.add(2, Integer.parseInt(prices.get(1).getMarch()));
                officeDataTwo.add(3, Integer.parseInt(prices.get(1).getApril()));
                officeDataTwo.add(4, Integer.parseInt(prices.get(1).getMay()));
                officeDataTwo.add(5, Integer.parseInt(prices.get(1).getJune()));
                officeDataTwo.add(6, Integer.parseInt(prices.get(1).getJuly()));
                officeDataTwo.add(7, Integer.parseInt(prices.get(1).getAugust()));
                officeDataTwo.add(8, Integer.parseInt(prices.get(1).getSeptember()));
                officeDataTwo.add(9, Integer.parseInt(prices.get(1).getOctober()));
                officeDataTwo.add(10, Integer.parseInt(prices.get(1).getNovember()));
                officeDataTwo.add(11, Integer.parseInt(prices.get(1).getDecember()));
                maxData.add(1, Long.parseLong(Collections.max(officeDataTwo).toString()));
                result.put("officeDataTwo", officeDataTwo);
            }
            if (prices.size() > 2) {
                List<Integer> officeDataThr = new ArrayList<>();
                officeDataThr.add(0, Integer.parseInt(prices.get(2).getJanuary()));
                officeDataThr.add(1, Integer.parseInt(prices.get(2).getFebruary()));
                officeDataThr.add(2, Integer.parseInt(prices.get(2).getMarch()));
                officeDataThr.add(3, Integer.parseInt(prices.get(2).getApril()));
                officeDataThr.add(4, Integer.parseInt(prices.get(2).getMay()));
                officeDataThr.add(5, Integer.parseInt(prices.get(2).getJune()));
                officeDataThr.add(6, Integer.parseInt(prices.get(2).getJuly()));
                officeDataThr.add(7, Integer.parseInt(prices.get(2).getAugust()));
                officeDataThr.add(8, Integer.parseInt(prices.get(2).getSeptember()));
                officeDataThr.add(9, Integer.parseInt(prices.get(2).getOctober()));
                officeDataThr.add(10, Integer.parseInt(prices.get(2).getNovember()));
                officeDataThr.add(11, Integer.parseInt(prices.get(2).getDecember()));
                maxData.add(1, Long.parseLong(Collections.max(officeDataThr).toString()));
                result.put("officeDataThr", officeDataThr);
            }
            if (maxData.size() != 0) {
                long sum = 0;
                for (int i = 0; i < maxData.size(); i++) {
                    sum += maxData.get(i);
                }
                long MaxNumber = getNumber(sum);
                result.put("maxData", MaxNumber);
                long Level = MaxNumber /5;
                result.put("Level", Level);
            }
        }
            return result;
        }


   /*
    取最高位上的数字
     */
    public long GetFirstDigit(long a) {
        a = Math.abs(a);
        if(a<10){
            return 1;
        }
        while(a>=10){
           a/= 10;
        }
        return a;
    }
    /*
    计算位数
     */
    public  long  getDigit(long b){
        long count=0;
        if(b < 10){
            return 2;
        }
        while(Math.abs(b)%10>0 || b/10!=0){
            count++;
            b=b/10;
        }
        return  count;
    }
    /*
    向上取整十、整百等等
     */
    public long getNumber(long c){
        c=Math.abs(c);
        long a=0;
        long b=0;
        long d=0;
        a=GetFirstDigit(c);
        b=getDigit(c)-1;
        d=(long)Math.pow(10,b);
        long number=0;
        number=d*(a+1);
        return  number;
    }
}
