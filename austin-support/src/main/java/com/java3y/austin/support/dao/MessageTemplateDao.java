package com.java3y.austin.support.dao;

import com.java3y.austin.support.domain.MessageTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 消息模板Dao
 * @author 3y
 */
public interface MessageTemplateDao extends JpaRepository<MessageTemplate, Long> {


    /**
     * 查询 列表（分页)
     * @param deleted  0：未删除 1：删除
     * @param pageable 分页对象
     * @return
     */
    List<MessageTemplate> findAllByIsDeletedEquals(Integer deleted, Pageable pageable);


    /**
     * 统计未删除的条数
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);

    /**
     * 通过模板id查询模板
     * @param id-模板id
     * @return 消息模板
     * @author zhaolifeng
     * @date 2022/10/5 18:58
     */
    MessageTemplate getMessageTemplateById(Long id);

    void deleteById(@NotNull Long id);

    List<MessageTemplate> getMessageTemplatesByIsDeleted(Integer isDeleted);
}
