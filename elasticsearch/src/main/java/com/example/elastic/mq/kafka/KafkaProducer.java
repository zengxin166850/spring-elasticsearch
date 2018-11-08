package com.example.elastic.mq.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    //仅用于测试
    public void send(){
        logger.info("生产者发送消息到test001......");
        kafkaTemplate.send("test001","22222");
    }
}
