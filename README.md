# spring-elasticsearch
**使用spring-boot-starter-data-elasticsearch集成ES：**  
1.修改pom文件

    <dependency>  
	    <groupId>org.springframework.boot</groupId>  
	    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency>

   2.配置application.properties文件
   

    #elasticsearch 
     spring.data.elasticsearch.cluster-name=elasticsearch  
     spring.data.elasticsearch.repositories.enabled=true  
     spring.data.elasticsearch.cluster-nodes=localhost:9300

3.新建一个接口继承ElasticsearchRepository，此时默认的增删改查就已经存在了

    @Component  //让spring管理
    public interface ElasticRepository extends ElasticsearchRepository<T, Long> {
    }
4.根据jpa的命名方式，在接口中新建方法(不需要实现)

| 关键字| 方法命名示例|
|--|--|
|  And  | findByNameAndNum |
|  Or   | findByNameOrDiscription |
|  Between| findByNumBetween|
|  Like  | findByNameLike|
|  NotLike| findByNameNotLike|
|  OrderBy| findByIdOrderByXDesc|
|  Not | findByNameNot|  


5.注入到Controller中，调用方法时给上对应的参数即可。  

6.复杂查询使用[ElasticsearchTemplate](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/)或[TransportClient](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html)


          
   


    
          




