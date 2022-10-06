package com.java3y.austin.support.mq;


import com.alibaba.fastjson.JSON;

/**
 * 发送数据至消息队列
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 21:13
 * @version 1.0
 */
public interface SendMqService {

    /**
     * 发送消息
     * @param topic 主题
     * @param jsonValue {@link JSON}格式的数据
     * @param tagId 标签id
     */
    void send(String topic, String jsonValue, String tagId);


    /**
     * 发送消息
     *
     * @param topic 主题
     * @param jsonValue {@link JSON}格式的数据
     */
    void send(String topic, String jsonValue);

}
