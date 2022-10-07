package com.java3y.austin.service.api.service;

import com.java3y.austin.service.api.domain.SendRequest;
import com.java3y.austin.service.api.domain.SendResponse;

/**
 * 撤回接口
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 21:31
 * @version 1.0
 */
public interface RecallService {


    /**
     * 根据模板ID撤回消息
     *
     * @param sendRequest 发送请求
     * @return 发送回应
     */
    SendResponse recall(SendRequest sendRequest);
}
