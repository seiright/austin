package com.java3y.austin.cron.xxl.enums;

/**
 * 调度过期策略
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 20:08
 * @version 1.0
 */
public enum MisfireStrategyEnum {

    /**
     * do nothing
     */
    DO_NOTHING,

    /**
     * fire once now
     */
    FIRE_ONCE_NOW;

    MisfireStrategyEnum() {
    }
}
