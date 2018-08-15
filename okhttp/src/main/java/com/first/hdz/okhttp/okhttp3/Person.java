package com.first.hdz.okhttp.okhttp3;

/**
 * created by hdz
 * on 2018/8/8
 */
public class Person {
    private String username;
    private String sex;

    public Person() {
    }

    public Person(String username, String sex) {
        this.username = username;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
