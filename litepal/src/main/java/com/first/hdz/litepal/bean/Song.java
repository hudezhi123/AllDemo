package com.first.hdz.litepal.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * created by hdz
 * on 2018/12/17
 */
public class Song extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    @Column(nullable = false)
    private String duration;

    @Column(ignore = true)
    private long lastModified;

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
