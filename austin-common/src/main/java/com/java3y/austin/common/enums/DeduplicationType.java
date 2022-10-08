package com.java3y.austin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 去重类型枚举
 * @description:
 * @author zhaolifeng
 * @date 2022/10/8 14:08
 * @version 1.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum DeduplicationType {

    CONTENT(10, "N分钟相同内容去重"),
    FREQUENCY(20, "一天内N次相同渠道去重"),
    ;
    private final Integer code;
    private final String description;


    /**
     * 获取去重渠道的列表
     * @return 去重渠道的列表
     */
    public static List<Integer> getDeduplicationList() {
        ArrayList<Integer> result = new ArrayList<>();
        for (DeduplicationType value : DeduplicationType.values()) {
            result.add(value.getCode());
        }
        return result;
    }
}
