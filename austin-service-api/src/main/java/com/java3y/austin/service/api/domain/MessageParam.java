package com.java3y.austin.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * 消息参数。包含：
 * <ul><li>接收者(若有多个以,分割)<li>占位符变量<li>额外信息</ul>
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 23:02
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageParam {

    /**
     * 接收者: 多个用,逗号号分隔开
     * <p>【不能大于100个】
     * <p>必传
     */
    private String receiver;

    /**
     * 消息内容中的可变部分(占位符替换)
     * <p>可选
     */
    private Map<String, String> variables;

    /**
     * 扩展参数
     * <p>可选
     */
    private Map<String,String> extra;
}
