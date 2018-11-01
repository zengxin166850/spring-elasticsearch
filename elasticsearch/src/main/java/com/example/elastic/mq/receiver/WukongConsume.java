package com.example.elastic.mq.receiver;

import com.example.elastic.consts.Qconst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WukongConsume {
    Logger logger = LoggerFactory.getLogger(WukongConsume.class);

    @RabbitListener(queues = Qconst.Qkey.WU_KONG)
    public void processWukong(String msg){
        logger.info("wukong 接收到消息:"+msg);

    }
}
