package com.java3y.austin.handler.pending;


import cn.hutool.core.collection.CollUtil;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.handler.deduplication.DeduplicationRuleService;
import com.java3y.austin.handler.discard.DiscardMessageService;
import com.java3y.austin.handler.handler.HandlerHolder;
import com.java3y.austin.handler.shield.ShieldService;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Task 执行器
 * <ol>
 *     <li>丢弃消息</li>
 *     <li>屏蔽消息</li>
 *     <li>平台通用去重</li>
 *     <li>发送消息</li>
 * </ol>
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:54
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable {

    @Autowired
    private HandlerHolder handlerHolder;

    @Autowired
    private DeduplicationRuleService deduplicationRuleService;

    @Autowired
    private DiscardMessageService discardMessageService;

    @Autowired
    private ShieldService shieldService;

    private TaskInfo taskInfo;


    /**
     * 任务执行流程：
     * <ol>
     *     <li>丢弃消息</li>
     *     <li>屏蔽消息</li>
     *     <li>平台通用去重</li>
     *     <li>发送消息</li>
     * </ol>
     * @author zhaolifeng
     * @date 2022/10/8 14:52
     */
    @Override
    public void run() {
        if (discardMessageService.isDiscard(taskInfo)) {
            return;
        }

        shieldService.shield(taskInfo);

        if (CollUtil.isNotEmpty(taskInfo.getReceiver())) {
            deduplicationRuleService.duplication(taskInfo);
        }

        if (CollUtil.isNotEmpty(taskInfo.getReceiver())) {
            handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
        }

    }
}
