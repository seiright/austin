package com.java3y.austin.support.utils;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import com.java3y.austin.support.config.ThreadPoolExecutorShutdownDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程工具类
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 20:55
 * @version 1.0
 */
@Component
public class ThreadPoolUtils {

    @Autowired
    private ThreadPoolExecutorShutdownDefinition shutdownDefinition;

    private static final String SOURCE_NAME = "austin";



    /**
     * 将当前线程池注册到动态线程池内
     * <p>1. 将当前线程池 加入到 动态线程池内
     * <p>2. 注册 线程池 被Spring管理，优雅关闭
     * @param dtpExecutor 动态线程池执行者，其重写了{@link ThreadPoolExecutor}
     * @author zhaolifeng
     * @date 2022/10/6 20:56
     */
    public void register(DtpExecutor dtpExecutor) {
        DtpRegistry.register(dtpExecutor, SOURCE_NAME);
        shutdownDefinition.registryExecutor(dtpExecutor);
    }
}
