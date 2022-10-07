package com.java3y.austin.cron.xxl.enums;

/**
 * 调度类型
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 20:08
 * @version 1.0
 */
public enum ScheduleTypeEnum {

    NONE,
    /**
     * schedule by cron
     */
    CRON,

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE;

    ScheduleTypeEnum() {
    }

}
