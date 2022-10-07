package com.java3y.austin.handler.utils;


import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.enums.ChannelType;
import com.java3y.austin.common.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * groupId映射工具类
 * <p>groupId 标识着每一个消费者组
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 21:55
 * @version 1.0
 */
public class GroupIdMappingUtils {

    /**
     * 获取所有的groupIds
     * (不同的渠道不同的消息类型拥有自己的groupId)
     */
    public static List<String> getAllGroupIds() {
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType : ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }


    /**
     * 根据TaskInfo获取当前消息的groupId
     * @param taskInfo 任务信息
     * @return groupId
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        ChannelType channelType = ChannelType.getEnumByCode(taskInfo.getSendChannel());
        MessageType messageType = MessageType.getEnumByCode(taskInfo.getMsgType());
        if (channelType==null||messageType==null) return "";
        return channelType.getCodeEn() + "." + messageType.getCodeEn();
    }
}
