package com.java3y.austin.support.mq.kafka;

import cn.hutool.core.util.StrUtil;
import com.java3y.austin.support.constans.MessageQueuePipeline;
import com.java3y.austin.support.mq.SendMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;


/**
 * kafka 发送实现类
 * @description: TODO
 * @author zhaolifeng
 * @date 2022/10/6 21:10
 * @version 1.0
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
public class KafkaSendMqServiceImpl implements SendMqService {

    @SuppressWarnings("rawtypes")
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${austin.business.tagId.key}")
    private String tagIdKey;



    @Override
    @SuppressWarnings({"rawtypes","unchecked"})
    public void send(String topic, String jsonValue, String tagId) {
        if (StrUtil.isNotBlank(tagId)) {
            List<Header> headers = Collections.singletonList(new RecordHeader(tagIdKey, tagId.getBytes(StandardCharsets.UTF_8)));
            kafkaTemplate.send(new ProducerRecord(topic, null, null, null, jsonValue, headers));
        } else {
            kafkaTemplate.send(topic, jsonValue);
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }
}
