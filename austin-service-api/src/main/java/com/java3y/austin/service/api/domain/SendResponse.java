package com.java3y.austin.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 发送接口返回值
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 21:30
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class SendResponse {
    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应编码
     */
    private String msg;

}
