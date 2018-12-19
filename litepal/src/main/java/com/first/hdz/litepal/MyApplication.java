package com.first.hdz.litepal;

import android.app.Application;

import org.litepal.LitePal;

/**
 * created by hdz
 * on 2018/12/17
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
