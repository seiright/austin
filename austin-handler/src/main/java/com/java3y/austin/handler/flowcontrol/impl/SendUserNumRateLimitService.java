package com.java3y.austin.handler.flowcontrol.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.handler.enums.RateLimitStrategy;
import com.java3y.austin.handler.flowcontrol.FlowControlParam;
import com.java3y.austin.handler.flowcontrol.FlowControlService;
import com.java3y.austin.handler.flowcontrol.annotations.LocalRateLimit;

/**
 * Created by TOM
 * On 2022/7/21 17:14
 */
@LocalRateLimit(rateLimitStrategy = RateLimitStrategy.SEND_USER_NUM_RATE_LIMIT)
public class SendUserNumRateLimitService implements FlowControlService {

    /**
     * 根据渠道进行流量控制 <br>
     * FIXME 跟{@link RequestRateLimitService}的设计大同小异, 有没有更好的方案?目前的阻塞貌似没有意义
     * @param taskInfo         任务信息
     * @param flowControlParam 流量控制参数
     */
    @Override
    @SuppressWarnings("UnstableApiUsage")
    public Double flowControl(TaskInfo taskInfo, FlowControlParam flowControlParam) {
        RateLimiter rateLimiter = flowControlParam.getRateLimiter();
        return rateLimiter.acquire(taskInfo.getReceiver().size());
    }
}
