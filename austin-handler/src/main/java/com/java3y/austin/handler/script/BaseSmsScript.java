package com.java3y.austin.handler.script;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;

/**
 * sms发送脚本的抽象类
 *
 * @author 3y
 */
@Slf4j
public abstract class BaseSmsScript implements SmsScript {

    @Autowired
    private SmsScriptHolder smsScriptHolder;

    /**
     * 1. 注册短信服务的handler，在对象依赖注入后调用。
     * 2. 执行顺序：Constructor > @Autowired > @PostConstruct
     * 3. 由于TencentSmsScript和YunPianSmsScript都继承了当前类，因此这两者在注册bean的时候会触发registerProcessScript()方法
     * 4. SmsScript需在类上加@SmsScriptHandler注解，以注册渠道商短信处理业务。
     * @author zhaolifeng
     * @date 2022/10/5 14:31
     */
    @PostConstruct
    public void registerProcessScript() {
        if (ArrayUtils.isEmpty(this.getClass().getAnnotations())) {
            log.error("BaseSmsScript can not find annotation!");
            return;
        }
        Annotation handlerAnnotations = null;
        for (Annotation annotation : this.getClass().getAnnotations()) {
            if (annotation instanceof SmsScriptHandler) {
                handlerAnnotations = annotation;
                break;
            }
        }
        if (handlerAnnotations == null) {
            log.error("handler annotations not declared");
            return;
        }
        //注册handler
        smsScriptHolder.putHandler(((SmsScriptHandler) handlerAnnotations).value(), this);
    }
}
