# spring-elasticsearch
测试elasticsearch在spring中的api

repository接口中，自定义的方法名称关键字会影响查询的语句：
  
例如：  And出现时 -->  findByNameAndPrice   代表需要满足field为name和price的两个条件，解释之后的DSL语句为：

    {"bool" : 
      {"must" :
         [ 
         {"field" : {"name" : "?"}},
         {"field" : {"price" : "?"}} 
        ]
      }
    }

同理，使用OR则代表，两者满足其一就行。 findByNameOrPrice代表

    {"bool" : 
      {"should" : 
        [ 
        {"field" : {"name" : "?"}}, 
        {"field" : {"price" : "?"}} 
        ]
      }
    }




