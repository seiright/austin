package com.java3y.austin.handler.deduplication.service;

import cn.hutool.core.util.StrUtil;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.enums.DeduplicationType;
import com.java3y.austin.handler.deduplication.limit.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * 频次去重服务 : 一天内一个用户只能收到某个渠道的消息 N 次<br>主要用于获取key<br>
 *
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 15:16
 * @version 1.0
 */
@Service
public class FrequencyDeduplicationService extends AbstractDeduplicationService {


    @Autowired
    public FrequencyDeduplicationService(@Qualifier("SimpleLimitService") LimitService limitService) {

        this.limitService = limitService;
        deduplicationType = DeduplicationType.FREQUENCY.getCode();

    }

    private static final String PREFIX = "FRE";

    /**
     * 业务规则去重 构建key
     * <p>
     * key ： receiver + templateId + sendChannel
     * <p>
     * 一天内一个用户只能收到某个渠道的消息 N 次
     *
     * @param taskInfo 任务信息
     * @param receiver 接收者
     * @return 去重规则key
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return PREFIX + StrUtil.C_UNDERLINE
                + receiver + StrUtil.C_UNDERLINE
                + taskInfo.getMessageTemplateId() + StrUtil.C_UNDERLINE
                + taskInfo.getSendChannel();
    }
}
