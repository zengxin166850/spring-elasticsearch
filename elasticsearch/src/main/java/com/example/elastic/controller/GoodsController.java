package com.example.elastic.controller;

import com.example.elastic.elastic.ElasticRepository;
import com.example.elastic.po.Goods;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RestController
@RequestMapping("goods")
public class GoodsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    ElasticRepository repository;
    @Autowired
    private ElasticsearchTemplate template;

    @GetMapping("save")
    public Goods save() {
        long timeMillis = System.currentTimeMillis();
        Goods goods = new Goods(timeMillis, "商品" + timeMillis, "测试商品", 20);
        Goods result = repository.save(goods);
        LOGGER.info("商品添加成功----------");
        return result;
    }

    @GetMapping("delete")
    public String delete(Long id) {
        repository.deleteById(id);
        LOGGER.info("商品删除成功----------");
        return "success";
    }

    @GetMapping("update")
    public Goods update(Long id, String name, String description, int num) {
        Goods goods = new Goods(id, name, description, num);
        Goods result = repository.save(goods);
        LOGGER.info("商品更新成功----------");
        return result;
    }

    @GetMapping("getById")
    public Goods getById(long id) {
        Optional<Goods> byId = repository.findById(id);
        if (byId.isPresent()) {
            LOGGER.info("获取商品成功----------");
            return byId.get();
        }
        return null;
    }

    @GetMapping("findByNameOrDescription")
    public List<Goods> findByNameOrDescription(String name, String description) {
        LOGGER.info("根据名称或描述获取商品----------");
        return repository.findByNameOrDescription(name, description);
    }

    @GetMapping("findByNameLike")
    public List<Goods> findByNameLike(String name) {
        LOGGER.info("模糊查询----------");
        return repository.findByNameLike(name);
    }

    @GetMapping("findByNumBetween")
    public List<Goods> findByNumBetween(Integer from, Integer to) {
        LOGGER.info("范围查询----------");
        return repository.findByNumBetween(from, to);
    }

    @GetMapping("findByNameAndDescription")
    public List<Goods> findByNameAndDescription(String name, String description) {
        LOGGER.info("根据名称和描述获取商品----------");
        return repository.findByNameAndDescription(name, description);
    }

    @GetMapping("findAll")
    public Page<Goods> findAll() {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "num");
        Page<Goods> result = repository.findAll(pageable);
        LOGGER.info("总记录为：" + result.getTotalPages() + "---elements:" + result.getTotalElements());
        return result;
    }

    @GetMapping("findByDSL")
    public Page<Goods> findByDSL() {
        Pageable pageable = PageRequest.of(0, 5);
        //name或者num任一满足条件即可
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                boolQuery()
                        .should(QueryBuilders.matchQuery("name", "鼠标"))
                        .should(QueryBuilders.matchQuery("num", 20))
        );
        //
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
        LOGGER.info("\n search  DSL  = \n " + searchQuery.getQuery().toString());
        return repository.search(searchQuery);
    }

    @GetMapping("findByTemplate")
    public Page<Goods> findByTemplate() {
        //查询关键字
        String word = "罗技";
        // 分页设置,postTime倒序
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "num");
        SearchQuery searchQuery;
        //1.multiMatchQuery多个字段匹配 .operator(Operator.AND)多项匹配使用and查询即完全匹配都存在才查出来
//        searchQuery = new NativeSearchQueryBuilder().withQuery(
//                multiMatchQuery(word, "name", "description").operator(Operator.AND)
//        ).withPageable(pageable).build();

        //2.多条件查询：name和description必须包含word=“XXX”且num必须大于200,并按照num倒序分页结果
        searchQuery = new NativeSearchQueryBuilder().withQuery(
                boolQuery().must(multiMatchQuery(word, "name", "description").operator(Operator.AND))
                        .must(rangeQuery("num").gt(200))).withPageable(pageable).build();
        Page<Goods> result = template.queryForPage(searchQuery,Goods.class);
        LOGGER.info("查询到共："+result.getTotalElements()+" 条记录");
        return result;
    }

}
