package com.jeesite.modules.task;

import com.jeesite.modules.advisory.service.HtAdvisoryInfoService;
import com.jeesite.modules.forminfo.entity.HtFormInfo;
import com.jeesite.modules.forminfo.service.HtFormInfoService;
import com.jeesite.modules.htmaintenancepoint.dao.HtMaintainPointKpiDao;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintainPointKpi;
import com.jeesite.modules.htmaintenancepoint.entity.HtMaintenancePoint;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintainPointKpiService;
import com.jeesite.modules.htmaintenancepoint.service.HtMaintenancePointService;
import com.jeesite.modules.httimeefficiency.service.HtTimeEfficiencyService;
import com.jeesite.modules.sys.service.OfficeService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务*/
public class MaintainPointTask {

    @Autowired
    HtMaintenancePointService htMaintenancePointService;
    @Autowired
    HtFormInfoService htFormInfoService;
    @Autowired
    HtTimeEfficiencyService htTimeEfficiencyService;
    @Autowired
    HtAdvisoryInfoService htAdvisoryInfoService;
    @Autowired
    HtMaintainPointKpiService htMaintainPointKpiService;

    /**
     * 计算每个维修网点得kpi分数
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public void maintainPointKpi() {
        //查询所有网点
        List<HtMaintenancePoint> pointList = htMaintenancePointService.findPointOfficeList();
        if (pointList != null && pointList.size() > 0) {
            htMaintainPointKpiService.truncateInfo();
            for (HtMaintenancePoint point : pointList) {
                String pointId = point.getId();
                //获取网点维修完成数量
                double orderfinishSum =  htFormInfoService.findFormCount(point.getOrganizationId());
                //获取网点维修总数量
                double orderSum = htFormInfoService.findOrderSum(point.getOrganizationId());
                //获取总金额
                Double sumPrice = htFormInfoService.getPointSumPrice(point.getOrganizationId());
                List<HtFormInfo> totalList = htFormInfoService.findTotalFromInOffice(point.getOrganizationId());
                //工单维修总时间相加
                double sumDay = 0;
                for (HtFormInfo htFormInfo : totalList) {
                     sumDay += htTimeEfficiencyService.getTimelinessSum(htFormInfo.getId());
                }
                //获取投诉总数
                double complaintCount = htAdvisoryInfoService.findCountInOffice("3",point.getOrganizationId());
                //获取返修总鼠
                double repairCount = htAdvisoryInfoService.findCountInOffice("4",point.getOrganizationId());

                //格式化小数点后保留两位
                DecimalFormat df = new DecimalFormat("#.##");

                double average_price = sumPrice==null?0.0:Double.parseDouble(df.format(sumPrice/orderfinishSum));
                double time_efficiency = sumDay/orderSum;
                if (Double.isNaN(time_efficiency)){
                    time_efficiency = 0.00;
                }
                double repair_rate = repairCount/orderSum;
                if (Double.isNaN(repair_rate)){
                    repair_rate = 0.00;
                }
                double complain_efficiency =complaintCount/orderfinishSum;
                if (Double.isNaN(complain_efficiency)){
                    complain_efficiency = 0.00;
                }
                log.info("计算后分数"+average_price);
                log.info("计算后分数"+time_efficiency);
                log.info("计算后分数"+repair_rate);
                log.info("计算后分数"+complain_efficiency);
                HtMaintainPointKpi htMaintainPointKpi = new HtMaintainPointKpi();
                htMaintainPointKpi.setPointId(pointId);
                htMaintainPointKpi.setAveragePrice(average_price);
                htMaintainPointKpi.setTimeEfficiency(time_efficiency);
                htMaintainPointKpi.setRepairRate(repair_rate);
                htMaintainPointKpi.setComplainEfficiency(complain_efficiency);
                htMaintainPointKpiService.save(htMaintainPointKpi);
            }
        }
    }
}
