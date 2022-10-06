package com.java3y.austin.cron.service;


/**
 * 具体处理定时任务逻辑的Handler
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 23:27
 * @version 1.0
 */
public interface TaskHandler {

    /**
     * 处理具体的逻辑
     * @param messageTemplateId 消息模板id
     */
    void handle(Long messageTemplateId);

}
