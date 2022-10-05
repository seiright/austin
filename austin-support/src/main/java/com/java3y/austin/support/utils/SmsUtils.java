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
 * @author zhaolifeng
 * @version 1.0
 * @description: TODO
 * @date 2022/10/5 17:02
 */
@Component
public class SmsUtils {

    @Autowired
    private ConfigService config;

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
