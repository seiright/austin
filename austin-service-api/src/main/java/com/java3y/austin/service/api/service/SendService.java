package com.java3y.austin.service.api.service;

import com.java3y.austin.service.api.domain.BatchSendRequest;
import com.java3y.austin.service.api.domain.SendRequest;
import com.java3y.austin.service.api.domain.SendResponse;

/**
 * 发送接口
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 16:02
 * @version 1.0
 */
public interface SendService {


    /**
     * 单文案发送接口
     * @param sendRequest 单文案发送请求封装类
     * @return 发送状态
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     * @param batchSendRequest 多文案发送请求封装类
     * @return 发送状态
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);

}
