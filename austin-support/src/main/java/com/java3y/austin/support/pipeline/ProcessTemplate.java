package com.java3y.austin.support.pipeline;

import java.util.List;

/**
 * 业务执行模板（把责任链的逻辑串起来）
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 22:13
 * @version 1.0
 */
public class ProcessTemplate {

    @SuppressWarnings("rawtypes")
    private List<BusinessProcess> processList;

    @SuppressWarnings("rawtypes")
    public List<BusinessProcess> getProcessList() {
        return processList;
    }

    @SuppressWarnings("rawtypes")
    public void setProcessList(List<BusinessProcess> processList) {
        this.processList = processList;
    }
}