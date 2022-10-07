package com.java3y.austin.handler.script;


import com.java3y.austin.handler.script.impl.TencentSmsScript;
import com.java3y.austin.handler.script.impl.YunPianSmsScript;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;

/**
 * sms发送脚本的抽象类
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 22:20
 * @version 1.0
 */
@Slf4j
public abstract class BaseSmsScript implements SmsScript {

    @Autowired
    private SmsScriptHolder smsScriptHolder;

    /**
     * 注册处理脚本
     * <ol>
     *     <li>注册短信服务的handler，在对象依赖注入后调用。</li>
     *     <li>执行顺序：Constructor > @Autowired > @PostConstruct</li>
     *     <li>由于{@link TencentSmsScript}和{@link YunPianSmsScript}都继承了当前类，因此这两者在注册bean的时候会触发该方法</li>
     *     <li>SmsScript需在类上加{@link SmsScriptHandler}注解，以注册渠道商短信处理业务。</li>
     * </ol>
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
        smsScriptHolder.putHandler(((SmsScriptHandler) handlerAnnotations).value(), this);
    }
}
