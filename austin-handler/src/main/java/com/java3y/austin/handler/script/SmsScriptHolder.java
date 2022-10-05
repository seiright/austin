package com.java3y.austin.handler.script;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * sendAccount->SmsScript的映射关系
 *
 * @author 3y
 */
@Component
public class SmsScriptHolder {

    private Map<String, SmsScript> handlers = new HashMap<>(8);

    /**
     * 绑定渠道商业务bean
     * @param scriptName-渠道商名称
     * @param handler-渠道商业务bean
     * @author zhaolifeng
     * @date 2022/10/5 14:49
     */
    public void putHandler(String scriptName, SmsScript handler) {
        handlers.put(scriptName, handler);
    }

    /**
     * 由渠道商名称得到处理业务的对象
     * @param scriptName-渠道商名称
     * @return 渠道商业务bean
     * @author zhaolifeng
     * @date 2022/10/5 14:49
     */
    public SmsScript route(String scriptName) {
        return handlers.get(scriptName);
    }
}
