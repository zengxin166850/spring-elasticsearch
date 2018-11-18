package com.example.elastic;

import com.example.elastic.mongo.GoodsRepository;
import com.example.elastic.po.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticApplicationTests {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    GoodsRepository goodsRepository;

    @Test
    public void contextLoads() {
        kafkaTemplate.send("test9527", "1111");
    }

    @Test
    public void addData() {
        goodsRepository.save(new Goods(1L, "鼠标", "罗技G502,售价299", 20));
        goodsRepository.save(new Goods(2L, "鼠标", "罗技G402,售价179", 13));
        goodsRepository.save(new Goods(3L, "鼠标", "罗技G603,售价479", 6));
        goodsRepository.save(new Goods(4L, "键盘", "炼狱者XX", 100));
        goodsRepository.save(new Goods(5L, "键盘", "雷蛇XX", 46));
        goodsRepository.save(new Goods(6L, "键盘", "免费赠送", 18));
        goodsRepository.save(new Goods(7L, "键盘", null, 20));
        goodsRepository.save(new Goods(8L, "耳机", "森海塞尔80S", 999));
        goodsRepository.save(new Goods(9L, "耳机", null, 149));
        goodsRepository.save(new Goods(10L, "耳机", "1MORE，圈铁pro", 99));
        goodsRepository.save(new Goods(11L, "耳机", "铁三角", 66));
        goodsRepository.save(new Goods(12L, "手机", "小米8", 3200));
        goodsRepository.save(new Goods(13L, "手机", "华为", 1899));
        goodsRepository.save(new Goods(14L, "手机", "三星", 6799));
        goodsRepository.save(new Goods(15L, "手机", "iphoe XS Max", 12799));
        logger.info(String.valueOf(goodsRepository.findAll().size()));
        logger.info("数据插入完毕--------");
    }

    @Test
    public void search() {
        List<Goods> u1 = goodsRepository.findByDescriptionLike("罗技");
        logger.info(u1.toString());
        List<Goods> u2 = goodsRepository.findByDescription("炼狱者");
        logger.info(u2.toString());
        List<Goods> u3 = goodsRepository.findByNumGreaterThan(100);
        logger.info(u3.toString());
        List<Goods> u4 = goodsRepository.findByNumLessThan(100);
        logger.info(u4.toString());
        List<Goods> u5 = goodsRepository.findByNumBetween(0, 5000);
        logger.info(u5.toString());
        List<Goods> u6 = goodsRepository.findByDescriptionNotNull();
        logger.info(u6.toString());
        List<Goods> u7 = goodsRepository.findByDescriptionNull();
        this.logger.info(u7.toString());
        List<Goods> u8 = goodsRepository.findByDescriptionNot("iphoe XS Max");
        this.logger.info(u8.toString());
    }
    @Test
    public void searchByQuery(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Goods> u1 = goodsRepository.findByDecriptionAndAgeRange("罗技",20,pageable);
        logger.info(u1.toString());
        Page<Goods> u2 = goodsRepository.findByDescriptionAndAgeRange2("罗技",0,50,pageable);
        logger.info(u2.toString());
        Page<Goods> u3 = goodsRepository.findByDescriptionAndAgeRange3("罗技",0,50,pageable);
        logger.info(u3.toString());
    }

}
