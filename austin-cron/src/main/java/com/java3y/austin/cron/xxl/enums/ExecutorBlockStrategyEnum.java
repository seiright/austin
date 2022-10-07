package com.java3y.austin.cron.xxl.enums;

/**
 * 执行阻塞队列
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 20:07
 * @version 1.0
 */
public enum ExecutorBlockStrategyEnum {
    /**
     * 单机串行
     */
    SERIAL_EXECUTION,

    /**
     * 丢弃后续调度
     */
    DISCARD_LATER,

    /**
     * 覆盖之前调度
     */
    COVER_EARLY;

    ExecutorBlockStrategyEnum() {

    }
}
