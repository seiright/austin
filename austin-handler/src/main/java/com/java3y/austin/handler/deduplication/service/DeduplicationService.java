package com.java3y.austin.handler.deduplication.service;


import com.java3y.austin.handler.deduplication.DeduplicationParam;

/**
 * 去重服务
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 22:33
 * @version 1.0
 */
public interface DeduplicationService {

    /**
     * 去重
     * @param param 去重参数
     */
    void deduplication(DeduplicationParam param);
}
