package com.first.hdz.qq.bean;

import java.io.Serializable;

public class Protocol extends BaseData implements Serializable {

    public Protocol() {
    }

    public Protocol(int id, String name, int type, String url, String title, String author, String createTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
        this.title = title;
        this.author = author;
        this.createTime = createTime;
    }

    private int id;
    private String name;
    private int type;
    private String url;
    private String title;
    private String author;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
