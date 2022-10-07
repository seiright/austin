package com.java3y.austin.cron.dto.getui;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 请求token时的参数
 * <p><a href="https://docs.getui.com/getui/server/rest_v2/token/">个推帮助文档</a>
 * @description: TODO 待处理
 * @author zhaolifeng
 * @date 2022/10/7 19:50
 * @version 1.0
 */
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class QueryTokenParamDTO {
    /**
     * sign
     */
    @JSONField(name = "sign")
    private String sign;
    /**
     * timestamp
     */
    @JSONField(name = "timestamp")
    private String timestamp;
    /**
     * appkey
     */
    @JSONField(name = "appkey")
    private String appKey;
}
