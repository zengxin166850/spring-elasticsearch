package com.example.elastic.mq.receiver;

import com.example.elastic.consts.Qconst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class KonghaoConsume {
    Logger logger = LoggerFactory.getLogger(KonghaoConsume.class);

    @RabbitListener(queues = Qconst.Qkey.KONG_HAO)
    public void processKonghao(byte[] msg){
        logger.info("Konghao接收到消息:"+new String(msg));

    }
}
