package com.wangzy.httpmodel.gson.ext;

/**
 * Created by wangzy on 2018/3/27.
 */

public class Result<T> {

    public String message;
    public String code;
    public T data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
