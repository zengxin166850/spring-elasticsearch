package com.example.elastic.po;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "goods",type = "test")
public class Goods implements Serializable {

    private Long id;
    private String name;
    private String description;
    private int num;

    public Goods(Long id, String name, String description,int num) {
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
