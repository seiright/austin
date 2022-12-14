package com.java3y.austin.cron.xxl.service;

import com.java3y.austin.cron.xxl.entity.XxlJobGroup;
import com.java3y.austin.cron.xxl.entity.XxlJobInfo;
import com.java3y.austin.common.vo.BasicResultVO;

/**
 * 定时任务服务
 * @description:
 * @author zhaolifeng
 * @date 2022/10/7 20:12
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface CronTaskService {


    /**
     * 新增/修改 定时任务
     * @param xxlJobInfo-定时任务信息
     *
     * @return 新增时返回任务Id，修改时无返回
     */
    BasicResultVO saveCronTask(XxlJobInfo xxlJobInfo);

    /**
     * 删除定时任务
     *
     * @param taskId-任务id
     * @return BasicResultVO
     */
    BasicResultVO deleteCronTask(Integer taskId);

    /**
     * 启动定时任务
     *
     * @param taskId-任务id
     * @return BasicResultVO
     */
    BasicResultVO startCronTask(Integer taskId);


    /**
     * 暂停定时任务
     *
     * @param taskId-任务id
     * @return BasicResultVO
     */
    BasicResultVO stopCronTask(Integer taskId);


    /**
     * 得到执行器Id
     * @param appName-app名称
     * @param title-job名称
     * @return BasicResultVO
     */
    BasicResultVO getGroupId(String appName, String title);

    /**
     * 创建执行器
     * @param xxlJobGroup-xxlJob组
     * @return BasicResultVO
     */
    BasicResultVO createGroup(XxlJobGroup xxlJobGroup);

}
