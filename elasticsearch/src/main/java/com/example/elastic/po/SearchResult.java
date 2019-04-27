package com.example.elastic.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索结果封装
 */
public class SearchResult {

    private Integer total;//总记录数
    private Float useTime;//耗时
    private List<Map<String,Object>> result = new ArrayList<>();//结果集

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Float getUseTime() {
        return useTime;
    }

    public void setUseTime(Float useTime) {
        this.useTime = useTime;
    }

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }
}
