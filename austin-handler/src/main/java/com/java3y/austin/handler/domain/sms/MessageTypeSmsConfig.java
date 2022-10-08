package com.java3y.austin.handler.domain.sms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对于每种消息类型的 短信配置
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 13:57
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTypeSmsConfig {

    /**
     * 权重(决定着流量的占比)
     */
    private Integer weights;

    /**
     * script名称
     */
    private String scriptName;

}
