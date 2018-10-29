package com.example.springbootredisrabbit.po;

public class Result<T> {
    private String msg;
    private String status;
    private T data;

    public Result(String msg, String status, T data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public Result() {
    }

    public Result<T> success(T data){
        this.msg = "操作成功";
        this.status = "200";
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
