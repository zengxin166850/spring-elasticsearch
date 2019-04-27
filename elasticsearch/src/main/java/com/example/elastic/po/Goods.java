package com.example.elastic.po;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "goods",type = "test")
public class Goods implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Integer num;

    public Goods(Long id, String name, String description,Integer num) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.num = num;
    }
    public Goods() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", num=" + num +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
