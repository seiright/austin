package com.java3y.austin.handler.deduplication.service;

import cn.hutool.core.collection.CollUtil;
import com.java3y.austin.common.domain.AnchorInfo;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.handler.deduplication.DeduplicationHolder;
import com.java3y.austin.handler.deduplication.DeduplicationParam;
import com.java3y.austin.handler.deduplication.limit.LimitService;
import com.java3y.austin.support.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 去重服务
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:41
 * @version 1.0
 */
@Slf4j
public abstract class AbstractDeduplicationService implements DeduplicationService {

    protected Integer deduplicationType;

    protected LimitService limitService;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @PostConstruct
    private void init() {
        deduplicationHolder.putService(deduplicationType, this);
    }

    @Autowired
    private LogUtils logUtils;


    @Override
    public void deduplication(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();

        Set<String> filterReceiver = limitService.limitFilter(this, taskInfo, param);

        if (CollUtil.isNotEmpty(filterReceiver)) {
            taskInfo.getReceiver().removeAll(filterReceiver);
            logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(filterReceiver).state(param.getAnchorState().getCode()).build());
        }
    }


    /**
     * 构建去重的Key
     *
     * @param taskInfo 任务信息
     * @param receiver 接收者
     * @return 去重Key
     */
    public abstract String deduplicationSingleKey(TaskInfo taskInfo, String receiver);


}
