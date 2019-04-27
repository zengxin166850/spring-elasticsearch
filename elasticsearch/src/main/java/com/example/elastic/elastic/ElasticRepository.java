package com.example.elastic.elastic;

import com.example.elastic.po.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ElasticRepository extends ElasticsearchRepository<Goods, Long> {
    //使用or代表
    List<Goods> findByNameOrDescription(String name, String description);

    List<Goods> findByNameAndDescription(String name, String description);

    List<Goods> findByNameLike(String name);

    List<Goods> findByNumBetween(Integer from, Integer to);

//    List<Goods> findByNameNot(String name);

    //分页
    Page<Goods> findAll(Pageable pageable);

}
