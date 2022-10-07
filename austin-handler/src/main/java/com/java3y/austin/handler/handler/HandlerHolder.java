package com.java3y.austin.handler.handler;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * channel->Handler的映射关系
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 22:48
 * @version 1.0
 */
@Component
public class HandlerHolder {

    private final Map<Integer, Handler> handlers = new HashMap<>(128);

    public void putHandler(Integer channelCode, Handler handler) {
        handlers.put(channelCode, handler);
    }

    public Handler route(Integer channelCode) {
        return handlers.get(channelCode);
    }
}
