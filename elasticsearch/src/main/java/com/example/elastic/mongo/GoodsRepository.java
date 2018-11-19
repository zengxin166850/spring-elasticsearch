package com.example.elastic.mongo;

import com.example.elastic.po.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GoodsRepository extends MongoRepository<Goods, Long> {

    //模糊查询
    List<Goods> findByDescriptionLike(String description);

    List<Goods> findByDescription(String description);

    //大于，(不包含)
    List<Goods> findByNumGreaterThan(int num);
    //小于，(不包含)
    List<Goods> findByNumLessThan(int num);

    List<Goods> findByNumBetween(int from, int to);

    List<Goods> findByDescriptionNotNull();

    List<Goods> findByDescriptionNull();

    List<Goods> findByDescriptionNot(String description);

    @Query("{\"description\":{\"$regex\":?0}, \"num\": ?1}")
    Page<Goods> findByDecriptionAndAgeRange(String description, int num, Pageable page);

    @Query(value = "{\"description\":{\"$regex\":?0},\"num\":{\"$gte\":?1,\"$lte\": ?2}}")
    Page<Goods> findByDescriptionAndAgeRange2(String description, int numFrom, int numTo, Pageable page);

    //regex 表示模糊查询、fields表示返回的字段，这次表示只返回description和num字段
    @Query(value = "{\"description\":{\"$regex\":?0},\"num\":{\"$gte\":?1,\"$lte\": ?2}}", fields = "{\"description\" : 1, \"num\" : 1}")
    Page<Goods> findByDescriptionAndAgeRange3(String description, int numFrom, int numTo, Pageable page);
}
