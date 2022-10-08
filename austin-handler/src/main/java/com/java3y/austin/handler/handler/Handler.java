package com.java3y.austin.handler.handler;

import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.support.domain.MessageTemplate;

/**
 * 消息处理器
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:01
 * @version 1.0
 */
public interface Handler {

    /**
     * 处理器 发送消息
     *
     * @param taskInfo 任务信息
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     *
     * @param messageTemplate 消息模板
     */
    void recall(MessageTemplate messageTemplate);


}
