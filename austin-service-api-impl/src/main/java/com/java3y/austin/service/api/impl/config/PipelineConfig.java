package com.java3y.austin.service.api.impl.config;


import com.java3y.austin.service.api.enums.BusinessCode;
import com.java3y.austin.service.api.impl.action.AfterParamCheckAction;
import com.java3y.austin.service.api.impl.action.AssembleAction;
import com.java3y.austin.service.api.impl.action.PreParamCheckAction;
import com.java3y.austin.service.api.impl.action.SendMqAction;
import com.java3y.austin.service.api.impl.domain.SendTaskModel;
import com.java3y.austin.support.pipeline.BusinessProcess;
import com.java3y.austin.support.pipeline.ProcessController;
import com.java3y.austin.support.pipeline.ProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * api层的pipeline配置类
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 23:15
 * @version 1.0
 */
@Configuration
public class PipelineConfig {

    @Autowired
    private PreParamCheckAction preParamCheckAction;
    @Autowired
    private AssembleAction assembleAction;
    @Autowired
    private AfterParamCheckAction afterParamCheckAction;
    @Autowired
    private SendMqAction sendMqAction;

    /**
     * 普通发送执行流程<ol>
     * <li> 前置参数校验
     * <li> 组装参数
     * <li> 后置参数校验
     * <li> 发送消息至MQ</ol>
     * @return 执行模板
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(preParamCheckAction, assembleAction,
                afterParamCheckAction, sendMqAction));
        return processTemplate;
    }

    /**
     * 消息撤回执行流程<ol>
     * <li> 组装参数
     * <li> 发送MQ</ol>
     * @return 执行模板
     */
    @Bean("recallMessageTemplate")
    public ProcessTemplate recallMessageTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(assembleAction, sendMqAction));
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode和ProcessTemplate
     * @return 流程控制器
     */
    @Bean
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        templateConfig.put(BusinessCode.RECALL.getCode(), recallMessageTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

}
