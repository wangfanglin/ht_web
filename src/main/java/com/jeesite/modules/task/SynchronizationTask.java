package com.jeesite.modules.task;

import com.jeesite.modules.policy.entity.JxPolicyInfo;
import com.jeesite.modules.policy.entity.OldPolicyInfo;
import com.jeesite.modules.policy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jeesite.modules.policy.service.PolicyConverter.ConVertType.*;

/**
 *
 * 同步和德1.0数据的TASK
 * @author tangchao
 *
 */
@Service
public class SynchronizationTask {
    private final JxPolicyInfoService jxPolicyInfoService;
    private final OldPolicyInfoService oldPolicyInfoService;
    private final JxPolicyConverter jxPolicyConverter;
    private final OldPolicyConverter oldPolicyConverter;

    @Autowired
    public SynchronizationTask(JxPolicyConverter jxPolicyConverter, OldPolicyConverter oldPolicyConverter, JxPolicyInfoService jxPolicyInfoService, OldPolicyInfoService oldPolicyInfoService) {
        this.jxPolicyConverter = jxPolicyConverter;
        this.oldPolicyConverter = oldPolicyConverter;
        this.jxPolicyInfoService = jxPolicyInfoService;
        this.oldPolicyInfoService = oldPolicyInfoService;
    }

    /**
     * 同步捷信的保单
     */
    public void convertJxPolicy(){
        List<JxPolicyInfo> list = jxPolicyInfoService.findNewList(new JxPolicyInfo());
        for (JxPolicyInfo policyInfo:
        list) {
            jxPolicyConverter.convert(policyInfo, INSERT);
        }
    }


    /**
     * 同步主保单
     */
    public void convertOldPolicy(){
        List<OldPolicyInfo> list = oldPolicyInfoService.findNewList(new OldPolicyInfo());
        for (OldPolicyInfo policyInfo:
                list) {
            oldPolicyConverter.convert(policyInfo, INSERT);
        }
    }

}
