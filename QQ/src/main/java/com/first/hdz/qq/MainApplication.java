package com.first.hdz.qq;

import android.app.Application;

import com.first.hdz.qq.utils.CrashHandler;
import com.first.hdz.qq.utils.FileUtils;

/**
 * created by hdz
 * on 2018/9/10
 */
public class MainApplication extends Application {


    private CrashHandler mCrashHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mCrashHandler = CrashHandler.getInstance("QQ_Crash.txt");
            mCrashHandler.init(getApplicationContext());
            FileUtils.init(getApplicationContext(), FileUtils.LOCAL_EXTERNAL); //外部存储
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {

    }
}
