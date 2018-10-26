package com.example.elastic.elastic;

import com.example.elastic.po.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ElasticRepository extends ElasticsearchRepository<Goods, Long> {

}
