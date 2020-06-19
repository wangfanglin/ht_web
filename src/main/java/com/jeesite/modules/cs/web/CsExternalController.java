package com.jeesite.modules.cs.web;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.cs.rongmo.RongMoEventPushParameter;
import com.jeesite.modules.settlementform.htcalllog.entity.HtCallLog;
import com.jeesite.modules.settlementform.htcalllog.service.HtCallLogService;
import com.jeesite.modules.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 客服的外部服务接口
 * @author tangchao
 */
@RestController
@RequestMapping("/cs")
public class CsExternalController {

    @Autowired
    private HtCallLogService htCallLogService;



    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 容联客服的 通话结束后的推送
     * @param parameter     容联客服入参
     */
    @RequestMapping("ronglian/hangup-push")
    @ResponseBody
    public void ronglianHangupPush(RongMoEventPushParameter parameter){
        logger.info("容联客服的 通话结束后的推送 入参：{}",parameter);
        String id = parameter.getActionID();
        if (StringUtils.isBlank(id)) id = parameter.getUserID();
        String url = parameter.getUrl();
        HtCallLog htCallLog = new HtCallLog(id);
        htCallLog.setData(url);

        if (StringUtils.isNotBlank(id)){
            htCallLogService.update(htCallLog);
        }
    }


    /**
     * 容联客服的 通话接通后的推送
     * @param parameter     容联客服入参
     */
    @RequestMapping("ronglian/link-push")
    @ResponseBody
    public void ronglianLinkPush(RongMoEventPushParameter parameter){
        logger.info("容联客服的 通话接通后的推送 入参：{}",parameter);
        if(parameter==null) return;
        String id = parameter.getUserID();
        if(StringUtils.isBlank(id)) return;
        String empId = parameter.getAgent();
        String userId = htCallLogService.getUserIdByEmpNoFirst(empId);
        if(StringUtils.isBlank(userId)) return;
        HtCallLog htCallLog = new HtCallLog(id);
        htCallLog.setUserId(userId);
        htCallLogService.save(htCallLog);
    }


    /**
     * 容联客服 呼入时的 事件推送接口
     * @param callNo
     * @return
     */
    @GetMapping("ronglian/incoming-call")
    public String incomingCall(String callNo){
        logger.info("容联客服 呼入时的 事件推送接口 入参：{}",callNo);
        HtCallLog callLog = new HtCallLog();
        callLog.setUserPhone(callNo);
        callLog.setType("1");
        htCallLogService.insert(callLog);
        return callLog.getId();
    }
}
