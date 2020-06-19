package com.jeesite.modules.cs.serivce;

import com.jeesite.modules.cs.enetity.OutBoundParameter;
import com.jeesite.modules.cs.enetity.OutBoundResult;
import org.springframework.stereotype.Service;

/**
 * 客服平台的服务接口
 *
 *
 */

@Service
public interface CsService {
    /**
     * 外呼方法
     */
    OutBoundResult outbound(OutBoundParameter parameter);
}
