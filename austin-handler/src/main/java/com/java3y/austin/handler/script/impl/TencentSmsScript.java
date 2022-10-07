package com.java3y.austin.handler.script.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.java3y.austin.common.constant.MessageConfigConstant;
import com.java3y.austin.common.constant.SendAccountConstant;
import com.java3y.austin.common.dto.account.TencentSmsAccount;
import com.java3y.austin.common.enums.SmsStatus;
import com.java3y.austin.handler.domain.sms.SmsParam;
import com.java3y.austin.handler.script.BaseSmsScript;
import com.java3y.austin.handler.script.SmsScript;
import com.java3y.austin.handler.script.SmsScriptHandler;
import com.java3y.austin.support.domain.SmsRecord;
import com.java3y.austin.support.utils.AccountUtils;
import com.java3y.austin.support.utils.SmsUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 腾讯云短信服务
 * <ol>
 *     <li><a href="https://cloud.tencent.com/document/api/382/55981">发送短信接入文档</a>
 *     <li>推荐直接使用SDK调用
 *     <li>推荐使用API Explorer 生成代码
 * </ol>
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 22:07
 * @version 1.0
 */
@Slf4j
@SmsScriptHandler("TencentSmsScript")
public class TencentSmsScript extends BaseSmsScript implements SmsScript {

    private static final Integer PHONE_NUM = 11;

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private SmsUtils smsUtils;

    @Override
    public List<SmsRecord> send(SmsParam smsParam) {
        try {
            TencentSmsAccount tencentSmsAccount = accountUtils.getAccount(SendAccountConstant.TENCENT_SMS_CODE, SendAccountConstant.SMS_ACCOUNT_KEY, SendAccountConstant.SMS_PREFIX, TencentSmsAccount.class);
            tencentSmsAccount.setTemplateId(smsUtils.getTemplateIdByName(smsParam.getTemplateName(), MessageConfigConstant.SMS_PROVIDER_TENCENT));
            SmsClient client = init(tencentSmsAccount);
            SendSmsRequest request = assembleReq(smsParam, tencentSmsAccount);
            SendSmsResponse response = client.SendSms(request);
            return assembleSmsRecord(smsParam, response, tencentSmsAccount);
        } catch (Exception e) {
            log.error("TencentSmsScript#send fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
            return null;
        }
    }

    private List<SmsRecord> assembleSmsRecord(SmsParam smsParam, SendSmsResponse response, TencentSmsAccount tencentSmsAccount) {
        if (response == null || ArrayUtil.isEmpty(response.getSendStatusSet())) {
            return null;
        }

        List<SmsRecord> smsRecordList = new ArrayList<>();
        for (SendStatus sendStatus : response.getSendStatusSet()) {

            // 腾讯返回的电话号有前缀，这里取巧直接翻转获取手机号
            String phone = new StringBuilder(new StringBuilder(sendStatus.getPhoneNumber())
                    .reverse().substring(0, PHONE_NUM)).reverse().toString();

            SmsRecord smsRecord = SmsRecord.builder()
                    .sendDate(Integer.valueOf(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)))
                    .messageTemplateId(smsParam.getMessageTemplateId())
                    .phone(Long.valueOf(phone))
                    .supplierId(tencentSmsAccount.getSupplierId())
                    .supplierName(tencentSmsAccount.getSupplierName())
                    .msgContent(smsParam.getContent())
                    .seriesId(sendStatus.getSerialNo())
                    .chargingNum(Math.toIntExact(sendStatus.getFee()))
                    .status(SmsStatus.SEND_SUCCESS.getCode())
                    .reportContent(sendStatus.getCode())
                    .created(Math.toIntExact(DateUtil.currentSeconds()))
                    .updated(Math.toIntExact(DateUtil.currentSeconds()))
                    .build();

            smsRecordList.add(smsRecord);
        }
        return smsRecordList;
    }

    /**
     * 依照腾讯云API-组装消息
     * @param smsParam-消息参数
     * @param account-腾讯云消息账户
     * @return 消息请求
     * @author zhaolifeng
     * @date 2022/10/3 21:46
     */
    private SendSmsRequest assembleReq(SmsParam smsParam, TencentSmsAccount account) {
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet = smsParam.getPhones().toArray(new String[smsParam.getPhones().size() - 1]);
        req.setPhoneNumberSet(phoneNumberSet);
        req.setSmsSdkAppId(account.getSmsSdkAppId());
        req.setSignName(account.getSignName());
        req.setTemplateId(account.getTemplateId());
        String[] templateParamSet = smsParam.getContent().split(",");
        req.setTemplateParamSet(templateParamSet);
        req.setSessionContext(IdUtil.fastSimpleUUID());
        return req;
    }

    /**
     * 初始化消息客户端
     * @param account-腾讯云消息账户
     */
    private SmsClient init(TencentSmsAccount account) {
        Credential cred = new Credential(account.getSecretId(), account.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(account.getUrl());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, account.getRegion(), clientProfile);
    }

}

