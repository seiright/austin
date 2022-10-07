package com.java3y.austin.cron.csv;

import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvRowHandler;
import lombok.Data;


/**
 * 用于统计当前文件有多少行
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 14:14
 * @version 1.0
 */
@Data
public class CountFileRowHandler implements CsvRowHandler {

    private long rowSize;

    @Override
    public void handle(CsvRow row) {
        rowSize++;
    }

}
