package com.java3y.austin.cron.dto.getui;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 完成移动端手机(ios/android)个推帮助
 * <p><a href="https://docs.getui.com/getui/server/rest_v2/token/">个推帮助文档</a>
 * @description: TODO 待弄懂
 * @author zhaolifeng
 * @date 2022/10/7 19:47
 * @version 1.0
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class GeTuiTokenResultDTO {


    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JSONField(name = "expire_time")
        private String expireTime;
        @JSONField(name = "token")
        private String token;
    }
}
