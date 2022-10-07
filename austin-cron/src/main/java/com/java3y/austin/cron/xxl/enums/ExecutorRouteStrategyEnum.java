package com.java3y.austin.cron.xxl.enums;


/**
 * 路由策略
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 20:07
 * @version 1.0
 */
public enum ExecutorRouteStrategyEnum {

    FIRST,
    LAST,
    ROUND,
    RANDOM,
    CONSISTENT_HASH,
    LEAST_FREQUENTLY_USED,
    LEAST_RECENTLY_USED,
    FAILOVER,
    BUSYOVER,
    SHARDING_BROADCAST;

    ExecutorRouteStrategyEnum() {
    }
}
