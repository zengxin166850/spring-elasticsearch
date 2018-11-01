package com.example.elastic.mq.config;

import com.example.elastic.consts.Qconst;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    //测试
    @Bean
    public Queue wukong(){
        return new Queue(Qconst.Qkey.WU_KONG);
    }
    @Bean
    public Queue bajie(){
        return new Queue(Qconst.Qkey.BA_JIE);
    }
    @Bean
    public Queue konghao(){
        return new Queue(Qconst.Qkey.KONG_HAO);
    }
    @Bean
    public FanoutExchange ex_wukong(){
        return new FanoutExchange(Qconst.Ex.WU_KONG);
    }
    @Bean
    public TopicExchange ex_bajie(){
        return new TopicExchange(Qconst.Ex.BA_JIE);
    }
    @Bean
    public TopicExchange ex_konghao(){
        return new TopicExchange(Qconst.Ex.KONG_HAO);
    }
    @Bean
    public Binding binding_konghao(Queue konghao,TopicExchange ex_konghao){
        return BindingBuilder.bind(konghao).to(ex_konghao).with("konghao.#");
    }
}
