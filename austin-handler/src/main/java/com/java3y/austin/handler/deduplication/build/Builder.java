package com.java3y.austin.handler.deduplication.build;

import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.handler.deduplication.DeduplicationParam;

/**
 * 去重构建器：用来构建去重参数 {@link DeduplicationParam}
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 22:39
 * @version 1.0
 */
public interface Builder {

    String DEDUPLICATION_CONFIG_PRE = "deduplication_";

    /**
     * 根据配置构建去重参数
     *
     * @param deduplication
     * @param taskInfo
     * @return
     */
    DeduplicationParam build(String deduplication, TaskInfo taskInfo);
}
