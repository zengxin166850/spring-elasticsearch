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
        long millis = System.currentTimeMillis();
        if (millis%2==0){
//            kafkaTemplate.send("test001",2,"1","22222");
            kafkaTemplate.send("test001",2,"1","11111");
        }else{
//            kafkaTemplate.send("test001",4,"2","33333");
//            经试验，分区不能动态改变，只能在server.properties中进行固定设置。或者使用命令行针对某topic进行动态扩容(alter)
            kafkaTemplate.send("test001",2,"2","33333");
        }

    }
}
