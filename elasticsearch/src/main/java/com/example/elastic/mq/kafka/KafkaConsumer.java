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

    @KafkaListener(topics = {"test001"},groupId = "group1")
    public void g1consumer1(ConsumerRecord<String,String> record){
        logger.info("消费者consumer1(group1)监听到来自test001的消息.....");
        Optional<String> msg = Optional.ofNullable(record.value());

        if(msg.isPresent()){
            System.out.println(msg.get());
            logger.info("----------record:"+record);
            logger.info("----------msg:"+msg);
            logger.info("------offset:"+record.offset());
        }
    }
    @KafkaListener(topics = {"test001"},groupId = "group1")
    public void g1consumer2(ConsumerRecord<String,String> record){
        logger.info("消费者consumer2(group1)监听到来自test001的消息.....");
        Optional<String> msg = Optional.ofNullable(record.value());
        if(msg.isPresent()){
            System.out.println(msg.get());
            logger.info("----------record:"+record);
            logger.info("----------msg:"+msg);
            logger.info("------offset:"+record.offset());
        }
    }
    @KafkaListener(topics = {"test001"},groupId = "group2")
    public void g2consumer1(ConsumerRecord<String,String> record){
        logger.info("消费者consumer2(group2)监听到来自test001的消息.....");
        Optional<String> msg = Optional.ofNullable(record.value());
        if(msg.isPresent()){
            System.out.println(msg.get());
            logger.info("----------record:"+record);
            logger.info("----------msg:"+msg);
            logger.info("------offset:"+record.offset());
        }
    }
    @KafkaListener(topics = {"test001"},groupId = "group2")
    public void g2consumer2(ConsumerRecord<String,String> record){
        logger.info("消费者consumer2(group2)监听到来自test001的消息.....");
        Optional<String> msg = Optional.ofNullable(record.value());
        if(msg.isPresent()){
            System.out.println(msg.get());
            logger.info("----------record:"+record);
            logger.info("----------msg:"+msg);
            logger.info("------offset:"+record.offset());
        }
    }
}
