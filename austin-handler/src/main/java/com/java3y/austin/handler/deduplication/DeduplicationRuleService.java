package com.java3y.austin.handler.deduplication;

import com.java3y.austin.common.constant.AustinConstant;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.enums.DeduplicationType;
import com.java3y.austin.support.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 去重服务功能：1. 获取配置信息 2. 执行所有的去重服务
 * <p>配置样例：
 * <p>key : {@value DeduplicationRuleService#DEDUPLICATION_RULE_KEY}</p>
 * <p>value : {"deduplication_{@code 10}":{"num":1,"time":300},"deduplication_{@code 20}":{"num":5}}</p>
 * <p>means : <ul><li>{"deduplication_{@code 10}：限定同一文案在时间time内，只允许对同一用户发送num次。
 * <li>{"deduplication_{@code 10}：限定用户每天只能接收num次消息</ul></p>
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:29
 * @version 1.0
 */
@Service
public class DeduplicationRuleService {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    @Autowired
    private ConfigService config;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    /**
     * @param taskInfo 任务信息
     * @author zhaolifeng
     * @date 2022/10/8 14:25
     */
    public void duplication(TaskInfo taskInfo) {
        String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, AustinConstant.APOLLO_DEFAULT_VALUE_JSON_OBJECT);

        List<Integer> deduplicationList = DeduplicationType.getDeduplicationList();
        for (Integer deduplicationType : deduplicationList) {
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
            if (deduplicationParam != null) {
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }
    }


}
