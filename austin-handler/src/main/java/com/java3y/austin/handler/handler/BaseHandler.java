package com.java3y.austin.handler.handler;

import com.java3y.austin.common.domain.AnchorInfo;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.enums.AnchorState;
import com.java3y.austin.handler.flowcontrol.FlowControlFactory;
import com.java3y.austin.handler.flowcontrol.FlowControlParam;
import com.java3y.austin.support.utils.LogUtils;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发送各个渠道的handler
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 22:47
 * @version 1.0
 */
public abstract class BaseHandler implements Handler {

    @Autowired
    private HandlerHolder handlerHolder;

    @Autowired
    private LogUtils logUtils;

    @Autowired
    private FlowControlFactory flowControlFactory;

    /**
     * 标识渠道的Code
     * 子类初始化的时候指定
     */
    protected Integer channelCode;

    /**
     * 限流相关的参数
     * 子类初始化的时候指定
     */
    protected FlowControlParam flowControlParam;

    /**
     * 初始化渠道与Handler的映射关系
     */
    @PostConstruct
    private void init() {
        handlerHolder.putHandler(channelCode, this);
    }

    /**
     * 流量控制
     * <p>只有子类指定了限流参数，才需要限流</p>
     * @param taskInfo 任务信息
     */
    public void flowControl(TaskInfo taskInfo) {
        if (flowControlParam != null) {
            flowControlFactory.flowControl(taskInfo, flowControlParam);
        }
    }
    @Override
    public void doHandler(TaskInfo taskInfo) {
        flowControl(taskInfo);
        if (handler(taskInfo)) {
            logUtils.print(AnchorInfo.builder().state(AnchorState.SEND_SUCCESS.getCode()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
            return;
        }
        logUtils.print(AnchorInfo.builder().state(AnchorState.SEND_FAIL.getCode()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
    }




    /**
     * 统一处理的handler接口
     *
     * @param taskInfo 任务信息
     * @return 处理状态
     */
    public abstract boolean handler(TaskInfo taskInfo);



}
