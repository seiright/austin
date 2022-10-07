package com.java3y.austin.handler.shield;

import com.java3y.austin.common.domain.TaskInfo;

/**
 * 屏蔽服务
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 21:56
 * @version 1.0
 */
public interface ShieldService {


    /**
     * 屏蔽消息
     * @param taskInfo 任务信息
     */
    void shield(TaskInfo taskInfo);
}
