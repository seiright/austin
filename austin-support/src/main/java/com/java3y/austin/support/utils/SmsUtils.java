package com.java3y.austin.support.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java3y.austin.common.constant.AustinConstant;
import com.java3y.austin.common.constant.MessageConfigConstant;
import com.java3y.austin.support.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信通用类
 * @author zhaolifeng
 * @version 1.0
 * @description:
 * @date 2022/10/5 17:02
 */
@Component
public class SmsUtils {

    @Autowired
    private ConfigService config;

    /**
     * 根据模板名称和短信服务渠道商得到模板id。需要配置apollo
     * <p>1. 从apollo配置中心寻找{@link com.java3y.austin.common.constant.MessageConfigConstant#SMS_TEMPLATE_CONFIG_KEY}
     * <p>2. 从{@link com.java3y.austin.common.constant.MessageConfigConstant#SMS_TEMPLATE_CONFIG_KEY}中寻找模板名称对应的渠道商模板id集合
     * <p>3. 从集合中寻找指定渠道商的模板id
     * @param templateName 模板名称
     * @param smsServiceProvider 短信服务渠道商名称
     * @return 渠道商对应的模板ID
     * @author zhaolifeng
     * @date 2022/10/6 20:44
     */
    public String getTemplateIdByName(String templateName,String smsServiceProvider) {
        String accountValues = config.getProperty(MessageConfigConstant.SMS_TEMPLATE_CONFIG_KEY, AustinConstant.APOLLO_DEFAULT_VALUE_JSON_ARRAY);
        JSONArray jsonArray = JSON.parseArray(accountValues);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject templates = jsonObject.getJSONObject(templateName);
            if (templates!=null) return templates.getObject(smsServiceProvider,String.class);
        }
        return null;
    }
}
