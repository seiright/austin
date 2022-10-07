package com.java3y.austin.handler.receiver.service;


import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.support.domain.MessageTemplate;

import java.util.List;

/**
 * 消费消息服务
 * <p>从MQ拉取消息进行消费。发送/撤回消息
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 22:27
 * @version 1.0
 */
public interface ConsumeService {

    /**
     * 从MQ拉到消息进行消费，发送消息
     *
     * @param taskInfoLists 任务信息列表
     */
    void consume2Send(List<TaskInfo> taskInfoLists);


    /**
     * 从MQ拉到消息进行消费，撤回消息
     *
     * @param messageTemplate 短信模板
     */
    void consume2recall(MessageTemplate messageTemplate);


}
