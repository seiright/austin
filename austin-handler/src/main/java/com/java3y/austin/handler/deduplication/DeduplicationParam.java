package com.java3y.austin.handler.deduplication;

import com.alibaba.fastjson.annotation.JSONField;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.enums.AnchorState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 去重服务所需要的参数
 * 主要去重参数设置：
 * <ul>
 *     <li>{@link DeduplicationParam#deduplicationTime}-去重时间. 可以用来配置内容去重的滑动窗口大小</li>
 *     <li>{@link DeduplicationParam#countNum}-去重阈值. 可以用来配置内容去重的阈值: 即最大相同次数;
 *     或者频次去重的频次：即同一模板能像同一用户一天能发送的最大数量</li>
 * </ul>
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:39
 * @version 1.0
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {
    /**
     * TaskIno信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间
     * 单位：秒
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需达到的次数去重
     */
    @JSONField(name = "num")
    private Integer countNum;

    /**
     * 标识属于哪种去重(数据埋点)
     */
    private AnchorState anchorState;
}
