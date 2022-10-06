package com.java3y.austin.support.pipeline;


/**
 * 业务执行器
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 23:09
 * @version 1.0
 */
public interface BusinessProcess<T extends ProcessModel> {


    /**
     * 真正处理逻辑
     * @param context 上下文参数
     * @author zhaolifeng
     * @date 2022/10/6 23:09
     */
    void process(ProcessContext<T> context);
}
