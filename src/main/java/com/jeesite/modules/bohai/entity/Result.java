package com.jeesite.modules.bohai.entity;

import java.util.StringJoiner;

/**
 * 返回信息类
 * @author tangchao
 */

public class Result<T> {
    private ResultStatus status;
    private String content;
    private T data;

    public Result(ResultStatus status) {
        this.status = status;
        this.content = status.getContent();
    }

    public Result(ResultStatus status,String content) {
        this.status = status;
        this.content = content;
    }

    public Result(ResultStatus status, T data) {
        this.status = status;
        this.content = status.getContent();
        this.data = data;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Result.class.getSimpleName() + "[", "]")
                .add("status=" + status)
                .add("content='" + content + "'")
                .add("data=" + data)
                .toString();
    }
}
