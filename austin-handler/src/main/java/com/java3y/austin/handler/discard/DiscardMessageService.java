package com.java3y.austin.handler.discard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.java3y.austin.common.constant.AustinConstant;
import com.java3y.austin.common.domain.AnchorInfo;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.enums.AnchorState;
import com.java3y.austin.support.service.ConfigService;
import com.java3y.austin.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 丢弃模板消息
 * <p>配置示例</p>
 * <p>key: {@value DiscardMessageService#DISCARD_MESSAGE_KEY}</p>
 * <p>value: {@code ["1","2"]}</p>
 * <p>means: 丢弃模板id为1和2的信息</p>
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:02
 * @version 1.0
 */
@Service
public class DiscardMessageService {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";

    @Autowired
    private ConfigService config;

    @Autowired
    private LogUtils logUtils;
    

    /**
     * 丢弃消息，配置在apollo
     * @param taskInfo 消息信息
     * @return 是否丢弃
     */
    public boolean isDiscard(TaskInfo taskInfo) {
        JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY,
                AustinConstant.APOLLO_DEFAULT_VALUE_JSON_ARRAY));

        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
            return true;
        }
        return false;
    }

}
