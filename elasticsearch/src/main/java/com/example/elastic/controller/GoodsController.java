package com.example.elastic.controller;

import com.example.elastic.consts.Qconst;
import com.example.elastic.elastic.ElasticRepository;
import com.example.elastic.mq.kafka.KafkaProducer;
import com.example.elastic.po.Goods;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    ElasticRepository repository;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private KafkaProducer producer;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("save")
    public String save() {
        long timeMillis = System.currentTimeMillis();
        Goods goods = new Goods(timeMillis, "商品" + timeMillis, "测试商品",20);
        repository.save(goods);
        return "success";
    }

    @GetMapping("delete")
    public String delete(Long id) {
        repository.deleteById(id);
        return "success";
    }

    @GetMapping("update")
    public String update(Long id, String name, String description,int num) {
        Goods goods = new Goods(id, name, description,num);
        repository.save(goods);
        return "success";
    }

    @GetMapping("getById")
    public Goods getById(long id) {
        Optional<Goods> byId = repository.findById(id);
        Goods goodsInfo = byId.get();
        return goodsInfo;
    }

    @GetMapping("getList")
    public List<Goods> getList(String name, String description) {

        return repository.findByNameOrDescription(name, description);
    }

    @GetMapping("getQuery")
    public List<Goods> getQuery(String name, String description) {

        return repository.findByNameAndDescription(name, description);
    }
    @GetMapping("bajie")
    public void sendToBajie(String msg){
        rabbitTemplate.convertAndSend(Qconst.Ex.BA_JIE,"test",msg);
    }
    @GetMapping("konghao")
    public void sendToKonghao(String msg){
        rabbitTemplate.convertAndSend(Qconst.Ex.KONG_HAO,"konghao.11",msg);
    }

    @GetMapping("test001")
    public void sendToKafka(){
        producer.send();
    }
}
