package com.java3y.austin.handler.script;


import com.java3y.austin.handler.domain.sms.SmsParam;
import com.java3y.austin.support.domain.SmsRecord;

import java.util.List;


/**
 * 短信脚本 接口
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 22:20
 * @version 1.0
 */
public interface SmsScript {

    /**
     * 发送短信
     * @param smsParam
     * @return 渠道商接口返回值

     */
    List<SmsRecord> send(SmsParam smsParam);

}
