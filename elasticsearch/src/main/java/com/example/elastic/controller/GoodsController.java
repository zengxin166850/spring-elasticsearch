package com.example.elastic.controller;

import com.example.elastic.elastic.ElasticRepository;
import com.example.elastic.po.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("goods")
public class GoodsController {


    private final Integer PAGE_SIZE = 10;
    @Autowired
    ElasticRepository repository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("save")
    public String save() {
        long timeMillis = System.currentTimeMillis();
        Goods goods = new Goods(timeMillis, "商品" + timeMillis, "测试商品");
        repository.save(goods);
        return "success";
    }

    @GetMapping("delete")
    public String delete(Long id) {
        repository.deleteById(id);
        return "success";
    }

    @GetMapping("update")
    public String update(Long id, String name, String description) {
        Goods goods = new Goods(id, name, description);
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

}
