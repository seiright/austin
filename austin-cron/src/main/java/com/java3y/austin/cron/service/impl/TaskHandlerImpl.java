package com.java3y.austin.cron.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.util.StrUtil;
import com.java3y.austin.cron.csv.CountFileRowHandler;
import com.java3y.austin.cron.pending.CrowdBatchTaskPending;
import com.java3y.austin.cron.service.TaskHandler;
import com.java3y.austin.cron.utils.ReadFileUtils;
import com.java3y.austin.cron.vo.CrowdInfoVo;
import com.java3y.austin.support.dao.MessageTemplateDao;
import com.java3y.austin.support.domain.MessageTemplate;
import com.java3y.austin.support.pending.AbstractLazyPending;
import com.java3y.austin.support.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 定时任务处理的实现类
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 23:28
 * @version 1.0
 */
@Service
@Slf4j
public class TaskHandlerImpl implements TaskHandler {
    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Override
    public void handle(Long messageTemplateId) {
        Optional<MessageTemplate> templateOptional = messageTemplateDao.findById(messageTemplateId);
        if (!templateOptional.isPresent()){
            log.error("TaskHandler#handle messageTemplate does not exist! messageTemplatedId:{}",messageTemplateId);
            return;
        }
        MessageTemplate messageTemplate = templateOptional.get();
        if (StrUtil.isBlank(messageTemplate.getCronCrowdPath())) {
            log.error("TaskHandler#handle crowdPath empty! messageTemplateId:{}", messageTemplateId);
            return;
        }
        // 1. 获取文件行数大小
        long countCsvRow = ReadFileUtils.countCsvRow(messageTemplate.getCronCrowdPath(), new CountFileRowHandler());

        // 2. 读取文件得到每一行记录给到队列做lazy batch处理
        CrowdBatchTaskPending crowdBatchTaskPending = SpringBeanUtil.getBean(CrowdBatchTaskPending.class);
        ReadFileUtils.getCsvRow(messageTemplate.getCronCrowdPath(), row -> {

            Map<String, String> fieldMap = row.getFieldMap();
            if (CollUtil.isEmpty(fieldMap)
                || StrUtil.isBlank(fieldMap.get(ReadFileUtils.RECEIVER_KEY))) {
                return;
            }
            // 3. 每一行处理交给LazyPending
            HashMap<String, String> params = ReadFileUtils.getParamFromLine(fieldMap);
            CrowdInfoVo crowdInfoVo = CrowdInfoVo.builder().receiver(fieldMap.get(ReadFileUtils.RECEIVER_KEY))
                .params(params).messageTemplateId(messageTemplateId).build();
            crowdBatchTaskPending.pending(crowdInfoVo);

            // 4. 判断是否读取文件完成回收资源且更改状态
            onComplete(row, countCsvRow, crowdBatchTaskPending, messageTemplateId);
        });
    }

    /**
     * 文件遍历结束时
     * <ol><li> 暂停单线程池消费(最后会回收线程池资源)
     * <li> TODO 更改消息模板的状态(暂未实现)</ol>
     * @param row 当前行数
     * @param countCsvRow csv文件总行数
     * @param crowdBatchTaskPending 延迟批量处理人群信息
     * @param messageTemplateId 模板id
     */
    @SuppressWarnings("rawtypes")
    private void onComplete(CsvRow row, long countCsvRow, AbstractLazyPending crowdBatchTaskPending, Long messageTemplateId) {
        if (row.getOriginalLineNumber() == countCsvRow) {
            crowdBatchTaskPending.setStop(true);
            log.info("messageTemplate:[{}] read csv file complete!", messageTemplateId);
        }
    }
}
