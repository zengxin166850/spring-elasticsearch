package com.example.elastic.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {
    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(topics = {"test001"})
    public void listen(ConsumerRecord<String,String> record){
        logger.info("消费者监听到来自test001的消息.....");
        Optional<String> msg = Optional.ofNullable(record.value());
        if(msg.isPresent()){
            System.out.println(msg.get());
            logger.info("----------record:"+record);
            logger.info("----------msg:"+msg);
            logger.info("------offset:"+record.offset());
        }
    }
}
