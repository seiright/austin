package com.java3y.austin.support.exception;

import com.java3y.austin.common.enums.RespStatusEnum;
import com.java3y.austin.support.pipeline.ProcessContext;


/**
 * 流程处理异常类
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 22:24
 * @version 1.0
 */
public class ProcessException extends RuntimeException {

    /**
     * 流程处理上下文
     */
    @SuppressWarnings("rawtypes")
    private final ProcessContext processContext;

    @SuppressWarnings("rawtypes")
    public ProcessException(ProcessContext processContext) {
        super();
        this.processContext = processContext;
    }

    @SuppressWarnings("rawtypes")
    public ProcessException(ProcessContext processContext, Throwable cause) {
        super(cause);
        this.processContext = processContext;
    }

    @Override
    public String getMessage() {
        if (this.processContext != null) {
            return this.processContext.getResponse().getMsg();
        } else {
            return RespStatusEnum.CONTEXT_IS_NULL.getMsg();
        }
    }

    @SuppressWarnings("rawtypes")
    public ProcessContext getProcessContext() {
        return processContext;
    }
}
