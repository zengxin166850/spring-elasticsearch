package com.example.springbootredisrabbit.queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitInit {

//    static final String DIRECT_QUEUE01 = "direct_queue01";
//    static final String DIRECT_QUEUE02 = "direct_queue02";

    static final String TOPIC_QUEUE01 = "topic_queue01";
    static final String TOPIC_QUEUE02 = "topic_queue02";

//    static final String FANOUT_QUEUE01 = "fanout_queue01";
//    static final String FANOUT_QUEUE02 = "fanout_queue02";
//    static final String FANOUT_QUEUE03 = "fanout_queue03";

    static final String EXCHANGE_DIRECT = "directExchange";
    static final String EXCHANGE_TOPIC = "topicExchange";
    static final String EXCHANGE_FANOUT = "fanoutExchange";

//    static final String KEY_REGULAR_DIRECT = "direct.*";
    static final String KEY_REGULAR_TOPIC = "topic.*.message";
    static final String KEY_REGULAR_TOPICS = "topic.#";

//    @Bean
//    public Queue direct01(){
//        return new Queue(DIRECT_QUEUE01);
//    }
//
//    @Bean
//    public Queue direct02(){
//        return new Queue(DIRECT_QUEUE02);
//    }

    @Bean
    public Queue topic01(){
        return new Queue(TOPIC_QUEUE01);
    }

    @Bean
    public Queue topic02(){
        return new Queue(TOPIC_QUEUE02);
    }

//    @Bean
//    public Queue fanout01(){
//        return new Queue(FANOUT_QUEUE01);
//    }
//
//    @Bean
//    public Queue fanout02(){
//        return new Queue(FANOUT_QUEUE02);
//    }
//
//    @Bean
//    public Queue fanout03(){
//        return new Queue(FANOUT_QUEUE03);
//    }
    @Bean
    public DirectExchange direct(){
        return new DirectExchange(EXCHANGE_FANOUT);
    }
    @Bean
    public TopicExchange topic(){
        return new TopicExchange(EXCHANGE_FANOUT);
    }
    @Bean
    public FanoutExchange fanout(){
        return new FanoutExchange(EXCHANGE_FANOUT);
    }


    @Bean
    Binding bindingTopic(Queue topic02 ,TopicExchange topic){
        return BindingBuilder.bind(topic02).to(topic).with(KEY_REGULAR_TOPIC);
    }
}
