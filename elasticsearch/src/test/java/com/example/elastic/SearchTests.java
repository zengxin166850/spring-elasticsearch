package com.example.elastic;

import com.example.elastic.elastic.ElasticRepository;
import com.example.elastic.po.Goods;
import com.example.elastic.util.JsonUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.UnknownHostException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTests {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String INDEX = "goods";
//    private static final String TYPE = "test";
    @Autowired
    ElasticRepository repository;
    @Autowired
    TransportClient client;

    @Test
    public void addData() {
        repository.save(new Goods(1L, "罗技鼠标", "罗技G502,售价299", 500));
        repository.save(new Goods(2L, "鼠标", "罗技G402,售价179", 300));
        repository.save(new Goods(3L, "罗技键盘", "罗技G603,售价479", 260));
        repository.save(new Goods(4L, "键盘", "炼狱者XX", 100));
        repository.save(new Goods(5L, "键盘", "雷蛇XX", 46));
        repository.save(new Goods(6L, "键盘", "免费赠送", 18));
        repository.save(new Goods(7L, "键盘", null, 20));
        repository.save(new Goods(8L, "耳机", "森海塞尔80S", 999));
        repository.save(new Goods(9L, "耳机", null, 149));
        repository.save(new Goods(10L, "耳机", "1MORE，圈铁pro", 99));
        repository.save(new Goods(11L, "耳机", "铁三角", 66));
        repository.save(new Goods(12L, "手机", "小米8", 3200));
        repository.save(new Goods(13L, "手机", "华为", 1899));
        repository.save(new Goods(14L, "手机", "三星", 6799));
        repository.save(new Goods(15L, "手机", "iphoe XS Max", 12799));
        Iterable<Goods> all = repository.findAll();
        all.forEach(a -> {
            System.out.println(a.toString());
        });
        logger.info("数据插入完毕--------");
    }

    @Test
    public void TransportClientTest() throws UnknownHostException {
        long start = System.currentTimeMillis();

        QueryBuilder queryBuilders = QueryBuilders.matchQuery("name", "罗技");
        //匹配所有
//        QueryBuilder queryBuilders = QueryBuilders.matchAllQuery();
        HighlightBuilder hiBuilder = new HighlightBuilder().field("name").requireFieldMatch(true);
        hiBuilder.preTags("<span style=\"color:red\">");
        hiBuilder.postTags("</span>");
        hiBuilder.fragmentSize(10000);//高亮内容长度
        SearchResponse response = client.prepareSearch(INDEX)
                .setQuery(queryBuilders)
                .setFrom(0)
                .setSize(5)
                .highlighter(hiBuilder)
                //高亮字段前缀
                .setExplain(true)// 设置是否按查询匹配度排序
                .execute().actionGet();
        SearchHits hits = response.getHits();
        for(SearchHit hit:hits){
            Map<String, Object> source = hit.getSource();
            String s = JsonUtils.toString(source);
            System.out.println("查询到结果："+s);
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            Text[] names = highlightFields.get("name").getFragments();
//            for(Text text:names){
//                System.err.println(text);
//            }
        }
        System.out.println("命中条数为："+hits.getTotalHits());
        long end = System.currentTimeMillis();
        System.out.println("---------耗时："+(end-start)+" ms");
        SearchResponse responses = client.prepareSearch(INDEX)
                .setQuery(QueryBuilders.matchAllQuery())
                .execute().actionGet();
        SearchHits hi = responses.getHits();
    //总记录数
        System.out.println("总记录数："+hi.getTotalHits());
    }
}
