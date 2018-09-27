package com.first.hdz.qq.bean;

import java.io.Serializable;

/**
 * created by hdz
 * on 2018/9/25
 */
public class BaseJson<T> implements Serializable {

    private static final int SUCCESS = 0;

    private String msg;
    private T data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean IsCodeValid() {
        return this.code == SUCCESS;
    }

}
