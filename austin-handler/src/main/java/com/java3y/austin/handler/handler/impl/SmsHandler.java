package com.java3y.austin.handler.handler.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Throwables;
import com.java3y.austin.common.constant.AustinConstant;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.dto.model.SmsContentModel;
import com.java3y.austin.common.enums.ChannelType;
import com.java3y.austin.handler.domain.sms.MessageTypeSmsConfig;
import com.java3y.austin.handler.domain.sms.SmsParam;
import com.java3y.austin.handler.handler.BaseHandler;
import com.java3y.austin.handler.handler.Handler;
import com.java3y.austin.handler.script.SmsScriptHolder;
import com.java3y.austin.support.dao.MessageTemplateDao;
import com.java3y.austin.support.dao.SmsRecordDao;
import com.java3y.austin.support.domain.MessageTemplate;
import com.java3y.austin.support.domain.SmsRecord;
import com.java3y.austin.support.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * 短信发送处理
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 22:14
 * @version 1.0
 */
@Component
@Slf4j
public class SmsHandler extends BaseHandler implements Handler {

    public SmsHandler() {
        channelCode = ChannelType.SMS.getCode();
    }

    @Autowired
    private SmsRecordDao smsRecordDao;

    @Autowired
    private SmsScriptHolder smsScriptHolder;

    @Autowired
    private ConfigService config;

    @Autowired
    private MessageTemplateDao messageTemplateDao;


    /**
     * 短信处理服务
     * <ol>
     *     <li>动态配置做流量负载</li>
     *     <li>发送短信</li>
     * </ol>
     * @param taskInfo 任务信息
     * @return 是否处理成功
     * @author zhaolifeng
     * @date 2022/10/7 22:14
     */
    @Override
    public boolean handler(TaskInfo taskInfo) {
        SmsParam smsParam = SmsParam.builder()
                .phones(taskInfo.getReceiver())
                .content(getSmsContent(taskInfo))
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .templateName(messageTemplateDao.getMessageTemplateById(taskInfo.getMessageTemplateId()).getTemplateName())
                .build();
        try {
            MessageTypeSmsConfig[] messageTypeSmsConfigs = loadBalance(Objects.requireNonNull(getMessageTypeSmsConfig(taskInfo.getMsgType())));
            assert messageTypeSmsConfigs != null;
            for (MessageTypeSmsConfig messageTypeSmsConfig : messageTypeSmsConfigs) {
                List<SmsRecord> recordList = smsScriptHolder.route(messageTypeSmsConfig.getScriptName()).send(smsParam);
                if (CollUtil.isNotEmpty(recordList)) {
                    smsRecordDao.saveAll(recordList);
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("SmsHandler#handler fail:{},params:{}",
                    Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
        }
        return false;
    }


    /**
     * 流量负载
     * 根据配置的权重优先走某个账号渠道商，并取出一个备份的账号渠道商
     * @param messageTypeSmsConfigs-对于每种消息类型的 短信配置
     * @return 账号数组：一个现用账号渠道商，一个备份账号渠道商(如果有)
     * @author zhaolifeng
     * @date 2022/10/5 14:23
     */
    private MessageTypeSmsConfig[] loadBalance(List<MessageTypeSmsConfig> messageTypeSmsConfigs) {

        int total = 0;
        for (MessageTypeSmsConfig channelConfig : messageTypeSmsConfigs) {
            total += channelConfig.getWeights();
        }

        // 生成一个随机数[1,total]，看落到哪个区间
        Random random = new Random();
        int index = random.nextInt(total) + 1;

        MessageTypeSmsConfig supplier;
        MessageTypeSmsConfig supplierBack;
        for (int i = 0; i < messageTypeSmsConfigs.size(); ++i) {
            if (index <= messageTypeSmsConfigs.get(i).getWeights()) {
                supplier = messageTypeSmsConfigs.get(i);

                // 取下一个供应商
                int j = (i + 1) % messageTypeSmsConfigs.size();
                if (i == j) {
                    return new MessageTypeSmsConfig[]{supplier};
                }
                supplierBack = messageTypeSmsConfigs.get(j);
                return new MessageTypeSmsConfig[]{supplier, supplierBack};
            }
            index -= messageTypeSmsConfigs.get(i).getWeights();
        }
        return null;
    }

    /**
     * 每种类型都会有其下发渠道账号的配置(流量占比也会配置里面)
     *
     * <p>样例：
     * <p>key：msgTypeSmsConfig
     * <p>value：[{"message_type_10":[{"weights":80,"scriptName":"TencentSmsScript"},{"weights":20,"scriptName":"YunPianSmsScript"}]},{"message_type_20":[{"weights":20,"scriptName":"YunPianSmsScript"}]},{"message_type_30":[{"weights":20,"scriptName":"TencentSmsScript"}]},{"message_type_40":[{"weights":20,"scriptName":"TencentSmsScript"}]}]
     * <ul>
     *     <li>通知类短信有两个发送渠道 TencentSmsScript 占80%流量，YunPianSmsScript占20%流量</li>
     *     <li>营销类短信只有一个发送渠道 YunPianSmsScript</li>
     *     <li>验证码短信只有一个发送渠道 TencentSmsScript</li>
     * </ul>
     * @param msgType-消息类型
     * @return 渠道账号配置
     */
    private List<MessageTypeSmsConfig> getMessageTypeSmsConfig(Integer msgType) {

        String apolloKey = "msgTypeSmsConfig";
        String messagePrefix = "message_type_";

        String property = config.getProperty(apolloKey, AustinConstant.APOLLO_DEFAULT_VALUE_JSON_ARRAY);
        JSONArray jsonArray = JSON.parseArray(property);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray array = jsonArray.getJSONObject(i).getJSONArray(messagePrefix + msgType);
            if (CollUtil.isNotEmpty(array)) {
                return JSON.parseArray(JSON.toJSONString(array), MessageTypeSmsConfig.class);
            }
        }
        return null;
    }

    /**
     * 如果有输入链接，则把链接拼在文案后
     * <p>
     * PS: 这里可以考虑将链接 转 短链
     * PS: 如果是营销类的短信，需考虑拼接 回TD退订 之类的文案
     */
    private String getSmsContent(TaskInfo taskInfo) {
        SmsContentModel smsContentModel = (SmsContentModel) taskInfo.getContentModel();
        if (StrUtil.isNotBlank(smsContentModel.getUrl())) {
            return smsContentModel.getContent() + " " + smsContentModel.getUrl();
        } else {
            return smsContentModel.getContent();
        }
    }

    /**
     * TODO 待完善召回功能
     * @param messageTemplate 消息模板
     * @author zhaolifeng
     * @date 2022/10/7 22:17
     */
    @Override
    public void recall(MessageTemplate messageTemplate) {

    }
}
