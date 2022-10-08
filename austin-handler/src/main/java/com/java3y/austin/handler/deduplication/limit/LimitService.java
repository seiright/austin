package com.java3y.austin.handler.deduplication.limit;

import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.handler.deduplication.DeduplicationParam;
import com.java3y.austin.handler.deduplication.service.AbstractDeduplicationService;

import java.util.Set;

/**
 * 限流服务
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:42
 * @version 1.0
 */
public interface LimitService {


    /**
     * 去重限制
     * @param service 去重器对象
     * @param taskInfo 任务信息
     * @param param 去重参数
     * @return 返回不符合条件的手机号码
     */
    Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param);

}
