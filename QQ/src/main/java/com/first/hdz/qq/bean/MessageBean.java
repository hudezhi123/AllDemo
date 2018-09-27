package com.first.hdz.qq.bean;

/**
 * created by hdz
 * on 2018/9/18
 */
public class MessageBean {
    private String title;
    private String content;
    private String time;
    private String speaker;
    private int imgResId;

    public MessageBean() {
    }

    public MessageBean(String title, String speaker, String content, String time, int imgResId) {
        this.title = title;
        this.speaker = speaker;
        this.content = content;
        this.time = time;
        this.imgResId = imgResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
