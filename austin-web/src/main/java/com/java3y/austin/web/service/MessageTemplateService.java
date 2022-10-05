package com.java3y.austin.web.service;


import com.java3y.austin.common.vo.BasicResultVO;
import com.java3y.austin.support.domain.MessageTemplate;
import com.java3y.austin.web.vo.MessageTemplateParam;

import java.util.List;

/**
 * 消息模板管理 接口
 *
 * @author 3y
 */
public interface MessageTemplateService {


    /**
     * 查询未删除的模板列表（分页)
     *
     * @param messageTemplateParam
     * @return
     */
    List<MessageTemplate> queryList(MessageTemplateParam messageTemplateParam);

    /**
     * 统计未删除的条数
     * @return-未删除的条数
     */
    Long count();

    /**
     * 单个 保存或者更新
     * 存在ID 更新
     * 不存在ID保存
     * @param messageTemplate
     * @return
     */
    MessageTemplate saveOrUpdate(MessageTemplate messageTemplate);


    /**
     * 软删除(deleted=1)
     *
     * @param ids-传入的id
     */
    void deleteByIds(List<Long> ids);

    /**
     * 硬删除，将isDeleted=1的条目全删除掉
     * @author zhaolifeng
     * @date 2022/10/5 18:57
     */
    void delete();

    /**
     * 根据ID查询模板信息
     *
     * @param id
     * @return
     */
    MessageTemplate queryById(Long id);

    /**
     * 复制配置
     *
     * @param id
     */
    void copy(Long id);

    /**
     * 启动模板的定时任务
     * @param id
     * @return
     */
    BasicResultVO startCronTask(Long id);

    /**
     * 暂停模板的定时任务
     * @param id
     * @return
     */
    BasicResultVO stopCronTask(Long id);

}
