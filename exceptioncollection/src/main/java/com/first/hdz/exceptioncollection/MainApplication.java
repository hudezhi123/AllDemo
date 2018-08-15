package com.first.hdz.exceptioncollection;

import android.app.Application;

/**
 * created by hdz
 * on 2018/8/9
 */
public class MainApplication extends Application{

    private CrashHandler mCrash;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void init(){

    }
}
