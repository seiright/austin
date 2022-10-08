package com.java3y.austin.handler.deduplication.limit;

import cn.hutool.core.collection.CollUtil;
import com.java3y.austin.common.constant.AustinConstant;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.handler.deduplication.DeduplicationParam;
import com.java3y.austin.handler.deduplication.service.AbstractDeduplicationService;
import com.java3y.austin.support.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 采用普通的计数去重方法，限制的是每天发送的条数。
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 22:54
 * @version 1.0
 */
@Service(value = "SimpleLimitService")
public class SimpleLimitService extends AbstractLimitService {

    private static final String LIMIT_TAG = "SP_";

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 获取去重用户列表<br>
     * 对于符合去重条件的用户, 加入return列表中; 不符合条件的用户, 无记录则添加, 有纪录则更新ttl和频次
     * @param service 当前去重服务
     * @param taskInfo 任务信息
     * @param param 去重信息
     * @return 去重用户列表
     * @author zhaolifeng
     * @date 2022/10/8 22:52
     */
    @Override
    public Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param) {
        Set<String> filterReceiver = new HashSet<>(taskInfo.getReceiver().size());

        Map<String, String> readyPutRedisReceiver = new HashMap<>(taskInfo.getReceiver().size());

        List<String> keys = deduplicationAllKey(service, taskInfo).stream().map(key -> LIMIT_TAG + key).collect(Collectors.toList());
        Map<String, String> inRedisValue = redisUtils.mGet(keys);

        for (String receiver : taskInfo.getReceiver()) {
            String key = LIMIT_TAG + deduplicationSingleKey(service, taskInfo, receiver);
            String value = inRedisValue.get(key);

            // 符合条件的用户
            if (value != null && Integer.parseInt(value) >= param.getCountNum()) {
                filterReceiver.add(receiver);
            } else {
                readyPutRedisReceiver.put(receiver, key);
            }
        }


        putInRedis(readyPutRedisReceiver, inRedisValue, param.getDeduplicationTime());

        return filterReceiver;
    }


    /**
     * 存入redis 实现去重
     *
     * @param readyPutRedisReceiver 准备存入或更新redis的用户列表, 用于将来去重服务
     */
    private void putInRedis(Map<String, String> readyPutRedisReceiver,
        Map<String, String> inRedisValue, Long deduplicationTime) {
        Map<String, String> keyValues = new HashMap<>(readyPutRedisReceiver.size());
        for (Map.Entry<String, String> entry : readyPutRedisReceiver.entrySet()) {
            String key = entry.getValue();
            if (inRedisValue.get(key) != null) {
                keyValues.put(key, String.valueOf(Integer.parseInt(inRedisValue.get(key)) + 1));
            } else {
                keyValues.put(key, String.valueOf(AustinConstant.TRUE));
            }
        }
        if (CollUtil.isNotEmpty(keyValues)) {
            redisUtils.pipelineSetEx(keyValues, deduplicationTime);
        }
    }

}
