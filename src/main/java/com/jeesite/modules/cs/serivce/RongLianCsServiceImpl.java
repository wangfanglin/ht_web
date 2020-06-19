package com.jeesite.modules.cs.serivce;

import com.jeesite.modules.cs.enetity.OutBoundParameter;
import com.jeesite.modules.cs.enetity.OutBoundResult;
import com.jeesite.modules.cs.rongmo.RongMoClint;
import com.jeesite.modules.cs.rongmo.RongMoOutboundParameter;
import com.jeesite.modules.cs.rongmo.RongMoOutboundResult;
import com.jeesite.modules.log.entity.ThirdInterfaceLog;
import com.jeesite.modules.log.entity.ThirdInterfaceType;
import com.jeesite.modules.log.service.ThirdInterfaceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.jeesite.modules.cs.rongmo.RongMoClint.OUTBOUND_PATH;

/**
 * 容联七陌的客服平台服务实现
 * @author tangchao
 */
@Service
public class RongLianCsServiceImpl implements CsService {
    @Autowired
    ThirdInterfaceLogService logService;

    /**
     * 外呼接口
     * @param parameter 外呼需要的参数
     * @return
     */
    @Override
    public OutBoundResult outbound(OutBoundParameter parameter) {
        RongMoOutboundResult result = RongMoClint.outbound(new RongMoOutboundParameter(parameter));
        logService.save(new ThirdInterfaceLog(OUTBOUND_PATH, ThirdInterfaceType.RONGLIAN,"outbound",parameter,result));
        return new OutBoundResult(result);
    }
}
