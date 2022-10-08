package com.java3y.austin.handler.pending;

import com.dtp.core.thread.DtpExecutor;
import com.java3y.austin.handler.config.HandlerThreadPoolConfig;
import com.java3y.austin.handler.utils.GroupIdMappingUtils;
import com.java3y.austin.support.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;


/**
 * 存储 每种消息类型 与 TaskPending 的关系
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 21:44
 * @version 1.0
 */
@Component
public class TaskPendingHolder {
    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    private final Map<String, ExecutorService> taskPendingHolder = new HashMap<>(32);

    /**
     * 获取得到所有的groupId
     */
    private static final List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();

    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     * <p>example ThreadPoolName:austin.im.notice
     * <p>可以通过apollo配置：dynamic-tp-apollo-dtp.yml  动态修改线程池的信息
     */
    @PostConstruct
    public void init() {
        for (String groupId : groupIds) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            threadPoolUtils.register(executor);

            taskPendingHolder.put(groupId, executor);
        }
    }

    /**
     * 得到对应的线程池
     *
     * @param groupId 业务id
     * @return 线程池服务
     */
    public ExecutorService route(String groupId) {
        return taskPendingHolder.get(groupId);
    }


}
